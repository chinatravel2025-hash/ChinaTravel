package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker

class UserPreferencesAccountVM:ViewModel() {
    var selectLoginType = MutableLiveData(0)


    fun selectLogin(type:Int){
        selectLoginType.value=type
        if (type==3){
            ARouter.getInstance().build(ARouterPathList.USER_EMAIL_ADDRESS)
                .navigation()
        }else{
            ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                .navigation()
        }
    }

    fun send(){

    }
}