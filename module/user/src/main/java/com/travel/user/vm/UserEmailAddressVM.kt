package com.travel.user.vm

import LoginRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.dialog.LoginDialog
import com.china.travel.widget.dialog.StatusDialog
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList

class  UserEmailAddressVM:ViewModel() {
    var emailContent = MutableLiveData("")
    var canNext = MutableLiveData(false)




    fun showClear(content:CharSequence?){
        emailContent.value=content.toString()
        canNext.value = !content.toString().isNullOrEmpty()
    }

    fun navigationSignIn(){
        ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
            .withString("mail",emailContent.value)
            .navigation()
    }


}