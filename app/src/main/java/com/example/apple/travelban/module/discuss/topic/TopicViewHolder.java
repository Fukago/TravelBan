package com.example.apple.travelban.module.discuss.topic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.Topic;
import com.example.apple.travelban.model.bean.User;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicViewHolder extends BaseViewHolder<Topic> {

    private User mUser;

    private SimpleDraweeView face;

    private List<String> pic;

    private TextView name;
    private TextView title;
    private TextView content;
    private TextView time;
    private TextView commentNum;
    private TextView goodNum;
    private TextView badNum;

    public static OnTopicItemViewClickListener mListener;
    public interface OnTopicItemViewClickListener{
        void onClick(View v, Topic topic);
    }
    public static void setOnTopicItemViewClickListener(OnTopicItemViewClickListener listener){
        mListener = listener;
    }

    public TopicViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_topic);

        face = $(R.id.id_topic_face);
        name = $(R.id.id_topic_name);
        title = $(R.id.id_topic_title);
        content = $(R.id.id_topic_content);
        time = $(R.id.id_topic_time);
        commentNum = $(R.id.id_topic_numComment);
        goodNum = $(R.id.id_item_topic_good);
        badNum = $(R.id.id_item_topic_bad);
    }

    @Override
    public void setData(final Topic data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, data);
            }
        });
        mUser = data.getAuthor();
        title.setText(data.getTitle());
        content.setText(data.getContent());
        time.setText(data.getTime());
        name.setText(mUser.getUsername());
        // TODO: 15-9-17 头像
//        face.setImageURI(Uri.parse(mUser.getFace()));
        commentNum.setText(data.getCommentNum() + "");
        goodNum.setText(data.getGood() + "");
        badNum.setText(data.getBad() + "");
        pic = data.getPic();
    }
}
