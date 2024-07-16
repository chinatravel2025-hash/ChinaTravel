package com.example.base.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.base.utils.ChannelInfo;

/**
 * Desc:
 * <p>
 * Author: li yi
 * Date: 2021/10/22
 */
public class App {
    public static void initApp(Application context){
        application=context;
    }

    public static void init(Context ct,String c) {
        context = ct;
        channel = c.equals("fat") || c.equals("dev") || c.equals("uat") || c.equals("production")?"OURS":c;
    }
    public static Application getApp() {
        return application;
    }
    public static Context getContext() {
        return context;
    }


    public static String getChannel() {
        if(channel == null){
            channel = ChannelInfo.getChannelNameFromAssets();
        }
        return channel;
    }

    public static String getVersion() {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    public static int getVersionCode() {
        if (context == null) {
            return 0;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }


    public static void setDeviceId(String deviceId) {
        App.deviceId = deviceId;
    }

    public static void setDefaultDeviceId() {
        deviceId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

//        DeviceUtils.getUniqueDeviceId()

        if(TextUtils.isEmpty(deviceId)){
            WifiManager wifiManager = (WifiManager) App.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String macAddress = wifiInfo.getMacAddress();
            deviceId = macAddress;
            return;
        }

        if(TextUtils.isEmpty(deviceId)){
            deviceId = Build.SERIAL;
            return;
        }

        if(TextUtils.isEmpty(deviceId)){
            TelephonyManager telephonyManager = (TelephonyManager)App.getContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            deviceId = imei;
        }
    }
    /**
     * 获取设备唯一ID
     *
     * @return
     */
    public static String getDeviceId() {
        if(TextUtils.isEmpty(deviceId)){
            setDefaultDeviceId();
        }
        return !TextUtils.isEmpty(deviceId)?deviceId:"";
    }

    public static String getLat() {
        return lat;
    }

    public static void setLat(String lat) {
        App.lat = lat;
    }

    public static String getLng() {
        return lng;
    }

    public static void setLng(String lng) {
        App.lng = lng;
    }

    /**
     * 上下文
     */
    private static Context context;
    /**
     * 渠道号
     */
    private static String channel;

    /**
     * 设备号
     */
    private static String deviceId;

    /**
     * 维度
     */
    private static String lat;
    /**
     * 经度
     */
    private static String lng;

    private static Application application;

    private static long startTime = 0;

    public static void setStartTime(long startTime) {
        App.startTime = startTime;
    }

    public static long getStartTime() {
        return startTime;
    }
}
