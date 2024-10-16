package com.travel.home.vm

import HomeAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductDTO
import com.aws.bean.entities.home.TravelProductItem
import com.aws.bean.util.ObjectTypeUtil
import com.example.base.utils.LogUtils
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCityDetailViewModel : ViewModel() {

    var cityItem: CityItem?=null
    var mCityDetail = MutableLiveData<CityItem>()
    var mTravelProducts = MutableLiveData<List<TravelProductItem>>(emptyList())

    var mSightPlace = MutableLiveData<List<PlaceItem>>(emptyList())

    var mShopPlace = MutableLiveData<List<PlaceItem>>(emptyList())

    var mRestaurantPlace = MutableLiveData<List<PlaceItem>>(emptyList())

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getHomeCityDetail(id:Long){
        homeApi.getHomeCityDetail(id).enqueue(object :
            Callback<ResponseResult<CityItem?>> {
            override fun onResponse(
                call: Call<ResponseResult<CityItem?>>,
                response: Response<ResponseResult<CityItem?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mCityDetail.value=response.body()?.data
                    cityItem=response.body()?.data
                }
            }

            override fun onFailure(call: Call<ResponseResult<CityItem?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")
            }
        })
    }
    fun getHomeTravelProducts(){
        homeApi.getHomeTravelProducts(1,10).enqueue(object :
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

    fun getPlaceList(objectType:ObjectType,cityId:Long){
        homeApi.getHomePlaceType(ObjectTypeUtil.getObjectType(objectType),1,10,cityId).enqueue(object :
            Callback<ResponseResult<PlaceDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<PlaceDTO?>>,
                response: Response<ResponseResult<PlaceDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                   when(objectType ){
                       ObjectType.SIGHT->{
                           mSightPlace.value=response.body()?.data?.list
                       }
                       ObjectType.SHOP->{
                           mShopPlace.value=response.body()?.data?.list
                       }
                      else->{
                           mRestaurantPlace.value=response.body()?.data?.list
                       }
                   }
                }
            }
            override fun onFailure(call: Call<ResponseResult<PlaceDTO?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }

    fun moreContent(type:ObjectType){
        ARouter.getInstance().build(ARouterPathList.HOME_RECOMMEND)
            .withString("type",ObjectTypeUtil.getObjectType(type))
            .withSerializable("city",cityItem)
            .navigation()
    }


    fun addFavorite(objectType: ObjectType, id:Long, callback: (Boolean) -> Unit){
        HomeRepository.homeRepository.addFavorite(objectType, id, callback)
    }
    fun cancelFavorite(objectType: ObjectType, id:Long, callback: (Boolean) -> Unit){
        HomeRepository.homeRepository.cancelFavorite(objectType, id, callback)
    }


}