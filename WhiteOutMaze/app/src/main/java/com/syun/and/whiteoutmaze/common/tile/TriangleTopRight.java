package com.syun.and.whiteoutmaze.common.tile;

import android.graphics.Path;

/**
 * Created by qijsb on 2017/10/22.
 */
public class TriangleTopRight extends Tile {
    private Path path;

    public TriangleTopRight(int squareSize) {
        this.squareSize = squareSize;

        init();
    }

    private void init() {
        path = new Path();
    }

    public Path getPath(int row, int column) {
        path.reset();

        path.moveTo(squareSize * (column + 1), squareSize * row); // Top Right
        path.lineTo(squareSize * (column + 1), squareSize * (row + 1)); // Bottom Right
        path.lineTo(squareSize * column, squareSize * row); // Bottom Left
        path.lineTo(squareSize * (column + 1), squareSize * row); // Back to Top Right
        path.close();

        return path;
    }
}
