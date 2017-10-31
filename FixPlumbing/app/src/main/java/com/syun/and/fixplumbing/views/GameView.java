package com.syun.and.fixplumbing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.common.Brick;
import com.syun.and.fixplumbing.listener.OnGameEventListener;
import com.syun.and.fixplumbing.common.Map;
import com.syun.and.fixplumbing.common.Plumber;

import java.util.ArrayList;
import java.util.List;
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

    private Map map;
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
        Log.d(TAG, "surfaceChanged: ");
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
        if(plumber == null && map == null) {

            // init map
            map = new Map(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);

            // init plumber
            plumber = new Plumber(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
        }
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
            update();

            // present
            present();

            // sleep
            sleep();
        }
    }

    private void update() {

        // TODO : update
        updatePlumberPosition();
    }

    List<Brick> bricks = new ArrayList<>();
    private void updatePlumberPosition() {
        // dt
        plumber.incrementVT();

        // tempPX
        plumber.setTempPX(
                plumber.getPX() + plumber.getVX()
        );

        // tempPY
        plumber.setTempPY(
                plumber.getPY() + plumber.getVY()
        );


        int tempPlumberRow = plumber.getTempRow();
        int tempPlumberColumn = plumber.getTempColumn();
//        Log.d(TAG, "updatePlumberPosition: tempPlumber [row, column] # ["+tempPlumberRow+", "+tempPlumberColumn+"]");

        int dx = plumber.getDX();
        int dy = plumber.getDY();

        // count bricks
        bricks.clear();
        for(Brick brick : map.getBrickList()) {
            int brickRow = brick.getRow();
            int brickColumn = brick.getColumn();
//            Log.d(TAG, "updatePlumberPosition: brick [left, right], plumber [left, right] # ["+brick.getLeft()+", "+brick.getRight()+"], ["+plumber.getLeft()+", "+plumber.getRight()+"]");
//            Log.d(TAG, "updatePlumberPosition: brick [top, bottom], plumber [top, bttom] # ["+brick.getTop()+", "+brick.getBottom()+"], ["+plumber.getTop()+", "+plumber.getBottom()+"]");

            if( (tempPlumberRow - brickRow == 0 || tempPlumberRow - brickRow == -1)
                    && (tempPlumberColumn - brickColumn == 0 || tempPlumberColumn - brickColumn == -1) ) {
                // check bricks

            }
        }

//        Log.d(TAG, "updatePlumberPosition: bricks Size # "+bricks.size());

        // confirm plumber's position
        plumber.confirmPosition();
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
        canvas.drawBitmap(map.getImage(), new Matrix(), null);
    }

    private void drawPlumber(Canvas canvas) {
        canvas.drawBitmap(plumber.getImage(), plumber.getPX(), plumber.getPY(), null);
//        Log.d(TAG, "drawPlumber: p [x, y] # ["+plumber.getPX()+", "+plumber.getPY()+"]");
    }

    public void update(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                plumber.jump();
                break;
        }
    }

    /**
     * @param sensorEvent : https://developer.android.com/reference/android/hardware/SensorEvent.html
     */
    public void update(SensorEvent sensorEvent) {
        plumber.setVX(-sensorEvent.values[0]);
    }

    private void sendEvent(String msg) {
        mListener.onEvent(msg);
    }
}
