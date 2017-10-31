package com.syun.and.fixplumbing.listener;

/**
 * Created by qijsb on 2017/10/28.
 */

public interface OnGameEventListener {
    void onEvent(String msg);

    String CREATE = "create";
    String DESTROY = "destroy";

    String READY = "ready";
    String START = "start";
    String CLEAR = "clear";
    String DEAD = "dead";
}
