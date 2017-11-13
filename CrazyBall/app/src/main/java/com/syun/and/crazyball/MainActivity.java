package com.syun.and.crazyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements OnGameEventListener, View.OnTouchListener {

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        mGameView = findViewById(R.id.gameView);
        mGameView.init(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mGameView.update(motionEvent);
        return true;
    }

    @Override
    public void onEvent(String msg) {
        switch (msg) {
            case OnGameEventListener.CREATE:
                // Touch時にTouchEventを取得するリスなを登録
                mGameView.setOnTouchListener(this);
                break;

            case OnGameEventListener.DESTROY:
                // 非アクティブ時にTouchEventを取らないようにリスなの登録解除
                mGameView.setOnTouchListener(null);
                break;
        }
    }
}
