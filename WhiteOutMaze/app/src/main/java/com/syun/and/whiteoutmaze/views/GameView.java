package com.syun.and.whiteoutmaze.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.syun.and.whiteoutmaze.Const;
import com.syun.and.whiteoutmaze.R;
import com.syun.and.whiteoutmaze.common.map.Map;
import com.syun.and.whiteoutmaze.common.map.Map_1;
import com.syun.and.whiteoutmaze.common.unit.Home;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qijsb on 2017/10/21.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback2, View.OnTouchListener, Runnable {
    private static final String TAG = GameView.class.getSimpleName();

    private final static int INTERVAL = 1000 / Const.FPS;

    private Context mContext;

    private ExecutorService mExecutor;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private Map map;
    private Home home;

    private int catX, catY;

    private int catSize, footprintSize;
    private int catSpeed;

    private int steps;
    private Bitmap cat, footprint, track;

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
     * here is life cycle.
     *
     * 1. handle touch events
     * 2. updates
     * 3. present
     *
     * loop { 1.2.3. }
     */
    public void init() {
        mExecutor = Executors.newSingleThreadExecutor();

        getHolder().addCallback(this);
    }

    /* begin temp code (1)  */
    private int lifeCount = 3;
    private ImageView life1, life2, life3;
    public void setLife(ImageView life1, ImageView life2, ImageView life3) {
        this.life1 = life1;
        this.life2 = life2;
        this.life3 = life3;
    }
    /* end of temp code (1) */

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
        squareSize = width / Const.COLUMN;

        initComponents();

        startDrawing(holder);
    }

    private void initComponents() {
        if(cat == null && home == null) {
            /* begin temp code (2) */
            catSize = squareSize * 2 / 3;
            footprintSize = squareSize / 4;
            catSpeed = squareSize / 18;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            // init cat
            cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
            cat = Bitmap.createScaledBitmap(cat, catSize, catSize, true);

            catX = 1 * squareSize + squareSize / 2;
            catY = 1 * squareSize + squareSize / 2;

            footprint = BitmapFactory.decodeResource(getResources(), R.drawable.footprint, options);
            footprint = Bitmap.createScaledBitmap(footprint, footprintSize, footprintSize, true);

            // init track
            track = Bitmap.createBitmap(mSurfaceWidth, mSurfaceHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(track);
            canvas.drawColor(Color.TRANSPARENT);
            /* end of temp code (2) */

            // init home
            home = new Home(mContext, squareSize);

            // init map
            map = new Map_1(squareSize);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopDrawing();
    }

    public void startDrawing(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;
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
        // TODO : show key board
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

            /* begin temp code (3) */
            if(++steps == 10) {
                addTrack();
                steps = 0;
            }
            /* end of temp code (3) */
        }
    }

    private void addTrack() {
        Canvas canvas = new Canvas(track);
        canvas.drawBitmap(footprint, catX - (footprintSize / 2), catY - (footprintSize / 2), null);
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


    boolean willStart = true;

    @Override
    public void run() {
        while(!mExecutor.isShutdown()) {
            // update
            update();

            // if start
            if(willStart) {
                for (int progress = 0; progress < 133; progress++) {
                    start(progress);

                    sleep();
                }
                willStart = false;
            }

            // if dead
            if(willDead()) {
                switch (lifeCount) {
                    case 3:
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                life1.setImageResource(R.drawable.death);
                            }
                        });
                        break;

                    case 2 :
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                life2.setImageResource(R.drawable.death);
                            }
                        });
                        break;

                    case 1 :
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                life3.setImageResource(R.drawable.death);
                            }
                        });
                        break;
                }
                lifeCount--;

                for (int progress = 0; progress < 133; progress++) {
                    dead(progress);

                    sleep();
                }

                if(lifeCount == 0) {
                    /* begin temp code (n) */
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((Activity) mContext).finish();
                    /* end of temp code (n) */
                }

                catX = 1 * squareSize + squareSize / 2;
                catY = 1 * squareSize + squareSize / 2;
                track = Bitmap.createBitmap(mSurfaceWidth, mSurfaceHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(track);
                canvas.drawColor(Color.TRANSPARENT);
                for (int progress = 0; progress < 67; progress++) {
                    restart(progress);

                    sleep();
                }
            }

            // if goal
            if(willFinish()) {
                for (int progress = 0; progress < Const.COLUMN * 2; progress++) {
                    finish(progress);

                    sleep();
                }
                /* begin temp code (n) */
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((Activity) mContext).finish();
                /* end of temp code (n) */
                break;
            }

            // present
            present();

            // sleep
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void start(int progress) {
        Canvas canvas = mSurfaceHolder.lockCanvas();

        // draw map with start anim
        if(progress > 67) {
            canvas.drawColor(Color.WHITE);
            canvas.save();

            float radius = catSize * (133 - progress);

            float left = catX - radius;
            float top = catY - radius;
            float right = catX + radius;
            float bottom = catY + radius;

            RectF rectF = new RectF(left, top, right, bottom);
            Path path = new Path();
            path.addRoundRect(rectF, radius, radius, Path.Direction.CW);

            canvas.clipPath(path);
            canvas.drawBitmap(map.getImage(), new Matrix(), null);
            canvas.restore();
        } else  {
            canvas.drawBitmap(map.getImage(), new Matrix(), null);
        }

        // draw cat
        drawCat(canvas);

        // draw home
        drawHome(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void restart(int progress) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        // draw track
        drawTrack(canvas);

        if(progress < 13) {

        } else if(progress < 26) {
            // draw cat
            drawCat(canvas);
        } else if(progress < 39) {

        } else if(progress < 52) {
            // draw cat
            drawCat(canvas);
        } else {

        }
        // draw home
        drawHome(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private boolean willFinish() {
        double distance = Math.sqrt(
                Math.pow(home.getPX() - catX, 2)
                        + Math.pow(home.getPY() - catY, 2)
        );
        return home.getSize() / 2 > distance;
    }

    private boolean willDead() {
        int x = catX == map.getImage().getWidth() ? catX - 1 : catX; // to use get pixel
        int y = catY == map.getImage().getHeight() ? catY - 1 : catY; // to use get pixel
        int pixel = map.getImage().getPixel(x, y);
        return pixel == Color.BLACK;
    }

    private void dead(int progress) {
        if(progress < 50) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            canvas.save();

            float radius = catSpeed * progress;

            float left = catX - radius;
            float top = catY - radius;
            float right = catX + radius;
            float bottom = catY + radius;

            RectF rectF = new RectF(left, top, right, bottom);
            Path path = new Path();
            path.addRoundRect(rectF, radius, radius, Path.Direction.CW);

            canvas.clipPath(path);
            canvas.drawBitmap(map.getImage(), new Matrix(), null);
            canvas.restore();

            // draw track
            drawTrack(canvas);

            // draw cat
            drawCat(canvas);

            // draw home
            drawHome(canvas);

            mSurfaceHolder.unlockCanvasAndPost(canvas);
        } else if(progress > 83) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.save();

            float radius = catSpeed * (133 - progress);

            float left = catX - radius;
            float top = catY - radius;
            float right = catX + radius;
            float bottom = catY + radius;

            RectF rectF = new RectF(left, top, right, bottom);
            Path path = new Path();
            path.addRoundRect(rectF, radius, radius, Path.Direction.CW);

            canvas.clipPath(path);
            canvas.drawBitmap(map.getImage(), new Matrix(), null);
            canvas.restore();

            // draw track
            drawTrack(canvas);

            // draw cat
            drawCat(canvas);

            // draw home
            drawHome(canvas);

            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void finish(int progress) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        canvas.save();

        float radius = squareSize * progress;

        float left = catX - radius;
        float top = catY - radius;
        float right = catX + radius;
        float bottom = catY + radius;

        RectF rectF = new RectF(left, top, right, bottom);
        Path path = new Path();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);

        canvas.clipPath(path);
        canvas.drawBitmap(map.getImage(), new Matrix(), null);
        canvas.restore();

        // draw track
        drawTrack(canvas);

        // draw cat
        drawCat(canvas);

        // draw home
        drawHome(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }


    private void present() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        // draw track
        drawTrack(canvas);

        // draw cat
        drawCat(canvas);

        // draw home
        drawHome(canvas);

        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawTrack(Canvas canvas) {
        canvas.drawBitmap(track, new Matrix(), null);
    }

    private void drawCat(Canvas canvas) {
        canvas.drawBitmap(cat, catX - cat.getWidth() / 2, catY - cat.getHeight() / 2, null);
    }

    private void drawHome(Canvas canvas) {
        canvas.drawBitmap(home.getImage(),
                home.getPX() - home.getSize() / 2,
                home.getPY() - home.getSize() / 2,
                null);
    }
}
