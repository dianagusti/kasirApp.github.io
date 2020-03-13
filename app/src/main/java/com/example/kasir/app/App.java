package com.example.kasir.app;

import android.app.Application;
import android.content.Context;

import com.example.kasir.model.MasterDataModel;

import java.util.ArrayList;
import java.util.List;

public class App  extends Application {


    static public List<MasterDataModel> listMasterData = new ArrayList<>();
    static  public  int intTotal;

    public static App mInstance;



//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

}
