package com.example.base.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.base.base.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MobileUtils {

    //跳转至授权页面
    public static void jumpStartInterface(Activity activity) {

        String sdk = Build.VERSION.SDK; // SDK号
        String model = Build.MODEL; // 手机型号
        String release = Build.VERSION.RELEASE; // android系统版本号
        String brand = Build.BRAND;//手机厂商

        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(activity);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(activity);
        } else if(TextUtils.equals(brand.toLowerCase(), "vivo")){
            gotoVivoPermission(activity);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(activity);
        } else {
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }
    private static String getLauncherClassName(Context context) {
        ComponentName launchComponent = getLauncherComponentName(context);
        if (launchComponent == null) {
            return "";
        } else {
            return launchComponent.getClassName();
        }
    }
    private static ComponentName getLauncherComponentName(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context
                .getPackageName());
        if (launchIntent != null) {
            return launchIntent.getComponent();
        } else {
            return null;
        }
    }
    public static boolean setHuaweiBadge(int count, Context context) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")){
            try {
                String launchClassName = getLauncherClassName(context);
                if (TextUtils.isEmpty(launchClassName)) {
                    return false;
                }
                Bundle bundle = new Bundle();
                bundle.putString("package", context.getPackageName());
                bundle.putString("class", launchClassName);
                bundle.putInt("badgenumber", count);
                context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher" +
                        ".settings/badge/"), "change_badge", null, bundle);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", "com.taoke.talllk");
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 跳转到vivo的权限管理系统
     */
    private static void gotoVivoPermission(Context context) {
        Intent localIntent;
        if (((Build.MODEL.contains("Y85")) && (!Build.MODEL.contains("Y85A"))) || (Build.MODEL.contains("vivo Y53L"))) {
            localIntent = new Intent();
            localIntent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
            localIntent.putExtra("packagename", context.getPackageName());
            localIntent.putExtra("tabId", "1");
            context.startActivity(localIntent);
        } else {
            localIntent = new Intent();
            localIntent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
            localIntent.setAction("secure.intent.action.softPermissionDetail");
            localIntent.putExtra("packagename", context.getPackageName());
            context.startActivity(localIntent);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     */
    public static void copyContentToClipboard(String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    public static String getContentToClipboard(){
        ClipboardManager clipboard =  (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            CharSequence text = clipData.getItemAt(0).getText();
            if (text != null) {
               return text.toString();
            }
        }
        return "";
    }


    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
    }




    public static String hidePhoneNumber(String phoneNumber) {
        if (phoneNumber.length()<4){
            return "未绑定";
        }
        return phoneNumber.substring(0, 3) + "******" + phoneNumber.substring(phoneNumber.length()-2);
    }

}


