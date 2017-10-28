package com.syun.and.fixplumbing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by qijsb on 2017/10/28.
 */

public class Plumber {
    private static final String TAG = Plumber.class.getSimpleName();

    private Context mContext;
    private int squareWidth;
    private int squareHeight;

    private Bitmap image;

    private int pX;
    private int pY;

    private int width;
    private int height;

    public Plumber(Context context, int squareWidth, int squareHeight) {
        this.mContext = context;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }

    private void init() {
        width = squareWidth * 1 / 2;
        height = squareHeight * 1;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);

        pX = squareWidth * (16-1) + width / 2;
        pY = squareHeight * (9-1) + height / 2;

        Log.d(TAG, "init: p [X, Y] # ["+pX+", "+pY+"]");
        Log.d(TAG, "init: image [width, height] # ["+image.getWidth()+", "+image.getHeight()+"]");
    }

    public void setPX(int pX) {
        this.pX = pX;
    }

    public void setPY(int pY) {
        this.pY = pY;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getPX() {
        return pX;
    }

    public int getPY() {
        return pY;
    }

}
