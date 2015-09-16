package com.example.apple.travelban.model;

import android.content.Context;

import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.bean.PlaceBean;
import com.example.apple.travelban.model.callback.DataCallback;
import com.jude.beam.model.AbsModel;
import com.jude.http.RequestManager;

import java.util.HashMap;

/**
 * Created by apple on 15/8/16.
 */
public class PlaceModel extends AbsModel {
    public static final PlaceModel modle = new PlaceModel();

    public static PlaceModel getInstance() {
        return modle;
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
    }

    public void getTrainListFromServe(String url, DataCallback<PlaceBean> callback) {
        HashMap<String, String> header = new HashMap<>();
        header.put("apikey", API.CITY_KEY.API_KEY);
        RequestManager.getInstance().get(url, header, callback, false);
    }
}
