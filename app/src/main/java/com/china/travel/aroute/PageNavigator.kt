package com.china.travel.aroute
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList

object PageNavigator {


    /**
     * 跳转登录选择
     */
    fun navigatorMainPage(){
        ARouter.getInstance().build(ARouterPathList.APP_MAIN)
            .navigation()
    }


}