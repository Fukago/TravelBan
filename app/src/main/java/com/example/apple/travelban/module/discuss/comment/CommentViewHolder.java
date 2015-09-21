package com.example.apple.travelban.module.discuss.comment;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.Comment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Kermit on 15-9-17.
 * e-mail : wk19951231@163.com
 */
public class CommentViewHolder extends BaseViewHolder<Comment> {


    private SimpleDraweeView face;
    private TextView name;
    private TextView time;
    private TextView content;
    private TextView location;

    public CommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_comment);
        face = $(R.id.id_comment_face);
        name = $(R.id.id_comment_username);
        time = $(R.id.id_comment_time);
        content = $(R.id.id_comment_comment);
    }

    @Override
    public void setData(Comment data) {
        super.setData(data);
        face.setImageURI(Uri.parse(data.getUser().getFace()));
        name.setText(data.getUser().getUsername());
        time.setText(data.getTime());
        content.setText(data.getCommentContent());
    }
}
