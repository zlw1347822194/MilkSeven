package com.zlw.milkseven.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.PicBean;
import com.zlw.milkseven.common.CommonKeyId;
import com.zlw.milkseven.common.SeverConfig;
import com.zlw.milkseven.ui.activity.PicActivity;
import com.zlw.milkseven.ui.adapter.PicAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by S01 on 2017/5/2.
 */

public class PicFragment extends Fragment {
    public static final int TYPE_REFRESH = 0X01;
    public static final int TYPE_LOADMORE = 0X02;
    @BindView(R.id.prf_listView)
    PullToRefreshListView prfListView;
    Unbinder unbinder;
    @BindView(R.id.titleText)
    TextView titleText;

    private View rootView;
    private static int page = 1;
    private List<PicBean> data = new ArrayList<>();
    private PicAdapter picAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listeview, null);
        unbinder = ButterKnife.bind(this, rootView);
        titleText.setText("图片");


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
        prfListView.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化适配器
        picAdapter = new PicAdapter(data);
        //绑定适配器
        prfListView.setAdapter(picAdapter);
        //添加监听事件
        prfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pic", data.get(position - 1));
                intent.putExtras(bundle);
                PicFragment.this.startActivity(intent);
            }
        });
        prfListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新,先清空数据，在加载新数据,更新UI
                page = 1;
                getAsyncData(1, TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多：加载下一页数据,请求page++,新数据添加在后面
                getAsyncData(page++, TYPE_LOADMORE);
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
                .url(SeverConfig.TIANXING_MEINVPIC_URL)
                .addParams("key", CommonKeyId.TXINGSHUJV_MEINVTUPIAN_KEY)
                .addParams("num", "10")
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        prfListView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prfListView.onRefreshComplete();
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("newslist");
                        //解析数据
                        switch (type) {
                            case TYPE_REFRESH:
                                picAdapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(), PicBean.class));
                                break;
                            case TYPE_LOADMORE:
                                picAdapter.setMoreData(JSONArray.parseArray(jsonArray.toJSONString(), PicBean.class));
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
