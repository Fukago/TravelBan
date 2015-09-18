package com.example.apple.travelban.module.discuss.comment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseFragment;
import com.example.apple.travelban.model.CommentModel;
import com.example.apple.travelban.model.bean.Comment;
import com.example.apple.travelban.model.bean.Topic;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kermit.exutils.utils.ExUtils;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class CommentFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnLoadMoreListener{

    public static final String TAG = "CommentFragment";

    private static CommentFragment commentFragment;

    public static CommentFragment newInstance(){
        if (commentFragment == null){
            commentFragment = new CommentFragment();
        }
        return commentFragment;
    }


    private EasyRecyclerView mRecyclerView;
    private CommentAdatper mAdatper;
    private Topic mTopic;
    private int page;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_fragment_comment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setRefreshListener(this);
        mAdatper = new CommentAdatper(getActivity());
        mRecyclerView.setAdapterWithProgress(mAdatper);
        mAdatper.setMore(R.layout.view_moreprogress, this);
        mTopic = (Topic) getArguments().getSerializable("topic");

        ExUtils.Toast(TAG);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchData();
        addHeader();
        mAdatper.notifyDataSetChanged();
        page = 1;
    }


    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onLoadMore() {
        fetchData();
        page++;
    }

    public void fetchData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CommentModel.getInstance().queryComment(page, mTopic, new FindListener<Comment>() {
                    @Override
                    public void onSuccess(List<Comment> list) {
                        mAdatper.addAll(list);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        }, 1000);
    }

    private void addHeader(){
        mAdatper.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup viewGroup) {
                View view =  LayoutInflater.from(getActivity()).inflate(R.layout.item_comment_title, viewGroup, false);
                return view;
            }

            @Override
            public void onBindView(View view) {
                ((TextView)view.findViewById(R.id.id_comment_title)).setText(mTopic.getTitle());
                ((TextView)view.findViewById(R.id.id_comment_title_content)).setText(mTopic.getContent());
                ((TextView)view.findViewById(R.id.id_comment_title_time)).setText(mTopic.getTime());
                ((TextView)view.findViewById(R.id.id_comment_title_username)).setText(mTopic.getAuthor().getUsername());
                // TODO: 15-9-17 头像
//                ((SimpleDraweeView)view.findViewById(R.id.id_comment_title_face)).setImageURI(Uri.parse(mTopic.getAuthor().getFace()));

            }
        });
    }
}
