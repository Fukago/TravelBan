package com.example.apple.travelban.module.ad;

import android.os.Bundle;
import android.util.Log;

import com.example.apple.travelban.model.ADModel;
import com.example.apple.travelban.model.bean.AdBean;
import com.example.apple.travelban.model.callback.ADCallBack;
import com.example.apple.travelban.module.ad.AdFragment;
import com.jude.beam.nucleus.manager.Presenter;

import java.util.List;

/**
 * Created by apple on 15/8/24.
 */
public class Adpresenter extends Presenter<AdFragment> {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onCreateView(AdFragment view) {
        super.onCreateView(view);
    }

    public void onResponse() {
        ADModel.getInstance().getTrainListFromServe("http://apis.baidu.com/qunartravel/travellist/travellist?query=%22%22&page=1", new ADCallBack<AdBean>() {
            @Override
            public void success(boolean ret, AdBean data) {
                Log.d("data1", String.valueOf(data.getData().getCount()));
                List<AdBean.AdData.Books> booksList = data.getData().getBooksList();
                if (data!=null) {
                    Log.d("data2","true");
                    if (getView() == null) {
                        return;
                    }
                    Log.d("data3","true");
                        getView().setData(booksList);
                }
            }

            @Override
            public void failure(boolean ret) {

            }
        });
    }
}

