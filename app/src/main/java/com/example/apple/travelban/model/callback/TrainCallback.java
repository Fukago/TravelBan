package com.example.apple.travelban.model.callback;

import android.util.Log;

import com.example.apple.travelban.cofing.API;
import com.example.apple.travelban.model.bean.TrainBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/8/20.
 */
public abstract class TrainCallback<T> extends LinkCallback{

    @Override
    public void onRequest() {
        super.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        super.onSuccess(s);
        JSONObject jsonObject;
        String ret = "";
        TrainBean data=null;
        try {
            jsonObject = new JSONObject(s);
            ret = jsonObject.getString(API.KEY.RET);


            // Log.d("result", (String) d.get(1));
            // Log.d("dato",dato);
            Log.d("ret", ret);
            if (ret=="true") {
                Log.d("result","true");
                //data= (TrainList) JSON.parseArray(jsonObject.getString(API.KEY.TRAINLIST),TrainList.class);
                Gson gson=new Gson();
                data=gson.fromJson(s,TrainBean.class);
                //data= (T) JSON.parseObject(s, TrainBean.class);
                Log.d("type", "x");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        if (ret == "true") {
            success(ret, data);
        }
        else if(ret =="false"){
            failure(ret);
        }

    }
    @Override
    public void onError(String s) {
        super.onError(s);
    }
    public abstract void success(String ret,TrainBean data);
    public abstract void failure(String ret);
}