package com.zlw.milkseven.common;

/**
 * Created by S01 on 2017/5/6.
 */

public class Constant {
    /** 是否第一次运行 **/
    public static final String IS_FIRST_RUN = "isFirstRun";
    /** 是否登录 */
    public static final String IS_LOGIN = "isLogin";
    /** 用户头像地址 **/
    public static final String USER_PHOTO = "user_photo";
    /** 用户头昵称 **/
    public static final String USER_NAME = "user_name";
    /** 用户头密码 **/
    public static final String USER_PWD = "user_pwd";
    /** 用户登录方式 **/
    public static final String LOGINTYPE = "login_type";

    /** 数据库名称 **/
    public static final String DB_NAME = "MilkSeven";


    public static final String PAGE_SIZE="15";

    public static final int LOGIN_TYPE_NORMAL = 0X001;
    public static final int LOGIN_TYPE_THIRD = 0X002;


    public static final int COLLECTION_TYPE_NEWS = 0X001;
    public static final int COLLECTION_TYPE_JOKE = 0X002;
    public static final int COLLECTION_TYPE_PIC = 0X003;
}
