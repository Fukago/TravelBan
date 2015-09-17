package com.example.apple.travelban.module.discuss.topic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseFragment;
import com.example.apple.travelban.model.AccountModel;
import com.example.apple.travelban.model.TopicModel;
import com.kermit.exutils.utils.ExUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class WriteTopicFragment extends BaseFragment{

    private static final String TAG = "WriteTopicFragment";


    private static WriteTopicFragment writeTopicFragment;


    public static WriteTopicFragment newInstance(){
        if (writeTopicFragment == null){
            writeTopicFragment = new WriteTopicFragment();
        }
        return writeTopicFragment;
    }

    public interface OnWriteTopicFragmentListener{
        void writeTopicFragmentCallback();
    }

    private OnWriteTopicFragmentListener mListener;


    private MaterialEditText mTitleEd;
    private EditText mContentEd;
    private Button mButton;

    private String mTitle, mContent, placeName;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnWriteTopicFragmentListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        placeName = bundle.getString("placeName");

        ExUtils.Toast(placeName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writetopic, container, false);
        mTitleEd = (MaterialEditText) view.findViewById(R.id.materialdditText_writetopic_title);
        mContentEd = (EditText) view.findViewById(R.id.edit_writetopic_content);
        mButton = (Button) view.findViewById(R.id.button_topic_send);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = mTitleEd.getText().toString();
                mContent = mContentEd.getText().toString();

                if (TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mContent) || TextUtils.isEmpty(placeName))
                    return;

                // TODO: 15-9-16 提交topic
                TopicModel.getInstance().publishTopic(
                        AccountModel.getInstance().getCurrentUser(),
                        placeName,
                        mContent,
                        mTitle,
                        new SaveListener() {
                            @Override
                            public void onSuccess() {
                                ExUtils.Toast("success");
                                mListener.writeTopicFragmentCallback();
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
