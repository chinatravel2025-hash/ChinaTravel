package com.example.base.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Title:手机系统声音帮助类
 * description:
 * autor:pei
 * created on 2020/2/9
 */
public class PhoneAudioHelper {

    /**
     * 获取手机模式: AudioManager.RINGER_MODE_SILENT(静音模式)
     *             AudioManager.RINGER_MODE_VIBRATE(震动模式)
     *             AudioManager.RINGER_MODE_NORMAL(声音模式)
     * @return int
     */
    public static int getDefultRingerMode(Context context){
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        return am.getRingerMode();
    }

    public static boolean isMute(Context context){
        return getDefultRingerMode(context) == AudioManager.RINGER_MODE_SILENT;
    }

    public static boolean isVibrator(Context context){
        return getDefultRingerMode(context) == AudioManager.RINGER_MODE_VIBRATE;
    }


    /**获取系统默认铃声**/
    public static void getDefultRingtone(Context context) {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE); //获取系统默认的notification提示音,Uri:通用资源标志符
        Ringtone mRingtone = RingtoneManager.getRingtone(context, uri);
        mRingtone.play();
    }

    /**获取系统默认的notification提示音**/
    public static void getDefultRingNotification(Context context) {
        Uri mUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone mRingtone = RingtoneManager.getRingtone(context, mUri);
        mRingtone.play();
    }

}