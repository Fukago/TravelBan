package com.example.apple.travelban.app;

import android.app.Application;

import com.example.apple.travelban.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.beam.Beam;
import com.jude.http.RequestManager;
import com.kermit.exutils.utils.ExUtils;


/**
 * Created by apple on 15/8/14.
 */
public class App extends Application{

    private static App instance;

    public static App getIntance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Fresco.initialize(this);
        ExUtils.initialize(this);
        RequestManager.getInstance().init(this);
        RequestManager.getInstance().setDebugMode(BuildConfig.DEBUG, "NET");
        Beam.init(this);
    }
}
