package com.zlw.milkseven.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zlw.milkseven.R;
import com.zlw.milkseven.common.BaseActivity;
import com.zlw.milkseven.common.Constant;
import com.zlw.milkseven.common.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.btn_loginout)
    Button btnLoginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        setRightVisibility(View.GONE);
        setTitle(getString(R.string.setting));
    }

    @OnClick({R.id.btn_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_loginout:
                loginout();
                break;
        }
    }

    private void loginout() {
        BmobUser.logOut(SettingsActivity.this);   //清除缓存用户对象
        PreferencesManager.getInstance(SettingsActivity.this).put(Constant.IS_LOGIN, false);
        SettingsActivity.this.finish();
    }
}
