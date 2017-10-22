package com.syun.and.whiteoutmaze.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.syun.and.whiteoutmaze.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qijsb on 2017/10/21.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback2, View.OnTouchListener, Runnable {
    private static final String TAG = GameView.class.getSimpleName();

    private ExecutorService mExecutor;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private float catX, catY;

    private Context mContext;

    private final static int FPS = 1000 / 30;
    private static final int COLUMNS = 10;

    private int catSize;
    private int homeSize;
    private int catSpeed;

    private Bitmap cat, home;

    private int squareSize;
    private boolean shouldMove;

    private boolean isUpArrowKeyPressed;
    private boolean isLeftArrowKeyPressed;
    private boolean isRightArrowKeyPressed;
    private boolean isDownArrowKeyPressed;

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
     * 1. handle touch events
     * 2. update members
     *
     * loop { draw with members }
     */
    public void init() {
        mExecutor = Executors.newSingleThreadExecutor();

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: [width, height] # ["+width+", "+height+"]");

        squareSize = width / COLUMNS;
        Log.d(TAG, "surfaceChanged: squareSize # "+squareSize);

        initComponents();

        startDrawing(holder, width, height);
    }

    private void initComponents() {
        if(cat == null && home == null) {
            // TODO : set factors
            catSize = squareSize / 2;
            homeSize = squareSize / 1;
            catSpeed = squareSize / 12;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
            cat = Bitmap.createScaledBitmap(cat, catSize, catSize, true);

            home = BitmapFactory.decodeResource(getResources(), R.drawable.home, options);
            home = Bitmap.createScaledBitmap(home, homeSize, homeSize, true);

            // TODO : create cat's footPrints bitmap
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed: ");
        stopDrawing();
    }

    public void startDrawing(SurfaceHolder holder, int width, int height) {
        this.mSurfaceHolder = holder;
        this.mSurfaceWidth = width;
        this.mSurfaceHeight = height;
        setOnTouchListener(this);

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        showKeyBoard(motionEvent);
        return true;
    }

    private void showKeyBoard(MotionEvent motionEvent) {
        // TODO : show keyBoard
    }

    private void configure(Canvas canvas) {
        // TODO : draw a Map

        canvas.drawColor(Color.WHITE);

        if(shouldMove) {
            if(isUpArrowKeyPressed)
                catY = catY - catSpeed;

            if(isLeftArrowKeyPressed)
                catX = catX - catSpeed;

            if(isRightArrowKeyPressed)
                catX = catX + catSpeed;

            if(isDownArrowKeyPressed)
                catY = catY + catSpeed;

            // TODO : check - is cat position valid?
        }

        canvas.drawBitmap(cat, catX - cat.getWidth() / 2, catY - cat.getHeight() / 2, null);

        canvas.drawBitmap(home, 400 - home.getWidth() / 2, 400 - home.getHeight() / 2, null);
    }

    public void setUpArrowKeyPressed(boolean b){
        this.isUpArrowKeyPressed = b;
        shouldMove();
    }

    public void setLeftArrowKeyPressed(boolean b){
        this.isLeftArrowKeyPressed = b;
        shouldMove();
    }

    public void setRightArrowKeyPressed(boolean b){
        this.isRightArrowKeyPressed = b;
        shouldMove();
    }

    public void setDownArrowKeyPressed(boolean b) {
        this.isDownArrowKeyPressed = b;
        shouldMove();
    }

    private void shouldMove() {
        shouldMove = isUpArrowKeyPressed || isLeftArrowKeyPressed || isRightArrowKeyPressed || isDownArrowKeyPressed;
    }

    @Override
    public void run() {
        while(!mExecutor.isShutdown()) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            configure(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
