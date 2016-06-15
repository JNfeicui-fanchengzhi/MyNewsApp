package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fanfan.mynewsapp.Adapter.NewAdapter;
import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.bean.NewBean;
import com.fanfan.mynewsapp.utils.BitmapUtils;
import com.fanfan.mynewsapp.utils.HttpUtil;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "hxashdsh";
    private ImageButton mbtn1;
    private ImageButton mbtn2;
    private SlidingMenu mSlidingMenu;
    private RequestQueue mQueue;
    private ImageLoader mLoader;
    private NewAdapter mAdapter;
    private Gson mGson;
    private List<NewBean.DataBean> mList;
    private ListView mListView;
    private Button mlogin;
    private static final String path = "http://118.244.212.82:9092/" +
            "newsClient/news_list?ver=1&subid=1&dir=1&nid=5&stamp=20140321&cnt=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        mQueue = Volley.newRequestQueue(getApplicationContext());
        mLoader = new ImageLoader(mQueue, new BitmapUtils());
        mAdapter = new NewAdapter(this, mLoader);
        mListView.setAdapter(mAdapter);
        new AsyncTask<String, Void, NewBean>() {
            @Override
            protected NewBean doInBackground(String... params) {
                String info = HttpUtil.getInfo(path);
                mList = new ArrayList<>();
                mGson = new Gson();
                NewBean bean = mGson.fromJson(info, NewBean.class);
                return bean;
            }

            @Override
            protected void onPostExecute(NewBean newBean) {
                mAdapter.addData(newBean.getData());
                mAdapter.notifyDataSetChanged();
                super.onPostExecute(newBean);
            }
        }.execute();
    }

    private void initView() {
        mbtn1 = (ImageButton) findViewById(R.id.ibtn1);
        mbtn2 = (ImageButton) findViewById(R.id.ibtn2);
        mListView = (ListView) findViewById(R.id.lv_list);
        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsActivity.this, WebActivity.class);
                intent.putExtra("url", mAdapter.getItem(position).getLink());
                startActivity(intent);
            }
        });
        //侧拉菜单初始化
        mSlidingMenu = new SlidingMenu(this);
        //侧拉菜单的触摸响应范围
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧拉菜单的偏移量
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_width);
        //布置侧拉菜单界面
        mSlidingMenu.setMenu(R.layout.left_list);
        mSlidingMenu.setSecondaryMenu(R.layout.right_list);
        mlogin = (Button) findViewById(R.id.btn_login);
        mlogin.setOnClickListener(this);
    }

    //一些控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn1:
                mSlidingMenu.toggle();
                break;
            case R.id.ibtn2:
                mSlidingMenu.toggle();
                mSlidingMenu.showSecondaryMenu();
                break;
            case R.id.btn_login:
                Intent intent = new Intent(NewsActivity.this, NewsLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}