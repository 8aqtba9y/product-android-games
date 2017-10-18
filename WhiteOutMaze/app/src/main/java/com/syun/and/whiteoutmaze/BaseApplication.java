package com.syun.and.whiteoutmaze;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by qijsb on 2017/10/18.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAnalytics.getInstance(this);
    }
}