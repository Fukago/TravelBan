package com.example.apple.travelban.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.User;
import com.example.apple.travelban.module.user.LogInActivity;

import cn.bmob.v3.BmobUser;

public class WelcomeActivity extends AppCompatActivity {

    private final int SPLASH_DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User userInfo = BmobUser.getCurrentUser(WelcomeActivity.this, User.class);
                if (userInfo != null) {
                    Intent intent = new Intent(WelcomeActivity.this, TravelBanActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("username",userInfo.getUsername());
                    intent.putExtras(bundle);
                    WelcomeActivity.this.startActivity(intent);
                    WelcomeActivity.this.finish();
                } else {
                    WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
                    WelcomeActivity.this.finish();
                }

            }
        }, SPLASH_DELAY_TIME);
    }
}