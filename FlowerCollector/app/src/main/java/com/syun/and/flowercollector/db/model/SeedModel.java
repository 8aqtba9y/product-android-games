package com.syun.and.flowercollector.db.model;

import io.realm.RealmObject;

/**
 * Created by qijsb on 2017/11/08.
 */

public class SeedModel extends RealmObject {
    private String name;

    private float cX;
    private float cY;

    private int growthPoint;

    private int healthPoint;

    private int lightGauge;
    private int waterGauge;

    // TODO : add createTimeStamp
    // TODO : add updateTimeStamp

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
