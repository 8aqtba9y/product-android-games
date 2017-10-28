package com.syun.and.fixplumbing;

/**
 * Created by qijsb on 2017/10/28.
 */

public interface OnGameEventListener {
    void onEvent(String msg);

    String DEAD = "dead";
}
