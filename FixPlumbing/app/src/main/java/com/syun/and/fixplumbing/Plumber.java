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
    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private Bitmap image;

    private int pX;
    private int pY;

    private int width;
    private int height;

    private int xSpeed;
    private int ySpeed;

    public Plumber(Context context, int surfaceWidth, int surfaceHeight ,int squareWidth, int squareHeight) {
        this.mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }

    private void init() {
        width = squareWidth * 4/3;
        height = squareHeight * 5/2;

        xSpeed = squareWidth / 18;
        ySpeed = squareHeight;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);

        pX = squareWidth * (Const.COLUMN/2) + width / 2;
        pY = squareHeight * (Const.ROW/2) + height / 2;

        Log.d(TAG, "init: p [X, Y] # ["+pX+", "+pY+"]");
        Log.d(TAG, "init: image [width, height] # ["+image.getWidth()+", "+image.getHeight()+"]");
    }

    public void setPX(int pX) {
        if(pX < 0) {
            this.pX = 0;
        } else {
            this.pX = pX > surfaceWidth - width ? surfaceWidth - width: pX;
        }
    }

    public void setPY(int pY) {
        if(pY < 0) {
            pY = 0;
        } else {
            this.pY = pY > surfaceHeight - height ? surfaceHeight - height : pY;
        }
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

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

}
