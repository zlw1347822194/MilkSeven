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
import com.zlw.milkseven.bean.PicBean;
import com.zlw.milkseven.common.BaseApplication;
import com.zlw.milkseven.common.Constant;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by S01 on 2017/5/8.
 */

public class PicAdapter extends BaseAdapter {
    private List<PicBean> data;
    private viewHolder holder;
    public PicAdapter(List<PicBean>data){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, null);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(data.get(position).getPicUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_pic);
        holder.tv_pic.setText(data.get(position).getTitle());

        return convertView;
    }



    class viewHolder{
        ImageView img_pic;
        TextView tv_pic;

        public viewHolder(View view) {
            img_pic = (ImageView) view.findViewById(R.id.img_pic);
            tv_pic = (TextView) view.findViewById(R.id.tv_pic);
        }
    }
    //下拉刷新
    public void  setNewData(List<PicBean> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    public void setMoreData(List<PicBean> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
