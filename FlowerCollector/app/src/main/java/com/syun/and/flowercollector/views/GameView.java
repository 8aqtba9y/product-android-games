package com.syun.and.flowercollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;
import com.syun.and.flowercollector.db.model.SeedModel;
import com.syun.and.flowercollector.listener.OnGameEventListener;
import com.syun.and.flowercollector.model.GameModel;

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

    private GameModel gameModel;

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
//        sendEvent(OnGameEventListener.REGISTER_LIGHT_SENSOR);
    }

    Bitmap seedImage;

    private Paint black;

    private void initComponents() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        // init gameModel
        if(gameModel == null) {
            gameModel = new GameModel(mContext, mSurfaceWidth, mSurfaceHeight);

            seedImage = BitmapFactory.decodeResource(getResources(), R.drawable.seed, options);
            seedImage = Bitmap.createScaledBitmap(seedImage, mSquareWidth, mSquareHeight, true);

            black = new Paint();
            black.setColor(Color.BLACK);
            black.setStyle(Paint.Style.STROKE);
            black.setStrokeWidth(5f);
        }
    }

    public void startDrawing(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;

        if(mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(this);

        // TODO : save
        gameModel.save();

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
        if(gameModel.getKeyboard().shouldShow())
            drawKeyboard(canvas);

        // handle lux
        drawFilter(canvas);

        drawDebugLines(canvas);

        drawSeed(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawSeed(Canvas canvas) {
        for (SeedModel seed : gameModel.getSeeds()) {
            float left = seed.getCX() + gameModel.getMap().getLeft();
            float top = seed.getCY();

            if(!(left < 0) || !(left > mSurfaceWidth)) {
                canvas.drawBitmap(seedImage,  left, top, null);
            }
        }
    }

    private void drawDebugLines(Canvas canvas) {
        for (int i = 0; i < Const.COLUMN; i++) {
            canvas.drawLine(i*mSquareWidth, 0, i*mSquareWidth, mSurfaceHeight, black);
        }
        for (int j = 0; j < Const.ROW; j++) {
            canvas.drawLine(0, j*mSquareHeight, mSurfaceWidth, j*mSquareHeight, black);
        }
    }

    private int lux;
    private void drawFilter(Canvas canvas) {
//        canvas.drawARGB(lux, 0,0, 0);
    }

    private void drawMap(Canvas canvas) {
        canvas.drawBitmap(gameModel.getMap().getImage(), gameModel.getMap().getLeft(), gameModel.getMap().getTop(), null);
    }

    private void drawKeyboard(Canvas canvas) {
        canvas.drawBitmap(gameModel.getKeyboard().getKeyboardImage()
                , gameModel.getKeyboard().getKeyboardCX()
                , gameModel.getKeyboard().getKeyboardCY()
                , null);

        canvas.drawBitmap(gameModel.getKeyboard().getTapImage()
                , gameModel.getKeyboard().getTapCX()
                , gameModel.getKeyboard().getTapCY()
                , null);
    }

    private void drawCharacter(Canvas canvas) {
        canvas.drawBitmap(gameModel.getCharacter().getImage()
                , gameModel.getCharacter().getCX()
                , gameModel.getCharacter().getCY()
                , null);
    }

    private void sendEvent(String msg) {
        mListener.onEvent(msg);
    }

    public void update(SensorEvent sensorEvent) {
        lux = 255 - (int) sensorEvent.values[0] < 0 ? 0 : 255 - (int) sensorEvent.values[0];
        Log.d(TAG, "update: lux # "+lux);
    }

    public void update(MotionEvent motionEvent) {
        gameModel.parse(motionEvent);
    }

}
