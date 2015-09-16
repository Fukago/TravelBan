package com.example.apple.travelban.model;

import android.content.Context;

import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.bean.TrainBean;
import com.example.apple.travelban.model.callback.DataCallback;
import com.example.apple.travelban.model.callback.TrainCallback;
import com.jude.beam.model.AbsModel;
import com.jude.http.RequestManager;

import java.util.HashMap;

/**
 * Created by apple on 15/8/9.
 */
public class TrainModel extends AbsModel {
    private static final TrainModel model = new TrainModel();
    public static TrainModel getInstance(){
        return model;
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
    }

    public void getTrainListFromServe(String url,TrainCallback<TrainBean> callback){
        HashMap<String,String>header=new HashMap<>();
        header.put("apikey", API.TRAIN_KEY.API_KEY);
        RequestManager.getInstance().get(url,header,callback,false);
    }
}
