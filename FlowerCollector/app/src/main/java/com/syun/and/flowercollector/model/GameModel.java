package com.syun.and.flowercollector.model;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.syun.and.flowercollector.Const;
import com.syun.and.flowercollector.common.unit.Character;
import com.syun.and.flowercollector.common.Inventory;
import com.syun.and.flowercollector.common.unit.Seed;
import com.syun.and.flowercollector.common.Keyboard;
import com.syun.and.flowercollector.common.Map;
import com.syun.and.flowercollector.db.model.SeedModel;
import com.syun.and.flowercollector.utils.RanStrUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by qijsb on 2017/11/07.
 */
public class GameModel {
    private static final String TAG = GameModel.class.getSimpleName();

    private Context mContext;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mSquareWidth;
    private int mSquareHeight;

    private Map map;
    private Keyboard keyboard;
    private Character character;
    private Inventory inventory;

    List<Seed> seedList = new ArrayList<>();

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
        inventory = new Inventory(mContext, mSurfaceWidth, mSurfaceHeight);
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

    public List<Seed> getSeedList() {
        return seedList;
    }

    public void parse(MotionEvent motionEvent) {
        // TODO : ACTION_DOWNで INTERACTIONの TYPEを 判別する
        // TODO : TYPEのenumは GAME_MODELに 作成
        if(onInventoryTouch(motionEvent) || inventory.onTouched()) {

        } else {
            showKeyboard(motionEvent);
        }
    }

    private void showKeyboard(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                keyboard.parse(motionEvent);
                character.isMoving(true);
                break;

            case MotionEvent.ACTION_MOVE:
                if(character.isMoving()) {
                    keyboard.parse(motionEvent);
                    character.updateDirectionWithTranslate(map, keyboard.getDiffX(), keyboard.getDiffY());
                }
                break;

            case MotionEvent.ACTION_UP:
                if(character.isMoving()) {
                    keyboard.parse(motionEvent);
                    character.isMoving(false);
                }
                break;
        }
    }

    private boolean onInventoryTouch(MotionEvent motionEvent) {
        return inventory.onInventoryTouch(motionEvent);
    }

    public void drawInventory(Canvas canvas) {
        inventory.draw(canvas);
    }

    public void reload() {
//        writeSeed(mSurfaceWidth/2, mSurfaceHeight/2);
        // TODO : reload seeds
        reloadSeeds();
    }

    public void save() {
        // TODO : save seeds
        saveSeeds();
    }

    /**
     * Writes
     */
    private void writeSeed(float cX, float cY) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // Create a new object
        SeedModel seedModel = realm.createObject(SeedModel.class);
        seedModel.setName(RanStrUtil.generate(10));
        seedModel.setCX(cX);
        seedModel.setCY(cY);
        seedModel.setGrowthPoint(0);
        seedModel.setHealthPoint(10000);
        seedModel.setLightGauge(0);
        seedModel.setWaterGauge(0);
        realm.commitTransaction();

        seedList.add(new Seed(seedModel, mContext, mSurfaceWidth, mSurfaceHeight));
    }

    /**
     * deletion
     */
    private void deleteSeed(Seed seed) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // delete
        realm.where(SeedModel.class)
                .equalTo("name", seed.getName())
                .findFirst()
                .deleteFromRealm();

        realm.commitTransaction();
    }

    private void reloadSeeds() {
        Realm realm = Realm.getDefaultInstance();

        // Build the query looking at seed:
        RealmQuery<SeedModel> query = realm.where(SeedModel.class);

        // Execute the query:
        RealmResults<SeedModel> resultAll = query.findAll();
        Log.d(TAG, "reloadSeeds: size # " + resultAll.size());

        for (SeedModel seedModel : resultAll) {
            seedList.add(new Seed(seedModel, mContext, mSurfaceWidth, mSurfaceHeight));
        }
    }

    private void saveSeeds() {

    }

}
