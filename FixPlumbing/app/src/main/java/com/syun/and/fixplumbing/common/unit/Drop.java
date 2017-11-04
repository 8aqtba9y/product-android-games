package com.syun.and.fixplumbing.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.syun.and.fixplumbing.R;

import java.util.Random;

/**
 * Created by qijsb on 2017/11/02.
 */
public class Drop extends BaseUnit {
    private static final String TAG = Drop.class.getSimpleName();

    private Bitmap image;

    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private int row;
    private int column;

    private int pX;
    private int pY;

    private int width;
    private int height;

    private int vt;
    private int maxVT;
    private float g;

    public Drop(Context context, int surfaceWidth, int surfaceHeight , int squareWidth, int squareHeight) {
        mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;

        init();
    }

    private void init() {
        Random rnd = new Random();

        width = squareWidth * 2 / 3;
        height = squareHeight * 2 / 3;

        pX = rnd.nextInt(surfaceWidth);
        pY = -rnd.nextInt(squareHeight);

        g = squareHeight / 4f;
        maxVT = rnd.nextInt((int) g / 2) + 1;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.drop, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Bitmap getImage() {
        return image;
    }

    public int getVY() {
        vt = ++vt > maxVT ? maxVT: vt;
        return (int) (g / 2f * vt);
    }

    public int getPX() {
        return pX;
    }

    public int getPY() {
        return pY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPY(int pY) {
        this.pY = pY;
    }
}
