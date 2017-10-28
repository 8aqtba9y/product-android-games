package com.syun.and.fixplumbing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qijsb on 2017/10/28.
 */

public class GameView extends SurfaceView  implements SurfaceHolder.Callback2, Runnable {
    private static final String TAG = GameView.class.getSimpleName();

    private final static int INTERVAL = 1000 / Const.FPS;

    private Context mContext;

    private ExecutorService mExecutor;

    private OnGameEventListener mListener;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private int mSquareWidth;
    private int mSquareHeight;

    private Plumber plumber;

    public GameView(Context context) {
        super(context);
        this.mContext = context;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * here is life cycle.
     *
     * 1. handle touch events
     * 2. updates
     * 3. present
     *
     * loop { 1.2.3. }
     */
    public void init(OnGameEventListener listener) {
        mListener = listener;

        mExecutor = Executors.newSingleThreadExecutor();

        getHolder().addCallback(this);
    }


    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        mSquareWidth = mSurfaceWidth / Const.COLUMN;
        mSquareHeight = mSurfaceHeight / Const.ROW;

        initComponents();

        startDrawing(holder);
    }


    private void initComponents() {
        if(plumber == null) {

            // init plumber
            plumber = new Plumber(mContext, mSquareWidth, mSquareHeight);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopDrawing();
    }


    public void startDrawing(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;

        if(mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(this);
    }


    public void stopDrawing() {
        if(!mExecutor.isShutdown()) {
            mExecutor.shutdownNow();
        }
    }

    private void dead() {
        mListener.onEvent(OnGameEventListener.DEAD);
    }

    @Override
    public void run() {
        while(!mExecutor.isShutdown()) {
            update();

            // present
            present();

            // sleep
            sleep();
        }
    }

    private void update() {
        updatePlumberPosition();
    }

    private void updatePlumberPosition() {
        if(yaw != 0) { // && should jump
            plumber.setPX(
                    plumber.getPX() + yaw
            );
        }
    }

    private void sleep() {
        try {
            Thread.sleep(INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void present() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        // draw Map
        drawMap(canvas);

        // draw plumber
        drawPlumber(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawMap(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(map.image, new Matrix(), null);
    }

    private void drawPlumber(Canvas canvas) {
        canvas.drawBitmap(plumber.getImage(), plumber.getPX(), plumber.getPY(), null);
    }

    public void update(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                // TODO : begin jump()
                break;

            case MotionEvent.ACTION_UP :
                // TODO : end jump()
                break;
        }
    }

    private int yaw;
    public void update(SensorEvent sensorEvent) {
        // 数値の単位はm/s^2
        // X軸
        float x = sensorEvent.values[0];
        // Y軸
        float y = sensorEvent.values[1];
        // Z軸
        float z = sensorEvent.values[2];
        // Logに出力
        Log.d(TAG, "update: sensorEvent [x, y, z] # ["+x+", "+y+", "+z+"]");

        yaw = (int) y;
    }
}
