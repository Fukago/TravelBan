package com.example.apple.travelban.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Kermit on 15-8-11.
 * e-mail : wk19951231@163.com
 */
public class Comment extends BmobObject {

    public static final String TAG = "Comment";

    private User user;
    private String commentContent;
    private Topic mTopic;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Topic getTopic() {
        return mTopic;
    }

    public void setTopic(Topic topic) {
        this.mTopic = topic;
    }

    public static String getTAG() {

        return TAG;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
