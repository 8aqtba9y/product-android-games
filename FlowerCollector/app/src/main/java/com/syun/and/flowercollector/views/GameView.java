package com.syun.and.flowercollector.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.listener.OnGameEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qijsb on 2017/11/05.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback2, Runnable {
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

//    private Map map;
//    private Plumber plumber;
//    private Drops drops;
//    private Wave wave;

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

        Log.d(TAG, "surfaceChanged: surface [width, height] # ["+mSurfaceWidth + ", "+mSurfaceHeight+"]");
        Log.d(TAG, "surfaceChanged: square [width, height] # ["+mSquareWidth+ ", "+mSquareHeight+"]");

        initComponents();

        startDrawing(holder);
    }

    private void initComponents() {
//        if(plumber == null && map == null) {
//
//            // init map
//            map = new Map(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
//
//            // init plumber
//            plumber = new Plumber(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
//
//            // init drops
//            drops = new Drops(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
//
//            // init wave
//            wave = new Wave(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
//        }
    }

    public void startDrawing(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;

        if(mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(this);

        sendEvent(OnGameEventListener.CREATE);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed: ");
        stopDrawing();
    }

    public void stopDrawing() {
        sendEvent(OnGameEventListener.DESTROY);

        if(!mExecutor.isShutdown()) {
            mExecutor.shutdownNow();
        }
    }


    @Override
    public void run() {
        while(!mExecutor.isShutdown()) {
            // update Units
//            update();

            // present
            present();

            // sleep
            sleep();
        }
    }

    private void update() {
        // drop
//        updateDropPosition();

        // plumber
//        updatePlumberPosition();

        // plumbing
//        updatePlumbingGauge();

        // wave
//        updateWavePosition();
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

        canvas.drawColor(Color.BLUE);
        // draw Map
//        drawMap(canvas);

        // draw plumbing
//        drawPlumbing(canvas);

        // draw plumber
//        drawPlumber(canvas);

        // draw drop
//        drawDrop(canvas);

        // draw wave;
//        drawWave(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void sendEvent(String msg) {
        mListener.onEvent(msg);
    }

    public void update(SensorEvent sensorEvent) {
        int lux = (int) sensorEvent.values[0];
        Log.d(TAG, "update: lux # "+lux);
    }

    public void update(MotionEvent motionEvent) {

    }
}
