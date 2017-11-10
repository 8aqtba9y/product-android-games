package com.syun.and.flowercollector.db.model;

import io.realm.RealmObject;

/**
 * Created by qijsb on 2017/11/08.
 */

public class SeedModel extends RealmObject {
    private String name;

    private float cX;
    private float cY;

    private int healthPoint;

    private int lightGauge;
    private int waterGauge;

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
}
