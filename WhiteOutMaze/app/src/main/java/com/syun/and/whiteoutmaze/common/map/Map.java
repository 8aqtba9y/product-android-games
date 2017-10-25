package com.syun.and.whiteoutmaze.common.map;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.syun.and.whiteoutmaze.common.tile.Square;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleBottomRight;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopLeft;
import com.syun.and.whiteoutmaze.common.tile.TriangleTopRight;

/**
 * Created by qijsb on 2017/10/21.
 */
public class Map {
    protected int squareSize;

    protected Square square;
    protected TriangleTopLeft triangleTopLeft;
    protected TriangleTopRight triangleTopRight;
    protected TriangleBottomLeft triangleBottomLeft;
    protected TriangleBottomRight triangleBottomRight;

    protected Bitmap image;

    protected Paint blackPaint;
    protected Paint whitePaint;

    public Bitmap getImage() {
        return image;
    }
}
