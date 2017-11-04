package com.syun.and.fixplumbing.common.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.R;
import com.syun.and.fixplumbing.common.unit.Brick;
import com.syun.and.fixplumbing.common.unit.Plumbing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qijsb on 2017/10/29.
 */
public class Map {
    private static final String TAG = Map.class.getSimpleName();

    private Context mContext;
    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private Bitmap image;

    private int pX;
    private int pY;

    private int width;
    private int height;

    private List<Brick> brickList = new ArrayList<>();
    private List<Plumbing> plumbingList = new ArrayList<>();

    public Map(Context context, int surfaceWidth, int surfaceHeight, int squareWidth, int squareHeight) {
        this.mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }


    private void init() {
        width = surfaceWidth;
        height = surfaceHeight;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap background = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.background, options);
        background = Bitmap.createScaledBitmap(background, surfaceWidth, surfaceHeight, true);

        image = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        canvas.drawBitmap(background, new Matrix(), null);

        for (int row = 0; row < Const.ROW; row++) {
            for (int column = 0; column < Const.COLUMN; column++) {

                int tileType = tiles[row][column];
                switch (tileType) {
                    case 0 :
                        // do nothing.
                        break;

                    case 1 :
                        Brick brick = new Brick(mContext, squareWidth, squareHeight, row, column);
                        canvas.drawBitmap(brick.getImage(), brick.getPX(), brick.getPY(), null);
                        brickList.add(brick);
                        break;

                    case 2 :
                        Plumbing plumbing = new Plumbing(mContext, squareWidth, squareHeight, row, column);
                        plumbingList.add(plumbing);
                        break;
                }
            }
        }
    }

    public Bitmap getImage(){
        return image;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    public List<Plumbing> getPlumbingList() {
        return plumbingList;
    }

    private int[][] tiles = {
             {0,0,0,0,0,2,0,0,0}
            ,{0,0,0,0,0,1,1,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,1,1,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,1,1,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,1,1,0,0,0,0,2}
            ,{0,0,0,0,0,0,0,0,1}
            ,{1,0,0,0,0,0,0,0,0}
            ,{0,0,2,0,0,0,0,0,0}
            ,{0,0,1,1,0,0,0,0,0}
            ,{0,0,0,0,0,0,1,1,0}
            ,{0,0,0,0,0,0,0,0,0}
            ,{0,0,0,0,0,0,0,0,0}
    };

}
