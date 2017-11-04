package com.syun.and.fixplumbing.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.syun.and.fixplumbing.R;

/**
 * Created by qijsb on 2017/11/02.
 */

public class Plumbing extends BaseUnit {
    private static final String TAG = Plumbing.class.getSimpleName();

    private int squareWidth;
    private int squareHeight;

    private Bitmap plumbing05;
    private Bitmap plumbing25;
    private Bitmap plumbing45;
    private Bitmap plumbing65;
    private Bitmap plumbing85;
    private Bitmap plumbingFixed;

    private int row;
    private int column;

    private int plumbingWidth;
    private int plumbingHeight;
    private int gaugeWidth;
    private int gaugeHeight;

    private int pX;
    private int pY;

    private boolean fixed;
    private int gauge;

    public Plumbing(Context context, int squareWidth, int squareHeight, int row, int column) {
        mContext = context;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.column = column;
        this.row = row;

        plumbingWidth = squareWidth * 1;
        plumbingHeight = squareHeight * 1;

        gaugeWidth = squareWidth * 1;
        gaugeHeight = squareHeight / 5;

        pX = squareWidth * column;
        pY = squareHeight * row;

        init();
    }

    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Canvas canvas;

        Bitmap gauge05 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gauge_05, options);
        gauge05 = Bitmap.createScaledBitmap(gauge05, gaugeWidth, gaugeHeight, true);
        Bitmap gauge25 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gauge_25, options);
        gauge25 = Bitmap.createScaledBitmap(gauge25, gaugeWidth, gaugeHeight, true);
        Bitmap gauge45 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gauge_45, options);
        gauge45 = Bitmap.createScaledBitmap(gauge45, gaugeWidth, gaugeHeight, true);
        Bitmap gauge65 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gauge_65, options);
        gauge65 = Bitmap.createScaledBitmap(gauge65, gaugeWidth, gaugeHeight, true);
        Bitmap gauge85 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gauge_85, options);
        gauge85 = Bitmap.createScaledBitmap(gauge85, gaugeWidth, gaugeHeight, true);

        plumbing05 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_should_repair, options);
        plumbing05 = Bitmap.createScaledBitmap(plumbing05, plumbingWidth, plumbingHeight, true);
        canvas = new Canvas(plumbing05);
        canvas.drawBitmap(gauge05, 0, 0, null);

        plumbing25 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_should_repair, options);
        plumbing25 = Bitmap.createScaledBitmap(plumbing25, plumbingWidth, plumbingHeight, true);
        canvas = new Canvas(plumbing25);
        canvas.drawBitmap(gauge25, 0, 0, null);

        plumbing45 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_should_repair, options);
        plumbing45 = Bitmap.createScaledBitmap(plumbing45, plumbingWidth, plumbingHeight, true);
        canvas = new Canvas(plumbing45);
        canvas.drawBitmap(gauge45, 0, 0, null);

        plumbing65 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_should_repair, options);
        plumbing65 = Bitmap.createScaledBitmap(plumbing65, plumbingWidth, plumbingHeight, true);
        canvas = new Canvas(plumbing65);
        canvas.drawBitmap(gauge65, 0, 0, null);

        plumbing85 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_should_repair, options);
        plumbing85 = Bitmap.createScaledBitmap(plumbing85, plumbingWidth, plumbingHeight, true);
        canvas = new Canvas(plumbing85);
        canvas.drawBitmap(gauge85, 0, 0, null);

        plumbingFixed = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumbing_fixed, options);
        plumbingFixed = Bitmap.createScaledBitmap(plumbingFixed, plumbingWidth, plumbingHeight, true);
    }

    public Bitmap getImage() {
        if(gauge < 80) {
            return plumbing05;
        } else if(gauge < 160) {
            return plumbing25;
        } else if(gauge < 240) {
            return plumbing45;
        } else if(gauge < 320) {
            return plumbing65;
        } else if(gauge < 400) {
            return plumbing85;
        } else {
            return plumbingFixed;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPX() {
        return pX;
    }

    public int getPY() {
        return pY;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void incrementRepairGauge(){
        if(gauge == 400) {
            fixed = true;
            return;
        }
        gauge++;
    }

    public int getGauge() {
        return gauge;
    }
}
