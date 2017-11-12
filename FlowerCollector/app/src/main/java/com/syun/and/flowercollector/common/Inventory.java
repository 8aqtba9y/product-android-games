package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;

/**
 * Created by qijsb on 2017/11/12.
 */
public class Inventory extends BaseCommon {
    private int mSquareWidth;
    private int mSquareHeight;
    private Bitmap image;

    private boolean shouldOpen;

    public Inventory(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {

    }

    public void onDraw(Canvas canvas) {
        if(shouldOpen) {
            canvas.drawBitmap(image, );
        } else {

        }
    }

    public boolean onInventoryTouch(MotionEvent motionEvent) {
        return false;
    }
}
