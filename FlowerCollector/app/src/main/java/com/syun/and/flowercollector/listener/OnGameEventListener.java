package com.syun.and.flowercollector.listener;

/**
 * Created by qijsb on 2017/11/05.
 */

public interface OnGameEventListener {
    void onEvent(String msg);

    String CREATE = "create";
    String DESTROY = "destroy";

    String COUNT_DOWN = "count_down";

    String READY = "ready";
    String START = "start";
    String CLEAR = "clear";
    String DEAD = "dead";

    String REGISTER_LIGHT_SENSOR = "register_light_sensor";
    String UNREGISTER_LIGHT_SENSOR = "unregister_light_sensor";
}
