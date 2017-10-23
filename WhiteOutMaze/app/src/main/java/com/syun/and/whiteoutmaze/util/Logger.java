package com.syun.and.whiteoutmaze.util;

import android.util.Log;

import com.syun.and.whiteoutmaze.BuildConfig;

/**
 * Created by qijsb on 2017/10/23.
 */
public class Logger {
    private String mTag;

    public Logger(String tag) {
        this.mTag = tag;
    }
    public void print(String message) {
        if(BuildConfig.DEBUG) {
            Log.d(mTag, message);
        }
    }
}
