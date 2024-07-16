package com.example.base.utils

import android.content.Context
import com.permissionx.guolindev.PermissionX


object PermissionCheckUtil {

    /**
     * 检测权限
     */
    fun checkPermission(context: Context,permissions:ArrayList<String>):Boolean{
        permissions.forEach {
            if(!PermissionX.isGranted(context,it)){

                LogUtils.d("checkPermission","it$it")
                return false
            }
            LogUtils.d("checkPermission","true= $it")
        }
        return true
    }
}