package com.example.base.utils

import android.net.Uri


/**
 * @Description:commponent_file
 * @Author: li_yi
 * @CreateDate: 2021/8/13 13:38
 */
object UrlUtil {
    /**
     * 是否包含url
     * @param url
     */
    fun isHasUrl(url: String?): Boolean {
        return (url?.startsWith("http") == true || url?.startsWith("ttps") == true || url?.startsWith("ours://") == true)
    }

    /**
     * 得到文件名
     * @param url
     */
    fun urlToFileName(url: String): String {
        if (url.startsWith("http") || url.startsWith("ttps") || url.startsWith("ours://")) {
            return Uri.parse(url).path ?: url
        }
        return url
    }

    /**
     * 得到文件名
     * @param url
     */
    fun urlToBase(url: String): String {
        if (url.startsWith("http") || url.startsWith("ttps") || url.startsWith("ours://")) {
            Uri.parse(url).authority?.apply {
                val index = indexOf(".")
                if (index > 0) {
                    return substring(0, index)
                }
            }
        }
        return ""
    }
}