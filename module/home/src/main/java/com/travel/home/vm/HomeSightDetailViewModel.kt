package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.PlaceType
import com.aws.bean.util.ObjectTypeUtil
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSightDetailViewModel : ViewModel() {

    var title = MutableLiveData<String>("Sightseeing")
    var mDataPlace = MutableLiveData<PlaceItem>()

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)


    fun getPlaceList(type: Int, id:Long){
        var placeType:PlaceType

         when(type){
            1->{
                placeType=  PlaceType.SIGHT
                title.value="Sightseeing"
            }

            2->{
                placeType=   PlaceType.SHOP
                title.value="Shop"
            }
            else->{
                placeType=   PlaceType.RESTAURANT
                title.value="Restaurant"
            }
        }

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

    fun navigatorMapPage(){
        ARouter.getInstance().build(ARouterPathList.MAP_HOME_VIEW)
            .navigation()
    }
}