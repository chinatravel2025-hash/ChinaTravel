package com.example.base.utils

import android.content.Context
import android.net.ConnectivityManager
import com.blankj.utilcode.util.NetworkUtils

/**
 * @ClassName: NetworkUtil
 * @Description:检查网络工具类
 * @Author: li_yi
 * @CreateDate: 2021/8/13 13:38
 */
class NetworkUtil {


    enum class SimpleNetworkType{
        WIFI,
        MOBILE,
        NO
    }
    companion object {
        /**
         * 是否开启了网络设置 true 开启  false 关闭
         * @param context
         */
        @JvmStatic
        fun isNetworkAvailable(context: Context): Boolean {
            val networkInfo = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            if (networkInfo is ConnectivityManager) {
                return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    networkInfo.activeNetwork != null
                } else {
                    networkInfo.activeNetworkInfo != null
                }
            }
            return false
        }


        /**
         * 获取ip 地址
         */
        fun getIPAddress():String{
            return NetworkUtils.getIPAddress(true);
        }





        @JvmStatic
        fun isWifiAvalilable():Boolean{
            var type = NetworkUtils.getNetworkType()
            if(type == NetworkUtils.NetworkType.NETWORK_WIFI){
                return true;
            }
            return false
        }


        @JvmStatic
        fun getFriendlyNetworkTypeName():String{
            var type = NetworkUtils.getNetworkType()
            return when(type){
                NetworkUtils.NetworkType.NETWORK_WIFI ->
                   "WIFI 网络"

                NetworkUtils.NetworkType.NETWORK_2G,
                NetworkUtils.NetworkType.NETWORK_3G,
                NetworkUtils.NetworkType.NETWORK_4G,
                NetworkUtils.NetworkType.NETWORK_5G,
                ->
                    "移动网络"

                else
                -> "未知"
            }
        }

        /**
         * 网络类型判断
         */
        @JvmStatic
        fun getNetWorkType():SimpleNetworkType{
            var type = NetworkUtils.getNetworkType()
           return when(type){
                NetworkUtils.NetworkType.NETWORK_WIFI ->
                    SimpleNetworkType.WIFI

                NetworkUtils.NetworkType.NETWORK_2G,
                    NetworkUtils.NetworkType.NETWORK_3G,
                    NetworkUtils.NetworkType.NETWORK_4G,
                    NetworkUtils.NetworkType.NETWORK_5G,
                    -> SimpleNetworkType.MOBILE

                else
                    -> SimpleNetworkType.NO
            }

        }


    }
}