package com.travel.guide.utils

import android.content.Context
import android.widget.Toast
import com.aws.bean.util.GsonUtil
/*import com.tencent.qcloud.tim.push.TIMPushManager
import com.tencent.qcloud.tim.push.interfaces.TIMPushCallback*/

object V2TMPush {
    val TAG = "LoginRepository"
    fun registerPush(context: Context){
//todo 推送
        /*TIMPushManager.getInstance().registerPush(context, object : TIMPushCallback<Any?>() {
            override fun a(data: Any?) {
                data?.let {
                    LogUtils.i(TAG, "TIMPushManager ${GsonUtil.toJson(data)}")

                }
            }
        })*/
    }

    fun unRegisterPush(context: Context){
        //todo 推送
        /*LogUtils.i(TAG, "TIMPushManager unRegisterPush}")
        TIMPushManager.getInstance().unRegisterPush(object : TIMPushCallback<Any?>() {
            override fun a(data: Any?) {
                data?.let {
                    LogUtils.i(TAG, "TIMPushManager 注销 ${GsonUtil.toJson(data)}")

                }
            }

        })*/
    }
}