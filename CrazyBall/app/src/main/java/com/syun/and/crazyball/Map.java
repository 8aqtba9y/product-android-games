package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by qijsb on 2017/11/14.
 */

public class Map {
    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private float cX, cY;
    private int width, height;

    private Bitmap image;

    public Map(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        width = mSurfaceWidth;
        height = mSurfaceHeight * 2;

        top = mSurfaceHeight - height - mSquareHeight * 2;

        image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.BLACK);

        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        for (int row = 0; row < (Const.ROW * 2); row++) {
            for (int column = 0; column < Const.COLUMN; column++) {

                int tileType = tiles[row][column];
                switch (tileType) {
                    case 0:
                        break;

                    case 1:
                        Rect rect = new Rect();
                        rect.left = mSquareWidth * column;
                        rect.top = mSquareHeight * row;
                        rect.right = mSquareWidth * (column + 1);
                        rect.bottom = mSquareHeight * (row + 1);
                        canvas.drawRect(rect, white);
                        break;
                }
            }
        }
    }

    private float top;
    private boolean topSwitch;

    public void draw(Canvas canvas) {
        if(topSwitch) {
            top = top + 2;
            if(top >= 0) {
                topSwitch = !topSwitch;
            }
        } else {
            top = top - 2;
            if(top <= - mSurfaceHeight) {
                topSwitch = !topSwitch;
            }
        }
        canvas.drawBitmap(image, 0, top, null);
    }

    public void translate() {

    }

    public float getTop() {
        return top;
    }

    private int[][] tiles = {
             {0,0,0,0,1,0,0,0,1}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,1,0,0,1,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,1,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,1,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,1,0,1,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,1,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,1,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,1,0,0,1,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,1,0}
            ,{0,0,0,0,0,0,0,0,0}
    };
}
