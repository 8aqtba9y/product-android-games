package com.syun.and.fixplumbing.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.syun.and.fixplumbing.views.GameView;
import com.syun.and.fixplumbing.listener.OnGameEventListener;
import com.syun.and.fixplumbing.R;

public class MainActivity extends AppCompatActivity implements OnGameEventListener, View.OnTouchListener, SensorEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GameView mGameView;

    /** センサーマネージャオブジェクト */
    private SensorManager mSensorManager;

    /** 加速度センサーオブジェクト */
    private Sensor mAccelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        mGameView = findViewById(R.id.gameView);
        mGameView.init(this);

        // センサーマネージャを獲得する
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // マネージャから加速度センサーオブジェクトを取得
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mGameView.update(motionEvent);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 加速度センサの場合、以下の処理を実行
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGameView.update(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onEvent(String msg) {
        switch (msg) {
            case OnGameEventListener.CREATE :
                // 200msに一度SensorEventを観測するリスナを登録
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
                // Touch時にTouchEventを取得するリスなを登録
                mGameView.setOnTouchListener(this);
                break;

            case OnGameEventListener.DESTROY :
                // 非アクティブ時にSensorEventをとらないようにリスナの登録解除
                mSensorManager.unregisterListener(this);
                // 非アクティブ時にTouchEventを取らないようにリスなの登録解除
                mGameView.setOnTouchListener(null);
                break;
        }

    }
}
