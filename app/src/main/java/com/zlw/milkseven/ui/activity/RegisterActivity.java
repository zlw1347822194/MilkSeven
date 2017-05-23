package com.zlw.milkseven.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zlw.milkseven.R;
import com.zlw.milkseven.common.CommonKeyId;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText registe_et_name,registe_et_pwd;
    private Button btn_registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //这里的AppLication ID 写上自己创建项目得到的那个AppLication ID
        Bmob.initialize(this, CommonKeyId.BMOB_ID);
        findViews();
    }

    private void findViews() {
        registe_et_name = (EditText) findViewById(R.id.registe_et_name);
        registe_et_pwd = (EditText) findViewById(R.id.registe_et_pwd);
        btn_registe = (Button) findViewById(R.id.btn_registe);
    }

    public void onRegister(View view){
        switch (view.getId()){
            case R.id.btn_registe:
//             Judge();
                MlikRegister();
                break;
        }
    }


//    private void Judge(){
//        String RegisterName = registe_et_name.getText().toString();
//        String Registerpwd = registe_et_pwd.getText().toString();
//
//        final Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                //执行
//                startActivity(it);
//            }
//        };
//        if (RegisterName.equals("") || Registerpwd.equals("")){
//            Toast.makeText(RegisterActivity.this,"账户密码不能为空",Toast.LENGTH_SHORT).show();
//        }else if (RegisterName.length()<6 ||Registerpwd.length()<6){
//            Toast.makeText(RegisterActivity.this,"账户或密码不能小于6位数",Toast.LENGTH_SHORT).show();
//        }else {
//            MlikRegister();
//            timer.schedule(task,800*1);//0.8秒后跳转到登录页面
//            finish();
//        }
//    }

    private void MlikRegister() {
        final String RegisterName = registe_et_name.getText().toString();
        final String Registerpwd = registe_et_pwd.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(RegisterName);
        user.setPassword(Registerpwd);
        if (RegisterName.equals("") || Registerpwd.equals("")){
            Toast.makeText(RegisterActivity.this,"账户密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (RegisterName.length()<6 ||Registerpwd.length()<6){
            Toast.makeText(RegisterActivity.this,"账户或密码不能小于6位数",Toast.LENGTH_SHORT).show();
        }else {

            //注意：不能用save方法进行注册
            user.signUp(RegisterActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                    final Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            //执行
                            startActivity(it);
                        }
                    };
                    timer.schedule(task,800*1);//0.8秒后跳转到登录页面
                    finish();
                }
                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    Toast.makeText(RegisterActivity.this,"账户名已存在",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



}
