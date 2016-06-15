package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fanfan.mynewsapp.R;

public class AlpshActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpsh);
        mImageView = (ImageView) findViewById(R.id.iv_view);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.alpsh);
        mImageView.startAnimation(mAnimation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AlpshActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}

