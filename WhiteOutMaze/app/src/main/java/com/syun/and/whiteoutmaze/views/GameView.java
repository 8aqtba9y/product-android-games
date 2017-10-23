package com.syun.and.whiteoutmaze.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.syun.and.whiteoutmaze.R;
import com.syun.and.whiteoutmaze.common.map.Map;
import com.syun.and.whiteoutmaze.common.map.Map_1;
import com.syun.and.whiteoutmaze.util.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qijsb on 2017/10/21.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback2, View.OnTouchListener, Runnable {
    private static final String TAG = GameView.class.getSimpleName();

    private Logger mLogger;

    private ExecutorService mExecutor;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private float catX, catY;
    private float homeX, homeY;

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
        mLogger = new Logger(TAG);

        mExecutor = Executors.newSingleThreadExecutor();

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mLogger.print("surfaceCreated: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        squareSize = width / COLUMNS;

        mLogger.print("surfaceChanged: [width, height, squareSize] # ["+width+", "+height+", "+squareSize+"]");

        initComponents();

        startDrawing(holder, width, height);
    }

    private Map map;

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

            catX = 0 * squareSize;
            catY = 0 * squareSize;

            homeX = 4 * squareSize;
            homeY = 4 * squareSize;

            map = new Map_1(squareSize);
            // TODO : create cat's footPrints bitmap
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mLogger.print("surfaceDestroyed: ");
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

    private void update() {
        if(shouldMove) {
            if(isUpArrowKeyPressed)
                catY = (catY - catSpeed) < 0 ? 0 : (catY - catSpeed);

            if(isLeftArrowKeyPressed)
                catX = (catX - catSpeed) < 0 ? 0 : (catX - catSpeed);

            if(isRightArrowKeyPressed)
                catX = (catX + catSpeed) > mSurfaceWidth ? mSurfaceWidth : (catX + catSpeed);

            if(isDownArrowKeyPressed)
                catY = (catY + catSpeed) > mSurfaceHeight ? mSurfaceHeight : (catY + catSpeed);
        }
    }

    private void present(Canvas canvas){
        canvas.drawBitmap(map.getMap(), new Matrix(), null);

        canvas.drawBitmap(cat, catX - cat.getWidth() / 2, catY - cat.getHeight() / 2, null);

        canvas.drawBitmap(home, homeX - home.getWidth() / 2, homeY - home.getHeight() / 2, null);
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
            // update
            update();

            // switch to present
            Canvas canvas = mSurfaceHolder.lockCanvas();
            present(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);

            // check validation
            if(checkValidation()) {
                canvas = mSurfaceHolder.lockCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText("GG!", 6*squareSize, 6*squareSize, paint);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;
            }

            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkValidation() {
        int pixel = map.getMap().getPixel((int)catX, (int)catY);
        mLogger.print(""+pixel);
        if(pixel != Color.WHITE) {
            return true;
        }

        double distance = Math.sqrt(
                Math.pow(homeX - catX, 2)
                + Math.pow(homeY - catY, 2)
        );

        return homeSize / 2 > distance;
    }

}
