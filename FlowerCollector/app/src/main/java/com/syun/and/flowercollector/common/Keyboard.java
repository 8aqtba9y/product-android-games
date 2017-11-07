package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;

/**
 * Created by qijsb on 2017/11/07.
 */

public class Keyboard {
    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Bitmap keyboardImage;
    private Bitmap tapImage;

//    private float keyboardLeft, keyboardTop;
    private float keyboardCX, keyboardCY;
    private int keyboardWidth, keyboardHeight;

//    private float tapLeft, tapTop;
    private float tapCX, tapCY;
    private int tapWidth, tapHeight;

    public Keyboard(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        keyboardWidth = mSquareWidth * 2;
        keyboardHeight = mSquareHeight * 2;

        tapWidth = mSquareWidth;
        tapHeight = mSquareHeight;

        keyboardImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.keyboard, options);
        keyboardImage = Bitmap.createScaledBitmap(keyboardImage, keyboardWidth, keyboardHeight, true);

        tapImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.tap, options);
        tapImage = Bitmap.createScaledBitmap(tapImage, tapWidth, tapHeight, true);
    }

    public Bitmap getKeyboardImage() {
        return keyboardImage;
    }

    public Bitmap getTapImage() {
        return tapImage;
    }

    public float getKeyboardCX() {
        return keyboardCX;
    }

    public float getKeyboardCY() {
        return keyboardCY;
    }

    public float getTapCX() {
        return tapCX;
    }

    public float getTapCY() {
        return tapCY;
    }

    /**
     * handling motionEvent.
     */
    private float preX, preY, postX, postY, diffX, diffY;
    private boolean shouldShow;

    public void parse(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                shouldShow = true;
                preX = motionEvent.getX(0);
                preY = motionEvent.getY(0);
                keyboardCX = preX - keyboardWidth / 2;
                keyboardCY = preY - keyboardHeight / 2;
                break;

            case MotionEvent.ACTION_MOVE :
                postX = motionEvent.getX(0);
                postY = motionEvent.getY(0);
                tapCX = postX - tapWidth / 2;
                tapCY = postY - tapHeight / 2;
                diffX = postX - preX;
                diffY = postY - preY;
                break;

            case MotionEvent.ACTION_UP :
                shouldShow = false;

                break;
        }
    }

    public boolean shouldShow() {
        return shouldShow;
    }

    public float getDiffX() {
        return diffX;
    }

    public float getDiffY() {
        return diffY;
    }
}
