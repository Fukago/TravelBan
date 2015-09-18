package com.example.apple.travelban.model;

import android.content.Context;

import com.example.apple.travelban.app.App;
import com.example.apple.travelban.model.bean.Comment;
import com.example.apple.travelban.model.bean.Topic;
import com.example.apple.travelban.model.bean.User;
import com.kermit.exutils.utils.ExUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Kermit on 15-9-17.
 * e-mail : wk19951231@163.com
 */
public class CommentModel {

    private static final String TAG = "TopicModel";

    private static CommentModel commentModel;

    private Context mContext;

    private CommentModel(Context context){
        mContext = context;
    }

    public static CommentModel getInstance(){
        if (commentModel == null){
            commentModel = new CommentModel(App.getIntance());
        }
        return commentModel;
    }

    public void publishComment(User user, Topic topic, String commentContent, SaveListener listener){
        Comment comment = new Comment();
        comment.setCommentContent(commentContent);
        comment.setTopic(topic);
        comment.setUser(user);
        comment.setTime(ExUtils.getTime());
        comment.save(mContext, listener);
    }

    public void queryComment(int page, Topic topic, FindListener<Comment> listener){
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("mTopic", new BmobPointer(topic));
        query.include("user,topic.author");
        query.setLimit(5);
        query.setSkip((page-1)*5);
        query.findObjects(mContext, listener);
    }
}
