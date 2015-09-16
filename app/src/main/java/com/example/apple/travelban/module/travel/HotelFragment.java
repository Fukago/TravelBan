package com.example.apple.travelban.module.travel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.travelban.R;
import com.example.apple.travelban.module.ad.ADWebActivity;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.io.InputStream;

/**
 * Created by apple on 15/8/19.
 */
public class HotelFragment extends Fragment {
    private ObservableScrollView mScrollView;
    private Button mButton;
    private TextView mTextView;
    private ImageView lisikaerdu;
    private TextView textLisikaerdun;
    private ImageView payue;
    private TextView textPayue;
    public static HotelFragment newInstance() {
        return new HotelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        mButton= (Button) view.findViewById(R.id.button);
        mTextView= (TextView) view.findViewById(R.id.text);
        mTextView.setText("五星级酒店扫盲：");
        textPayue= (TextView) view.findViewById(R.id.text_payue);
        textPayue.setText("简介：\n        凯悦旗下拥有五星级的凯悦（Hyatt Regency）、超五星级的君悦(Grand Hyatt)和奢华品牌柏悦（Park Hyatt）。\n" +
                "       其中Park Hyatt为档次最高的极至尊贵精品型酒店，全都位居最繁华城市的一流房产的位置，如芝加哥、北京，上海，洛杉矶、东京、首尔、巴黎和米兰。\n" +
                "       而Grand Hyatt以则体现了气势华贵（Grand）这个字眼：位于文化氛围浓厚的地点，吸引着休闲和公务旅行的客人，同时也是各种大型国际会议和宴会的理想场所。其以周全的会议和宴会设施及专业而富有创意的服务而闻名.");
        textLisikaerdun= (TextView) view.findViewById(R.id.text_lisikaerdun);
        textLisikaerdun.setText("简介：\n      酒店位于北京国贸CBD外围的华贸中心，紧邻新光天地、万达广场、新世界百货等，交通便利。\n       酒店客房延续英伦庄园风格，色调混合以奶油黄和苹果绿。品质一流的古典式家具完美的融合在最先进的科技设备中，包括配备iPod接口的高科技平面电视和无线上网等。房间的艺术风格同时吸取了西方和东方元素精华，和谐统一。北京丽思卡尔顿酒店还设有行政楼层，配有独立的电梯通道。舒适的行政楼层酒廊提供全天多次精美茶点服务。掌握多种语言的礼宾处为客人提供打印登机牌服务预定服务，以及解答与中国相关的商业常规等服务。");
        lisikaerdu= (ImageView) view.findViewById(R.id.image_lisikaerdun);
        payue= (ImageView) view.findViewById(R.id.image_payue);
        ChangeBitmap(view,R.drawable.ic_payue);
        ChangeBitmap(view,R.drawable.ic_lisikaerdun);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelFragment.this.getActivity(), ADWebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url","http://www.booking.com/index.zh.html?aid=383420;label=baidu-index-Hotels-73Cp7e3e2UY1wW0A_2uTcQ-6164420407&utm_source=baidu&utm_medium=cpc&utm_term=73Cp7e3e2UY1wW0A_2uTcQ&utm_campaign=Chinese_ZH-XX_Phrase_Core");
                intent.putExtras(bundle);
                HotelFragment.this.startActivity(intent);
            }
        });
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
    public void ChangeBitmap(View view,int resource){
        ImageView imageView = new ImageView(view.getContext());
        InputStream is = getActivity().getResources().openRawResource(resource);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap btp = BitmapFactory.decodeStream(is, null, options);
        imageView.setImageBitmap(btp);
    }

}
