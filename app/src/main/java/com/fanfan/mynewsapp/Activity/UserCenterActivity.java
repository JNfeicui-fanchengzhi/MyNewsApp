package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.utils.ContextUtil;
import com.fanfan.mynewsapp.utils.SPUtil;

public class UserCenterActivity extends AppCompatActivity {
    private TextView mUsername;
    private TextView mUser_point;
    private Button mBtn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
    }
    private void initView() {
        mUsername = (TextView) findViewById(R.id.tv_username);
        mUser_point = (TextView) findViewById(R.id.tv_user_point);
        mBtn_exit = (Button) findViewById(R.id.btn_exit);
        mUsername.setText(SPUtil.getString(getApplicationContext(), ContextUtil.USERNAME));
        mUser_point.setText("0");
        mBtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCenterActivity.this,NewsLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
