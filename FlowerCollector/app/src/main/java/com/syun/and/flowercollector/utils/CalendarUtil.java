package com.syun.and.flowercollector.utils;

import java.util.Calendar;

/**
 * Created by qijsb on 2017/11/06.
 */

public class CalendarUtil {
    public static int getHours() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
}
