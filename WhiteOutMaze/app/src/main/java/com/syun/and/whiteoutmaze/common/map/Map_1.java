package com.syun.and.whiteoutmaze.common.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.syun.and.whiteoutmaze.Const;
import com.syun.and.whiteoutmaze.common.tile.Square;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomRight;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopRight;

import java.util.Random;

/**
 * Created by qijsb on 2017/10/23.
 */
public class Map_1 extends Map {
    private static final String TAG = Map_1.class.getSimpleName();

    private int[][] tiles = {
             {0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0,0}
    };

    public Map_1(int squareSize) {
        this.squareSize = squareSize;

        /* begin temp code (1) */
        switch (new Random().nextInt(5)) {
            case 0 :
                tiles = tiles_1;
                break;

            case 1 :
                tiles = tiles_2;
                break;

            case 2 :
                tiles = tiles_3;
                break;

            case 3 :
                tiles = tiles_4;
                break;

            case 4 :
                tiles = tiles_5;
                break;
        }
        /* end of temp code (1) */

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
        image = Bitmap.createBitmap(Const.COLUMN * squareSize, Const.COLUMN * squareSize, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);

        for (int row = 0; row < Const.COLUMN; row++) {
            for (int column = 0; column < Const.COLUMN; column++) {
                int tileType = tiles[row][column];

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

    /* begin temp code (2) */
    private int[][] tiles_1 = {
             {1,0,0,0,1,0,0,0,0,0}
            ,{1,0,0,0,1,0,0,0,0,0}
            ,{1,0,0,0,1,0,0,0,0,0}
            ,{2,5,1,1,2,0,0,0,0,0}
            ,{0,0,0,0,5,1,1,1,2,5}
            ,{0,0,0,0,1,0,0,0,0,0}
            ,{0,0,0,0,1,0,0,0,0,0}
            ,{0,0,0,0,1,0,0,0,0,0}
            ,{0,0,0,0,1,0,0,0,0,0}
            ,{0,0,0,0,1,0,0,0,0,0}
    };

    private int[][] tiles_2 = {
             {0,0,0,0,0,0,0,1,1,0}
            ,{0,0,0,0,5,1,0,1,0,1}
            ,{0,0,0,5,1,1,0,1,1,0}
            ,{1,1,1,1,1,1,0,1,0,1}
            ,{1,0,1,0,1,1,0,3,1,1}
            ,{0,1,0,1,0,1,4,0,3,1}
            ,{1,0,1,0,1,1,1,4,0,1}
            ,{0,1,0,1,0,1,1,2,0,1}
            ,{1,0,1,0,1,1,2,0,0,1}
            ,{0,1,0,1,0,1,0,0,0,1}
    };

    private int[][] tiles_3 = {
             {0,0,1,0,1,0,1,0,1,0}
            ,{0,0,1,1,0,1,0,1,0,1}
            ,{0,0,1,0,1,0,1,0,1,0}
            ,{0,0,1,1,0,1,0,1,0,1}
            ,{0,0,1,1,1,1,1,1,1,1}
            ,{0,0,0,0,0,0,0,0,0,0}
            ,{0,1,1,1,1,0,1,1,1,1}
            ,{0,0,0,0,1,0,0,0,0,1}
            ,{0,0,0,0,1,0,0,0,0,1}
            ,{0,1,1,1,1,0,1,1,1,1}
    };

    private int[][] tiles_4 = {
             {0,0,1,1,0,1,0,1,0,1}
            ,{0,0,1,0,1,0,1,0,1,0}
            ,{0,1,1,1,0,1,0,1,0,1}
            ,{0,1,1,0,1,0,1,0,1,0}
            ,{0,1,0,1,0,1,0,1,0,1}
            ,{0,1,1,0,1,0,1,0,1,0}
            ,{0,1,0,1,0,1,1,1,1,1}
            ,{0,1,1,0,1,1,2,0,3,1}
            ,{0,1,1,1,1,1,0,1,0,1}
            ,{4,0,0,0,0,0,5,1,1,1}
    };

    private int[][] tiles_5 = {
             {0,0,0,0,0,0,0,1,0,0}
            ,{0,0,0,0,0,0,0,1,0,0}
            ,{0,0,0,0,0,0,0,1,4,0}
            ,{0,0,0,0,0,0,0,3,2,0}
            ,{0,0,0,0,0,0,5,4,0,0}
            ,{1,1,1,1,1,1,1,2,0,0}
            ,{0,0,0,0,0,0,0,5,1,1}
            ,{0,0,0,0,0,0,0,3,2,0}
            ,{0,0,0,0,0,0,5,4,0,0}
            ,{0,0,0,0,0,0,3,1,0,0}
    };
    /* end of temp code (2) */
}
