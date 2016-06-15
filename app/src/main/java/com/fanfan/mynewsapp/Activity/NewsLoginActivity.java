package com.fanfan.mynewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.utils.ContextUtil;
import com.fanfan.mynewsapp.utils.SPUtil;

public class NewsLoginActivity extends AppCompatActivity {
    private EditText mname;
    private EditText mpwd;
    private Button mbtn_log;
    private Button menroll;
    private EditText musername;
    private EditText muserpwd;
    private EditText muserpwd1;
    private Button mbtn_verify;
    private Button mbtn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_login);
        mname = (EditText) findViewById(R.id.et_name);
        mpwd = (EditText) findViewById(R.id.et_pwd);
        mbtn_log = (Button) findViewById(R.id.btn_log);
        menroll = (Button) findViewById(R.id.btn_enroll);
        mbtn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterDialog();
                Toast.makeText(NewsLoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        });
        menroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetupDialog();
            }
        });
    }

    //注册
    private void showSetupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.login_item, null);
        musername = (EditText) view.findViewById(R.id.user_name);
        muserpwd = (EditText) view.findViewById(R.id.user_pwd);
        muserpwd1 = (EditText) view.findViewById(R.id.user_pwd1);
        mbtn_verify = (Button) view.findViewById(R.id.btn_verify);
        mbtn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        mbtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mbtn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 校验文本框内容
                String username = musername.getText().toString().trim();
                String userpwd = muserpwd.getText().toString().trim();
                String userpwd1 = muserpwd1.getText().toString().trim();
                //非空判断
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userpwd) || TextUtils.isEmpty(userpwd1)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //相等判断
                if (!userpwd.equals(userpwd1)) {
                    Toast.makeText(getApplicationContext(), "密码不一致请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                String svaename = SPUtil.getString(getApplicationContext(), ContextUtil.USERNAME);
                if (username.equals(svaename)) {
                    Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存输入框的内容
                SPUtil.putString(getApplicationContext(), ContextUtil.USERNAME, username);
                SPUtil.putString(getApplicationContext(), ContextUtil.USER_PWD, userpwd);
                SPUtil.putString(getApplicationContext(), ContextUtil.USER_PWD, userpwd1);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //登陆
    private void showEnterDialog() {
        // 校验文本框内容
        String name = mname.getText().toString().trim();
        String pwd = mpwd.getText().toString().trim();
        //判断是否为空
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存账号 密码
        String savename = SPUtil.getString(getApplicationContext(), ContextUtil.USERNAME);
        String savepwd = SPUtil.getString(getApplicationContext(), ContextUtil.USER_PWD);
        //判断相等
        if (!name.equals(savename)) {
            Toast.makeText(getApplicationContext(), "账号错误，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(savepwd)) {
            Toast.makeText(getApplicationContext(), "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存输入框的内容
        SPUtil.putString(getApplicationContext(), ContextUtil.USERNAME, name);
        SPUtil.putString(getApplicationContext(), ContextUtil.USER_PWD, pwd);
        Intent intent = new Intent(NewsLoginActivity.this, UserCenterActivity.class);
        startActivity(intent);

    }
}
