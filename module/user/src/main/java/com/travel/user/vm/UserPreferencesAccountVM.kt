package com.travel.user.vm

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import com.travel.user.api.UserImpApi
import LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPreferencesAccountVM:ViewModel() {
    var selectLoginType = MutableLiveData(0)

    private val userApi = RequestManager.build(UserImpApi().host()).create(LoginAPI::class.java)

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

    fun closeUser(callback: (Boolean) -> Unit) {
        userApi.closeUser().enqueue(object : Callback<ResponseResult<Any?>> {
            override fun onResponse(
                call: Call<ResponseResult<Any?>>,
                response: Response<ResponseResult<Any?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any?>>, t: Throwable) {
                callback.invoke(false)
            }
        })
    }
}