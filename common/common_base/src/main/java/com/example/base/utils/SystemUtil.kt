package com.example.base.utils

import android.os.Build
import com.example.base.base.SDKConstant

object SystemUtil {

    // 华为
    const val PHONE_HUAWEI = "huawei"

    // 荣耀
    const val PHONE_HONOR = "honor"

    // 华为 NOVA
    const val PHONE_NOVA = "nova"

    // 小米
    const val PHONE_XIAOMI = "xiaomi"

    // vivo
    const val PHONE_VIVO = "vivo"

    // 魅族
    const val PHONE_MEIZU = "meizu"

    // 索尼
    const val PHONE_SONY = "sony"

    // 三星
    const val PHONE_SAMSUNG = "samsung"

    // OPPO
    const val PHONE_OPPO = "oppo"

    // 乐视
    const val PHONE_Letv = "letv"

    // 一加
    const val PHONE_OnePlus = "oneplus"

    // 锤子
    const val PHONE_SMARTISAN = "smartisan"

    // 联想
    const val PHONE_LENOVO = "lenovo"

    // LG
    const val PHONE_LG = "lg"

    // HTC
    const val PHONE_HTC = "htc"

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    fun getSystemVersion(): String? {
        return Build.VERSION.RELEASE
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    fun getSystemModel(): String? {
        return Build.MODEL
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    fun getDeviceBrand(): String? {
        return Build.BRAND
    }

    fun getBrandAppId(): String {
        return when (getDeviceBrand()?.lowercase()) {
            PHONE_HUAWEI, PHONE_NOVA, PHONE_HONOR -> SDKConstant.HX_PUSH_HUAWEI_ID
            PHONE_XIAOMI -> SDKConstant.HX_PUSH_MI_ID
            PHONE_VIVO -> "${SDKConstant.HX_PUSH_VIVO_ID}#${SDKConstant.HX_PUSH_VIVO_KEY}"
            PHONE_MEIZU -> SDKConstant.HX_PUSH_MEIZU_ID
            PHONE_OPPO -> SDKConstant.HX_PUSH_OPPO_KEY
            else -> ""
        }
    }

    fun getBrandAppSecret(): String {
        return when (getDeviceBrand()) {
            PHONE_HUAWEI, PHONE_NOVA, PHONE_HONOR -> SDKConstant.HX_PUSH_HUAWEI_SECRET
            PHONE_XIAOMI -> SDKConstant.HX_PUSH_MI_SECRET
            PHONE_VIVO -> SDKConstant.HX_PUSH_VIVO_SECRET
            PHONE_MEIZU -> SDKConstant.HX_PUSH_MEIZU_SECRET
            PHONE_OPPO -> SDKConstant.HX_PUSH_OPPO_SECRET
            else -> ""
        }
    }
}
