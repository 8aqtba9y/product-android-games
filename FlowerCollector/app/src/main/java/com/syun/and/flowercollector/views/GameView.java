package com.syun.and.flowercollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;
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

    private Bitmap keyboardImage;
    private Bitmap tapImage;

    private Bitmap character_e;
    private Bitmap character_w;
    private Bitmap character_n;
    private Bitmap character_ne;
    private Bitmap character_nw;
    private Bitmap character_s;
    private Bitmap character_se;
    private Bitmap character_sw;

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

    private float characterX, characterY;
    private float characterRelativeX;
    private float mapX, mapY;
    private int mapWidth, mapHeight;
    private int characterWidth, characterHeight;
    private Bitmap map;

    private void initComponents() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        keyboardImage = BitmapFactory.decodeResource(getResources(), R.drawable.keyboard, options);
        keyboardImage = Bitmap.createScaledBitmap(keyboardImage, mSquareWidth * 2, mSquareHeight * 2, true);

        tapImage = BitmapFactory.decodeResource(getResources(), R.drawable.tap, options);
        tapImage = Bitmap.createScaledBitmap(tapImage, mSquareWidth, mSquareHeight, true);

        characterX = mSurfaceWidth / 2;
        characterY = mSurfaceHeight / 2;

        mapX = - mSurfaceWidth / 2;
        mapY = 0;

        mapWidth = mSurfaceWidth * 2;
        mapHeight = mSurfaceHeight;

        characterWidth = mSquareWidth;
        characterHeight = mSquareHeight;

        character_e = BitmapFactory.decodeResource(getResources(), R.drawable.character_e, options);
        character_e = Bitmap.createScaledBitmap(character_e, characterWidth, characterHeight, true);

        character_w = BitmapFactory.decodeResource(getResources(), R.drawable.character_w, options);
        character_w = Bitmap.createScaledBitmap(character_w, characterWidth, characterHeight, true);

        character_n = BitmapFactory.decodeResource(getResources(), R.drawable.character_n, options);
        character_n = Bitmap.createScaledBitmap(character_n, characterWidth, characterHeight, true);

        character_ne = BitmapFactory.decodeResource(getResources(), R.drawable.character_ne, options);
        character_ne = Bitmap.createScaledBitmap(character_ne, characterWidth, characterHeight, true);

        character_nw = BitmapFactory.decodeResource(getResources(), R.drawable.character_nw, options);
        character_nw = Bitmap.createScaledBitmap(character_nw, characterWidth, characterHeight, true);

        character_s = BitmapFactory.decodeResource(getResources(), R.drawable.character_s, options);
        character_s = Bitmap.createScaledBitmap(character_s, characterWidth, characterHeight, true);

        character_se = BitmapFactory.decodeResource(getResources(), R.drawable.character_se, options);
        character_se = Bitmap.createScaledBitmap(character_se, characterWidth, characterHeight, true);

        character_sw = BitmapFactory.decodeResource(getResources(), R.drawable.character_sw, options);
        character_sw = Bitmap.createScaledBitmap(character_sw, characterWidth, characterHeight, true);

        map = BitmapFactory.decodeResource(getResources(), R.drawable.map, options);
        map = Bitmap.createScaledBitmap(map, mapWidth, mapHeight, true);

        // if(character == null) { ... }
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

        // draw Map
        drawMap(canvas);

        // draw plumbing
        drawCharacter(canvas);

        // draw plumber
//        drawPlumber(canvas);

        // draw drop
//        drawDrop(canvas);

        // draw wave;
//        drawWave(canvas);

        // draw keyboard
        if(shouldKeyboardShow)
            drawKeyboard(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawMap(Canvas canvas) {
        canvas.drawBitmap(map, mapX, mapY, null);
    }

    private void drawKeyboard(Canvas canvas) {
        canvas.drawBitmap(keyboardImage, preX - keyboardImage.getWidth() / 2, preY - keyboardImage.getHeight() / 2, null);
        canvas.drawBitmap(tapImage, postX - tapImage.getWidth() / 2, postY - tapImage.getHeight() / 2, null);
    }

    private int direction = 0x00;

    private void drawCharacter(Canvas canvas) {
        switch (direction) {
            case 0x00 : // 中央
                canvas.drawBitmap(character_s, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x01 : // 右
                canvas.drawBitmap(character_e, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x02 : // 左
                canvas.drawBitmap(character_w, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x10 : // 下
                canvas.drawBitmap(character_s, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x20 : // 上
                canvas.drawBitmap(character_n, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x11 : // 下右
                canvas.drawBitmap(character_se, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x12 : // 下左
                canvas.drawBitmap(character_sw, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x21 : // 上右
                canvas.drawBitmap(character_ne, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;

            case 0x22 : // 上左
                canvas.drawBitmap(character_nw, characterX - characterWidth / 2, characterY - characterHeight / 2, null);
                break;
        }
    }

    private void sendEvent(String msg) {
        mListener.onEvent(msg);
    }

    public void update(SensorEvent sensorEvent) {
        int lux = (int) sensorEvent.values[0];
        Log.d(TAG, "update: lux # "+lux);
    }

    public void update(MotionEvent motionEvent) {
        if(shouldKeyboardShow(motionEvent)) {
            showKeyboard(motionEvent);
        } else {
            // TODO : interact
        }
    }

    private float preX, preY, postX, postY, disX, disY;

    private boolean shouldKeyboardShow;
    private void showKeyboard(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                shouldKeyboardShow = true;
                preX = motionEvent.getX(0);
                preY = motionEvent.getY(0);
                break;

            case MotionEvent.ACTION_MOVE:
                postX = motionEvent.getX(0);
                postY = motionEvent.getY(0);
                disX = postX - preX;
                disY = postY - preY;

                updateCharacterRelativePosition();
                break;

            case MotionEvent.ACTION_UP:
                shouldKeyboardShow = false;
                preX = -1;
                preY = -1;
                postX = -1;
                postY = -1;
                disX = 0;
                disY = 0;
                break;
        }
    }

    private void updateCharacterRelativePosition() {
        direction = 0x00;
        if(disY > mSquareHeight / 4) { // 下
            direction = 0x10 ;
            characterY++;
            if(characterY > mSurfaceHeight - mSquareHeight) {
                characterY = mSurfaceHeight - mSquareHeight;
            }
        } else if(disY < -mSquareHeight / 4) { // 上
            direction = 0x20 ;
            characterY--;
            if(characterY < mSquareHeight) {
                characterY = mSquareHeight;
            }
        }

        if(disX > mSquareWidth / 4) { // 右
            direction = direction + 0x01;
//            characterX++;
            mapX--;
            if(mapX < - mapWidth / 2) {
                mapX = - mapWidth / 2;
            }
        } else if(disX < -mSquareWidth / 4) { // 左
            direction = direction + 0x02;
//            characterX--;
            mapX++;
            if(mapX > 0) {
                mapX = 0;
            }
        }
    }

    private boolean shouldKeyboardShow(MotionEvent motionEvent) {
        return true;
    }
}
