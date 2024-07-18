package com.example.router.utils

import android.content.Context
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.exception.NoRouteFoundException
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.alibaba.android.arouter.launcher.ARouter

fun ARouter.isExist(context: Context, path:String):Boolean{
    val pretreatmentService = navigation(PretreatmentService::class.java)
    if (null != pretreatmentService && !pretreatmentService.onPretreatment(context, ARouter.getInstance().build(path))) {
        return false
    }
    try {
        LogisticsCenter.completion( ARouter.getInstance().build(path))
    }catch (e: Exception){
        return false
    }
    return true
}