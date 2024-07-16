package com.example.http


open class API {

    companion object {

        /**
         * grpc host
         */
        var env = "production"

        //测试环境
        private val WEBSOCKET_TEST_URL = "wss://ws-test.ours-meta.com:443/"
        //开发
        private val WEBSOCKET_DEVELOP_URL = "wss://ws-lan.ours-meta.com:31480/"
        //线上
        private val WEBSOCKET_ONLINE_URL =  "wss://ws.ours-meta.com/"

        //websocket 地址
        var WEBSOCKET_URL = WEBSOCKET_ONLINE_URL



        //开发
        private val TRACKING_DEVELOP_URL = "https://dc-lan.ours-meta.com/rtanalysis/dcdatacoll.do"
        //测试环境
        private val TRACKING_TEST_URL = "https://dc-test.ours-meta.com/rtanalysis/dcdatacoll.do"
        //线上
        private val TRACKING_ONLINE_URL =  "https://dc.ours-meta.com/rtanalysis/dcdatacoll.do"
        //使用的埋点统计地址
        var TRACKING_URL = TRACKING_ONLINE_URL

        /**
         * 使用说明
         *
         */
        //开发
        private val USER_MANUAL_URL = "http://h5-lan.ours-meta.com:31922"
        //测试环境
        private val USER_MANUAL_TEST_URL = "https://oa-h5.ours-meta.com"
        //线上
        private val USER_MANUAL_ONLINE_URL =  "https://h5.ours-meta.com"


        /**
         * 获取web 的全路径
         */
        fun getWebUrlFullPath(path:String?):String?{

            var fullPath = ""
            path?.let {
                fullPath = if(it.startsWith("https:") || it.startsWith("http:")){
                    it
                }else if(it.startsWith("ours-meta:") || it.startsWith("ours:")) {
                    it
                }else {
                    "${API().hostH5()}${if (it.startsWith("/")) it.substring(1) else it}"
                }
            }

            return fullPath
        }

    }

    private fun dev(): String {
        return "bff-lan.ours-meta.com"
    }


    private fun fat(): String {
        return "bff-test.ours-meta.com"
    }

    private fun uat(): String {
        return "bff-pre.ours-meta.com"
    }

    private fun release(): String {
        return "bff.ours-meta.com"
    }


    private fun h5dev(): String {
        return "https://oa-h5.ours-meta.com/"
    }
    private fun h5fat(): String {
        return "https://oa-h5.ours-meta.com/"
    }
    private fun h5uat(): String {
        return "https://h5.ours-meta.com/"
    }

    private fun h5release(): String {
        return "https://h5.ours-meta.com/"
    }


    open fun hostH5(): String {
        return when (env) {
            "dev" -> h5dev()
            "uat" -> h5uat()
            "fat" -> h5fat()
            else -> h5release()
        }
    }

    open fun host(): String {
        return when (env) {
            "dev" -> dev()
            "fat" -> fat()
            "uat" -> uat()
            else -> release()
        }
    }

    open fun port(): Int {
        return when (env) {
            "dev" -> 31480
            else -> 443
        }
    }

    open fun websocketHost(): String {
        return when (env) {
            "dev" -> WEBSOCKET_DEVELOP_URL
            "fat" -> WEBSOCKET_TEST_URL
            else -> WEBSOCKET_ONLINE_URL
        }
    }

    open fun userManualHost(): String {
        return when (env) {
            "dev" -> USER_MANUAL_URL
            "fat" -> USER_MANUAL_TEST_URL
            else -> USER_MANUAL_ONLINE_URL
        }
    }

    open fun trackingHost(): String {
        return when (env) {
            "dev" -> TRACKING_DEVELOP_URL
            "fat" -> TRACKING_TEST_URL
            else -> TRACKING_ONLINE_URL
        }
    }


    open fun QAPMPropertyKeyAppId(): String {
        return when (env) {
            "dev" -> "34cc622f-17139"
            "uat" ->"f68f6f2d-8036"
            "fat" ->"5a6bb246-4397"
            else ->"f68f6f2d-8036"
        }
    }
}