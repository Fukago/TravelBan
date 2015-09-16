package com.example.apple.travelban.module.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.UserDataBean;
import com.example.apple.travelban.utils.ActivityCollector;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class UserDataActivity extends AppCompatActivity {
    public ListView mListView;
    public ScrollView mScrollView;
    public TextView mTextView;
    public String mDescription;
    public TextView textView;
    public LinearLayout mLayout;
    public List<String> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeEdgePercent(0.2f)
                .setSwipeSensitivity(0.3f);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_user_data);
        setData();
        initView();
    }

    private void setData() {
        UserDataBean userInfo = BmobUser.getCurrentUser(UserDataActivity.this, UserDataBean.class);
        if (userInfo != null) {
            list.clear();
            list.add("我的账号          " + userInfo.getUsername());
            list.add("账号ID              " + userInfo.getObjectId());
            list.add("我的昵称          " + userInfo.getAccount());
            list.add("我的电话          " + userInfo.getPhoneNumber());
            list.add("我的地址          " + userInfo.getAddress());
            list.add("我的Email        " + userInfo.getUserEmail());
            mDescription = userInfo.getDescription();
        }

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
        mScrollView = (ScrollView) findViewById(R.id.datascroll);
        mTextView = (TextView) findViewById(R.id.datamessage);
        mLayout = (LinearLayout) findViewById(R.id.messageLayout);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDataActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
        if (mDescription != null)
            mTextView.setText(mDescription);
        else
            mTextView.setText("暂无");
        mListView = (ListView) findViewById(R.id.datalist);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                view = LayoutInflater.from(UserDataActivity.this.getApplicationContext()).inflate(R.layout.item_user_data, null);
                textView = (TextView) view.findViewById(R.id.datatext);
                textView.setText(list.get(position));
                return view;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UserDataActivity.this);
                LayoutInflater factory = LayoutInflater.from(UserDataActivity.this);
                final View DialogView = factory.inflate(R.layout.dialog_layout, null);
                final MaterialEditText mEditText = (MaterialEditText) DialogView.findViewById(R.id.materialEdit);
                final UserDataBean userInfo = BmobUser.getCurrentUser(UserDataActivity.this, UserDataBean.class);
                switch (position) {
                    case 2: {
                        mEditText.setHint("您的昵称");
                        mEditText.setFloatingLabelText("昵称");
                        mEditText.setMaxCharacters(10);
                        mEditText.setMinCharacters(1);
                        alert.setTitle("修改昵称");
                        alert.setView(DialogView);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                UserDataBean user = new UserDataBean();
                                user.setAccount(mEditText.getText().toString());
                                user.setObjectId(userInfo.getObjectId());
                                user.update(UserDataActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        // TODO Auto-generated method stub
                                        setData();
                                        initView();
                                        Toast.makeText(UserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(UserDataActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });

                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                        alert.show();
                        break;
                    }
                    case 3: {
                        mEditText.setHint("您的手机号码");
                        mEditText.setFloatingLabelText("手机号码");
                        mEditText.setMaxCharacters(11);
                        mEditText.setMinCharacters(11);
                        alert.setTitle("修改手机号码");
                        alert.setView(DialogView);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (mEditText.getText().toString().length() == 11) {
                                    UserDataBean user = new UserDataBean();
                                    user.setPhoneNumber(mEditText.getText().toString());
                                    user.setObjectId(userInfo.getObjectId());
                                    user.update(UserDataActivity.this, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            // TODO Auto-generated method stub
                                            setData();
                                            initView();
                                            Toast.makeText(UserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int code, String msg) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(UserDataActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                } else {
                                    Toast.makeText(UserDataActivity.this, "亲，电话号码有11位哦～", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                        alert.show();
                        break;
                    }
                    case 4: {
                        mEditText.setHint("您的地址");
                        mEditText.setFloatingLabelText("地址");
                        mEditText.setMinCharacters(1);
                        alert.setTitle("修改地址");
                        alert.setView(DialogView);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                UserDataBean user = new UserDataBean();
                                user.setAddress(mEditText.getText().toString());
                                user.setObjectId(userInfo.getObjectId());
                                user.update(UserDataActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        // TODO Auto-generated method stub
                                        setData();
                                        initView();
                                        Toast.makeText(UserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(UserDataActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });

                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                        alert.show();
                        break;
                    }
                    default:
                        mEditText.setHint("您的Email");
                        mEditText.setFloatingLabelText("Email");
                        mEditText.setMinCharacters(1);
                        alert.setTitle("修改Email");
                        alert.setView(DialogView);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                UserDataBean user = new UserDataBean();
                                user.setUserEmail(mEditText.getText().toString());
                                user.setObjectId(userInfo.getObjectId());
                                user.update(UserDataActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        // TODO Auto-generated method stub
                                        setData();
                                        initView();
                                        Toast.makeText(UserDataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(UserDataActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });

                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                        alert.show();
                        break;
                }
            }
        });
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

     void reSetData() {
        setData();
        initView();
    }
}
