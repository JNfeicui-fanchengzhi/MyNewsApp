package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanfan.mynewsapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String IS_FIST_RUN = "isFistRun";
    private ViewPager mPager;
    int[] pics = {R.drawable.bd, R.drawable.small, R.drawable.welcome, R.drawable.wy};
    ImageView icon1, icon2, icon3, icon4;
    ImageView[] icons = {icon1, icon2, icon3, icon4};
    private ArrayList<View> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断是否为第一次打开应用，使用 SharedPreferences存储状态
        SharedPreferences preferences = getSharedPreferences("runconfig", MODE_PRIVATE);
        //定义标记，为true
        boolean isFistRun = preferences.getBoolean(IS_FIST_RUN, false);
        //如果不是第一次运行，则调到主界面
        if (!isFistRun) {
            //创建跳转意图,并开启
            Intent intent = new Intent(MainActivity.this, AlpshActivity.class);
            startActivity(intent);
            finish();
        }
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.vp_pic);
        icons[0] = (ImageView) findViewById(R.id.iv_icon1);
        icons[1] = (ImageView) findViewById(R.id.iv_icon2);
        icons[2] = (ImageView) findViewById(R.id.iv_icon3);
        icons[3] = (ImageView) findViewById(R.id.iv_icon4);
        icons[0].setImageResource(R.drawable.adware_style_selected);
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(pics[i]);
            mList.add(iv);
        }
        mPager.setAdapter(new MypagerAdapter(mList));
        mPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置pager在滑动到最后一页时跳转
        if (position == pics.length - 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, AlpshActivity.class);
                    startActivity(intent);
                }
            }, 2000);
        }
        for (int i = 0; i < icons.length; i++) {
            icons[i].setImageResource(R.drawable.adware_style_default);
        }
        icons[position].setImageResource(R.drawable.adware_style_selected);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 定义一个适配器用于加载图片到viewpager
     */
    private class MypagerAdapter extends PagerAdapter {
        private ArrayList<View> mList;

        public MypagerAdapter(ArrayList<View> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        // 初始化position 展现到界面上来
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position),0);
            return mList.get(position);
        }
        //当不可见时销毁position
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
    //官方提供的动画1
    private class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}