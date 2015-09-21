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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Kermit on 15-8-20.
 * e-mail : wk19951231@163.com
 */
public class TopicModel {

    private static final String TAG = "TopicModel";

    private static TopicModel topicModel;
    private Context mContext;

    private TopicModel(Context context){
        mContext = context;
    }

    public static TopicModel getInstance(){
        if (topicModel == null){
            topicModel = new TopicModel(App.getIntance());
        }
        return topicModel;
    }

    public void publishTopic(User user, Topic topic, String placeName, String content, String title, SaveListener listener){
        topic.setAuthor(user);
        topic.setPlaceName(placeName);
        topic.setContent(content);
        topic.setTitle(title);
        topic.setTime(ExUtils.getTime());

        topic.save(mContext, listener);
    }

    public void queryTopic(User user, FindListener<Topic> listener){
        BmobQuery<Topic> query = new BmobQuery<>();
        query.addWhereEqualTo("author", user);
        query.order("-updateAt");
        query.include("author");
        query.findObjects(mContext, listener);
    }

    public void deleteTopic(User user, Topic topic){
        topic.remove("author");
        topic.update(mContext, topic.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void queryAllTopic(int curPage, String placeName, FindListener<Topic> listener){
        BmobQuery<Topic> query = new BmobQuery<>();
        query.addWhereEqualTo("placeName", placeName);
        query.include("author");
        query.setLimit(3);
        query.setSkip((curPage - 1) * 3);
        query.order("-time");
        query.findObjects(mContext, listener);
    }


    public void updateCommentNum(Topic topic, UpdateListener listener, boolean isAdd){
        if (isAdd) {
            topic.setCommentNum(topic.getCommentNum() + 1);
        }else{
            topic.setCommentNum(topic.getCommentNum() - 1);
        }
        topic.update(mContext, topic.getObjectId(), listener);
    }

    public void updateGood(Topic topic, UpdateListener listener, boolean isAdd){
            if (isAdd) {
                topic.increment("love", 1);
            } else {
                topic.increment("love", -1);
            }
            topic.update(mContext, topic.getObjectId(), listener);
    }

    public void updateBad(Topic topic, UpdateListener listener, boolean isAdd){
            if (isAdd) {
                topic.increment("hate", 1);
            } else {
                topic.increment("hate", -1);
            }
            topic.update(mContext, topic.getObjectId(), listener);
    }

}
