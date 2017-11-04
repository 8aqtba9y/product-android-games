package com.syun.and.fixplumbing.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.syun.and.fixplumbing.R;

/**
 * Created by qijsb on 2017/10/29.
 */

public class Brick extends BaseUnit {
    private Bitmap image;

    private int column;
    private int row;

    private int width;
    private int height;

    private int pX;
    private int pY;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public Brick(Context context, int squareWidth, int squareHeight, int row, int column) {
        this.mContext = context;
        this.column = column;
        this.row = row;

        width = squareWidth;
        height = squareHeight;

        pX = squareWidth * column;
        pY = squareHeight * row;

        left = column * squareWidth;
        top = row * squareHeight;
        right = (column + 1) * squareWidth;
        bottom = (row + 1) * squareHeight;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.brick, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
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
