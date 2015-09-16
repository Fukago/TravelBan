package com.example.apple.travelban.module.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.UserDataBean;
import com.example.apple.travelban.utils.ActivityCollector;
import com.example.apple.travelban.module.main.TravelBanActivity;
import com.rengwuxian.materialedittext.MaterialEditText;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class LogInActivity extends AppCompatActivity {


    public Button logIn;
    public Button register;
    public MaterialEditText username;
    public MaterialEditText password;
    private CheckBox show;
    public ProgressDialog p_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Bmob.initialize(this, "dbff096320f269ffc3a981bcdcb8adde");
        ActivityCollector.addActivity(this);
        iniView();
    }

    private void iniView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        username = (MaterialEditText) findViewById(R.id.username);
        password = (MaterialEditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.regisiter);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        logIn = (Button) findViewById(R.id.login);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    p_dialog = ProgressDialog
                            .show(LogInActivity.this,
                                    null,
                                    "正在为您登录...",
                                    true);
                    UserDataBean user = new UserDataBean();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.login(LogInActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        p_dialog.dismiss();
                        Toast.makeText(LogInActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, TravelBanActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        p_dialog.dismiss();
                        Toast.makeText(LogInActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
