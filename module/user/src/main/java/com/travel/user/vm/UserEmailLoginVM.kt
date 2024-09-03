package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.utils.PasswordUtil
import com.example.router.ARouterPathList

class UserEmailLoginVM:ViewModel() {
    var originPw = MutableLiveData("")
    var email = MutableLiveData("")
    var showPw = MutableLiveData(false)
    var canNext = MutableLiveData(false)

    fun navigationLogin() {
        if (email.value.isNullOrEmpty() || originPw.value.isNullOrEmpty()){
            SmartToast.classic().showInCenter("密码不符合规则")
        }else{
            LoginRepository.loginRepository.loginByEmail(email.value?:"",originPw.value?:""){
                ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                    .navigation()
            }
        }

    }

    fun setOriginPw(content: CharSequence?) {
        originPw.value = content.toString()
    }

    fun setEmail(content: CharSequence?) {
        email.value = content.toString()
    }



}