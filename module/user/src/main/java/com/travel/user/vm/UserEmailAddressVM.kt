package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList

class UserEmailAddressVM:ViewModel() {
    var emailContent = MutableLiveData("")
    var canNext = MutableLiveData(false)


    fun navigationLogin(){

        ARouter.getInstance().build(ARouterPathList.USER_VERIFICATION_CODE)
            .navigation()

    }

    fun showClear(content:CharSequence?){
        emailContent.value=content.toString()
        canNext.value = !content.toString().isNullOrEmpty()
    }

    fun navigationSignIn(){

    }


}