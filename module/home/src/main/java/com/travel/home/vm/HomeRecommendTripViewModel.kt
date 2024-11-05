package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductDTO
import com.aws.bean.entities.home.TravelProductItem
import com.aws.bean.util.ObjectTypeUtil
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRecommendTripViewModel : ViewModel() {


    var mTravelProducts = MutableLiveData<List<TravelProductItem>>(emptyList())
    var mPlacePageNum = 1
    var currentSize=0
    var maxSize=0

    var defaultCityId=0L

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getHomeTravelProducts(){
        homeApi.getHomeTravelProducts(mPlacePageNum,10).enqueue(object :
            Callback<ResponseResult<TravelProductDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<TravelProductDTO?>>,
                response: Response<ResponseResult<TravelProductDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mTravelProducts.value=response.body()?.data?.list
                }
            }
            override fun onFailure(call: Call<ResponseResult<TravelProductDTO?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")
            }
        })
    }

}