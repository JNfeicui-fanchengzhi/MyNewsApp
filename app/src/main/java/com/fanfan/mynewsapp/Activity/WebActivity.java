package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fanfan.mynewsapp.R;

public class WebActivity extends AppCompatActivity {
    private WebView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        mView= (WebView) findViewById(R.id.webview);
        WebSettings settings=mView.getSettings();
        //缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        mView.loadUrl(url);
    }
}
