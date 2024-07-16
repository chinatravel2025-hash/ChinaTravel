package com.example.base.localstore;

import android.content.Context;

import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author: li yi
 * @Date: 2020/11/13
 */
public class MMKVSpUtils {

    private static final String KEY_PROCESS = "InterProcessKV";


    public static void init(Context context) {
        try {
            MMKV.initialize(context);
        } catch (Exception e) {
        }
    }

    private static MMKV getSharedPreferences() {
        return MMKV.defaultMMKV();
    }

    /**
     * 支持多进行
     *
     * @return
     */
    private static MMKV getSharedPreferencesProcess() {

        return MMKV.mmkvWithID(KEY_PROCESS, MMKV.MULTI_PROCESS_MODE);
    }


    /**
     * 支持多进程
     *
     * @param key
     * @param value
     * @param multiProcess 是否要支持多进程
     */
    public static void putString(String key, String value, boolean multiProcess) {
        if (multiProcess) {
            getSharedPreferencesProcess().encode(key, value);
        } else {
            putString(key, value);
        }
    }

    /**
     * 支持多进程
     *
     * @param key
     * @param value
     * @param multiProcess 是否要支持多进程
     */
    public static String getString(String key, String value, boolean multiProcess) {
        if (multiProcess) {
            return getSharedPreferencesProcess().decodeString(key, value);
        } else {
            return getString(key, value);
        }
    }


    public static void putString(String key, String value) {
        getSharedPreferences().encode(key, value);
    }

    public static String getString(String key, String defaultValue) {
        return getSharedPreferences().decodeString(key, defaultValue);
    }


    public static void putStringSet(String key, Set<String> value) {
        getSharedPreferences().encode(key, value);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getSharedPreferences().decodeStringSet(key, defaultValue);
    }



    public static void putInt(@Nullable String key, int value) {
        getSharedPreferences().encode(key, value);
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().decodeInt(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().encode(key, value);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().decodeBool(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        getSharedPreferences().encode(key, value);
    }

    public static float getFloat(String key, float defaultValue) {
        return getSharedPreferences().decodeFloat(key, defaultValue);
    }

    public static void putLong(String key, long value, boolean multiProcess) {
        if (multiProcess) {
            getSharedPreferencesProcess().encode(key, value);
        } else {
            getSharedPreferences().encode(key, value);
        }
    }

    public static long getLong(String key, long defaultValue, boolean multiProcess) {
        if (multiProcess) {
            return getSharedPreferencesProcess().decodeLong(key, defaultValue);
        } else {
            return getSharedPreferences().decodeLong(key, defaultValue);
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param
     * @param key
     */
    public static void remove(String key) {
        getSharedPreferences().remove(key);
    }


}
