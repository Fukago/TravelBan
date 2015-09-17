package com.example.apple.travelban.module.discuss.topic;

import android.content.Context;
import android.view.ViewGroup;

import com.example.apple.travelban.model.bean.Topic;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicAdapter extends RecyclerArrayAdapter<Topic> {

    public TopicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TopicViewHolder(viewGroup);
    }

}
