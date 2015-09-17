package com.example.apple.travelban.module.discuss.comment;

import android.content.Context;
import android.view.ViewGroup;

import com.example.apple.travelban.model.bean.Comment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Kermit on 15-9-17.
 * e-mail : wk19951231@163.com
 */
public class CommentAdatper extends RecyclerArrayAdapter<Comment> {

    public CommentAdatper(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommentViewHolder(viewGroup);
    }

}
