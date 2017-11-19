package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.List;

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
    private UI ui;

    private OnGameEventListener mListener;
    private String msg;

    public GameModel(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;
    }

    public void init(OnGameEventListener listener) {
        map = new Map(mContext, mSurfaceWidth, mSurfaceHeight);
        ball = new Ball(mContext, mSurfaceWidth, mSurfaceHeight);
        ui = new UI(mContext, mSurfaceWidth, mSurfaceHeight);

        this.mListener = listener;

        msg = OnGameEventListener.UNLOCK;
    }

    public void draw(Canvas canvas) {
        // draw Map
        drawMap(canvas);

        // draw Ball
        drawBall(canvas);

        // draw UI
        drawUI(canvas);
    }

    private void drawMap(Canvas canvas) {
        map.draw(canvas);
    }

    private void drawBall(Canvas canvas) {
        ball.draw(canvas);
    }

    private void drawUI(Canvas canvas) {
        ui.draw(canvas);
    }

    public void parse(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Command command = ball.getCommand();
            ui.setCommand(command);

            if(ui.getCommandList().size() == 3) {
                msg = OnGameEventListener.LOCK;
                mListener.onEvent(msg);
            }
        }
    }

    private int count = 0;
    public void update() {
        if(msg.equals(OnGameEventListener.LOCK)) {
            ball.updatePositionWith(map);


            // begin of temp code to clear list
            commandList.clear();
            // end of temp code to clear list

            count++;
            if(count > 400) {
                count = 0;
                msg = OnGameEventListener.UNLOCK;
                mListener.onEvent(msg);
            }
        }
    }

}
