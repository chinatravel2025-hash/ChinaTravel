package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.TravelProductDTO
import com.aws.bean.entities.home.TravelProductItem
import com.aws.bean.util.GsonUtil
import com.aws.bean.util.ObjectTypeUtil
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.bean.BlockDTO
import com.example.base.utils.BlockUtils
import com.example.base.utils.LogUtils
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeTripDetailViewModel : ViewModel() {


    var mTravelProduct = MutableLiveData<TravelProductItem>()

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getHomeTravelProducts(id:Long){
        homeApi.getTravelProductDetails(id).enqueue(object :
            Callback<ResponseResult<TravelProductItem?>> {
            override fun onResponse(
                call: Call<ResponseResult<TravelProductItem?>>,
                response: Response<ResponseResult<TravelProductItem?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mTravelProduct.value=response.body()?.data
                    response.body()?.data?.introduce?.let {
                      //  LogUtils.d("ssssss","introduce=${BlockUtils.getBlockDto(it)}")

                    }
                }
            }

            override fun onFailure(call: Call<ResponseResult<TravelProductItem?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }

    fun navigatorMapPage(){
        ARouter.getInstance().build(ARouterPathList.MAP_HOME_VIEW)
            .navigation()
    }
}