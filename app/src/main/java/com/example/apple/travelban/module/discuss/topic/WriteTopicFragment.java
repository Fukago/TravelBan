package com.example.apple.travelban.module.discuss.topic;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseFragment;
import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.AccountModel;
import com.example.apple.travelban.model.RemoteFileModel;
import com.example.apple.travelban.model.TopicModel;
import com.example.apple.travelban.model.bean.Topic;
import com.example.apple.travelban.utils.ImageProvider;
import com.example.apple.travelban.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.http.RequestListener;
import com.jude.http.RequestManager;
import com.kermit.exutils.utils.ExUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Kermit on 15-9-16.
 * e-mail : wk19951231@163.com
 */
public class WriteTopicFragment extends BaseFragment{

    public static final String TAG = "WriteTopicFragment";

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


    public interface OnActivityResultCallback{
        void onActivityResultCallback(Object o);
    }
    private OnActivityResultCallback mCallback;


    private String provider;
    private LocationManager locationManager;
    private Location location = null;

    private LocationListener mLocationListener =  new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            fetchLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


    private MaterialEditText mTitleEd;
    private EditText mContentEd;
    private Button mButton;
    private Topic topic;

    private String mTitle, mContent, placeName;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnWriteTopicFragmentListener) activity;
        mCallback = (OnActivityResultCallback) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        placeName = bundle.getString("placeName");
        mImageProvider = new ImageProvider(getActivity());
        mCallback.onActivityResultCallback(mImageProvider);
        pics = new ArrayList<>();

        ExUtils.Toast(placeName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writetopic, container, false);

        mTitleEd = (MaterialEditText) view.findViewById(R.id.materialdditText_writetopic_title);
        mContentEd = (EditText) view.findViewById(R.id.edit_writetopic_content);
        mButton = (Button) view.findViewById(R.id.button_topic_send);
        mImage = (ImageView) view.findViewById(R.id.button_writetopic_insert_img);
        topic = new Topic();

        return view;
    }


    private boolean isAdd;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isAdd = true;
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd){
                    addImage();
                    isAdd = false;
                }else{
                    removeImg();
                    isAdd = true;
                }
            }
        });


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);
        }

        if (providerList.contains(LocationManager.NETWORK_PROVIDER) && location == null){
            provider = LocationManager.NETWORK_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);
        }else{
            ExUtils.Toast("No location provider to use");
            return;
        }

        fetchLocation(location);

        locationManager.requestLocationUpdates(provider, 5000, 1, mLocationListener);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = mTitleEd.getText().toString();
                mContent = mContentEd.getText().toString();

                if (TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mContent) || TextUtils.isEmpty(placeName))
                    return;
                showProgress("正在发送");
                if(bmobFile != null) {
                    RemoteFileModel.getInstance().upload(bmobFile, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            img = bmobFile.getFileUrl(getActivity());
                            pics.add(img);
                            topic.setPic(pics);
                            ExUtils.Toast("上传文件成功");

                            TopicModel.getInstance().publishTopic(
                                    AccountModel.getInstance().getCurrentUser(),
                                    topic,
                                    placeName,
                                    mContent,
                                    mTitle,
                                    new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            ExUtils.Toast("success");
                                            mListener.writeTopicFragmentCallback();
                                            dismissProgress();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            dismissProgress();
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ExUtils.Toast("上传文件失败");
                            dismissProgress();
                        }
                    });
                }else{
                    TopicModel.getInstance().publishTopic(
                            AccountModel.getInstance().getCurrentUser(),
                            topic,
                            placeName,
                            mContent,
                            mTitle,
                            new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    ExUtils.Toast("success");
                                    mListener.writeTopicFragmentCallback();
                                    dismissProgress();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    dismissProgress();
                                }
                            });
                }
            }
        });
    }

    private void fetchLocation(Location location) {
        if (location != null){
            StringBuilder builder = new StringBuilder();
            builder.append(API.GEOCODING).append("&location=").
                    append(location.getLatitude()).append(",").
                    append(location.getLongitude()).append("&output=json").append("&pois=1");

            HashMap<String, String> header = new HashMap<>();
            header.put("apikey", API.CITY_KEY.API_KEY);
            RequestManager.getInstance().get(builder.toString(), new RequestListener() {
                @Override
                public void onRequest() {
                }

                @Override
                public void onSuccess(String s) {
                    JSONObject jsonObject = null;
                    JSONObject result = null;
                    try {
                        jsonObject = new JSONObject(s);
                        result = jsonObject.getJSONObject("result");
                        topic.setLocation(result.getString("formatted_address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String s) {

                }
            });
        }

    }

    private MaterialDialog dialog;
    public void showProgress(String title){
        dialog = new MaterialDialog.Builder(getActivity())
                .title(title)
                .content("请稍候")
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    public void dismissProgress(){
        if (dialog != null){
            dialog.dismiss();
        }
    }


    public void addImage(){
        new MaterialDialog.Builder(getActivity())
                .title("请选择图片来源")
                .items(new String[]{"拍照", "相册"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        preImage(i);
                    }
                })
                .show();
    }

    private String img = "";
    private ImageProvider mImageProvider;
    private List<String> pics;
    private ImageView mImage;
    private BmobFile bmobFile;
    public void preImage(int style){
        ImageProvider.OnImageSelectListener onImageSelectListener = new ImageProvider.OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                showProgress("请稍后");
            }

            @Override
            public void onImageLoaded(Uri uri) {
                dismissProgress();
                if (uri != null) {
                    ImageUtils.SaveBitmap(ImageUtils.readBitmapFromFile(uri.getPath(), 800, 400), uri.getPath());
                    bmobFile = new BmobFile(new File(uri.getPath()));
                    mImage.setImageURI(uri);
                }
            }

            @Override
            public void onError() {
                ExUtils.Toast("上传文件失败");
            }
        };
        switch (style){
            case 0:
                mImageProvider.getImageFromCamera(onImageSelectListener);
                break;
            case 1:
                mImageProvider.getImageFromAlbum(onImageSelectListener);
                break;
        }
    }

    private void removeImg(){
        mImage.setImageResource(R.drawable.insert_img);
    }

    @Override
    public void onStop() {
        super.onStop();
        bmobFile = null;
    }
}

