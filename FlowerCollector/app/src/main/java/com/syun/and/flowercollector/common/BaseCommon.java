package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by qijsb on 2017/11/11.
 */

public class BaseCommon {
    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;

    protected Bitmap[] parseDrawable(int[] res, int width, int height) {
        Bitmap[] bitmaps = new Bitmap[res.length];
        for (int i = 0; i < res.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(mContext.getResources(), res[i], null);
            bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i], width, height, true);
        }

        return bitmaps;
    }

}
