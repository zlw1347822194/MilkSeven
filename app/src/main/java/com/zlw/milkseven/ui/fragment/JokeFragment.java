package com.zlw.milkseven.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.bean.CollectionBean;
import com.zlw.milkseven.bean.JokeBean;
import com.zlw.milkseven.bean.JokeBean.ResultBean.Joke;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.common.CommonKeyId;
import com.zlw.milkseven.common.Constant;
import com.zlw.milkseven.common.SeverConfig;
import com.zlw.milkseven.ui.adapter.JokeAdapter;
import com.zlw.milkseven.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;

/**
 * Created by S01 on 2017/5/2.
 */

public class JokeFragment extends Fragment {
    public static final int TYPE_REFRESH = 0X01;
    public static final int TYPE_LOADMORE = 0X02;
    Unbinder unbinder;
    @BindView(R.id.prf_listView)
    PullToRefreshListView prflistView;
    @BindView(R.id.titleText)
    TextView titleText;
    private View rootView;
    private static int page = 1;
    private List<Joke> data = new ArrayList<>();
    private JokeAdapter jokeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listeview, null);
        unbinder = ButterKnife.bind(this, rootView);
        titleText.setText("笑话");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initView();
    }

    private void initView() {
        //设置列表刷新加载
        prflistView.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
        jokeAdapter = new JokeAdapter(data);
        //绑定适配器
        prflistView.setAdapter(jokeAdapter);
        //添加监听事件
        prflistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position - 1).getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        prflistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新,先清空数据，在加载新数据,更新UI
                page = 1;
                getAsyncData(page, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多：加载下一页数据,请求page++,新数据添加在后面
                getAsyncData(page++, TYPE_LOADMORE);
            }
        });


        prflistView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("收藏")
                        .setMessage("是否收藏？")
                        .setPositiveButton("收藏", new  DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(),Account.class);
                                if(account != null){
                                    CollectionBean collectionBean = new CollectionBean();
//                collectionBean.setPicUrl();
                                    collectionBean.setuId(account.getObjectId());
                                    collectionBean.setTitle(data.get(position).getContent());
                                    collectionBean.setType(Constant.COLLECTION_TYPE_JOKE);
                                    collectionBean.save(rootView.getContext(), new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(rootView.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Toast.makeText(rootView.getContext(),"收藏失败",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create()
                        .show();
                return false;
            }
        });
    }



    private void initData() {
        page = 1;
        getAsyncData(TYPE_REFRESH, page);
    }

    //获取异步请求
    private void getAsyncData(int page, final int type) {
        OkHttpUtils
                .get()
                .url(SeverConfig.JVHE_XIAOHUA_URL)
                .addParams("sort", "desc")
                .addParams("page", String.valueOf(page))
                .addParams("pagesize", Constant.PAGE_SIZE)
                .addParams("time", TimeUtils.getTime())
                .addParams("key", CommonKeyId.JVHE_JOKE_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        prflistView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prflistView.onRefreshComplete();
                        //解析数据
                        JokeBean jokeBean = JSON.parseObject(response, JokeBean.class);
                        switch (type) {
                            case TYPE_REFRESH:
                                jokeAdapter.setNewData(jokeBean.getResult().getData());
                                break;
                            case TYPE_LOADMORE:
                                jokeAdapter.setMoreData(jokeBean.getResult().getData());
                                break;
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
