package com.syun.and.whiteoutmaze.activites;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.syun.and.whiteoutmaze.R;
import com.syun.and.whiteoutmaze.views.GameView;

public class MainActivity extends BaseActivity implements View.OnTouchListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private GameView mGameView;

    private TextView mStageTextView;
    private ImageView mUpArrowKey, mLeftArrowKey, mRightArrowKey, mDownArrowKey;

//    private MediaPlayer bgm;

    /*
        here is life cycle.

        1. onResume:
        2. surfaceCreated:
        3. surfaceChanged:

        4. onPause:
        5. surfaceDestroyed:
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureUI();
//        bgm = MediaPlayer.create(this,  R.raw.bgm);
//        bgm.setLooping(true);
    }

    private void configureUI() {
        mGameView = findViewById(R.id.gameView);
        mGameView.init();

        mStageTextView = findViewById(R.id.stageTextView);
        mStageTextView.setText("Stage 1");

        mUpArrowKey = findViewById(R.id.upArrowKeyImageView);
        mUpArrowKey.setOnTouchListener(this);

        mLeftArrowKey = findViewById(R.id.leftArrowKeyImageView);
        mLeftArrowKey.setOnTouchListener(this);

        mRightArrowKey = findViewById(R.id.rightArrowKeyImageView);
        mRightArrowKey.setOnTouchListener(this);

        mDownArrowKey = findViewById(R.id.downArrowKeyImageView);
        mDownArrowKey.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bgm.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        bgm.stop();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                isArrowKeyPressed(view.getId(), true);
                break;

            case MotionEvent.ACTION_UP :
                isArrowKeyPressed(view.getId(), false);
                break;
        }
        return true;
    }

    private void isArrowKeyPressed(int id, boolean b) {
        switch (id) {
            case R.id.upArrowKeyImageView :
                mGameView.setUpArrowKeyPressed(b);
                break;

            case R.id.leftArrowKeyImageView :
                mGameView.setLeftArrowKeyPressed(b);
                break;

            case R.id.rightArrowKeyImageView :
                mGameView.setRightArrowKeyPressed(b);
                break;

            case R.id.downArrowKeyImageView :
                mGameView.setDownArrowKeyPressed(b);
                break;
        }
    }

}
