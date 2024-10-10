package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aws.bean.entities.home.CityDTO
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.util.ObjectTypeUtil
import com.example.base.utils.LogUtils
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRecommendViewModel : ViewModel() {

    var mDataCity = MutableLiveData<List<CityItem>>(emptyList())
    var mSelectCity = MutableLiveData<CityItem?>()


    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getCityList(){
        homeApi.getHomeCityList(1,100).enqueue(object :
            Callback<ResponseResult<CityDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<CityDTO?>>,
                response: Response<ResponseResult<CityDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mDataCity.value=response.body()?.data?.list
                }
            }

            override fun onFailure(call: Call<ResponseResult<CityDTO?>>, t: Throwable) {

            }
        })
    }

}