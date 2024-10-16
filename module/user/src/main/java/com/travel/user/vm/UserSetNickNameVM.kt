package com.travel.user.vm

import LoginAPI
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.user.OrderDTO
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.User
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.user.api.UserImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSetNickNameVM : ViewModel() {
    var nickName = MutableLiveData("")

    private val userApi = RequestManager.build(UserImpApi().host()).create(LoginAPI::class.java)

   private fun getOrderList(userName: String, callback: (() -> Unit)) {
        userApi.userName(hashMapOf(
            "user_name" to userName,
        )).enqueue(object :
            Callback<ResponseResult<Any?>> {
            override fun onResponse(
                call: Call<ResponseResult<Any?>>,
                response: Response<ResponseResult<Any?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    User.setUserName(userName)
                    callback.invoke()
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "设置昵称失败")
            }
        })
    }

    fun navigationLogin() {
        if (nickName.value.isNullOrEmpty()) {
            SmartToast.classic().showInCenter("请输入昵称")
            return
        }
        getOrderList(nickName.value ?: "") {
            ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                .navigation()
        }
    }

    fun inputNickName(content: CharSequence?) {
        nickName.value = content.toString()

    }
}