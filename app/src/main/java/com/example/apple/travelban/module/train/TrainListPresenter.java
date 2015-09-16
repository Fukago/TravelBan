package com.example.apple.travelban.module.train;

import android.os.Bundle;

import com.example.apple.travelban.model.TrainModel;
import com.example.apple.travelban.model.bean.TrainBean;
import com.example.apple.travelban.model.callback.TrainCallback;
import com.example.apple.travelban.module.train.TrainListFragment;
import com.jude.beam.nucleus.manager.Presenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by apple on 15/8/9.
 */
public class TrainListPresenter extends Presenter<TrainListFragment> {
    public String from;
    public String to;
    public String date;

    @Override
    public void create(Bundle bundle) {
        super.create(bundle);
    }

    @Override
    public void createView(TrainListFragment view) {
        super.createView(view);

    }

    public void setData(String date, String from, String to) {
        try {
            this.date= URLEncoder.encode(date, "UTF-8");
            this.from=URLEncoder.encode(from,"UTF-8");
            this.to=URLEncoder.encode(to,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void response() {
        TrainModel.getInstance().getTrainListFromServe("http://apis.baidu.com/qunar/qunar_train_service/s2ssearch?version=1.0&from="+from+"&to="+to+"&date=" + date, new TrainCallback<TrainBean>() {
            @Override
            public void success(String ret, TrainBean data) {
                List<TrainBean.TrainList> list = data.getData().getTrainList();
                if (list!= null) {
                    if (getView() == null) {
                        return;
                    }
                    getView().setData(list);
                }else {
                    if (getView() == null) {
                        return;
                    }
                    getView().setData(list);
                }
            }

            @Override
            public void failure(String ret) {
                if (getView() == null) {
                    return;
                }
                getView().setData(null);
            }

        });
    }
}
