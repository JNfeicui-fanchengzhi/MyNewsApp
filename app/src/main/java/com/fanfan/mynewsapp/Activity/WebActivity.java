package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.db.NewsDBManager;

public class WebActivity extends AppCompatActivity {
    private ImageButton mibtn_shoucang;
    private WebView mView;

    private String mLink;
    private String mTitle;
    private String mType;
    private String mIcon;
    private String mStamp;
    private String mNid;
    private String mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent=getIntent();
        mLink=intent.getStringExtra("link");
        mTitle=intent.getStringExtra("title");
        mType=intent.getStringExtra("type");
        mIcon=intent.getStringExtra("icon");
        mStamp=intent.getStringExtra("stamp");
        mNid=intent.getStringExtra("nid");
        mSummary=intent.getStringExtra("summary");
        mView= (WebView) findViewById(R.id.webview);
        initView();
        WebSettings settings=mView.getSettings();
        //缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        mView.loadUrl(mLink);
    }

    private void initView() {
        mibtn_shoucang= (ImageButton) findViewById(R.id.ibtn_shoucang);
        mibtn_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDBManager manager=new NewsDBManager(getApplicationContext());
                if (manager.saveNews(mSummary,mIcon,mStamp,mTitle,mNid,mLink,mType)){
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "已经收藏过了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
