package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by qijsb on 2017/11/14.
 */

public class Ball {
    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private float cX, cY;
    private int width, height;

    private Bitmap image;

    private final static int FRAMES = 4;
    private int frame;

    private double minDegree;
    private double maxDegree;
    private double minPower;
    private double maxPower;

    private double r;

    private double degree;
    private double power;
    private boolean degreeSwitch;
    private boolean powerSwitch;

    private double degreeVec;
    private double powerVec;

    private Rect[] src;
    private Rect dst;

    private Path path = new Path();
    private Paint dotPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint pathPaint = new Paint();

    private Random random = new Random();

    public Ball(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        width = mSquareWidth * 2;
        height = mSquareHeight * 2;

        cX = mSurfaceWidth / 2;
        cY = mSurfaceHeight - (mSquareHeight * 2) - (height / 2);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ball, options);
        image = Bitmap.createScaledBitmap(image, width * FRAMES, height, true);

        src = new Rect[FRAMES];
        for (int i = 0; i < FRAMES; i++) {
            src[i] = new Rect(i * width, 0, (i+1) * width, height);
        }

        dst = new Rect((int) (cX - width / 2), (int) (cY - height / 2), (int) (cX + width / 2), (int) (cY + height /2));

        r = mSurfaceWidth / 2;

        minDegree = 18.0; // 180 / 10
        maxDegree = 172.0; // 180 - minDegree
        minPower = mSquareWidth * 2;
        maxPower = mSquareWidth * 10;

        degree = minDegree + random.nextDouble() * (maxDegree - minDegree);
        power = minPower + random.nextDouble() * (maxPower - minPower);

        degreeVec = minDegree / 2;
        powerVec = minPower / 20;

        dotPaint.setColor(Color.RED);
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(30f);

        // TODO : should custom
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
    }


    private float pathPreX, pathPreY, pathPostX, pathPostY;
    public void draw(Canvas canvas) {
        pathPreX = cX;
        pathPreY = cY;

        if(degreeSwitch) {
                degree = degree + degreeVec; // should be increasing randomly.
            if(degree >= maxDegree) {
                degreeSwitch = !degreeSwitch;
            }
        } else {
            degree = degree - degreeVec; // should be decreasing randomly.
            if(degree <= minDegree) {
                degreeSwitch = !degreeSwitch;
            }
        }

        if(powerSwitch) {
            power = power + powerVec; // should be increasing randomly.
            if(power >= maxPower) {
                powerSwitch = !powerSwitch;
            }
        } else {
            power = power - powerVec; // should be decreasing randomly.
            if(power <= minPower) {
                powerSwitch = !powerSwitch;
            }
        }

        pathPaint.setStrokeWidth((float) (power / mSquareWidth));

        pathPostX = pathPreX - (float) (r * power / maxPower * Math.cos(Math.toRadians(degree)));
        pathPostY = pathPreY - (float) (r * power / maxPower * Math.sin(Math.toRadians(degree)));

        path.reset();
        path.moveTo(pathPreX, pathPreY);
        path.lineTo(pathPostX, pathPostY);
        canvas.drawPath(path, pathPaint);

        frame = ++frame == FRAMES ? frame = 0 : frame;
        canvas.drawBitmap(image, src[frame], dst, null);

        canvas.drawCircle(cX, cY, 10f, dotPaint);
        canvas.drawCircle(cX, getBottom(), 10f, dotPaint);
        canvas.drawText("degree # "+degree, mSurfaceWidth * 3 / 5, mSurfaceHeight - mSquareHeight * 8 /2, textPaint);
        canvas.drawText("power # "+power, mSurfaceWidth * 3 / 5, mSurfaceHeight - mSquareHeight * 7 / 2, textPaint);
    }

    public float getCX() {
        return cX;
    }

    public float getCY() {
        return cY;
    }

    public float getBottom() {
        return cY + height / 2;
    }

    public Command getCommand() {
        Command command = new Command(mContext, mSurfaceWidth, mSurfaceHeight);
        command.init(degree, power, maxPower);
        return command;
    }

    public void updatePositionWith(Map map) {

    }
}
