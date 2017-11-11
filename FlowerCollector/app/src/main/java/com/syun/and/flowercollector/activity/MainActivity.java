package com.syun.and.flowercollector.activity;

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

import com.syun.and.flowercollector.R;
import com.syun.and.flowercollector.listener.OnGameEventListener;
import com.syun.and.flowercollector.views.GameView;

public class MainActivity extends BaseActivity implements OnGameEventListener, View.OnTouchListener, SensorEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GameView mGameView;

    /** センサーマネージャオブジェクト */
    private SensorManager mSensorManager;

    /** 光度センサーオブジェクト */
    private Sensor mLightSensor;

    private boolean isLightSensorRegistered;

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

        // センサーマネージャを獲得する
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // マネージャから光度センサーオブジェクトを取得
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }
    @Override
    public void onEvent(String msg) {

        switch (msg) {
            case OnGameEventListener.CREATE:
                // Touch時にTouchEventを取得するリスなを登録
                mGameView.setOnTouchListener(this);
                break;

            case OnGameEventListener.DESTROY :
                // 非アクティブ時にTouchEventを取らないようにリスなの登録解除
                mGameView.setOnTouchListener(null);
                break;

            case OnGameEventListener.REGISTER_LIGHT_SENSOR :
                // 200msに一度SensorEventを観測するリスナを登録
                mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                isLightSensorRegistered = true;
                break;

            case OnGameEventListener.UNREGISTER_LIGHT_SENSOR :
                // 非アクティブ時にSensorEventをとらないようにリスナの登録解除
                mSensorManager.unregisterListener(this);
                isLightSensorRegistered = false;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isLightSensorRegistered) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isLightSensorRegistered) {
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * @param sensorEvent : https://developer.android.com/reference/android/hardware/SensorEvent.html
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 光度センサの場合、以下の処理を実行
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            mGameView.update(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mGameView.update(motionEvent);
        return true;
    }
}
