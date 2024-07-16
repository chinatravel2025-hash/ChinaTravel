package com.example.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class ActivityHelper {
    /**
     * 判断Activity是否已不在运行状态
     * @param activity
     * @return
     */
    public static boolean isInvalidActivity(Activity activity) {
        return activity == null || activity.isDestroyed() || activity.isFinishing();
    }

    public static void  setRightItemOnActionBar(AppCompatActivity activity, int resId, View.OnClickListener listener)  {
        Context context = activity;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayOptions(DISPLAY_SHOW_CUSTOM, DISPLAY_SHOW_CUSTOM);
        ImageButton imageButton = new ImageButton(context);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        imageButton.setImageResource(resId);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linearLayout.addView(imageButton);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        ActionBar.LayoutParams params  = new ActionBar.LayoutParams((int)(ViewGroup.LayoutParams.WRAP_CONTENT), ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.RIGHT;
        actionBar.setCustomView(linearLayout, params);
        imageButton.setOnClickListener(listener);
        //linearLayout.setOnClickListener(listener);
    }
}
