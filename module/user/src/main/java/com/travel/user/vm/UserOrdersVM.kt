package com.travel.user.vm

import LoginAPI
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.user.OrderDTO
import com.aws.bean.entities.user.OrderItem
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.user.api.UserImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserOrdersVM:ViewModel() {
    var mDataSource = MutableLiveData<List<OrderItem>>(emptyList())

    private val userApi = RequestManager.build(UserImpApi().host()).create(LoginAPI::class.java)



    fun navigationLogin(){
        ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
            .navigation()
    }


    fun getOrderList(){
        userApi.getOrderList().enqueue(object :
            Callback<ResponseResult<OrderDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<OrderDTO?>>,
                response: Response<ResponseResult<OrderDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mDataSource.value=response.body()?.data?.list
                }
            }
            override fun onFailure(call: Call<ResponseResult<OrderDTO?>>, t: Throwable) {
            }
        })
    }
}