package com.syun.and.whiteoutmaze.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.syun.and.whiteoutmaze.R;

/**
 * Created by qijsb on 2017/10/25.
 */
public class Home extends Unit{
    private int pX;
    private int pY;

    private int size;

    public Home(Context context, int squareSize) {
        this.context = context;
        this.squareSize = squareSize;

        init();
    }

    private void init() {
        size = squareSize * 1;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.home, options);
        image = Bitmap.createScaledBitmap(image, size, size, true);

        pX = squareSize * 8 + size / 2;
        pY = squareSize * 8 + size / 2;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getPX() {
        return pX;
    }

    public int getPY() {
        return pY;
    }

    public int getSize() {
        return size;
    }
}
