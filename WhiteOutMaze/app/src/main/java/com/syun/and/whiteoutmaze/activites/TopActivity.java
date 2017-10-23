package com.syun.and.whiteoutmaze.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.syun.and.whiteoutmaze.R;

public class TopActivity extends BaseActivity implements View.OnClickListener{

    private Button mSettingButton;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mSettingButton = findViewById(R.id.settingButton);
        mSettingButton.setOnClickListener(this);
        mStartButton = findViewById(R.id.startButton);
        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingButton :
                Toast.makeText(this, "Not Implemented!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.startButton :
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
