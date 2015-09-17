package com.example.apple.travelban.module.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.User;
import com.example.apple.travelban.utils.ActivityCollector;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class WriteActivity extends AppCompatActivity {

    public MaterialEditText tittle;
    public MaterialEditText description;
    public Button mButton;
    public EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeEdgePercent(0.2f)
                .setSwipeSensitivity(0.3f);
        ActivityCollector.addActivity(this);
        initView();
    }

    private void initView() {

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
        tittle = (MaterialEditText) findViewById(R.id.tittle);
        tittle.setHint("标题");
        description= (MaterialEditText) findViewById(R.id.description);
        description.setHint("描述");
        mEditText= (EditText) findViewById(R.id.edit);
        mEditText.setHint("个人说明／签名／心情");
        mButton= (Button) findViewById(R.id.put);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User userInfo = BmobUser.getCurrentUser(WriteActivity.this, User.class);
                User user = new User();
                user.setDescription(mEditText.getText().toString());
                user.setObjectId(userInfo.getObjectId());
                user.update(WriteActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Toast.makeText(WriteActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(WriteActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_writeFragment) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
