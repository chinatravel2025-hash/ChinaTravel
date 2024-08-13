package com.example.base.ext

import androidx.annotation.StringRes
import com.drake.tooltip.toast


/**
 * 显示 Toast
 */
fun showToast(msg: String?) {
    msg?.let {
        // 过滤掉协程退出的报错
        if(it.isNotBlank() && it != "Job was cancelled") {
           toast(msg)
        }
    }
}

fun showToast(@StringRes msg: Int) {
    toast(msg)
}