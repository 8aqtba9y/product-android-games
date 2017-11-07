package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.syun.and.flowercollector.R;

/**
 * Created by qijsb on 2017/11/07.
 */
public class Map {
    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private Bitmap image;

    private float left, top;
    private int width, height;

    public Map(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        left = - mSurfaceWidth / 2;
        top = 0;

        width = mSurfaceWidth * 2;
        height = mSurfaceHeight;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.map, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);
    }


    public Bitmap getImage() {
        return image;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void translateLeft() {
        left = --left < - width / 2 ? - width / 2 : left;
    }

    public void translateRight() {
        left = ++left > 0 ? 0 : left;
    }
}
