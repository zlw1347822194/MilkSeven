package com.zlw.milkseven.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by S01 on 2017/5/8.
 */

public class PicBean implements Parcelable {
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public PicBean() {
    }

    public PicBean(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ctime);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.picUrl);
        dest.writeString(this.url);
    }

    protected PicBean(Parcel in) {
        this.ctime = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.picUrl = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<PicBean> CREATOR = new Parcelable.Creator<PicBean>() {
        @Override
        public PicBean createFromParcel(Parcel source) {
            return new PicBean(source);
        }

        @Override
        public PicBean[] newArray(int size) {
            return new PicBean[size];
        }
    };
}
