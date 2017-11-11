package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;

/**
 * Created by qijsb on 2017/11/08.
 */

public class Character extends BaseCommon{
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Bitmap[] character_e;
    private Bitmap[] character_w;
    private Bitmap[] character_n;
    private Bitmap[] character_ne;
    private Bitmap[] character_nw;
    private Bitmap[] character_s;
    private Bitmap[] character_se;
    private Bitmap[] character_sw;

    private boolean isMoving;

    private float cX, cY;
    protected int width, height;

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
        width = mSquareWidth * 2;
        height = mSquareHeight * 2;

        cX = mSurfaceWidth / 2 - width / 2;
        cY = mSurfaceHeight / 2 - height / 2;

        character_e = parseDrawable(new int[]{R.drawable.character_e, R.drawable.character_e_l, R.drawable.character_e_r}, width, height);
        character_w = parseDrawable(new int[]{R.drawable.character_w, R.drawable.character_w_l, R.drawable.character_w_r}, width, height);
        character_n = parseDrawable(new int[]{R.drawable.character_n, R.drawable.character_n_l, R.drawable.character_n_r}, width, height);
        character_ne = parseDrawable(new int[]{R.drawable.character_ne, R.drawable.character_ne_l, R.drawable.character_ne_r}, width, height);
        character_nw = parseDrawable(new int[]{R.drawable.character_nw, R.drawable.character_nw_l, R.drawable.character_nw_r}, width, height);
        character_s = parseDrawable(new int[]{R.drawable.character_s, R.drawable.character_s_l, R.drawable.character_s_r}, width, height);
        character_se = parseDrawable(new int[]{R.drawable.character_se, R.drawable.character_se_l, R.drawable.character_se_r}, width, height);
        character_sw = parseDrawable(new int[]{R.drawable.character_sw, R.drawable.character_sw_l, R.drawable.character_sw_r}, width, height);
    }

    private boolean shouldShaking;
    private int count;

    public Bitmap getImage() {
        if(++count > Const.FPS / 2) {
            count = 0;
            shouldShaking = !shouldShaking;
        }

        switch (direction) {
            case 0x01 : // 右
                if(isMoving) {
                    return shouldShaking ? character_e[1] : character_e[2];
                } else {
                    return character_e[0];
                }

            case 0x02 : // 左
                if(isMoving) {
                    return shouldShaking ? character_w[1] : character_w[2];
                } else {
                    return character_w[0];
                }

            case 0x10 : // 下
                if(isMoving) {
                    return shouldShaking ? character_s[1] : character_s[2];
                } else {
                    return character_s[0];
                }
            case 0x20 : // 上
                if(isMoving) {
                    return shouldShaking ? character_n[1] : character_n[2];
                } else {
                    return character_n[0];
                }

            case 0x11 : // 下右
                if(isMoving) {
                    return shouldShaking ? character_se[1] : character_se[2];
                } else {
                    return character_se[0];
                }

            case 0x12 : // 下左
                if(isMoving) {
                    return shouldShaking ? character_sw[1] : character_sw[2];
                } else {
                    return character_sw[0];
                }

            case 0x21 : // 上右
                if(isMoving) {
                    return shouldShaking ? character_ne[1] : character_ne[2];
                } else {
                    return character_ne[0];
                }

            case 0x22 : // 上左
                if(isMoving) {
                    return shouldShaking ? character_nw[1] : character_nw[2];
                } else {
                    return character_nw[0];
                }

            default:
            case 0x00 : // 中央
                return character_s[0];
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
        if(diffY > mSquareHeight / 3) { // 下
            direction = 0x10 ;
            cY = ++cY > mSurfaceHeight - height ? mSurfaceHeight - height : cY;
        } else if(diffY < -mSquareHeight / 3) { // 上
            direction = 0x20 ;
            cY = --cY < 0 ? - 0 : cY;
        }

        if(diffX > mSquareWidth / 3) { // 右
            direction = direction + 0x01;
            map.translateLeft();
        } else if(diffX < -mSquareWidth / 3) { // 左
            direction = direction + 0x02;
            map.translateRight();
        }
    }

    public void isMoving(boolean b) {
        isMoving = b;
    }
}
