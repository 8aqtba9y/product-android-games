package com.syun.and.fixplumbing.common.unit;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by qijsb on 2017/11/02.
 */

public class Drops extends BaseUnit {
    private int surfaceWidth;
    private int surfaceHeight;
    private int squareWidth;
    private int squareHeight;

    List<Drop> drops;

    public Drops(Context context, int surfaceWidth, int surfaceHeight , int squareWidth, int squareHeight) {
        mContext = context;
        this.surfaceWidth = surfaceWidth;
        this.surfaceHeight = surfaceHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;

        init();
    }

    private List<Drop> tempDrops;

    private void init() {
        drops = new ArrayList<>();
        tempDrops = new ArrayList<>();
    }

    public List<Drop> getDrops() {
        return drops;
    }

    public void addDrop() {
        Random rnd = new Random();

        if(rnd.nextInt(20) == 19) {
            drops.add(new Drop(mContext, surfaceWidth, surfaceHeight, squareWidth, squareHeight));
        }
    }

    public void recycleDrops(int wavePY, Plumber plumber) {
        int plumberCX = plumber.getCX();
        int plumberCY = plumber.getCY();

        for (Drop drop : drops) {
            int dropCX = drop.getPX() + drop.getWidth() / 2;
            int dropCY = drop.getPY() + drop.getHeight() / 2;

            int distance = (int) Math.sqrt(
                    Math.pow(plumberCX - dropCX, 2)
                    + Math.pow(plumberCY - dropCY, 2)
            );

            if(distance < squareWidth / 2){
                plumber.incrementWetGauge();
                tempDrops.add(drop);
                continue;
            }

            if(drop.getPY() > wavePY) {
                tempDrops.add(drop);
            }
        }

        drops.removeAll(tempDrops);
        tempDrops.clear();
    }
}
