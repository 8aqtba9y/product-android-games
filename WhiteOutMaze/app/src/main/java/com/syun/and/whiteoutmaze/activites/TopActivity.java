package com.syun.and.whiteoutmaze.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.syun.and.whiteoutmaze.R;
import com.syun.and.whiteoutmaze.fragments.SettingFragment;

public class TopActivity extends BaseActivity implements View.OnClickListener{

    private Button mSettingButton;
    private Button mStage1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mSettingButton = findViewById(R.id.settingButton);
        mSettingButton.setOnClickListener(this);
        mStage1Button = findViewById(R.id.startButton);
        mStage1Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingButton :
                handleSettingFragment();
                break;

            case R.id.startButton :
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void handleSettingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(SettingFragment.TAG);
        if(fragment != null) {
            if(fragment.isVisible()) {
                fragmentManager.beginTransaction().hide(fragment).commit();
            } else {
                fragmentManager.beginTransaction().show(fragment).commit();
            }
        } else {
            fragmentManager.beginTransaction().add(R.id.container, new SettingFragment(), SettingFragment.TAG).commit();
        }
    }

}
