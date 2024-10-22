package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.PlaceType
import com.aws.bean.util.ObjectTypeUtil
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSightDetailViewModel : ViewModel() {

    var mDataPlace = MutableLiveData<PlaceItem>()

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)


    fun getPlaceList(placeType: PlaceType, id:Long){
        homeApi.getPlaceDetails(ObjectTypeUtil.getPlaceType(placeType),id).enqueue(object :
            Callback<ResponseResult<PlaceItem?>> {
            override fun onResponse(
                call: Call<ResponseResult<PlaceItem?>>,
                response: Response<ResponseResult<PlaceItem?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mDataPlace.value=response.body()?.data
                }
            }
            override fun onFailure(call: Call<ResponseResult<PlaceItem?>>, t: Throwable) {

            }
        })
    }
}