package com.syun.and.fixplumbing.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.R;

/**
 * Created by qijsb on 2017/10/28.
 */
public class Plumber {
    private static final String TAG = Plumber.class.getSimpleName();

    private Context mContext;
    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private Bitmap image;

    private int pX;
    private int pY;
    private int tempPX;
    private int tempPY;

    private int currentColumn;
    private int currentRow;
    private int tempColumn;
    private int tempRow;

    private float g; // gravity factor
    private int vt;
    private int vx;
    private int vy;

    private int width;
    private int height;

    private int xSpeed;
    private int ySpeed;

    private boolean jumping;
    private boolean fixing;
    private boolean isOnGround;

    public Plumber(Context context, int surfaceWidth, int surfaceHeight ,int squareWidth, int squareHeight) {
        this.mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        init();
    }

    private void init() {
        g = squareHeight / 4f;

        width = squareWidth * 3/4;
        height = squareHeight * 3/4;

        xSpeed = squareWidth / 18;
        ySpeed = squareHeight;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber, options);
        image = Bitmap.createScaledBitmap(image, width, height, true);

        setPX(squareWidth * (Const.COLUMN/2) + width / 2);
        setPY(squareHeight * (Const.ROW/2) + height / 2);

        Log.d(TAG, "init: p [X, Y] # ["+pX+", "+pY+"]");
        Log.d(TAG, "init: image [width, height] # ["+image.getWidth()+", "+image.getHeight()+"]");
    }

    public void setPX(int pX) { // plumber.getPX() + plumber.getVX()
        if(pX < 0) {
            this.pX = 0;
        } else {
            this.pX = pX > surfaceWidth - width ? surfaceWidth - width: pX;
        }
        setCurrentColumn();
    }

    public void setPY(int pY) { // plumber.getPY() + plumber.getVY()
        if(pY < 0) {
            this.pY = 0;
        } else {
            this.pY = pY > surfaceHeight - height ? surfaceHeight - height : pY;
        }
        setCurrentRow();
    }

    public Bitmap getImage() {
//        if(fixing) {
//            // TODO : return fixing motion
//        }
//
//        if(jumping) {
//            // TODO : return jumping motion
//        }

        return image;
    }

    public int getPX() {
        return pX;
    }

    public int getPY() {
        return pY;
    }

    private void setCurrentColumn() {
        currentColumn = pX / squareWidth;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    private void setCurrentRow() {
        currentRow = pY / squareHeight;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    private void setTempColumn() {
        tempColumn = tempPX / squareWidth;
    }

    public int getTempColumn() {
        return tempColumn;
    }

    public int getTempRow() {
        return tempRow;
    }

    private void setTempRow(){
        tempRow = tempPY / squareHeight;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public int getVX() {
        return vx;
    }

    public int getVY() {
        return (int) (g / 2f * vt);
    }

    public int getTempPX() {
        return tempPX;
    }

    public int getTempPY() {
        return tempPY;
    }

    public void setTempPX(int tempPX){
        if(tempPX < 0) {
            this.tempPX = 0;
        } else {
            this.tempPX = tempPX > surfaceWidth - width ? surfaceWidth - width: tempPX;
        }
        setTempColumn();
    }

    public void setTempPY(int tempPY) {
        if(tempPY < 0) {
            this.tempPY = 0;
        } else {
            this.tempPY = tempPY > surfaceHeight - height ? surfaceHeight - height : tempPY;
        }
        setTempRow();
    }

    public void setVX(float vx) {
        this.vx = (int) (vx * xSpeed);
    }

    public void setVY(int vy) {
        this.vy = vy;
    }

    public void setVT(int vt) {
        this.vt = vt;
    }

    public void incrementVT() {
        vt = ++vt > 7 ? 7: vt;
    }

    public void jump() {
        // TODO : canJump
        vt = -7;
    }

    public int getLeft() {
        return pX;
    }

    public int getTop() {
        return pY;
    }

    public int getRight() {
        return pX + width;
    }

    public int getBottom() {
        return pY + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFixing() {
        return fixing;
    }

    public int getDX() {
        return tempPX - pX;
    }

    public int getDY() {
        return tempPY - pY;
    }

    public void confirmPosition() {
        pX = tempPX;
        pY = tempPY;
    }
}
