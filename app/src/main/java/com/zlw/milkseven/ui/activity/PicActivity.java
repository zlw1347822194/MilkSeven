package com.zlw.milkseven.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.PicBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicActivity extends AppCompatActivity {
    @BindView(R.id.wv_pic)
    WebView wvPic;
    private Intent intent;
    private PicBean picBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);
        setEvent();
    }

    private void setEvent() {
        intent = getIntent();
        picBean = intent.getExtras().getParcelable("pic");
        wvPic.loadUrl(picBean.getUrl());
        WebSettings setting = wvPic.getSettings();
        setting.setJavaScriptEnabled(true);
        wvPic.setWebViewClient(new WebViewClient());
        wvPic.setWebChromeClient(new WebChromeClient());
    }


}
