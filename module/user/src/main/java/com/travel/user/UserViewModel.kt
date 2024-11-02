package com.travel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.User
import com.example.router.ARouterPathList

class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is User Fragment"
    }
    val text: LiveData<String> = _text

    fun navigationSupport(){
        ARouter.getInstance().build(ARouterPathList.USER_SUPPORT)
            .navigation()
    }

    fun navigationPreferences(){
        ARouter.getInstance().build(ARouterPathList.USER_PREFERENCES_HOME)
            .navigation()
    }
    fun navigationOrders(){
        ARouter.getInstance().build(ARouterPathList.USER_ORDERS)
            .navigation()
    }

    fun logOut(){
        User.logout()
        ARouter.getInstance().build(ARouterPathList.USER_REGISTER)
            .navigation()
    }
}