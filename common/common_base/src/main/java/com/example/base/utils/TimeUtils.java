package com.example.base.utils;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {

    public static final String mTAG = "singerHotTime";

    public static final String mTime = "mTime_";
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static TimeUtils mTimeUtils;

    public TimeUtils(Context context) {
        mPreferences = context.getSharedPreferences(mTAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();


    }

    public static TimeUtils getInstance(Context context) {
        if (mTimeUtils == null) {
            mTimeUtils = new TimeUtils(context);
        }
        return mTimeUtils;
    }

    public void putTime(String key,long value) {
        mEditor.putLong(mTime + key, value);
        mEditor.commit();
    }
    public void putTime(String key,String value) {
        mEditor.putString(mTime + key, value);
        mEditor.commit();
    }
    public long getLongTime(String key) {
        return mPreferences.getLong(mTime + key, 0);
    }
    public String getStringTime(String key) {
        return mPreferences.getString(mTime + key, "");
    }


    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }
    public static String getTimeToSysDay(){
        Calendar calendars = Calendar.getInstance();
        calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(calendars.get(Calendar.DATE));
    }
}
