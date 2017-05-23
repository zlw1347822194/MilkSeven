package com.zlw.milkseven.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zlw.milkseven.R;
import com.zlw.milkseven.common.BaseFragment;
import com.zlw.milkseven.common.Constant;
import com.zlw.milkseven.common.PreferencesManager;
import com.zlw.milkseven.ui.activity.CollectionActivity;
import com.zlw.milkseven.ui.activity.LoginActivity;
import com.zlw.milkseven.ui.activity.MoreInfoActivity;
import com.zlw.milkseven.ui.activity.SettingsActivity;
import com.zlw.milkseven.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by S01 on 2017/5/2.
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.img_photo)
    RoundedImageView imgPhoto;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_uname)
    TextView tvUname;
    Unbinder unbinder;
    @BindView(R.id.img_like)
    ImageView imgLike;
    @BindView(R.id.img_ninght)
    ImageView imgNinght;
    @BindView(R.id.tab_setting)
    TableRow tabSetting;
    @BindView(R.id.layout_feedback)
    TableRow layoutFeedback;
    @BindView(R.id.tabr_we)
    TableRow tabrWe;
    @BindView(R.id.llyout_row)
    LinearLayout llyoutRow;
    private Intent intent;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, null);
        setInflateView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        if (PreferencesManager.getInstance(getActivity()).get(Constant.IS_LOGIN, false)) {
            imgPhoto.setVisibility(View.VISIBLE);
            tvUname.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            loadUserInfo();
        } else {
            imgPhoto.setVisibility(View.GONE);
            tvUname.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }

    }

    //已经登录的话重新获取用户信息
    private void loadUserInfo() {
        String userPhoto = PreferencesManager.getInstance(getActivity()).get(Constant.USER_PHOTO);
        String userName = PreferencesManager.getInstance(getActivity()).get(Constant.USER_NAME);
        tvUname.setText(userName);
        ImageLoader.getInstance().displayImageTarget(imgPhoto, userPhoto);
    }


    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.llyout_row,R.id.tabr_we, R.id.img_photo, R.id.btn_login, R.id.img_like, R.id.img_ninght, R.id.layout_feedback, R.id.tab_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llyout_row:
                //TODO 修改用户信息
                intent = new Intent(getActivity(), MoreInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.img_like:
                //TODO 收藏
                intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.img_ninght:
                //TODO 夜间模式

                break;
            case R.id.layout_feedback:
                //TODO 用户反馈
                break;
            case R.id.tab_setting:
                // TODO: 2017/5/12 0012 用户设置
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.tabr_we:
                showAppInfo();
                break;
        }
    }

    private void showAppInfo() {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity())
                .setTitle("关于我们")
                .setMessage("开发人:WinFred\n地址:https://github.com/Winfred1989/Six")
                .setPositiveButton("确定", null);
        builer.create().show();
    }


//    //http://blog.csdn.net/guolin_blog/article/details/8881711
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.button);
//        me_use_name = (TextView) getActivity().findViewById(R.id.me_use_name);
//        rimg_tou = (RoundedImageView) getActivity().findViewById(R.id.rimg_tou);
//         editTextName = (EditText) getActivity().findViewById(R.id.login_et_name);
//         editTextPwd = (EditText) getActivity().findViewById(R.id.login_et_pwd);
//        meButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginActivity loginActivity = (LoginActivity) getActivity();
//                if (editTextName){
//                me_use_name.setText("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                rimg_tou.setBackground(R.mipmap.ssdk_oks_classic_facebook);
//                getActivity().findViewById(R.id.login_et_name);
//                v.setVisibility(v.GONE);
//                intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//                }
//            }
//        });
//    }


}
