package com.syun.and.flowercollector.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;

/**
 * Created by qijsb on 2017/11/12.
 */
public class Inventory extends BaseCommon {
    private int mSquareWidth;
    private int mSquareHeight;
    private Bitmap image;

    private int openInventoryLeft, openInventoryTop, openInventoryRight, openInventoryBottom;
    private int openInventoryItemStart, openInventoryItemEnd;
    private int closeInventoryLeft, closeInventoryTop, closeInventoryRight, closeInventoryBottom;

    private float inventoryCX, inventoryCY;
    private float seedItemCX, seedItemCY;
    private float bottleItemCX, bottleItemCY;

    private int openWidth, openHeight;
    private int closeWidth, closeHeight;

    private Rect openSrc; // bitmapソース
    private Rect openDst; // 描画箇所
    private Rect closeSrc; // bitmapソース
    private Rect closeDst; // 描画箇所

    private boolean shouldOpen;
    private boolean onTouched; // interact with inventory

    public Inventory(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        openWidth = mSquareWidth * 3;
        openHeight = mSquareHeight * 1;
        closeWidth = mSquareWidth * 1;
        closeHeight = mSquareHeight * 1;

        openInventoryLeft = mSquareWidth / 2;
        openInventoryTop = mSquareHeight / 2;
        openInventoryRight = openInventoryLeft + openWidth;
        openInventoryBottom = openInventoryTop + openHeight;
        openInventoryItemStart = openInventoryLeft + mSquareWidth;
        openInventoryItemEnd = openInventoryRight;

        closeInventoryLeft = mSquareWidth / 2;
        closeInventoryTop = mSquareHeight / 2;
        closeInventoryRight = closeInventoryLeft + closeWidth;
        closeInventoryBottom = closeInventoryTop + closeHeight;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap inventory = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.menu_inventory, options);
        inventory = Bitmap.createScaledBitmap(inventory, openWidth, openHeight, true);

        Bitmap seed = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.seed, options);
        seed = Bitmap.createScaledBitmap(seed, mSquareWidth, mSquareHeight, true);

        image = Bitmap.createBitmap(openWidth, openHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(inventory, 0, 0, null);
        canvas.drawBitmap(seed, mSquareWidth, 0, null);

        openSrc = new Rect(0, 0, openWidth, openHeight);
        openDst = new Rect(openInventoryLeft, openInventoryTop, openInventoryRight, openInventoryBottom);
        closeSrc = new Rect(0, 0, closeWidth, closeHeight);
        closeDst = new Rect(closeInventoryLeft, closeInventoryTop, closeInventoryRight, closeInventoryBottom);
    }

    public void draw(Canvas canvas) {
        if(shouldOpen) {
            canvas.drawBitmap(image, openSrc, openDst, null);
        } else {
            canvas.drawBitmap(image, closeSrc, closeDst, null);
        }
    }

    public boolean onInventoryTouch(MotionEvent motionEvent) {
        float pX = motionEvent.getX(0);
        float pY = motionEvent.getY(0);

        if(shouldOpen) { // inventory is opened
            if(pX > openInventoryLeft && pX < openInventoryRight && pY > openInventoryTop && pY < openInventoryBottom) {
                shouldOpen = false;
                return true;
            } else if(pX > openInventoryItemStart && pX < openInventoryItemEnd && pY > openInventoryTop && pY < openInventoryBottom) {

                return true;
            } else {
                return false;
            }
        } else { // inventory is closed
            if(pX > closeInventoryLeft && pX < closeInventoryRight && pY > closeInventoryTop && pY < closeInventoryBottom) {
                shouldOpen = true;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean onTouched() {
        return onTouched;
    }

    public void onTouched(boolean b) {
        onTouched = b;
    }

}
