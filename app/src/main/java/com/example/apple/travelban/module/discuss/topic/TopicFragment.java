package com.example.apple.travelban.module.discuss.topic;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseFragment;
import com.example.apple.travelban.model.TopicModel;
import com.example.apple.travelban.model.bean.Topic;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicFragment extends BaseFragment implements
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = "TopicFragment";


    private static TopicFragment topicFragment;


    public static TopicFragment newInstance(){
        if (topicFragment == null){
            topicFragment = new TopicFragment();
        }
        return topicFragment;
    }


    private EasyRecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private String placeName;
    private int page;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeName = getArguments().getString("placeName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);

        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_fragment_topic);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TopicAdapter(getActivity());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setRefreshListener(this);
        mAdapter.setMore(R.layout.view_moreprogress, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchData();
        page=1;
    }


    // TODO: 15-9-16 mock
    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onLoadMore() {
        fetchData();
        page++;
    }


    // TODO: 15-9-16 静态数据
    private void fetchData(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                TopicModel.getInstance().queryAllTopic(page, placeName, new FindListener<Topic>() {
                    @Override
                    public void onSuccess(List<Topic> list) {
                        mAdapter.addAll(list);
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });

            }
        }, 1000);
    }

}
