package com.syun.and.whiteoutmaze.common.tile;

import android.graphics.Path;

/**
 * Created by qijsb on 2017/10/22.
 */
public class TriangleBottomRight extends Tile {
    private Path path;

    public TriangleBottomRight(int squareSize) {
        this.squareSize = squareSize;

        init();
    }

    private void init() {
        path = new Path();
    }

    public Path getPath(int row, int column) {
        path.reset();

        path.moveTo(squareSize * (column + 1), squareSize * (row + 1)); // Bottom Right
        path.lineTo(squareSize * column, squareSize * (row + 1)); // Bottom Left
        path.lineTo(squareSize * (column + 1), squareSize * row); // Top Right
        path.lineTo(squareSize * (column + 1), squareSize * (row + 1)); // Back to Bottom Right
        path.close();

        return path;
    }
}
