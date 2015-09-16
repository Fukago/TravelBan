package com.example.apple.travelban.model.callback;


import android.util.Log;

import com.example.apple.travelban.cofing.API;
import com.google.gson.Gson;
import com.jude.utils.JUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

/**
 * Created by zhuchenxi on 15/5/11.
 */
public abstract class DataCallback<T> extends LinkCallback {

    @Override
    public void onRequest() {
        super.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        Log.d("onSucess", "true");
        JSONObject jsonObject;
        String status = "";
        int error;
        T data = null;
        try {
            jsonObject = new JSONObject(s);
            status = jsonObject.getString("status");
            error = jsonObject.getInt("error");
            if (status.equals(API.PLACE_KEY.STATUS)) {
                Gson gson = new Gson();
                data = gson.fromJson(s, ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            JUtils.Log(e.getLocalizedMessage());
//            error("数据解析错误");
            return;
        }
//        result(status);
        if (error == 0) {
            success(error, data);
        } else if (error == -3) {
            failure(error);
        }
        super.onSuccess(s);

    }

    @Override
    public void onError(String s) {
        result("网络错误");
        error("网络错误");
        super.onError(s);
    }

    public void result(String status) {
    }

    public abstract void success(int error, T data);

    public abstract void failure(int error);

    public void error(String errorInfo) {
        JUtils.Toast(errorInfo);
    }

}
