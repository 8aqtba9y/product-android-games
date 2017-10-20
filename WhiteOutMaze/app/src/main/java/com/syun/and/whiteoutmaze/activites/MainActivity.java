package com.syun.and.whiteoutmaze.activites;

import android.os.Bundle;

import com.syun.and.whiteoutmaze.R;
import com.syun.and.whiteoutmaze.views.GameView;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameView = findViewById(R.id.gameView);
    }
}
