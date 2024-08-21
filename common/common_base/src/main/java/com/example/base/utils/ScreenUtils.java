package com.example.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

    private static int screenHeight = 0;
    private static int screenWidth = 0;

    public static DisplayMetrics getDisPlayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context != null) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            screenWidth = getDisPlayMetrics(context).widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight(Context context) {
        if (screenHeight == 0) {
            screenHeight = getDisPlayMetrics(context).heightPixels;
        }
        return screenHeight;
    }

    public static float getDensity(Context context) {

        float density = getDisPlayMetrics(context).density;
        return density;
    }

    public static int getDensityDpi(Context context) {

        int densityDpi = getDisPlayMetrics(context).densityDpi;
        return densityDpi;
    }
}
