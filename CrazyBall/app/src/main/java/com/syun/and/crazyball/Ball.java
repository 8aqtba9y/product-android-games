package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;

/**
 * Created by qijsb on 2017/11/14.
 */

public class Ball {
    private static final int SHEET = 4;

    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private float cX, cY;
    private int width, height;

    private Bitmap image;

    private Rect[] src;
    private Rect dst;

    private Paint dot;

    public Ball(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        dot = new Paint();
        dot.setColor(Color.RED);
        dot.setStyle(Paint.Style.FILL);

        init();
    }

    private void init() {
        width = mSquareWidth * 2;
        height = mSquareHeight * 2;

        cX = mSurfaceWidth / 2 - width / 2;
        cY = mSurfaceHeight - height / 2;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ball, options);
        image = Bitmap.createScaledBitmap(image, width * SHEET, height, true);

        src = new Rect[SHEET];
        for (int i = 0; i < SHEET; i++) {
            src[i] = new Rect(i * width, 0, (i+1) * width, height);
        }

        dst = new Rect((int) cX, (int) (cY - height / 2), (int) (cX + width), (int) (cY + height /2));

    }

    int count;

    public void draw(Canvas canvas) {
        count = ++count == 4 ? count = 0 : count;
        canvas.drawBitmap(image, src[count], dst, null);

        canvas.drawCircle(cX, cY, 10f, dot);
    }
}
