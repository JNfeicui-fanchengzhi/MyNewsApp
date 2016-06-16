package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.db.NewsDBManager;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "xbsn";
    private ImageButton mibtn_shoucang;
    private WebView mView;

    private String mLink;
    private String mTitle;
    private String mType;
    private String mIcon;
    private String mStamp;
    private String mNid;
    private String mSummary;
    private String number;
    private PopupWindow mPopupWindow;
    OnekeyShare oks = new OnekeyShare();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        Log.d(TAG, "onCreate: " + number);
        mLink = intent.getStringExtra("link");
        Log.d(TAG, "onCreate: " + mLink);
        mTitle = intent.getStringExtra("title");
        mType = intent.getStringExtra("type");
        mIcon = intent.getStringExtra("icon");
        mStamp = intent.getStringExtra("stamp");
        mNid = intent.getStringExtra("nid");
        mSummary = intent.getStringExtra("summary");
        mView = (WebView) findViewById(R.id.webview);
        mibtn_shoucang = (ImageButton) findViewById(R.id.ibtn_shoucang);
        mibtn_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null & mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else if (mPopupWindow != null) {
                    mPopupWindow.showAsDropDown(mibtn_shoucang, 0, 12);
                }
            }
        });
        initView();
        WebSettings settings = mView.getSettings();
        //缩放
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        mView.loadUrl(mLink);
    }

    private void initView() {
        View popView = getLayoutInflater().inflate(R.layout.item_btn, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView collect = (TextView) popView.findViewById(R.id.tv_collect);
        TextView share = (TextView) popView.findViewById(R.id.tv_share);
        TextView cancel = (TextView) popView.findViewById(R.id.tv_cancel);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (number.equals("1")) {
            switch (v.getId()) {
                case R.id.tv_collect:
                    NewsDBManager manager = new NewsDBManager(getApplicationContext());
                    if (manager.saveNews(mSummary, mIcon, mStamp, mTitle, mNid, mLink, mType)) {
                        Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "已经收藏过了", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.tv_share:
                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                    oks.setTitle(getString(R.string.ssdk_wechat));
                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                    oks.setTitleUrl("http://sharesdk.cn");
                    // text是分享文本，所有平台都需要这个字段
                    oks.setText("我是分享文本");
                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                    // url仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl("http://sharesdk.cn");
                    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                    oks.setComment("我是测试评论文本");
                    // site是分享此内容的网站名称，仅在QQ空间使用
                    oks.setSite(getString(R.string.app_name));
                    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                    oks.setSiteUrl("http://sharesdk.cn");
                    // 启动分享GUI
                    oks.show(this);
                    break;
            }
        } else if (number.equals("2")) {
            switch (v.getId()) {
                case R.id.tv_collect:
                    Toast.makeText(getApplicationContext(), "已经收藏过了，不能重复收藏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_share:
                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                    oks.setTitle(getString(R.string.ssdk_wechat));
                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                    oks.setTitleUrl("http://sharesdk.cn");
                    // text是分享文本，所有平台都需要这个字段
                    oks.setText("我是分享文本");
                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                    // url仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl("http://sharesdk.cn");
                    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                    oks.setComment("我是测试评论文本");
                    // site是分享此内容的网站名称，仅在QQ空间使用
                    oks.setSite(getString(R.string.app_name));
                    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                    oks.setSiteUrl("http://sharesdk.cn");
                    // 启动分享GUI
                    oks.show(this);
                    break;
                case R.id.tv_cancel:
                    NewsDBManager dbManager = new NewsDBManager(getApplicationContext());
                    dbManager.deleteNews(mNid);
                    if (!mNid.isEmpty()) {

                        Intent intent = new Intent(getApplicationContext(), CollectActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "取消成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
