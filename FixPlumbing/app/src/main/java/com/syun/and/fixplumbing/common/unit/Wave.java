package com.syun.and.fixplumbing.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.syun.and.fixplumbing.R;

/**
 * Created by qijsb on 2017/11/03.
 */
public class Wave extends BaseUnit {
    private Bitmap background;

    private Context mContext;
    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private int pX;
    private int pY;

    private int width;
    private int height;

    private int dX;
    private int dY;
    private boolean dXToggle;
    private int dYCount;

    public Wave(Context context, int surfaceWidth, int surfaceHeight, int squareWidth, int squareHeight) {
        this.mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }

    private void init() {
        width = surfaceWidth + squareWidth;
        height = surfaceHeight;

        pX = surfaceWidth * 0;
        pY = surfaceHeight * 1;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap wave = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wave, options);
        wave = Bitmap.createScaledBitmap(wave, width, squareHeight / 2, true);

        background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        canvas.save();
        canvas.clipRect(0, squareHeight / 2, width, height);
        canvas.drawARGB(199, 0, 0, 255);
        canvas.restore();
        canvas.drawBitmap(wave, 0, 0, null);
    }

    public Bitmap getImage() {
        return background;
    }

    public int getPX() {
        return pX - dX;
    }

    public int getPY() {
        return pY - dY;
    }

    public void incrementPX() {
        if(dXToggle) {
            dX = dX + 2;
            if(dX >= squareWidth)
                dXToggle = !dXToggle;
        } else {
            dX = dX - 2;
            if(dX <= 0)
                dXToggle = !dXToggle;
        }
    }

    public void incrementPY() {
        if(++dYCount == 13){
            dY = dY + squareHeight / 40;
            dYCount = 0;
        }
    }
}
