package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList

class UserSetNickNameVM:ViewModel() {
    var selectLoginType = MutableLiveData(0)


    fun navigationLogin(){
        ARouter.getInstance().build(ARouterPathList.USER_PASSWORD_SET)
            .navigation()

    }
}