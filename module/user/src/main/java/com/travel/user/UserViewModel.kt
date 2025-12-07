package com.travel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.User
import com.example.base.base.UserInfo
import com.example.router.ARouterPathList

class UserViewModel : ViewModel() {

    var userInfo = MutableLiveData<UserInfo>()
    fun navigationSupport(){
        ARouter.getInstance().build(ARouterPathList.USER_SUPPORT)
            .navigation()
    }

    fun navigationFavorite(){
        ARouter.getInstance().build(ARouterPathList.HOME_FAVORITE)
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

    val showPrivacyPolicyDialog = MutableLiveData<Boolean>()
    
    fun showPrivacyPolicy(){
        showPrivacyPolicyDialog.value = true
    }
}