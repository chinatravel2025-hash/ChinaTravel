package com.example.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.annotation.IntDef;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class StatusBarUtil {

    public final static int TYPE_MIUI = 0;
    public final static int TYPE_FLYME = 1;
    public final static int TYPE_M = 3;//6.0

    @IntDef({TYPE_MIUI,
            TYPE_FLYME,
            TYPE_M})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }

    /***
     * 设置沉浸式
     */
    public static void immersive(Activity activity) {
        if (activity == null) return;
        immersive(activity.getWindow());
    }

    /**
     * 适配 UI浸入StatusBar但不浸入NavBar
     * Android碎片化太验证，导航栏适配过于复杂，底部视图很容易被导航栏覆盖导致不能被点击
     *
     * 主题中不能 设置 导航栏 相关属性，否则不会生效
     * 适配 Android 15 Edge-to-Edge 要求
     * @param window
     */
    public static void immersive(Window window) {

        if (window == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 15 (API 35) 强制要求 Edge-to-Edge
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // 使用 WindowCompat 启用 Edge-to-Edge
                WindowCompat.setDecorFitsSystemWindows(window, false);
            }
            
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            window.clearFlags(1024);

            //设置状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
            
            // 使用新的 WindowInsetsController API (API 30+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
                if (windowInsetsController != null) {
                    windowInsetsController.setAppearanceLightStatusBars(false);
                }
            } else {
                // 兼容旧版本
                int visibility = window.getDecorView().getSystemUiVisibility();
                //布局内容全屏显示,沉浸式 SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                //防止内容区域大小发生变化
                visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                window.getDecorView().setSystemUiVisibility(visibility);
            }

        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    private static int statusBarHeight = -1;

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight > 0) {
            return statusBarHeight;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        statusBarHeight = result;
        return result;
    }

    public static void setHeightAndPadding(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int statusHeight = getStatusBarHeight(context);
        if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height += statusHeight;
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusHeight, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static boolean setStatusBarDarkTheme(Activity activity, boolean dark) {
        if (activity == null) return false;

        return setStatusBarDarkTheme(activity.getWindow(), dark);
    }

    /**
     * 设置状态栏深色浅色切换
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean setStatusBarDarkTheme(Window window, boolean dark) {
        if (window == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarFontIconDark(window, TYPE_M, dark);
            } else if (OSUtil.isMiui()) {
                setStatusBarFontIconDark(window, TYPE_MIUI, dark);
            } else if (OSUtil.isFlyme()) {
                setStatusBarFontIconDark(window, TYPE_FLYME, dark);
            } else {//其他情况
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * 设置 状态栏深色浅色切换
     */
    public static boolean setStatusBarFontIconDark(Window window, @ViewType int type, boolean dark) {
        switch (type) {
            case TYPE_MIUI:
                return setMiuiUI(window, dark);
            case TYPE_FLYME:
                return setFlymeUI(window, dark);
            case TYPE_M:
            default:
                return setCommonUI(window, dark);
        }
    }

    //设置6.0 状态栏深色浅色切换
    // 适配 Android 15，使用新的 WindowInsetsController API
    public static boolean setCommonUI(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 优先使用新的 WindowInsetsController API (API 30+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
                if (windowInsetsController != null) {
                    windowInsetsController.setAppearanceLightStatusBars(dark);
                    return true;
                }
            }
            // 兼容旧版本 (API 23-29)
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (decorView.getSystemUiVisibility() != vis) {
                decorView.setSystemUiVisibility(vis);
            }
            return true;
        }
        return false;

    }

    //设置Flyme 状态栏深色浅色切换
    public static boolean setFlymeUI(Window window, boolean dark) {
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //设置MIUI 状态栏深色浅色切换
    public static boolean setMiuiUI(Window window, boolean dark) {
        try {
            Class<?> clazz = window.getClass();
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getDeclaredMethod("setExtraFlags", int.class, int.class);
            extraFlagField.setAccessible(true);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
        }
        return vh;
    }

}
