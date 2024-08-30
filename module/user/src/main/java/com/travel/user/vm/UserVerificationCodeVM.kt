package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.life
import com.alibaba.android.arouter.launcher.ARouter
import com.drake.interval.Interval
import com.example.router.ARouterPathList
import java.util.concurrent.TimeUnit

class UserVerificationCodeVM:ViewModel() {
    var countDown = MutableLiveData("")
    var resultError = MutableLiveData(false)
    var resend = MutableLiveData(false)
    fun navigationLogin(mail:String,captcha:String){
        LoginRepository.loginRepository.checkCaptcha(mail,captcha){
            ARouter.getInstance().build(ARouterPathList.USER_PASSWORD_SET)
                .withString("mail",mail)
                .navigation()
        }
    }

    fun startCountDown() {
        Interval(1, 1, TimeUnit.SECONDS, 60).life(this)
            .subscribe {
                countDown.value="${count - 1}"
                resend.value=false
            }
            .finish {
                countDown.value=""
                resend.value=true
            }
            .start()
    }

}