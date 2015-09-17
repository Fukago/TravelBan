package com.example.apple.travelban.module.discuss.comment;

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
import com.example.apple.travelban.model.CommentModel;
import com.example.apple.travelban.model.bean.Comment;
import com.example.apple.travelban.model.bean.Topic;
import com.kermit.exutils.utils.ExUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Kermit on 15-9-17.
 * e-mail : wk19951231@163.com
 */
public class WriteCommentFragment extends BaseFragment {

    private static final String TAG = "WriteCommentFragment";

    private static WriteCommentFragment writeCommentFragment;

    public static WriteCommentFragment newInstance(){
        if (writeCommentFragment == null){
            writeCommentFragment = new WriteCommentFragment();
        }
        return writeCommentFragment;
    }

    private Button btnSend;
    private EditText contentEd;
    private String mContent;
    private Topic mTopic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writecomment, container, false);
        btnSend = (Button) view.findViewById(R.id.button_comment_send);
        contentEd = (EditText) view.findViewById(R.id.edit_writecomment_content);
        mTopic = (Topic) getArguments().getSerializable("topic");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = contentEd.getText().toString();
                if (TextUtils.isEmpty(mContent))
                    return;
                CommentModel.getInstance().publishComment(
                        AccountModel.getInstance().getCurrentUser(),
                        mTopic,
                        mContent,
                        new SaveListener() {
                            @Override
                            public void onSuccess() {
                                ExUtils.Toast("success");
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        }
                );
            }
        });
        return view;
    }
}
