package com.travel.user.vm

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.life
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.drake.interval.Interval
import com.example.router.ARouterPathList
import java.util.concurrent.TimeUnit

class UserVerificationCodeVM:ViewModel() {
    var countDown = MutableLiveData("")
    var resultError = MutableLiveData(false)
    var resend = MutableLiveData(false)
    fun navigationLogin(mail:String,captcha:String,activity: FragmentActivity){
        //注册秘密发送的验证码
        LoginRepository.loginRepository.checkCaptcha(mail,captcha,"register"){ result->
            if (result){
                resultError.value=false
                ARouter.getInstance().build(ARouterPathList.USER_PASSWORD_SET)
                    .withString("mail",mail)
                    .withInt("actionType",0)
                    .navigation(activity, object : NavCallback() {
                        override fun onArrival(p0: Postcard?) {
                            activity.finish()
                        }

                    })
            }else{
                resultError.value=true
            }

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