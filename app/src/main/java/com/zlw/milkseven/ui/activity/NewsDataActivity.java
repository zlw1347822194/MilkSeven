package com.zlw.milkseven.ui.activity;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlw.milkseven.R;
import com.zlw.milkseven.bean.News;


public class NewsDataActivity extends AppCompatActivity {
    private WebView wv_detail;
    private Intent intent;
    private News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_data);
        findViews();
        setEvent();
    }

    private void setEvent() {
        intent = getIntent();
        news = intent.getExtras().getParcelable("news");
        wv_detail.loadUrl(news.getUrl());
        WebSettings setting = wv_detail.getSettings();
        setting.setJavaScriptEnabled(true);
        wv_detail.setWebViewClient(new WebViewClient());
        wv_detail.setWebChromeClient(new WebChromeClient());
    }

    private void findViews() {
        wv_detail = (WebView) findViewById(R.id.wv_detail);
    }
}
