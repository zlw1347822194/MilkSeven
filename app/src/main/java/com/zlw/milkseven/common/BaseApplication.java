package com.zlw.milkseven.common;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.utils.ToastUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by S01 on 2017/5/2.
 */

public class BaseApplication extends Application {
    private static final String TAG = "MilkSeven";
    private static BaseApplication instance = null;


    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = (BaseApplication) getApplicationContext();
//        initLooger();
        initOkhttpUtils();
        initShareSDK();
        initBmobSDK();
        initData();




    }

    private void initData() {
        //TODO 初始化数据
        //TODO 登录状态
        //TODO 已经登录的话自动完成登录，（重新请求个人信息数据，并获取sessionid，保存在cookie中，）
        if(PreferencesManager.getInstance(getApplicationContext()).get(Constant.IS_LOGIN,false)){
            String userPhoto = PreferencesManager.getInstance(getApplicationContext()).get(Constant.USER_PHOTO);
            String userName = PreferencesManager.getInstance(getApplicationContext()).get(Constant.USER_NAME);
            String userPwd = PreferencesManager.getInstance(getApplicationContext()).get(Constant.USER_PWD);
            BmobUser.BmobThirdUserAuth authInfo = (BmobUser.BmobThirdUserAuth) PreferencesManager.getInstance(getApplicationContext()).get(BmobUser.BmobThirdUserAuth.class);
            int loginType = PreferencesManager.getInstance(getApplicationContext()).get(Constant.LOGINTYPE,0);
            switch (loginType){
                case Constant.LOGIN_TYPE_NORMAL:
                    loginByUser(userName,userPwd);
                    break;
                case Constant.LOGIN_TYPE_THIRD:
                    loginByThird(authInfo);
                    break;
                default:
//                    ToastUtils.shortToast(getApplicationContext(),getString(R.string.auto_login_failed));
                    break;
            }
        }
    }

    private void loginByThird(BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(getApplicationContext(), authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
//                ToastUtils.shortToast(getApplicationContext(), getString(R.string.auto_login_third_failed) + msg);
            }
        });
    }

    private void loginByUser(String userName, final String userPwd) {
        //使用BmobSDK提供的登录功能
        Account user = new Account();
        user.setUsername(userName);
        user.setPassword(userPwd);
        user.login(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(getApplicationContext(), s);
            }
        });
    }

    private void initBmobSDK() {

        Bmob.initialize(this, "577c0324f3dd067f56476132f321b73f");
    }

    private void initShareSDK() {
        ShareSDK.initSDK(this,"1dbe6615f259e");

    }

    private void initOkhttpUtils() {
        //允许使用cookie
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("HTTP"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static synchronized BaseApplication getInstance(){
        return instance;
    }

    /**
     * 初始化Looger配置
     */
//    private void initLooger() {
//        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)        // default LogLevel.FULL
//                .methodOffset(2)                // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
//    }


//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base); MultiDex.install(this);
//    }
}
