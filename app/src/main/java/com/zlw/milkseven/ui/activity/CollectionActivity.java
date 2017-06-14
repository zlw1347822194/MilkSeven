package com.zlw.milkseven.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.bean.CollectionBean;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.ui.adapter.CollectionAdapter;
import com.zlw.milkseven.ui.adapter.JokeAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends AppCompatActivity {

    @BindView(R.id.prf_listView)
    PullToRefreshListView prfListView;
    private View loadFailed;
    //声明并初始化数据
    private List<CollectionBean> data = new ArrayList<>();
    //声明适配器
    private CollectionAdapter colldap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initViews();
        BmobQuerys();
    }

    private void initViews() {
        //设置列表刷新加载
        prfListView.setMode(PullToRefreshBase.Mode.BOTH);
        colldap = new CollectionAdapter(data);
        //绑定适配器
        prfListView.setAdapter(colldap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuerys();
    }

    private void BmobQuerys() {

        BmobQuery<CollectionBean> query = new BmobQuery<CollectionBean>();
//查询playerName叫“比目”的数据
        Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(), Account.class);
        if(account == null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {
            query.addWhereEqualTo("uId", account.getObjectId());
            query.setLimit(100);
//执行查询方法
            query.findObjects(this, new FindListener<CollectionBean>() {
                @Override
                public void onSuccess(List<CollectionBean> list) {
                    // TODO Auto-generated method stub
                    if(prfListView != null){
                        if(prfListView.isRefreshing()){
                            prfListView.setRefreshing(false);
                        }
                    }
                    colldap.setNewData(list);
                }

                @Override
                public void onError(int code, String msg) {
                    // TODO Auto-generated method stub
                    Toast.makeText(CollectionActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}
