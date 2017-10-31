package com.syun.and.fixplumbing.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.R;

import java.util.ArrayList;
import java.util.List;

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

    private List<Brick> brickList = new ArrayList<>();

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
        height = surfaceHeight;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        brick = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.brick, options);
        brick = Bitmap.createScaledBitmap(brick, squareWidth, squareHeight, true);

        image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);

        Log.d(TAG, "init: image [width, height] # ["+image.getWidth()+", "+image.getHeight()+"]");

//        rect = new Rect();

        for (int row = 0; row < Const.ROW; row++) {
            for (int column = 0; column < Const.COLUMN; column++) {
//                rect.setEmpty();
//
//                rect.left = squareWidth * column;
//                rect.top = squareHeight * row;
//                rect.right = squareWidth * (column + 1);
//                rect.bottom = squareHeight * (row + 1);

                int tileType = tiles[row][column];
                switch (tileType) {
                    case 0 :
                        // do nothing.
                        break;

                    case 1 :
                        brickList.add(new Brick(squareWidth, squareHeight, row, column));
                        canvas.drawBitmap(brick, squareWidth * column, squareHeight * row, null);
                        break;
                }
            }
        }
    }

    public Bitmap getImage(){
        return image;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    private int[][] tiles = {
             {0,0,0,0,0,0,0,0,0}
            ,{1,0,0,0,0,0,0,0,0}
            ,{1,0,0,0,0,0,0,0,0}
            ,{1,0,0,0,1,0,0,0,0}
            ,{1,0,0,0,1,0,0,0,0}
            ,{1,1,1,1,1,0,0,0,1}
            ,{0,0,0,0,0,0,0,0,1}
            ,{0,0,0,0,0,0,0,0,1}
            ,{0,1,1,1,1,1,0,0,1}
            ,{0,0,0,0,0,0,1,0,1}
            ,{1,0,0,0,0,0,1,0,1}
            ,{1,0,0,0,0,0,1,1,0}
            ,{1,1,1,1,0,0,0,1,0}
            ,{0,0,0,1,0,0,0,0,1}
            ,{0,1,0,0,0,1,0,0,1}
            ,{0,0,1,1,1,1,1,1,1}
    };
}
