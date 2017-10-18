package com.syun.and.whiteoutmaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback2, View.OnTouchListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView mSurfaceView;

    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private float preX, preY, postX, postY, deltaX, deltaY;
    private float distance;
    private static final float THRESHOLD = 10.0f;

    private Path path;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurfaceView = findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(this);

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        // TODO : should stop drawing
        stopDrawing();
    }

    /*
        onResume:
        surfaceCreated:
        surfaceChanged:

        onPause:
        surfaceDestroyed:
     */

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");
        // TODO : should start drawing
        startDrawing(holder, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed: ");
    }

    private void startDrawing(SurfaceHolder holder, int width, int height) {
        this.mSurfaceHolder = holder;
        this.mSurfaceWidth = width;
        this.mSurfaceHeight = height;
        mSurfaceView.setOnTouchListener(this);
    }

    private void stopDrawing() {

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                path.reset();

                preX = motionEvent.getX();
                preY = motionEvent.getY();

                path.moveTo(preX, preY);

                draw();
                break;

            case MotionEvent.ACTION_MOVE :
            case MotionEvent.ACTION_UP :
                postX = motionEvent.getX();
                postY = motionEvent.getY();

                path.lineTo(postX, postY);

                deltaX = postX - preX;
                deltaY = postY - preY;
                distance = (float) Math.sqrt(
                        Math.pow(deltaX, 2) + Math.pow(deltaY, 2)
                );
                if(distance > THRESHOLD) {
                    // TODO : do not add path
                } else {
                    // TODO : add path
                }

                preX = postX;
                preY = postY;
                draw();
                break;
        }
        return true;
    }

    private void draw() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawPath(path, paint);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

}
