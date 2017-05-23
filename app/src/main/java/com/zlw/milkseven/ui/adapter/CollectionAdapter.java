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
import com.zlw.milkseven.bean.JokeBean;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.common.Constant;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jayli on 2017/5/16 0016.
 */

public class CollectionAdapter extends BaseAdapter {
    private List<CollectionBean> data;
    private CollectionAdapter.viewHolder holder;
    public CollectionAdapter(List<CollectionBean>data){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, null);
            holder = new CollectionAdapter.viewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (CollectionAdapter.viewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(data.get(position).getPicUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.collection_item_img);

        holder.collection_item_tv.setText(data.get(position).getTitle());

        return convertView;
    }



    class viewHolder{
        TextView collection_item_tv;
        ImageView collection_item_img;
        public viewHolder(View view) {
           collection_item_tv = (TextView) view.findViewById(R.id.collection_item_tv);
            collection_item_img = (ImageView) view.findViewById(R.id.collection_item_img);
        }
    }
    //下拉刷新
    public void  setNewData(List<CollectionBean> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<CollectionBean> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
