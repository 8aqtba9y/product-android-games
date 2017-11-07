package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;

/**
 * Created by qijsb on 2017/11/08.
 */

public class Character {
    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Bitmap character_e;
    private Bitmap character_w;
    private Bitmap character_n;
    private Bitmap character_ne;
    private Bitmap character_nw;
    private Bitmap character_s;
    private Bitmap character_se;
    private Bitmap character_sw;

//    private float left, top;
    private float cX, cY;
    private int width, height;

    private int direction;

    public Character(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        width = mSquareWidth;
        height = mSquareHeight;

        cX = mSurfaceWidth / 2 - width / 2;
        cY = mSurfaceHeight / 2 - height / 2;

        character_e = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_e, options);
        character_e = Bitmap.createScaledBitmap(character_e, width, height, true);

        character_w = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_w, options);
        character_w = Bitmap.createScaledBitmap(character_w, width, height, true);

        character_n = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_n, options);
        character_n = Bitmap.createScaledBitmap(character_n, width, height, true);

        character_ne = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_ne, options);
        character_ne = Bitmap.createScaledBitmap(character_ne, width, height, true);

        character_nw = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_nw, options);
        character_nw = Bitmap.createScaledBitmap(character_nw, width, height, true);

        character_s = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_s, options);
        character_s = Bitmap.createScaledBitmap(character_s, width, height, true);

        character_se = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_se, options);
        character_se = Bitmap.createScaledBitmap(character_se, width, height, true);

        character_sw = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.character_sw, options);
        character_sw = Bitmap.createScaledBitmap(character_sw, width, height, true);
    }

    public Bitmap getImage() {
        switch (direction) {
            case 0x01 : // 右
                return character_e;

            case 0x02 : // 左
                return character_w;

            case 0x10 : // 下
                return character_s;

            case 0x20 : // 上
                return character_n;

            case 0x11 : // 下右
                return character_se;

            case 0x12 : // 下左
                return character_sw;

            case 0x21 : // 上右
                return character_ne;

            case 0x22 : // 上左
                return character_nw;

            default:
            case 0x00 : // 中央
                return character_s;
        }
    }

    public float getCX() {
        return cX;
    }

    public float getCY() {
        return cY;
    }

    public void updateDirectionWithTranslate(Map map, float diffX, float diffY) {
        direction = 0x00;
        if(diffY > mSquareHeight / 4) { // 下
            direction = 0x10 ;
            cY = ++cY > mSurfaceHeight - mSquareHeight ? mSurfaceHeight - mSquareHeight : cY;
        } else if(diffY < -mSquareHeight / 4) { // 上
            direction = 0x20 ;
            cY = --cY < mSquareHeight ? mSquareHeight : cY;
        }

        if(diffX > mSquareWidth / 4) { // 右
            direction = direction + 0x01;
            map.translateLeft();
        } else if(diffX < -mSquareWidth / 4) { // 左
            direction = direction + 0x02;
            map.translateRight();
        }
    }
}
