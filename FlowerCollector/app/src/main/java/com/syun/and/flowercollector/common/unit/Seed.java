package com.syun.and.flowercollector.common.unit;

import android.content.Context;
import android.graphics.Bitmap;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.R;
import com.syun.and.flowercollector.common.BaseCommon;
import com.syun.and.flowercollector.db.model.SeedModel;

/**
 * Created by qijsb on 2017/11/12.
 */
public class Seed extends BaseCommon{
    private int mSquareWidth;
    private int mSquareHeight;

    private Bitmap[] plantImages;

    private String name;

    private float cX, cY;
    private int width, height;

    private int growthPoint;

    private int healthPoint;

    private int lightGauge;
    private int waterGauge;

    // TODO : add createTimeStamp
    // TODO : add updateTimeStamp

    public Seed(SeedModel seedModel, Context context, int surfaceWidth, int surfaceHeight) {
        name = seedModel.getName();
        cX = seedModel.getCX();
        cY = seedModel.getCY();
        growthPoint = seedModel.getGrowthPoint();
        healthPoint = seedModel.getHealthPoint();
        lightGauge = seedModel.getLightGauge();
        waterGauge = seedModel.getWaterGauge();

        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        width = mSquareWidth;
        height = mSquareHeight;

        init();
    }

    private void init() {
        plantImages = parseDrawable(
                new int[]{R.drawable.plant_level_0
                        , R.drawable.plant_level_1
                        , R.drawable.plant_level_2
                        , R.drawable.plant_level_3
                        , R.drawable.plant_level_4}
                        , width, height);
    }

    public String getName() {
        return name;
    }

    public float getCX() {
        return cX;
    }

    public void setCX(float cX) {
        this.cX = cX;
    }

    public float getCY() {
        return cY;
    }

    public void setCY(float cY) {
        this.cY = cY;
    }

    public int getGrowthPoint() {
        return growthPoint;
    }

    public void setGrowthPoint(int growthPoint) {
        this.growthPoint = growthPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getLightGauge() {
        return lightGauge;
    }

    public void setLightGauge(int lightGauge) {
        this.lightGauge = lightGauge;
    }

    public int getWaterGauge() {
        return waterGauge;
    }

    public void setWaterGauge(int waterGauge) {
        this.waterGauge = waterGauge;
    }

    public Bitmap getImage() {
        if(growthPoint < 2000 ) {
            return plantImages[0];
        } else if(growthPoint < 4000) {
            return plantImages[1];
        } else if(growthPoint < 6000) {
            return plantImages[2];
        } else if(growthPoint < 8000) {
            return plantImages[3];
        } else {
            return plantImages[4];
        }
    }
}
