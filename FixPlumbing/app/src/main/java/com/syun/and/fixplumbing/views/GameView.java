package com.syun.and.fixplumbing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.common.unit.Brick;
import com.syun.and.fixplumbing.common.unit.Drop;
import com.syun.and.fixplumbing.common.unit.Drops;
import com.syun.and.fixplumbing.common.unit.Plumbing;
import com.syun.and.fixplumbing.common.unit.Wave;
import com.syun.and.fixplumbing.listener.OnGameEventListener;
import com.syun.and.fixplumbing.common.map.Map;
import com.syun.and.fixplumbing.common.unit.Plumber;

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
    private Drops drops;
    private Wave wave;

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

            // init drops
            drops = new Drops(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);

            // init wave
            wave = new Wave(mContext, mSurfaceWidth, mSurfaceHeight, mSquareWidth, mSquareHeight);
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
            // update Units
            update();

            // present
            present();

            // sleep
            sleep();
        }
    }

    private void update() {
        // drop
        updateDropPosition();

        // plumber
        updatePlumberPosition();

        // plumbing
        updatePlumbingGauge();

        // wave
        updateWavePosition();
    }

    private void updateWavePosition() {
        wave.incrementPY();
        if(wave.getPY() < 0) {
            endGame(OnGameEventListener.DEAD);
        }
        wave.incrementPX();
    }

    private void updatePlumbingGauge() {
        int tempPlumberRow = plumber.getTempRow();
        int tempPlumberColumn = plumber.getTempColumn();

        for(Plumbing plumbing : map.getPlumbingList()) {
            int plumbingRow = plumbing.getRow();
            int plumbingColumn = plumbing.getColumn();

            if(plumbingRow - tempPlumberRow == 0
                    && plumbingColumn - tempPlumberColumn == 0) {
                if (!plumbing.isFixed()) {
                    plumber.setFixing(true);
                    plumbing.incrementRepairGauge();
                }
            }
        }


        boolean isFixedAll = true;
        for(Plumbing plumbing : map.getPlumbingList()) {
            if(!plumbing.isFixed()) {
                isFixedAll = false;
                break;
            }
        }

        if(isFixedAll) {
            sendEvent(OnGameEventListener.CLEAR);
        }
    }

    private void updateDropPosition() {
        drops.addDrop();
        for (Drop drop : drops.getDrops()) {
            drop.setPY( drop.getPY() + drop.getVY());
        }

        drops.recycleDrops(wave.getPY(), plumber);
    }

    private void updatePlumberPosition() {
        // set default plumber
        plumber.setFixing(false);

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

        // check brick
        for(Brick brick : map.getBrickList()) {
            int brickRow = brick.getRow();
            int brickColumn = brick.getColumn();
//            Log.d(TAG, "updatePlumberPosition: brick [left, right], plumber [left, right] # ["+brick.getLeft()+", "+brick.getRight()+"], ["+plumber.getLeft()+", "+plumber.getRight()+"]");
//            Log.d(TAG, "updatePlumberPosition: brick [top, bottom], plumber [top, bttom] # ["+brick.getTop()+", "+brick.getBottom()+"], ["+plumber.getTop()+", "+plumber.getBottom()+"]");

            // TODO : too buggy
            if(tempPlumberRow - brickRow == -1
                    && (tempPlumberColumn - brickColumn == 0 || tempPlumberColumn - brickColumn == -1)
                    && plumber.getTempPY() + plumber.getHeight() >= brick.getTop()) {

                        plumber.setTempPY( brick.getTop() - plumber.getHeight() );
                        plumber.setVT(0);
                        plumber.setOnGround(true);
            }
        }

        // check ground
        if(plumber.getTempPY() == mSurfaceHeight - plumber.getHeight()) {
            plumber.setOnGround(true);
        }

        // check water
        if(plumber.getPY() > wave.getPY() + mSquareHeight / 2) {
            plumber.setUnderWater(true);
        } else {
            plumber.setUnderWater(false);
        }

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

        // draw background
        canvas.drawColor(Color.BLACK);

        // save and clipping
        saveAndClipping(canvas);

        // draw Map
        drawMap(canvas);

        // draw plumbing
        drawPlumbing(canvas);

        // draw plumber
        drawPlumber(canvas);

        // restoring
        canvas.restore();

        // draw drop
        drawDrop(canvas);

        // draw wave;
        drawWave(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void saveAndClipping(Canvas canvas) {
        // saving
        canvas.save();

        // clipping
        float radius = mSquareWidth * 4;

        float left = plumber.getCX() - radius;
        float top = plumber.getCY() - radius;
        float right = plumber.getCX() + radius;
        float bottom = plumber.getCY() + radius;

        RectF rectF = new RectF(left, top, right, bottom);
        Path path = new Path();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);

        canvas.clipPath(path);
    }

    private void drawMap(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(map.getImage(), new Matrix(), null);
    }

    private void drawPlumbing(Canvas canvas) {
        for(Plumbing plumbing : map.getPlumbingList()) {
            canvas.drawBitmap(plumbing.getImage(), plumbing.getPX(), plumbing.getPY(), null);
        }
    }

    private void drawPlumber(Canvas canvas) {
        // drawing
        canvas.drawBitmap(plumber.getImage(), plumber.getPX(), plumber.getPY(), null);
    }

    private void drawDrop(Canvas canvas) {
        for(Drop drop : drops.getDrops()) {
            canvas.drawBitmap(drop.getImage(), drop.getPX(), drop.getPY(), null);
        }
    }

    private void drawWave(Canvas canvas) {
        canvas.drawBitmap(wave.getImage(), wave.getPX(), wave.getPY(), null);
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

    public void endGame(String reason) {
        sendEvent(OnGameEventListener.DESTROY);

        if(!mExecutor.isShutdown()) {
            mExecutor.shutdownNow();
        }

        sendEvent(reason);
    }
}
