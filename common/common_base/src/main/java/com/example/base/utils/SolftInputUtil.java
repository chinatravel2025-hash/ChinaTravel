package com.example.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SolftInputUtil {
    /**
     * 是否触摸了当前焦点控件
     * @param event
     * @param focusView
     * @return
     */
    public static boolean isTouchView(MotionEvent event, View focusView){
        if(null == event || null == focusView){
            return false;
        }

        float x = event.getX();
        float y = event.getY();
        int[] outLocation = new int[2];
        focusView.getLocationOnScreen(outLocation);
        RectF rectF = new RectF(outLocation[0], outLocation[1] ,outLocation[0] + focusView.getWidth() ,outLocation[1] + focusView.getHeight());
        if(x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom){
            return true;
        }

        return false;
    }


    /**
     * 隐藏Activity里面获得焦点的View的软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager parentImm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (parentImm.isActive()) {
            View view = activity.getCurrentFocus();
            if (null != view) {
                parentImm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


}
