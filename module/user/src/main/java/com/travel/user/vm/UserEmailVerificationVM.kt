package com.travel.user.vm

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.life
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.dialog.StatusDialog
import com.drake.interval.Interval
import com.example.router.ARouterPathList
import java.util.concurrent.TimeUnit

class UserEmailVerificationVM:ViewModel() {
    var email = MutableLiveData("")
    var countDown = MutableLiveData("")
    var resultError = MutableLiveData(false)
    var resend = MutableLiveData(false)
    fun navigationMain(sms:String, fragmentManager: FragmentManager){
        //修改秘密发送的验证码
        LoginRepository.loginRepository.checkCaptcha(email.value?:"",sms){
            showDialog(fragmentManager)
        }



    }
    private fun showDialog(fragmentManager: FragmentManager) {
        val show = StatusDialog() {
            ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                .navigation()
            null
        }
        show.show(fragmentManager, "StatusDialog")
    }
    fun navigationLogin(){
        ARouter.getInstance().build(ARouterPathList.USER_REGISTER)
            .navigation()
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