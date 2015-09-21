package com.example.apple.travelban.module.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.apple.travelban.utils.ImageProvider;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicActivity extends AppCompatActivity implements
        TopicViewHolder.OnTopicItemViewClickListener,
        WriteTopicFragment.OnWriteTopicFragmentListener,
        WriteTopicFragment.OnActivityResultCallback,
        WriteCommentFragment.OnWriteCommentFragmentListenr{

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
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //写话题
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (isWriteFragment()) return false;

        switch (item.getItemId()){
            case R.id.menu_writeFragment:
                if (isCommentFragment()){
                    mFragment = WriteCommentFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("placeName", placeName);
                    bundle.putSerializable("topic", mTopic);
                    if (mFragment.getArguments() != null){
                        mFragment.getArguments().clear();
                        mFragment.getArguments().putAll(bundle);
                    }else {
                        mFragment.setArguments(bundle);
                    }
                    toAnotherFragmentWithTag(mFragment, WriteCommentFragment.TAG);
                }else {
                    mFragment = WriteTopicFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("placeName", placeName);
                    if (mFragment.getArguments() != null){
                        mFragment.getArguments().clear();
                        mFragment.getArguments().putAll(bundle);
                    }else {
                        mFragment.setArguments(bundle);
                    }
                    toAnotherFragmentWithTag(mFragment, WriteTopicFragment.TAG);
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isWriteFragment(){
        if (getSupportFragmentManager().findFragmentByTag(WriteCommentFragment.TAG) != null ||
                getSupportFragmentManager().findFragmentByTag(WriteTopicFragment.TAG) != null)
            return true;
        return false;
    }

    private void toAnotherFragmentWithTag(Fragment fragment, String TAG) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_topic, fragment, TAG)
                .addToBackStack(null)
                .commit();
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
        toAnotherFragment(TopicFragment.newInstance());
    }


    private boolean isCommentFragment(){
        if (getSupportFragmentManager().findFragmentByTag(CommentFragment.TAG) instanceof CommentFragment)
            return true;
        else return false;
    }

    private ImageProvider mImageProvider;
    @Override
    public void onActivityResultCallback(Object o) {
        mImageProvider  = (ImageProvider) o;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onWriteCommentFragmentListener() {
        toAnotherFragment(CommentFragment.newInstance());
    }

    private void toAnotherFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_topic, fragment)
                .addToBackStack(null)
                .commit();
    }

}
