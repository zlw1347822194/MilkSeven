package com.zlw.milkseven.ui.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.bean.CollectionBean;
import com.zlw.milkseven.bean.News;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.common.Constant;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/28.
 */

public class NewsAdapter extends BaseAdapter {
    private viewHolder holder;
    private List<News> data;

    public NewsAdapter(List<News> data) {
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
        if (convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,null);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(data.get(position).getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_news);

        holder.tv_title.setText(data.get(position).getTitle());


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
                                if(account != null) {
                                    CollectionBean collectionBean = new CollectionBean();
                                    collectionBean.setPicUrl(data.get(position).getThumbnail_pic_s());
                                    collectionBean.setuId(account.getObjectId());
                                    collectionBean.setUrl(data.get(position).getUrl());
                                    collectionBean.setTitle(data.get(position).getTitle());
                                    collectionBean.setType(Constant.COLLECTION_TYPE_NEWS);
                                    collectionBean.save(parent.getContext(), new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(parent.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Toast.makeText(parent.getContext(), "收藏失败", Toast.LENGTH_SHORT).show();
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
        ImageView img_news;
        TextView tv_title;

        public viewHolder(View convertView) {
            img_news = (ImageView) convertView.findViewById(R.id.img_news);
            tv_title  = (TextView) convertView.findViewById(R.id.tv_title);

        }
    }
    //    下拉刷新
    public void  setNewData(List<News> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<News> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
