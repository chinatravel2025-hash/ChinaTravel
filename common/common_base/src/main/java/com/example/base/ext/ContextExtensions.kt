package com.example.base.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.pm.PackageInfoCompat


//----------尺寸转换----------

//fun Context.dp2px(dpValue: Float): Int {
//    val scale = resources.displayMetrics.density
//    return (dpValue * scale + 0.5f).toInt()
//}
//
//fun Context.px2dp(pxValue: Float): Int {
//    val scale = resources.displayMetrics.density
//    return (pxValue / scale + 0.5f).toInt()
//}
//
//fun Context.sp2px(spValue: Float): Int {
//    val scale = resources.displayMetrics.scaledDensity
//    return (spValue * scale + 0.5f).toInt()
//}
//
//fun Context.px2sp(pxValue: Float): Int {
//    val scale = resources.displayMetrics.scaledDensity
//    return (pxValue / scale + 0.5f).toInt()
//}

fun Int.dp2px(): Int {
    return (this * getDensity() + 0.5f).toInt()
}

fun Float.dp2px(): Int {
    return (this * getDensity() + 0.5f).toInt()
}

fun Int.sp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.scaledDensity
    return (this * scale + 0.5f).toInt()
}

fun Float.sp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.scaledDensity
    return (this * scale + 0.5f).toInt()
}

fun getDensity(): Float {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val config: Configuration = Resources.getSystem().configuration
        config.densityDpi / 160f
    } else {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        metrics.density
    }
}

fun Int.getDimenPx(): Int {
    return Resources.getSystem().getDimensionPixelSize(this)
}


//----------屏幕尺寸----------

//fun Context.getScreenWidth(): Int {
//    val wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        ?: return resources.displayMetrics.widthPixels
//    val point = Point()
//    wm.defaultDisplay.getRealSize(point)
//    return point.x
//}
//
//fun Context.getScreenHeight(): Int {
//    val wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        ?: return resources.displayMetrics.heightPixels
//    val point = Point()
//    wm.defaultDisplay.getRealSize(point)
//    return point.y
//}

fun Context.getScreenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        windowMetrics.bounds.width()
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")  wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun Context.getScreenHeight(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        windowMetrics.bounds.height()
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")  wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun Context.getPackageInfo(packageNameParam: String? = null): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageNameParam ?: packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION") packageManager.getPackageInfo(packageNameParam ?: packageName, 0)
    }
}

fun Context.versionCode() = PackageInfoCompat.getLongVersionCode(getPackageInfo()).toInt()


fun Context.versionName(): String = getPackageInfo().versionName

fun Context.isPackageInstalled(packageName: String): Boolean {
    return try {
        getPackageInfo(packageName)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.startGoogleMarket(packageName: String) {
    val marketUri: Uri = Uri.parse("market://details?id=$packageName")
    val marketIntent = Intent(Intent.ACTION_VIEW).setData(marketUri)
    if(marketIntent.resolveActivity(packageManager) != null) {
        startActivity(marketIntent)
    } else {
        val httpUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        startActivity(Intent(Intent.ACTION_VIEW, httpUri))
    }

}

