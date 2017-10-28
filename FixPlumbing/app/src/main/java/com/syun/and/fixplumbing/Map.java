package com.syun.and.fixplumbing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by qijsb on 2017/10/29.
 */

public class Map {
    private static final String TAG = Map.class.getSimpleName();

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

    public Map(Context context, int surfaceWidth, int surfaceHeight, int squareWidth, int squareHeight) {
        this.mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }

    private Bitmap brick;

    private Rect rect;

    private void init() {
        width = surfaceWidth;
        height = squareHeight * Const.ROW;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        brick = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.brick, options);
        brick = Bitmap.createScaledBitmap(brick, squareWidth, squareHeight, true);

        image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);

        Log.d(TAG, "init: image [width, height] # ["+image.getWidth()+", "+image.getHeight()+"]");

//        rect = new Rect();

        for (int column = 0; column < Const.COLUMN; column++) {
            for (int row = 0; row < Const.ROW; row++) {
//                rect.setEmpty();
//
//                rect.left = squareWidth * column;
//                rect.top = squareHeight * row;
//                rect.right = squareWidth * (column + 1);
//                rect.bottom = squareHeight * (row + 1);

                canvas.drawBitmap(brick, squareWidth*column, squareHeight*row, null);
            }
        }
    }

    public Bitmap getImage(){
        return image;
    }
}
