package com.example.apple.travelban.module.discuss.topic;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.AccountModel;
import com.example.apple.travelban.model.TopicModel;
import com.example.apple.travelban.model.bean.Topic;
import com.jude.http.RequestListener;
import com.jude.http.RequestManager;
import com.kermit.exutils.utils.ExUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

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
        topic = new Topic();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

                // TODO: 15-9-16 提交topic
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
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
            }
        });
    }

    private void fetchLocation(Location location) {
        ExUtils.Toast(location.getLatitude() + "," + location.getLongitude());
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
                    ExUtils.Toast(s);
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
}
