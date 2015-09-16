package com.example.apple.travelban.module.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.UserDataBean;
import com.example.apple.travelban.utils.ActivityCollector;
import com.example.apple.travelban.module.main.TravelBanActivity;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    public Button mButton;
    public MaterialEditText username;
    public MaterialEditText password;
    private CheckBox show;
    public ProgressDialog p_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeEdgePercent(0.2f)
                .setSwipeSensitivity(0.3f);
        Bmob.initialize(this, "dbff096320f269ffc3a981bcdcb8adde");
        iniView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    private void iniView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_fanhui);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        username = (MaterialEditText) findViewById(R.id.username);
        password = (MaterialEditText) findViewById(R.id.password);
        mButton = (Button) findViewById(R.id.regisiter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_dialog = ProgressDialog
                        .show(RegisterActivity.this,
                                null,
                                "正在为您注册...",
                                true);
                if (username.getText().toString().length() <= 12 && username.getText().toString().length() >= 6
                        && password.getText().toString().length() <= 12 && password.getText().toString().length() >= 6) {
                    UserDataBean bu = new UserDataBean();
                    bu.setUsername(username.getText().toString());
                    bu.setPassword(password.getText().toString());
                    bu.signUp(RegisterActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            p_dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, TravelBanActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username", username.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            p_dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "账号和密码都应该由6-12个字符或数字组成哦～", Toast.LENGTH_SHORT).show();
                    p_dialog.dismiss();
                }
            }
        });


        show = (CheckBox) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

}
