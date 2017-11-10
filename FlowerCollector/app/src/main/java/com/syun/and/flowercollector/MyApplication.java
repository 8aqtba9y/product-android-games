package com.syun.and.flowercollector;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by qijsb on 2017/11/08.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
