package com.example.apple.travelban.module.place;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.travelban.R;
import com.example.apple.travelban.app.BaseActivity;
import com.example.apple.travelban.model.bean.PlaceDetailBean;
import com.example.apple.travelban.model.bean.User;
import com.example.apple.travelban.module.discuss.TopicActivity;
import com.example.apple.travelban.utils.ActivityCollector;
import com.jude.beam.nucleus.factory.RequiresPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by apple on 15/8/15.
 */
@RequiresPresenter(PlaceDetailPresenter.class)
public class PlaceDetailActivity extends BaseActivity<PlaceDetailPresenter> implements View.OnClickListener {
    private TextView name;
    private TextView phone;
    private TextView url;
    private TextView description;
    private TextView price;
    private TextView openTime;
    private TextView atName;
    private TextView atDescription;
    private ImageView icPhone;
    private ImageView icUrl;
    private ImageView icAnli;
    private ImageView icShare;
    private ImageView icCollect;
    private RelativeLayout mProgress;
    private RelativeLayout mEmpty;
    private String dataName;
    private String dataPhone;
    private String dataUrl;
    private String dataDescription;
    private String dataPrice;
    private String dataOpernTime;
    private String dataAtName;
    private String dataAtDescription;
    private Boolean flag = true;
    private Button btnMoreTopic;

    private List<String> hobby = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palcedetail);
        ShareSDK.initSDK(this);
        ActivityCollector.addActivity(this);
        mProgress = $(R.id.progress_layout);
        mEmpty = $(R.id.empty);
        icPhone = $(R.id.ic_phone);
        icAnli = $(R.id.ic_anli);
        icUrl = $(R.id.ic_url);
        icShare = $(R.id.share);
        icCollect = $(R.id.collect);
        icShare.setOnClickListener(this);
        icCollect.setOnClickListener(this);
        name = $(R.id.name);
        description = $(R.id.Description);
        description.setOnClickListener(this);
        url = $(R.id.url);
        phone = $(R.id.phone);
        price = $(R.id.price);
        openTime = $(R.id.open);
        atName = $(R.id.AtName);
        atDescription = $(R.id.AtDescription);
        btnMoreTopic = $(R.id.button_moretopic);
        btnMoreTopic.setOnClickListener(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        getPresenter().setHttpArg(intent.getStringExtra("httpArg"));
        getPresenter().onResponse();
        setToolBar(true);
        getToolbar().setTitle(intent.getStringExtra("tittle"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_fanhui);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void setData(PlaceDetailBean.PlaceResult data) {
        if (data != null) {
            dataName = data.getName();
            Log.d("name", dataName);
            if (!data.getTelephone().isEmpty()) {
                dataPhone = data.getTelephone();
            } else
                dataPhone = "无";
            dataUrl = data.getUrl();
            dataDescription = data.getDescription();
            dataPrice = data.getTicket_info().getPrice();
            dataOpernTime = data.getTicket_info().getOpen_time();

            if (!(data.getTicket_info().getAttention() == null)) {
                dataAtName = data.getTicket_info().getAttention().get(0).getName();
            } else dataAtName = "没有详细信息";
            if (!(data.getTicket_info().getAttention() == null)) {
                dataAtDescription = data.getTicket_info().getAttention().get(0).getDescription();
            } else dataAtDescription = "没有详细信息";
        }
        initView();
    }

    private void initView() {
        mProgress.setVisibility(View.GONE);
        if (dataName != null) {
            mEmpty.setVisibility(View.GONE);
        }
        name.setText(dataName);
        description.setText("简介：\n          " + dataDescription);
        url.setText(dataUrl);
        phone.setText(dataPhone);
        price.setText("票价：\n         " + dataPrice);
        openTime.setText("开放时间：\n           " + dataOpernTime);
        atName.setText(dataAtName);
        atDescription.setText("             " + dataAtDescription);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Description: {
                if (flag) {
                    flag = false;
                    description.setEllipsize(null);
                    description.setSingleLine(flag);
                } else {
                    flag = true;
                    description.setEllipsize(TextUtils.TruncateAt.END);
                    description.setLines(8);
                }
                break;
            }
            case R.id.share: {
                showShare();
                break;
            }
            case R.id.collect: {
                User userInfo = BmobUser.getCurrentUser(PlaceDetailActivity.this, User.class);
                Log.d("id", userInfo.getObjectId());
                User user = new User();
                BmobQuery<User> query = new BmobQuery<User>();
                query.getObject(PlaceDetailActivity.this, userInfo.getObjectId(), new GetListener<User>() {

                    @Override
                    public void onSuccess(User object) {
                        if (object.getHobby() != null) {
                            hobby.addAll(object.getHobby());
                        }
                    }

                    @Override
                    public void onFailure(int code, String arg0) {
                    }

                });
                hobby.add(dataName);
                user.setHobby(hobby);
                Log.d("ai", String.valueOf(hobby.size()));
                user.setObjectId(userInfo.getObjectId());
                user.addAllUnique("hobby", hobby);
                user.update(PlaceDetailActivity.this, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        icCollect.setImageResource(R.drawable.ic_shoucang);
                        Toast.makeText(PlaceDetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(PlaceDetailActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.button_moretopic:

                Bundle bundle = new Bundle();
                bundle.putString("placeName", dataName);
                Intent intent = new Intent(PlaceDetailActivity.this, TopicActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            default:
                break;
        }

    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("亲～跟我一起来一次说走就走的旅行吧！");
        oks.setTitleUrl("http://sharesdk.cn");
        oks.setText("天哪噜，突然想去" + dataName + "了，怎么办？约不约？");
        oks.setImagePath("/sdcard/test.jpg");
        oks.setUrl("http://sharesdk.cn");
        oks.setComment("约不约");
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl("http://sharesdk.cn");
        oks.show(this);
    }

    public void ChangeBitmap(View view, int resource) {
        ImageView imageView = new ImageView(view.getContext());
        InputStream is = PlaceDetailActivity.this.getResources().openRawResource(resource);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap btp = BitmapFactory.decodeStream(is, null, options);
        imageView.setImageBitmap(btp);
    }



}

