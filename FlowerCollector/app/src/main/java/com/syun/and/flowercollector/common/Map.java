package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;

import java.util.Random;

/**
 * Created by qijsb on 2017/11/07.
 */
public class Map extends BaseCommon{
    private static final int DETAIL = 1;

    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Bitmap image;

    private float left, top;
    private int width, height;

    public Map(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = DETAIL;

        left = - mSurfaceWidth / 2;
        top = 0;

        width = mSurfaceWidth * 2;
        height = mSurfaceHeight;

        image = Bitmap.createBitmap(width * 2, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);

        Random random = new Random();

        Bitmap[] tile_grass = parseDrawable(
                new int[]{
                        R.drawable.tile_grass_1
                        , R.drawable.tile_grass_2
                        , R.drawable.tile_grass_3
                        , R.drawable.tile_grass_4
                        , R.drawable.tile_grass_5
                }, mSquareWidth / DETAIL, mSquareHeight / DETAIL);

        Bitmap[] tile_water = parseDrawable(
                new int[]{
                        R.drawable.tile_water_1
                        , R.drawable.tile_water_2
                        , R.drawable.tile_water_3
                }, mSquareWidth / DETAIL, mSquareHeight / DETAIL);

        for (int row = 0; row < Const.ROW; row++) {
            for (int column = 0; column < (Const.COLUMN * 2); column++) {

                int tileType = tiles[row][column];
                switch (tileType) {
                    case 0:
                        canvas.drawBitmap(tile_grass[random.nextInt(tile_grass.length)]
                                , column * mSquareHeight, row * mSquareWidth, null);
                        break;

                    case 1:
                        canvas.drawBitmap(tile_water[0]
                                , column * mSquareHeight, row * mSquareWidth, null);
                        break;

                    case 2:
                        canvas.drawBitmap(tile_water[1]
                                , column * mSquareHeight, row * mSquareWidth, null);
                        break;

                    case 3 :
                        canvas.drawBitmap(tile_water[2]
                                , column * mSquareHeight, row * mSquareWidth, null);
                        break;
                }
            }
        }
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

    private int[][] tiles = {
             {3,3,3,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,3,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
            ,{3,3,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3}
    };
}
