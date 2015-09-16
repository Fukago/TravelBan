package com.example.apple.travelban.module.place;

import android.os.Bundle;
import android.util.Log;


import com.example.apple.travelban.model.PlaceDetailModel;
import com.example.apple.travelban.model.bean.PlaceDetailBean;
import com.example.apple.travelban.model.callback.DataCallback;
import com.example.apple.travelban.module.place.PlaceDetailActivity;
import com.jude.beam.nucleus.manager.Presenter;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by apple on 15/8/14.
 */
public class PlaceDetailPresenter extends Presenter<PlaceDetailActivity> {
    private String httpArg;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onCreateView(PlaceDetailActivity view) {
        super.onCreateView(view);
    }
    public void setHttpArg(String httpArg){
        this.httpArg=httpArg;
    }
    public  String converterToSpell(String httpArg){
        String pinyinName = "";
        Log.d("httpArg2",httpArg);
        char[] nameChar = httpArg.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
    public void onResponse(){
        PlaceDetailModel.getInstance().getTrainListFromServe("http://apis.baidu.com/apistore/attractions/spot?id="+httpArg+"&output=json", new DataCallback<PlaceDetailBean>() {
            @Override
            public void success(int error, PlaceDetailBean data) {
                Log.d("response","xx");
                PlaceDetailBean.PlaceResult list=data.getResult();
                if (error == 0){
                    if (getView() == null) {
                        return;
                    }
                    Log.d("response2",list.getName());
                    getView().setData(list);
                }
            }

            @Override
            public void failure(int error) {
                Log.d("failure","false");
                if (getView() == null) {
                    return;
                }
                getView().setData(null);
            }
        });
    }
}
