package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by qijsb on 2017/11/14.
 */

public class GameModel {
    private static final String TAG = GameModel.class.getSimpleName();

    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Map map;
    private Ball ball;

    public GameModel(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        map = new Map(mContext, mSurfaceWidth, mSurfaceHeight);
        ball = new Ball(mContext, mSurfaceWidth, mSurfaceHeight);
    }

    public void drawMap(Canvas canvas) {
        map.draw(canvas);
    }

    public void drawBall(Canvas canvas) {
        ball.draw(canvas);
    }

    public void parse(MotionEvent motionEvent) {

    }
}
