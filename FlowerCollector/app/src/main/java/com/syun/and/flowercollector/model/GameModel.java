package com.syun.and.flowercollector.model;

import android.content.Context;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.common.Character;
import com.syun.and.flowercollector.common.Keyboard;
import com.syun.and.flowercollector.common.Map;
import com.syun.and.flowercollector.db.model.SeedModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by qijsb on 2017/11/07.
 */

public class GameModel {
    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Map map;
    private Keyboard keyboard;
    private Character character;

    public GameModel(Context context, int surfaceWidth, int surfaceHeight) {
        this.mContext = context;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mSquareWidth = mSurfaceWidth / Const.COLUMN;
        this.mSquareHeight = mSurfaceHeight / Const.ROW;

        init();
    }

    private void init() {
        map = new Map(mContext, mSurfaceWidth, mSurfaceHeight);
        keyboard = new Keyboard(mContext, mSurfaceWidth, mSurfaceHeight);
        character = new Character(mContext, mSurfaceWidth, mSurfaceHeight);

        /**
         * Writes
         */
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // Create a new object
        SeedModel seed = realm.createObject(SeedModel.class);
        seed.setName("Seeds2");
        seed.setCX(mSurfaceWidth);
        seed.setCY(mSquareHeight/2);

        realm.commitTransaction();

        /**
         * deletion
         */
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        RealmResults<SeedModel> result = realm.where(SeedModel.class).findAll();
//
//        // delete All
//        result.deleteAllFromRealm();
//
//        realm.commitTransaction();
    }

    public RealmResults<SeedModel> getSeeds() {
        Realm realm = Realm.getDefaultInstance();

        // Build the query looking at seed:
        RealmQuery<SeedModel> query = realm.where(SeedModel.class);

        // Execute the query:
        RealmResults<SeedModel> resultAll = query.findAll();
        return resultAll;
    }

    public Map getMap() {
        return map;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Character getCharacter() {
        return character;
    }

    public void parse(MotionEvent motionEvent) {
        showKeyboard(motionEvent);
    }

    private void showKeyboard(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                keyboard.parse(motionEvent);
                break;

            case MotionEvent.ACTION_MOVE:
                keyboard.parse(motionEvent);
                character.updateDirectionWithTranslate(map, keyboard.getDiffX(), keyboard.getDiffY());
                break;

            case MotionEvent.ACTION_UP:
                keyboard.parse(motionEvent);
                break;
        }
    }

    public void save() {
        // TODO : save seeds
    }
}
