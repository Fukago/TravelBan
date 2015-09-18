package com.example.apple.travelban.module.discuss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.Topic;
import com.example.apple.travelban.module.discuss.comment.CommentFragment;
import com.example.apple.travelban.module.discuss.comment.WriteCommentFragment;
import com.example.apple.travelban.module.discuss.topic.TopicFragment;
import com.example.apple.travelban.module.discuss.topic.TopicViewHolder;
import com.example.apple.travelban.module.discuss.topic.WriteTopicFragment;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicActivity extends AppCompatActivity implements
        TopicViewHolder.OnTopicItemViewClickListener,
        WriteTopicFragment.OnWriteTopicFragmentListener {

    private static final String TAG = "TopicActivity";

    private Toolbar mToolbar;
    private String placeName = "";
    private Fragment mFragment;
    private Topic mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        if (getIntent() != null){
            placeName = getIntent().getExtras().getString("placeName");
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_topic);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(placeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mFragment = TopicFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("placeName", placeName);
        mFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_topic, mFragment)
                .commit();

        TopicViewHolder.setOnTopicItemViewClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //写话题
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_writeFragment:
                if (isCommentFragment()){
                    mFragment = WriteCommentFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("placeName", placeName);
                    bundle.putSerializable("topic", mTopic);
                    mFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_topic, mFragment)
                            .addToBackStack(null)
                            .commit();
                }else {
                    mFragment = WriteTopicFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("placeName", placeName);
                    mFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_topic, mFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v, Topic topic) {
        mTopic = topic;
        mFragment = CommentFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("topic", topic);
        mFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_topic, mFragment, CommentFragment.TAG)
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle(placeName);
    }

    @Override
    public void writeTopicFragmentCallback() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_topic, TopicFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private boolean isCommentFragment(){
        if (getSupportFragmentManager().findFragmentByTag(CommentFragment.TAG) instanceof CommentFragment)
            return true;
        else return false;
    }
}
