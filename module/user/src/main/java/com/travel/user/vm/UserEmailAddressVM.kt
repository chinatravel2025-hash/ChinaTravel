package com.travel.user.vm

import LoginRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList

class UserEmailAddressVM:ViewModel() {
    var emailContent = MutableLiveData("")
    var canNext = MutableLiveData(false)


    fun navigationVerificationEmail(){
        emailContent.value?.let { mail->
            LoginRepository.loginRepository.fetchCaptcha(mail){
                ARouter.getInstance().build(ARouterPathList.USER_VERIFICATION_CODE)
                    .withString("mail",mail)
                    .navigation()
                LogUtils.d(" lklklk","emailContent =$mail")

            }
        }

    }

    fun showClear(content:CharSequence?){
        emailContent.value=content.toString()
        canNext.value = !content.toString().isNullOrEmpty()
    }

    fun navigationSignIn(){

    }


}