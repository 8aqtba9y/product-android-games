package com.syun.and.whiteoutmaze.util;

import android.util.Log;

import com.syun.and.whiteoutmaze.BuildConfig;

/**
 * Created by qijsb on 2017/10/23.
 */
public class Logger {
    public static void print(String TAG, String message) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
