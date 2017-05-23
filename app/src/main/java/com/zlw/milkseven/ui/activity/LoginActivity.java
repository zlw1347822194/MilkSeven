package com.zlw.milkseven.ui.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zlw.milkseven.MainActivity;
import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.Account;
import com.zlw.milkseven.bean.UserInfo;
import com.zlw.milkseven.common.BaseActivity;
import com.zlw.milkseven.common.CommonKeyId;
import com.zlw.milkseven.common.Constant;
import com.zlw.milkseven.common.PreferencesManager;
import com.zlw.milkseven.utils.LogUtils;
import com.zlw.milkseven.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.meipai.Meipai;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends BaseActivity {
    private Button btn_login,me_button;
    private TextView login_tv_register;
    private EditText login_et_name,login_et_pwd;
    private RoundedImageView rdimg_qq,rdimg_sinaweibo,rdimg_meipai;
    private Intent intent;
    private String loginName;
    private String logingPwd;
    private  BmobUser login;
//
//    @BindView(R.id.rdimg_qq)
//    RoundedImageView QQ;
//    @BindView(R.id.rdimg_sinaweibo)
//    RoundedImageView SinaWeiBo;
//    @BindView(R.id.rdimg_meipai)
//    RoundedImageView MeiPai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //这里的AppLication ID 写上自己创建项目得到的那个AppLication ID
        Bmob.initialize(this, CommonKeyId.BMOB_ID);
        setRightVisibility(View.GONE);
        setTitle("登录");
        findViews();
    }

    private void findViews() {
        btn_login = (Button) findViewById(R.id.btn_login);
        login_tv_register = (TextView) findViewById(R.id.login_tv_register);
        login_et_name = (EditText) findViewById(R.id.login_et_name);
        login_et_pwd = (EditText) findViewById(R.id.login_et_pwd);


        rdimg_qq = (RoundedImageView) findViewById(R.id.rdimg_qq);
        rdimg_sinaweibo = (RoundedImageView) findViewById(R.id.rdimg_sinaweibo);
        rdimg_meipai = (RoundedImageView) findViewById(R.id.rdimg_meipai);

    }

    public void onLogin(View view){
        switch (view.getId()){
            case R.id.btn_login:
                MilkLogin();
                break;
            case R.id.login_tv_register:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.rdimg_qq:
                LoginQQ();
                break;
            case R.id.rdimg_sinaweibo:
                LoginSina();
                break;
            case R.id.rdimg_meipai:
//                LoginMeiPai();
                break;
         
        }
    }
    private void LoginSina() {
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("weibo", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.showUser(null);//授权并获取用户信息
    }

    private void LoginQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("qq", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        qq.showUser(null);//授权并获取用户信息

    }
//
//    private void LoginMeiPai() {
//        Platform sinawebo = ShareSDK.getPlatform(Meipai.a);
//        Meipai.setPlatformActionListener(this);
//        //authorize与showUser单独调用一个即可
//        Meipai.authorize();//单独授权,OnComplete返回的hashmap是空的
//        //weibo.showUser(null);//授权并获取用户信息
//        //移除授权
//        //weibo.removeAccount(true);
//    }
public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo, final PlatformDb data) {
    BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {

        @Override
        public void onSuccess(JSONObject userAuth) {
            // TODO Auto-generated method stub
            LogUtils.i(authInfo.getSnsType() + "登陆成功返回:" + userAuth);
            Account user = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
            //更新登录的账户信息
            updateUserInfo(user, data, authInfo);
        }

        @Override
        public void onFailure(int code, String msg) {
            // TODO Auto-generated method stub
            ToastUtils.shortToast(LoginActivity.this, "第三方登录失败:" + msg);
        }
    });
}


    private void updateUserInfo(Account user, PlatformDb data, final BmobUser.BmobThirdUserAuth authInfo) {
        Account newUser = new Account();
        newUser.setPhoto(data.getUserIcon());
        newUser.setSex("男".equals(data.getUserGender()) ? true : false);
        newUser.setUsername(data.getUserName());
        Account bmobUser = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
        newUser.update(LoginActivity.this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginActivity.this, getString(R.string.update_userinfo_success));
                //保存登录信息到本地
                saveUserInfo(Constant.LOGIN_TYPE_THIRD, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginActivity.this, getString(R.string.update_userinfo_failed) + msg);
            }
        });
    }




    private void MilkLogin() {
         loginName = login_et_name.getText().toString();
          logingPwd = login_et_pwd.getText().toString();

        login = new BmobUser();
        login.setUsername(loginName);
        login.setPassword(logingPwd);

        if (loginName.length()<6||logingPwd.length()<6){
            Toast.makeText(LoginActivity.this,"账户不能小于6位数",Toast.LENGTH_SHORT).show();
        }else {
            login.login(this, new SaveListener() {
                @Override
                public void onSuccess() {
//                    TextView a= (TextView)
//                    getSupportFragmentManager().findFragmentById(R.id.me_use_name).getView().findViewById(R.id.login_et_name);
//                    a.setText(loginName);
                    saveUserInfo(Constant.LOGIN_TYPE_NORMAL, null);
                    intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onFailure(int code, String msg) {
                    if(loginName.isEmpty()||logingPwd.isEmpty()){
                        Toast.makeText(LoginActivity.this,"账户或密码不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        clearInput();
                        Toast.makeText(LoginActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }



    private void saveUserInfo(int loginType, BmobUser.BmobThirdUserAuth authInfo) {
        /*
         * TODO 把用户的登录信息保存到本地：sp\sqlite：（登录状态，登录类别，登录账户信息）
         * 注意:为了保证数据安全，一般对数据进行加密
         * 通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
         * 如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
         * */
        Account user = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
        PreferencesManager preferences = PreferencesManager.getInstance(LoginActivity.this);
        preferences.put(Constant.IS_LOGIN, true);
        preferences.put(Constant.LOGINTYPE, loginType);
        preferences.put(Constant.USER_NAME, user.getUsername());
        preferences.put(Constant.USER_PHOTO, user.getPhoto());
        preferences.put(Constant.USER_PWD, login_et_pwd.getText().toString());
        if(authInfo != null){
            preferences.put(authInfo);
        }
        LoginActivity.this.finish();
    }

    private void clearInput() {
        login_et_name.setText("");
        login_et_pwd.setText("");
    }
}
