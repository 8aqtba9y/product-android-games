package com.syun.and.whiteoutmaze.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.syun.and.whiteoutmaze.Const;
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

    private final static int FPS = 1000 / 30;

    private Logger mLogger;

    private ExecutorService mExecutor;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private Map map;

    private float catX, catY;
    private float homeX, homeY;

    private Context mContext;

    private int catSize, footprintSize, homeSize;
    private int catSpeed;

    private Bitmap cat, footprint, home;

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
        squareSize = width / Const.COLUMN;

        mLogger.print("surfaceChanged: [width, height, squareSize] # ["+width+", "+height+", "+squareSize+"]");

        initComponents();

        startDrawing(holder, width, height);
    }

    private Paint blackText;

    private void initComponents() {
        if(cat == null && home == null) {
            blackText = new Paint();
            blackText.setColor(Color.BLACK);
            blackText.setStyle(Paint.Style.FILL);
            blackText.setTextSize(squareSize/3);

            // TODO : set factors
            catSize = squareSize * 2 / 3;
            footprintSize = squareSize / 2;
            catSpeed = squareSize / 16;

            homeSize = squareSize / 1;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
            cat = Bitmap.createScaledBitmap(cat, catSize, catSize, true);

            footprint = BitmapFactory.decodeResource(getResources(), R.drawable.footprint, options);
            footprint = Bitmap.createScaledBitmap(footprint, footprintSize, footprintSize, true);

            home = BitmapFactory.decodeResource(getResources(), R.drawable.home, options);
            home = Bitmap.createScaledBitmap(home, homeSize, homeSize, true);

            catX = 0 * squareSize + squareSize / 2;
            catY = 0 * squareSize + squareSize / 2;

            homeX = 6 * squareSize + squareSize / 2;
            homeY = 3 * squareSize + squareSize / 2;

            map = new Map_1(squareSize);
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

    int count = 0;
    private void present(Canvas canvas) {
        count++;

        if (count > 100) {
            canvas.drawColor(Color.WHITE);
        } else {
            // draw map
            canvas.drawBitmap(map.getMap(), new Matrix(), null);

            if(count < 33) {
                canvas.drawText("3!", 6*squareSize + squareSize/4, 5*squareSize + squareSize/4, blackText);
            } else if(count < 66) {
                canvas.drawText("2!", 6*squareSize + squareSize/4, 5*squareSize + squareSize/4, blackText);
            } else {
                canvas.drawText("1!", 6*squareSize + squareSize/4, 5*squareSize + squareSize/4, blackText);
            }
        }

        // draw footprints

        // draw cat
        canvas.drawBitmap(cat, catX - cat.getWidth() / 2, catY - cat.getHeight() / 2, null);

        // draw home
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
        if(count < 100) {
            return;
        }

        shouldMove = isUpArrowKeyPressed || isLeftArrowKeyPressed || isRightArrowKeyPressed || isDownArrowKeyPressed;
    }

    @Override
    public void run() {
        while(!mExecutor.isShutdown()) {
            // update
            update();

            // check validation
            if(checkValidation()) {
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                canvas.save();

                RectF rectF = new RectF(catX- squareSize*3,catY -squareSize*3,catX +squareSize*3,catY + squareSize*3);
                Path path = new Path();
                path.addRoundRect(rectF,squareSize*3,squareSize*3, Path.Direction.CW);

                canvas.clipPath(path);
                canvas.drawBitmap(map.getMap(), new Matrix(), null);
                canvas.restore();

                // draw cat
                canvas.drawBitmap(cat, catX - cat.getWidth() / 2, catY - cat.getHeight() / 2, null);

                // draw home
                canvas.drawBitmap(home, homeX - home.getWidth() / 2, homeY - home.getHeight() / 2, null);

                canvas.drawText("GG!", 6*squareSize + squareSize/4, 5*squareSize + squareSize/4, blackText);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;
            }

            // switch to present
            Canvas canvas = mSurfaceHolder.lockCanvas();
            present(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkValidation() {
        int pixel = map.getMap().getPixel((int)catX, (int)catY);
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
