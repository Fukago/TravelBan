package com.example.apple.travelban.module.place;

import android.os.Bundle;
import android.util.Log;

import com.example.apple.travelban.model.PlaceModel;
import com.example.apple.travelban.model.bean.PlaceBean;
import com.example.apple.travelban.model.callback.DataCallback;
import com.example.apple.travelban.module.place.PlaceActivity;
import com.jude.beam.nucleus.manager.Presenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by apple on 15/8/16.
 */
public class PlacePresenter extends Presenter<PlaceActivity> {
    private String location;
    public void setLocation(String location) throws UnsupportedEncodingException {
        this.location = URLEncoder.encode(location,"UTF-8");
        Log.d("location",this.location);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onCreateView(PlaceActivity view) {
        super.onCreateView(view);
    }

    public void onResponse() {
        Log.d("location",location);
        PlaceModel.getInstance().getTrainListFromServe("http://apis.baidu.com/apistore/travel/line?location="+location+"&day=1&output=json&coord_type=bd09ll&out_coord_type=bd09ll", new DataCallback<PlaceBean>() {
            @Override
            public void success(int error, PlaceBean data) {
                Log.d("response", "xx");
                PlaceBean.CityResult[] list = new PlaceBean.CityResult[1];
                int z = 0;
                int length=0;
                for (int j = 0; j < data.getResult().getItineraries().get(0).getItineraries().size(); j++) {
                    for (int i = 0; i < data.getResult().getItineraries().get(0).getItineraries().get(j).getPath().size(); i++) {
                        length++;
                    }
                }
                PlaceBean.CityResult.Plan.Detail.Place[] places = new PlaceBean.CityResult.Plan.Detail.Place[length];if (error == 0) {
                    if (getView() == null) {
                        return;
                    }
                    for (int j = 0; j < data.getResult().getItineraries().get(0).getItineraries().size(); j++) {
                        for (int i = 0; i < data.getResult().getItineraries().get(0).getItineraries().get(j).getPath().size(); i++) {

                            places[z] = data.getResult().getItineraries().get(0).getItineraries().get(j).getPath().get(i);
                            Log.d("result",data.getResult().getItineraries().get(0).getItineraries().get(j).getPath().get(i).getName());
                            z++;
                        }
                    }
                    Log.d("response", data.getResult().getItineraries().get(0).getItineraries().get(0).getPath().get(0).getName());

                    getView().refreshData(places);
                }
            }

            @Override
            public void failure(int error) {
                getView().refreshData(null);
            }
        });
    }
}

