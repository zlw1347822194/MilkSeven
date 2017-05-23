package com.zlw.milkseven.ui.adapter;
//import cn.sharesdk.tencent.qq.QQWebShareAdapter;（QQWeb登陆需要继承的类）
//import cn.sharesdk.tencent.qzone.QZoneWebShareAdapter;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;
//（QQ空间Web登陆需要继承的类）

        import cn.sharesdk.framework.TitleLayout;
        import cn.sharesdk.framework.authorize.AuthorizeAdapter;
        import com.mob.tools.utils.R;

public class MyAdapter extends AuthorizeAdapter {
    public void onCreate() {
        System.out.println("> ShareSDKUIShell created!");
//获取标题栏控件
        TitleLayout llTitle = getTitleLayout();
//标题栏的文字修改
        int resID= R.getStringRes(getActivity(), "second_title");//这个字段定义在strings.xml文件里面
        llTitle.getTvTitle().setText(resID);
    }

    public void onDestroy() {
        System.out.println("> ShareSDKUIShell will be destroyed.");
    }
}