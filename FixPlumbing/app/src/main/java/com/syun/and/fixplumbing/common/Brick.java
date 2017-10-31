package com.syun.and.fixplumbing.common;

/**
 * Created by qijsb on 2017/10/29.
 */

public class Brick {
    private int column;
    private int row;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public Brick(int squareWidth, int squareHeight, int row, int column) {
        this.column = column;
        this.row = row;

        left = column * squareWidth;
        top = row * squareHeight;
        right = (column + 1) * squareWidth;
        bottom = (row + 1) * squareHeight;
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
}
