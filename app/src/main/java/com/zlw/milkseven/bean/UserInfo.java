package com.zlw.milkseven.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 2017/4/14.
 */

public class UserInfo implements Parcelable  {

    String userID ;
    String icon;
    String token ;
    String nickname ;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.icon);
        dest.writeString(this.token);
        dest.writeString(this.nickname);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.userID = in.readString();
        this.icon = in.readString();
        this.token = in.readString();
        this.nickname = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
