package com.example.apple.travelban.model.callback;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

/**
 * Created by apple on 15/8/24.
 */
public abstract class ADCallBack<T>extends LinkCallback {

    @Override
    public void onRequest() {
        super.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        Log.d("onSucess", "true");
        JSONObject jsonObject;
        boolean ret;
        T data = null;
        try {
            jsonObject = new JSONObject(s);
            ret = jsonObject.getBoolean("ret");
            if (ret==true) {
                Log.d("ret","true");
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
        if (ret==true) {
            success(ret, data);
        } else if (ret==false) {
            failure(ret);
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

    public abstract void success(boolean ret, T data);

    public abstract void failure(boolean ret);

//    public void error(String errorInfo) {
//        Utils.Toast(errorInfo);
//    }

}

