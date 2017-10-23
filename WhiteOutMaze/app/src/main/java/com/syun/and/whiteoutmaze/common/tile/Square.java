package com.syun.and.whiteoutmaze.common.tile;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by qijsb on 2017/10/22.
 */

public class Square extends Tile {
    private static final String TAG = Square.class.getSimpleName();

    private Rect rect;

    private int mSquareSize;

    public Square(int squareSize) {
        rect = new Rect();
        this.mSquareSize = squareSize;
    }

    public Rect getRect(int row, int column) {
        rect.setEmpty();

        rect.left = mSquareSize * column;
        rect.top = mSquareSize * row;
        rect.right = mSquareSize * (column + 1);
        rect.bottom = mSquareSize * (row + 1);

        Log.d(TAG, "rect left, top, right, bottom # "+rect.left+", "+rect.top+", "+ rect.right + ", "+rect.bottom);
        return rect;
    }
}
