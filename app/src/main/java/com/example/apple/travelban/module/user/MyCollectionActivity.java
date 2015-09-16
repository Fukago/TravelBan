package com.example.apple.travelban.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.UserDataBean;
import com.example.apple.travelban.module.place.PlaceDetailPresenter;
import com.example.apple.travelban.utils.ActivityCollector;
import com.example.apple.travelban.module.place.PlaceDetailActivity;
import com.jude.swipbackhelper.SwipeBackHelper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;


public class MyCollectionActivity extends AppCompatActivity {
    public List<String> hobby = new ArrayList<>();
    public String[] list;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private static final int UPDATE = 0;
    private RelativeLayout mEmpty;
    private RelativeLayout mProgress;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO 接收消息并且去更新UI线程上的控件内容
            if (msg.what == UPDATE) {
                if(msg.obj!=null) {
                    hobby.addAll((Collection<? extends String>) msg.obj);
                    Log.d("ai3", String.valueOf(hobby.size()));
                }
                initView();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ActivityCollector.addActivity(this);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeEdgePercent(0.2f)
                .setSwipeSensitivity(0.3f);
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
        mProgress = (RelativeLayout) findViewById(R.id.progress_layout);
        mEmpty = (RelativeLayout) findViewById(R.id.empty);
        setData();

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
    public void initView() {
        mProgress.setVisibility(View.GONE);
        if (!hobby.isEmpty()) {
            mEmpty.setVisibility(View.GONE);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CollectionAdapter(hobby);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void setData() {
        UserDataBean userInfo = BmobUser.getCurrentUser(MyCollectionActivity.this, UserDataBean.class);
        BmobQuery<UserDataBean> query = new BmobQuery<UserDataBean>();
        final List<String> hobby = new ArrayList<>();
        query.getObject(MyCollectionActivity.this, userInfo.getObjectId(), new GetListener<UserDataBean>() {

            @Override
            public void onSuccess(UserDataBean object) {
                // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = UPDATE;
                msg.obj = object.getHobby();
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });


    }

    public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
        public List<String> mList;


        public CollectionAdapter(List<String> list) {
            mList = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;
            private MaterialRippleLayout container;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.text);
                container = (MaterialRippleLayout) v.findViewById(R.id.container);
            }
        }

        @Override
        public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cityviewhodler, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CollectionAdapter.ViewHolder holder, int position) {
            ViewHolder viewHolder = holder;
            holder.mTextView.setText(mList.get(position));
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaceDetailPresenter presenter = new PlaceDetailPresenter();
                    String httpArg = presenter.converterToSpell(holder.mTextView.getText().toString());
                    Intent intent = new Intent(MyCollectionActivity.this, PlaceDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("httpArg", httpArg);
                    bundle.putString("tittle", holder.mTextView.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
