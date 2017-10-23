package com.syun.and.whiteoutmaze.common.tile;

import android.graphics.Rect;

/**
 * Created by qijsb on 2017/10/22.
 */

public class Square extends Tile {
    private static final String TAG = Square.class.getSimpleName();

    private Rect rect;

    public Square(int squareSize) {
        this.squareSize = squareSize;

        init();
    }

    private void init() {
        rect = new Rect();
    }

    public Rect getRect(int row, int column) {
        rect.setEmpty();

        rect.left = squareSize * column;
        rect.top = squareSize * row;
        rect.right = squareSize * (column + 1);
        rect.bottom = squareSize * (row + 1);

        return rect;
    }
}
