package com.example.base.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ProcessUtils {
    public static boolean isMainProcess(Context context) {
        String curProcessName = getProcessName(context, android.os.Process.myPid());
        String packageName = context.getPackageName();
        if (curProcessName == null || packageName == null) {
            return false;
        }
        return curProcessName.equals(packageName);
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }
}
