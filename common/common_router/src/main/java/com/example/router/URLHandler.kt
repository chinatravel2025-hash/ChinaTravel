package com.example.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList.HOME_TAB_MAGAZINE
import com.example.router.ARouterPathList.HOME_TAB_SESSION
import com.example.router.ARouterPathList.HOME_TAB_ME
import com.example.router.utils.isExist

class URLHandler {

    companion object {

        const val ActivityQCReq = 10000
        const val UnityAlbumReq = 10001
        val activityList = HashSet<String>()
        @JvmStatic
        fun handleURL(context: Context, url: String?, extra: Map<String, String>?): Boolean {
            if (url == null) {
                return false
            }
            var u = url
            var extraMap = extra?.toMutableMap()
            var uri = Uri.parse(u)
            if (uri.scheme == null || uri.scheme == "") {
                u = "ours-meta://$url"
            }
            uri = Uri.parse(u)
            if (uri.scheme == "https" || uri.scheme == "http") {
                ARouter.getInstance().build(ARouterPathList.WEB_HOME).withString("url", u)
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).navigation()
                return true
            }
            if (uri.scheme == "ours-meta" && uri.path != null
            ) {
                var path = uri.path!!
                when (path) {
                    HOME_TAB_MAGAZINE,HOME_TAB_SESSION,HOME_TAB_ME -> {
                        ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                            .withString("tab", path)
                            .navigation()
                        return true
                    }
                }
                // 如果路由不存在
                if (!ARouter.getInstance().isExist(context, path)) {
                    return false
                }
                var postcard = ARouter.getInstance().build(path)
                uri.queryParameterNames.forEach { name ->
                    val value = uri.getQueryParameter(name)
                    postcard.withString(name, value)
                }
                extraMap?.let {
                    for (item in it) {
                        postcard.withString(item.key, item.value)
                    }
                }

            }
            return false
        }

        @JvmStatic
        fun handleURL(context: Context, url: String): Boolean {
            return handleURL(context, url, null)
        }

        private fun getStandardPath(path: String): String {
            var ret = path
            if (!ret.startsWith("/")) {
                ret = "/$ret"
            }
            if (!ret.endsWith("/")) {
                ret = "$ret/"
            }
            return ret
        }
    }
}