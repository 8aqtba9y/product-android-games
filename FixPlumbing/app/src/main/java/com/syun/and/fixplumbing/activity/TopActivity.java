package com.syun.and.fixplumbing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.syun.and.fixplumbing.R;

public class TopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
    }

    public void start(View view) {
        startActivityForResult(new Intent(this, MainActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK) {
            switch (data.getIntExtra("key", 0)) {
                case 1 :
                    Toast.makeText(this, "GAME OVER!", Toast.LENGTH_SHORT).show();
                    break;

                case 2 :
                    Toast.makeText(this, "CLEAR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
