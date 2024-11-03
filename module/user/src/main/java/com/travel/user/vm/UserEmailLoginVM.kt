package com.travel.user.vm

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.dialog.LoginDialog
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
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation()
            }
        }
    }

    fun resetPassword(){
        if (email.value.isNullOrEmpty()){
            SmartToast.classic().showInCenter("请填写邮箱")
            return
        }
        email.value?.let { mail->
            LoginRepository.loginRepository.fetchCaptcha(mail,"resetPwd"){
                if (it){
                    ARouter.getInstance().build(ARouterPathList.USER_VERIFICATION_EMAIL)
                        .withString("mail",email.value)
                        .navigation()
                }
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