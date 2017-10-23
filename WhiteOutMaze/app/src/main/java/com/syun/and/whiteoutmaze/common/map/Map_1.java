package com.syun.and.whiteoutmaze.common.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.syun.and.whiteoutmaze.Const;
import com.syun.and.whiteoutmaze.common.tile.Square;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomRight;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopRight;

/**
 * Created by qijsb on 2017/10/23.
 */
public class Map_1 extends Map {
    private static final String TAG = Map_1.class.getSimpleName();
    private int[][] tiles = {
             {0,1,0,0,1,1,1,1,1,1}
            ,{0,3,4,0,0,0,0,0,3,1}
            ,{0,0,1,0,1,1,1,4,0,1}
            ,{4,0,1,0,3,1,0,1,0,1}
            ,{1,0,1,4,0,1,0,1,0,1}
            ,{1,0,1,2,5,1,0,1,0,1}
            ,{1,0,1,4,0,0,5,1,0,1}
            ,{1,0,3,1,1,1,1,2,0,1}
            ,{1,4,0,0,0,0,0,0,5,1}
            ,{1,1,1,1,1,1,1,1,1,1}
    };
    public Map_1(int squareSize) {
        this.squareSize = squareSize;
        init();
    }


    private void init() {
        // init paints
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);

        // init Tiles
        square = new Square(squareSize);
        triangleTopLeft = new TriangleTopLeft(squareSize);
        triangleTopRight = new TriangleTopRight(squareSize);
        triangleBottomLeft = new TriangleBottomLeft(squareSize);
        triangleBottomRight = new TriangleBottomRight(squareSize);

        // init Maps
        map = Bitmap.createBitmap(Const.COLUMN * squareSize, Const.COLUMN * squareSize, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(map);
        canvas.drawColor(Color.WHITE);

        for (int row = 0; row < Const.COLUMN; row++) {
            for (int column = 0; column < Const.COLUMN; column++) {
                int tileType = tiles[row][column];

                Log.d(TAG, "init: tile [row, column, type] # ["+row+", "+column+", "+tileType+"]");

                switch(tileType) {
                    case 0 :
                        // do nothing.
                        break;

                    case 1 :
                        canvas.drawRect(square.getRect(row, column), blackPaint);
                        break;

                    case 2 :
                        canvas.drawPath(triangleTopLeft.getPath(row, column), blackPaint);
                        break;

                    case 3 :
                        canvas.drawPath(triangleTopRight.getPath(row, column), blackPaint);
                        break;

                    case 4 :
                        canvas.drawPath(triangleBottomLeft.getPath(row, column), blackPaint);
                        break;

                    case 5 :
                        canvas.drawPath(triangleBottomRight.getPath(row, column), blackPaint);
                        break;
                }
            }
        }

    }

}
