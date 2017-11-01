package com.syun.and.fixplumbing.common;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by qijsb on 2017/11/02.
 */

public class Drops {
    private Context mContext;
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

    public void recycleDrops() {
        for (Drop drop : drops) {
            if(drop.getPY() > surfaceHeight) {
                tempDrops.add(drop);
            }
        }

        drops.removeAll(tempDrops);
        tempDrops.clear();
    }
}
