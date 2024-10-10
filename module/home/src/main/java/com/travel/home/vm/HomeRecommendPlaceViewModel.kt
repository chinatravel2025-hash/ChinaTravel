package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.util.ObjectTypeUtil
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRecommendPlaceViewModel : ViewModel() {


    var mDataPlace = MutableLiveData<List<PlaceItem>>(emptyList())
    var mPlacePageNum = 1
    var currentSize=0
    var maxSize=0

    var defaultCityId=0L

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getPlaceList(objectType: ObjectType){
        homeApi.getHomePlaceType(ObjectTypeUtil.getObjectType(objectType),mPlacePageNum,10,defaultCityId).enqueue(object :
            Callback<ResponseResult<PlaceDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<PlaceDTO?>>,
                response: Response<ResponseResult<PlaceDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    maxSize=response.body()?.data?.count?:0
                    currentSize += (response.body()?.data?.list?.size ?: 0)
                    mDataPlace.value=response.body()?.data?.list
                }
            }
            override fun onFailure(call: Call<ResponseResult<PlaceDTO?>>, t: Throwable) {


            }
        })
    }

}