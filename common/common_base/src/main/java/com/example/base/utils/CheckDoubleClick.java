package com.example.base.utils;

import android.os.SystemClock;
import android.view.ViewConfiguration;

/**
 * 防快速点击
 */
public class CheckDoubleClick {

    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
//        long time = getBoostTimeMillis();
//        long timeD = time - lastClickTime;
//        if (0 < timeD && timeD < ViewConfiguration.getLongPressTimeout()) {
//            return true;
//        }
//        lastClickTime = time;
        return false;
    }

    private static long lastClickTime2 = 0;

    public static boolean isFastDoubleClick(long l) {
        long time = getBoostTimeMillis();
        long timeD = time - lastClickTime2;
        if (0 < timeD && timeD < l) {
            return true;
        }
        lastClickTime2 = time;
        return false;
    }

    private static long lastUpdateTime = 0;

    public static boolean isCanUpdate(long l) {
        long time = getBoostTimeMillis();
        long timeD = time - lastUpdateTime;
        if (0 < timeD && timeD < l) {
            return true;
        }
        lastUpdateTime = time;
        return false;
    }
    /**
     * 使用开机计时
     * 替代System.currentTimeMillis()
     * 可以防止修改手机时间导致判断时间间隔不对
     * @return
     */
    public static long getBoostTimeMillis() {
        return SystemClock.elapsedRealtime();
    }



    private static long splashClickTime = 0;

    public static boolean isSplashFastDoubleClick() {
        long time = getBoostTimeMillis();
        long timeD = time - splashClickTime;
        if (0 < timeD && timeD < ViewConfiguration.getLongPressTimeout()) {
            return true;
        }
        splashClickTime = time;
        return false;
    }
}
