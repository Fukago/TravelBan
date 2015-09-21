package com.example.apple.travelban.module.discuss.topic;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.model.bean.Topic;
import com.example.apple.travelban.model.bean.User;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.http.RequestManager;
import com.jude.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class TopicViewHolder extends BaseViewHolder<Topic> {

    private User mUser;

    private SimpleDraweeView face;

    private List<String> pics;

    private TextView name;
    private TextView title;
    private TextView content;
    private TextView time;
    private TextView commentNum;
    private TextView goodNum;
    private TextView badNum;
    private TextView location;
    private SimpleDraweeView img;


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
        location = $(R.id.id_item_topic_locationss);
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
        face.setImageURI(Uri.parse(mUser.getFace()));
        commentNum.setText(data.getCommentNum() + "");
        goodNum.setText(data.getGood() + "");
        badNum.setText(data.getBad() + "");
        if (!TextUtils.isEmpty(data.getLocation())) {
            location.setText(data.getLocation());
        }
        pics = data.getPic();

        if (img != null){
            img.setVisibility(View.GONE);
        }
        if (pics != null && !TextUtils.isEmpty(pics.get(0))) {
            if (img == null) {
                ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewstub_img);
                View view = viewStub.inflate();
                img = (SimpleDraweeView) view.findViewById(R.id.topic_img);
            }
            img.setVisibility(View.VISIBLE);
            img.setImageURI(Uri.parse(pics.get(0)));
        }
    }
}
