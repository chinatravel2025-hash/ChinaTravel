package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList

class UserSetNickNameVM:ViewModel() {
    var nickName = MutableLiveData("")


    fun navigationLogin(){
        ARouter.getInstance().build(ARouterPathList.APP_MAIN)
            .navigation()
    }

    fun inputNickName(content:CharSequence?){
        nickName.value=content.toString()

    }
}