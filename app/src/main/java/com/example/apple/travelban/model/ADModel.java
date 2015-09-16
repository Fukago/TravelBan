package com.example.apple.travelban.model;

import android.content.Context;

import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.bean.AdBean;
import com.example.apple.travelban.model.callback.ADCallBack;
import com.jude.beam.model.AbsModel;
import com.jude.http.RequestManager;

import java.util.HashMap;

/**
 * Created by apple on 15/8/24.
 */
public class ADModel extends AbsModel {
    public static final ADModel modle=new ADModel();
    public static ADModel getInstance(){
        return modle;
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
    }

    public void getTrainListFromServe(String url,ADCallBack<AdBean> callback){
        HashMap<String,String> header=new HashMap<>();
        header.put("apikey", API.PLACE_KEY.API_KEY);
        RequestManager.getInstance().get(url,header,callback,false);
    }
}
