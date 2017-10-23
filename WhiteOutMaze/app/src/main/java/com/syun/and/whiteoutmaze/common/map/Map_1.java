package com.syun.and.whiteoutmaze.common.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.syun.and.whiteoutmaze.common.tile.Square;

/**
 * Created by qijsb on 2017/10/23.
 */
public class Map_1 extends Map {
    private static final String TAG = Map_1.class.getSimpleName();
    private int[][] tiles = {
             {0,1,0,0,1,1,1,1,1,1}
            ,{0,1,1,0,0,0,0,0,0,1}
            ,{0,0,1,0,0,0,0,0,0,1}
            ,{1,0,1,0,0,0,0,0,0,1}
            ,{1,0,1,0,0,0,0,0,0,1}
            ,{1,0,1,0,0,0,0,0,0,1}
            ,{1,0,1,0,0,0,0,0,0,1}
            ,{1,0,1,1,1,1,1,1,0,1}
            ,{1,0,0,0,0,0,0,0,0,1}
            ,{1,1,1,1,1,1,1,1,1,1}
    };

    private int mSquareSize;

    private Square square;

    public Map_1(int squareSize) {
        this.mSquareSize = squareSize;

        init();
    }

    private void init() {
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        square = new Square(mSquareSize);
        map = Bitmap.createBitmap(10*mSquareSize, 10*mSquareSize, Bitmap.Config.RGB_565);

        Log.d(TAG, "init: map [width, height] # ["+map.getWidth()+", "+map.getHeight()+"]");

        Canvas canvas = new Canvas(map);

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                int tileType = tiles[row][column];
                Log.d(TAG, "tileType row column type # "+row+", "+column+", "+tileType);

                if(tileType == 1) {
                    canvas.drawRect(square.getRect(row, column), black);
                } else if(tileType == 0) {
                    canvas.drawRect(square.getRect(row, column), white);
                }
            }
        }

    }

}
