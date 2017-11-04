package com.syun.and.fixplumbing.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.syun.and.fixplumbing.Const;
import com.syun.and.fixplumbing.R;

/**
 * Created by qijsb on 2017/10/28.
 */
public class Plumber extends BaseUnit {
    private static final String TAG = Plumber.class.getSimpleName();

    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    private Bitmap plumber;
    private Bitmap plumberWet1;
    private Bitmap plumberWet2;
    private Bitmap plumberWet3;
    private Bitmap plumberFixing;
    private Bitmap plumberFixingWet1;
    private Bitmap plumberFixingWet2;
    private Bitmap plumberFixingWet3;
    private Bitmap plumberJumping;
    private Bitmap plumberJumpingWet1;
    private Bitmap plumberJumpingWet2;
    private Bitmap plumberJumpingWet3;

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

    private int wetGauge;

    private int xSpeed;
    private int ySpeed;

    private boolean jumping;
    private boolean fixing;
    private boolean onGround;
    private boolean underWater;


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

        plumber = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber, options);
        plumber = Bitmap.createScaledBitmap(plumber, width, height, true);
        plumberWet1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_wet_1, options);
        plumberWet1 = Bitmap.createScaledBitmap(plumberWet1, width, height, true);
        plumberWet2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_wet_2, options);
        plumberWet2 = Bitmap.createScaledBitmap(plumberWet2, width, height, true);
        plumberWet3 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_wet_3, options);
        plumberWet3 = Bitmap.createScaledBitmap(plumberWet3, width, height, true);

        plumberFixing = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_fixing, options);
        plumberFixing = Bitmap.createScaledBitmap(plumberFixing, width, height, true);
        plumberFixingWet1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_fixing_wet_1, options);
        plumberFixingWet1 = Bitmap.createScaledBitmap(plumberFixingWet1, width, height, true);
        plumberFixingWet2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_fixing_wet_2, options);
        plumberFixingWet2 = Bitmap.createScaledBitmap(plumberFixingWet2, width, height, true);
        plumberFixingWet3 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_fixing_wet_3, options);
        plumberFixingWet3 = Bitmap.createScaledBitmap(plumberFixingWet3, width, height, true);

        plumberJumping = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_jumping, options);
        plumberJumping = Bitmap.createScaledBitmap(plumberJumping, width, height, true);
        plumberJumpingWet1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_jumping_wet_1, options);
        plumberJumpingWet1 = Bitmap.createScaledBitmap(plumberJumpingWet1, width, height, true);
        plumberJumpingWet2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_jumping_wet_2, options);
        plumberJumpingWet2 = Bitmap.createScaledBitmap(plumberJumpingWet2, width, height, true);
        plumberJumpingWet3 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.plumber_jumping_wet_3, options);
        plumberJumpingWet3 = Bitmap.createScaledBitmap(plumberJumpingWet3, width, height, true);

        setPX(squareWidth * (Const.COLUMN / 2) + width / 2);
        setPY(squareHeight * (Const.ROW / 1) + height / 2);
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
        if(fixing) {
            if(wetGauge < 5) {
                return plumberFixing;
            } else if(wetGauge < 10) {
                return plumberFixingWet1;
            } else if(wetGauge < 15){
                return plumberFixingWet2;
            } else {
                return plumberFixingWet3;
            }
        }

        if(jumping) {
            if(wetGauge < 5) {
                return plumberJumping;
            } else if(wetGauge < 10) {
                return plumberJumpingWet1;
            } else if(wetGauge < 15){
                return plumberJumpingWet2;
            } else {
                return plumberJumpingWet3;
            }
        }

        if(wetGauge < 5) {
            return plumber;
        } else if(wetGauge < 10) {
            return plumberWet1;
        } else if(wetGauge < 15){
            return plumberWet2;
        } else {
            return plumberWet3;
        }
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
        if(underWater) {
            this.vx = (int) (vx * xSpeed * 40 / 100);
        } else {
            this.vx = (int) (vx * xSpeed * (100 - wetGauge * 2) / 100);
        }
    }

    public void setVY(int vy) {
        this.vy = vy;
    }

    public void setVT(int vt) {
        this.vt = vt;
    }

    public void incrementVT() {
        if(underWater) {
            vt++;

            if(vt > 2) {
                vt = 2;
            }
            return;
        }

        if(vt == 7) {
            return;
        }
        vt++;
    }

    public void jump() {
        if(underWater) {
            jumping = true;
            vt = -6;
            return;
        }

        if(canJump()) {
            jumping = true;
            vt = -7;
        }
    }

    public boolean isJumping() {
        return !isOnGround() && jumping;
    }

    public void setFixing(boolean b) {
        fixing = b;
    }

    public void setOnGround(boolean b) {
        onGround = b;
        if(b) {
            jumping = false;
        }
    }

    public boolean isOnGround() {
        return pY + height == surfaceHeight;
    }

    public void setUnderWater(boolean b) {
        underWater = b;
    }

    private boolean canJump() {
        return !isJumping();
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

    public int getCX() {
        return pX + width / 2;
    }
    public int getCY() {
        return pY + height / 2;
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

    public void incrementWetGauge() {
        if(wetGauge == 15) {
            return;
        }
        wetGauge++;
    }

    public void fix(Plumbing plumbing) {
        plumbing.incrementRepairGauge();
    }
}
