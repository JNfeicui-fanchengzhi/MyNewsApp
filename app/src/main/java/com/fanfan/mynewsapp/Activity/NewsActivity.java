package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fanfan.mynewsapp.Adapter.NewAdapter;
import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.bean.NewBean;
import com.fanfan.mynewsapp.utils.BitmapUtils;
import com.fanfan.mynewsapp.utils.HttpUtil;
import com.fanfan.mynewsapp.utils.OnRefreshListener;
import com.fanfan.mynewsapp.utils.RefreshListView;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsActivity extends AppCompatActivity implements OnRefreshListener, View.OnClickListener {
    public static final String TAG = "hxashdsh";
    private ImageButton mbtn1;
    private ImageButton mbtn2;
    private SlidingMenu mSlidingMenu;
    private RequestQueue mQueue;
    private ImageLoader mLoader;
    private NewAdapter mAdapter;
    private Gson mGson;
    private List<NewBean.DataBean> mList;
    private RefreshListView mListView;
    private Gson          gson;


    private Button mlogin;
    private ImageButton mqq;
    private ImageButton mweixin;
    private ImageButton mfriends;
    private ImageButton mweibo;
    private Button   mshare;
    private RelativeLayout mcollect;
    OnekeyShare oks = new OnekeyShare();
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
        mListView.setOnRefreshListener(this);
    }

    private void initView() {
        mbtn1 = (ImageButton) findViewById(R.id.ibtn1);
        mbtn2 = (ImageButton) findViewById(R.id.ibtn2);
        mListView = (RefreshListView) findViewById(R.id.lv_list);
        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsActivity.this, WebActivity.class);
                intent.putExtra("link", mAdapter.getItem(position).getLink());
                intent.putExtra("stamp",mAdapter.getItem(position).getStamp());
                intent.putExtra("icon",mAdapter.getItem(position).getIcon());
                intent.putExtra("nid",mAdapter.getItem(position).getNid());
                intent.putExtra("summary",mAdapter.getItem(position).getSummary());
                intent.putExtra("title",mAdapter.getItem(position).getTitle());
                intent.putExtra("type",mAdapter.getItem(position).getType());
                startActivity(intent);
            }
        });
        mListView.setOnRefreshListener(this);

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
        //收藏
        mcollect= (RelativeLayout) findViewById(R.id.collect);
        mcollect.setOnClickListener(this);
        mSlidingMenu.setSecondaryMenu(R.layout.right_list);
        //登陆
        mlogin = (Button) findViewById(R.id.btn_login);
        mlogin.setOnClickListener(this);
        //分享
        mshare= (Button) findViewById(R.id.tv_share);
        mshare.setOnClickListener(this);
    }

    //一些控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect:
                Intent intent=new Intent(NewsActivity.this,CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.ibtn1:
                mSlidingMenu.toggle();
                break;
            case R.id.ibtn2:
                mSlidingMenu.toggle();
                mSlidingMenu.showSecondaryMenu();
                break;
            case R.id.btn_login:
                Intent intent1 = new Intent(NewsActivity.this, NewsLoginActivity.class);
                startActivity(intent1);
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
    }

    //    实现listview的下拉刷新
    @Override
    public void onDownPullRefresh() {
        new AsyncTask<String, Void, NewBean>() {

            @Override
            protected NewBean doInBackground(String... params) {
                String info = HttpUtil.getInfo(path);
                mList=new ArrayList<>();
                gson=new Gson();
                NewBean bean = gson.fromJson(info, NewBean.class);
                return bean;
            }
            //            添加完数据后,隐藏头部view
            protected void onPostExecute(NewBean dataBean) {
                mAdapter.addData(dataBean.getData());
                mAdapter.notifyDataSetChanged();
                mListView.hideHeaderView();
            }
        }.execute();
    }
    //    实现listview的上拉加载
    @Override
    public void onLoadingMore() {
        new AsyncTask<String, Void, NewBean>() {

            @Override
            protected NewBean doInBackground(String... params) {
                String info = HttpUtil.getInfo(path);
                mList=new ArrayList<>();
                gson=new Gson();
                NewBean bean = gson.fromJson(info, NewBean.class);
                return bean;
            }
            //            添加完数据后,隐藏底部view
            protected void onPostExecute(NewBean dataBean) {
                mAdapter.addData(dataBean.getData());
                mAdapter.notifyDataSetChanged();
                mListView.hideFooterView();
            }
        }.execute();
    }
}