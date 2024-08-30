package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList
import kotlinx.coroutines.flow.StateFlow

class UserOrdersVM:ViewModel() {
    var messageList = MutableLiveData<List<String>>()
    var stateLayoutStatus= MutableLiveData<Int>()




    fun navigationLogin(){
        ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
            .navigation()
    }
}