package com.syun.and.crazyball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qijsb on 2017/11/15.
 */

public class UI {
    protected Context mContext;
    protected int mSurfaceWidth;
    protected int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private float left, top;
    private float cX, cY;
    private int width, height;

    private Bitmap image;

    private List<Command> commandList = new ArrayList<>();

    private Paint textPaint = new Paint();

    public UI(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        width = mSquareWidth * 9;
        height = mSquareHeight * 2;

        left = 0;
        top = mSurfaceHeight - (mSquareHeight * 2);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;


        image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.MAGENTA);
        Bitmap ui = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ui, options);
        ui = Bitmap.createScaledBitmap(ui, width, height, true);
        canvas.drawBitmap(ui, 0, 0, null);

        textPaint.setColor(Color.MAGENTA);
        textPaint.setTextSize(20f);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, left, top, null);

        for (int i = 0; i < commandList.size(); i++) {
            Command command = commandList.get(i);
            canvas.drawBitmap(command.getImage(), mSquareWidth*3 + mSquareWidth * i * 2, top, null);
//            canvas.drawText("degree # " + command.getDegree(), mSquareWidth * 3 + mSquareWidth * i * 2, top + mSquareHeight, textPaint);
//            canvas.drawText("power # " + command.getPower(), mSquareWidth * 3 + mSquareWidth * i * 2, top + mSquareHeight * 3 / 2, textPaint);
        }
    }

    public void setCommand(Command command) {
        commandList.add(command);
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
