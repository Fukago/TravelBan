package com.example.apple.travelban.model.callback;

import com.example.apple.travelban.cofing.API;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

/**
 * Created by apple on 15/8/24.
 */
public abstract class HotelCallback<T> extends LinkCallback {

    @Override
    public void onRequest() {
        super.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        JSONObject jsonObject;
        int error;
        T data = null;
        try {
            jsonObject = new JSONObject(s);
            error = jsonObject.getInt("showapi_res_code");
            if (error == API.HOTEL_KEY.SHOWAPI_RES_CODE) {
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
//        error("网络错误");
        super.onError(s);
    }

    public void result(String status) {
    }

    public abstract void success(int error, T data);

    public abstract void failure(int error);

//    public void error(String errorInfo) {
//        JUtils.Toast(errorInfo);
//    }

}
