package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * Created by qijsb on 2017/11/16.
 */

public class Command {
    private static final String TAG = Command.class.getSimpleName();

    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private float left, top;
    private float cX, cY;
    private int width, height;

    private Bitmap image;

    private double r;

    private double degree;
    private double power;

    private Path path = new Path();
    private Paint dotPaint = new Paint();
    private Paint pathPaint = new Paint();

    public Command(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        dotPaint.setColor(Color.RED);
        dotPaint.setStyle(Paint.Style.FILL);

        // TODO : should custom
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setTextSize(30f);
    }

    public void init(double degree, double power, double maxPower) {
        this.degree = degree;
        this.power = power;

        width = mSquareWidth * 2;
        height = mSquareHeight * 2;

        r = width / 2;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.TRANSPARENT);

        float pathPreX = width / 2;
        float pathPreY = height - mSquareHeight / 2;

        canvas.drawCircle(pathPreX, pathPreY, mSquareWidth / 8, dotPaint);

        float pathPostX = pathPreX - (float) (r * power / maxPower * Math.cos(Math.toRadians(degree)));
        float pathPostY = pathPreY - (float) (r * power / maxPower * Math.sin(Math.toRadians(degree)));

        path.reset();
        path.moveTo(pathPreX, pathPreY);
        path.lineTo(pathPostX, pathPostY);
        canvas.drawPath(path, pathPaint);
    }

    public double getDegree() {
        return degree;
    }

    public double getPower() {
        return power;
    }

    public Bitmap getImage() {
        return image;
    }
}
