package com.zlw.milkseven.ui.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.bean.CollectionBean;
import com.zlw.milkseven.bean.JokeBean.ResultBean.Joke;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.common.Constant;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by S01 on 2017/5/6.
 */

public class JokeAdapter extends BaseAdapter {
    private List<Joke> data;
    private viewHolder holder;
    public JokeAdapter(List<Joke>data){
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke, null);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.tv_joke.setText(data.get(position).getContent());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(parent.getContext())
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
                                collectionBean.save(parent.getContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(parent.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Toast.makeText(parent.getContext(),"收藏失败",Toast.LENGTH_SHORT).show();
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
        return convertView;
    }



    class viewHolder{
        TextView tv_joke;

        public viewHolder(View view) {
            this.tv_joke = (TextView) view.findViewById(R.id.tv_joke);
        }
    }
    //下拉刷新
    public void  setNewData(List<Joke> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<Joke> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
