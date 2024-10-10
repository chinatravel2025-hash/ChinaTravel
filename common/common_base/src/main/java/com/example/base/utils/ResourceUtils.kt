package com.example.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.base.base.App
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode

object ResourceUtils {

    private val TAG = this.javaClass.simpleName
    private val appContext by lazy {

        App.getApp()
    }

    fun getStringText(@StringRes stringResId: Int): String {
        return try {
            appContext.resources.getString(stringResId)
        } catch (e: Exception) {
            ""
        }
    }



    fun dp2Px(dpSize: Int): Float {
        return appContext.resources.displayMetrics.density * dpSize
    }

    fun getDimensPxSize(@DimenRes dimenResId: Int): Int {
        return appContext.resources.getDimensionPixelSize(dimenResId)
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appContext.resources.getColor(colorResId, appContext.theme)
        } else {
            appContext.resources.getColor(colorResId)
        }
    }
    fun getDrawable(@DrawableRes dimenResId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appContext.resources.getDrawable(dimenResId, appContext.theme)
        } else {
            appContext.resources.getDrawable(dimenResId)
        }
    }


    fun getScreenWidth(): Int {
        val outMetrics = DisplayMetrics()
        val wm = appContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        wm?.let {
            it.defaultDisplay?.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }
        return 0
    }

    fun getScreenHeight(): Int {
        val outMetrics = DisplayMetrics()
        val wm = appContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        wm?.let {
            it.defaultDisplay?.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }
        return 0
    }

    fun getColor(context: Context?, @ColorRes resourceId: Int): Int {
        return context?.let { ContextCompat.getColor(it, resourceId) } ?: Color.BLACK
    }

    fun setWH(view: View, w: Int, h: Int) {
        val lp: ViewGroup.LayoutParams = view.layoutParams
        lp.width = w
        lp.height = h
        view.layoutParams = lp
    }

    fun setWH(view: View, w: Float, h: Float) {
        val lp: ViewGroup.LayoutParams = view.layoutParams
        lp.width = w.toInt()
        lp.height = h.toInt()
        view.layoutParams = lp
    }



    fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
        return appContext.resources.getStringArray(arrayResId)
    }

    fun getStatusBarHeight(activity: Activity?): Int {
        activity?.resources?.apply {
            val resourceId = getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return getDimensionPixelSize(resourceId)
            }
        }
        return 0
    }

    /**
     * 加载rgba格式颜色
     * 注意格式是 rgba(7, 66, 244, 0.64)
     *
     * @param rgba
     */
    fun getColorRgba(rgba: String): Int {
        /*val rgba =
            (vo.textBackgroundColor ?: "rgba(255, 255, 255, 0)").trim().replace("rgba(", "")
                .replace(")", "").split(",")
        if(rgba.size==4){
            view.etContent.setTextColor(Color.argb(0, 255, 255, 255))
        }*/
        val substring = rgba.substring(5, rgba.length - 1)
        val split = substring.split(",").toTypedArray()
        val argb = 0
        try {
            val red = split[0].trim { it <= ' ' }.toInt()
            val green = split[1].trim { it <= ' ' }.toInt()
            val blue = split[2].trim { it <= ' ' }.toInt()
            val alphaF = split[3].trim { it <= ' ' }.toInt()
            if (0 <= red && red <= 255 && 0 <= green && green <= 255 && 0 <= blue && blue <= 255 && 0f <= alphaF && alphaF <= 1f) {
                return Color.rgb(red, green, blue)
            }
        } catch (ignored: NumberFormatException) {
        }
        return argb
    }


    fun getColorIdFrom16StrRgba(color16StrRgba:String):Int{
        try {
            if(TextUtils.isEmpty(color16StrRgba)){
                return Integer.MAX_VALUE
            }
            if(color16StrRgba.startsWith("#")){
                if(color16StrRgba.length == 9){
                    val apha = color16StrRgba.substring(7,9)
                    val rgb = color16StrRgba.substring(1,7)
                    val colorStr = "#${apha}${rgb}"
                    LogUtils.i(TAG,colorStr)
                    return Color.parseColor(colorStr)
                }else {
                    return Integer.MAX_VALUE
                }
            }
            if(color16StrRgba.length == 8){
                val apha = color16StrRgba.substring(6,8)
                val rgb = color16StrRgba.substring(0,6)
                val colorStr = "#${apha}${rgb}"
                LogUtils.i(TAG,colorStr)
                return Color.parseColor(colorStr)
            }
        }catch (e:Exception){
            return Integer.MAX_VALUE
        }

        return Integer.MAX_VALUE

    }


    /**
     * 加载rgba格式颜色
     * 注意格式是 rgba(7, 66, 244, 0.64)
     *
     * @param rgba
     */
    fun getColorFromRgba(rgba: String): Int {
        try{
            val substring = rgba.substring(5, rgba.length - 1)
            val split = substring.split(",").toTypedArray()
            val argb = 0
            try {
                val red = split[0].trim { it <= ' ' }.toInt()
                val green = split[1].trim { it <= ' ' }.toInt()
                val blue = split[2].trim { it <= ' ' }.toInt()
                val alphaF = split[3].trim { it <= ' ' }.toInt()
                if (0 <= red && red <= 255 && 0 <= green && green <= 255 && 0 <= blue && blue <= 255 && 0f <= alphaF && alphaF <= 255) {
                    return Color.argb(alphaF,red, green, blue)
                }
            } catch (ignored: NumberFormatException) {
            }
            return argb
        }catch (e:Exception){
            LogUtils.e(TAG,"error = ${e?.toString()}")
        }

        return Integer.MAX_VALUE

    }



}
