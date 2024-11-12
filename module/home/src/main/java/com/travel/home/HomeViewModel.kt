package com.travel.home

import HomeAPI
import HomeRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.BannerDTO
import com.aws.bean.entities.home.BannerItem
import com.aws.bean.entities.home.CityDTO
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductDTO
import com.aws.bean.entities.home.TravelProductItem
import com.aws.bean.util.GsonUtil
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.utils.LogUtils
import com.example.base.utils.SmartActivityUtils
import com.example.base.utils.TLog
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.example.router.ARouterPathList
import com.travel.home.api.HomeImpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
     val PAGE_SIZE=10
    var defaultCityId=0L

    var mDataBanner = MutableLiveData<List<BannerItem>>(emptyList())
    var mDataCity = MutableLiveData<List<CityItem>>(emptyList())
    var mCityPageNum = 1
    var cityMaxPage=1
    var mDataPlace = MutableLiveData<List<PlaceItem>>(emptyList())
    var mPlacePageNum = 1
    var placeMaxPage = 1

    var mTravelProducts = MutableLiveData<List<TravelProductItem>>(emptyList())
    var mTravelProductPageNum = 1
    var travelProductMaxPage = 1

    private val homeApi = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)


    fun navigationSearch(){
        ARouter.getInstance().build(ARouterPathList.HOME_SEARCH)
            .navigation()
    }

    fun getBannerList(){
        homeApi.getHomeBannerList(1,5).enqueue(object :
            Callback<ResponseResult<BannerDTO>> {
            override fun onResponse(
                call: Call<ResponseResult<BannerDTO>>,
                response: Response<ResponseResult<BannerDTO>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    mDataBanner.value=response.body()?.data?.list
                }
            }

            override fun onFailure(call: Call<ResponseResult<BannerDTO>>, t: Throwable) {
               // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }

    fun getCityList(){
        homeApi.getHomeCityList(mCityPageNum,PAGE_SIZE).enqueue(object :
            Callback<ResponseResult<CityDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<CityDTO?>>,
                response: Response<ResponseResult<CityDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    cityMaxPage=response.body()?.data?.page?:1
                    mDataCity.value=response.body()?.data?.list
                    LogUtils.d("lklklklk","cityMaxPage=${response.body()?.data?.page}")
                    if (!response.body()?.data?.list.isNullOrEmpty()){
                        defaultCityId=response.body()?.data?.list?.get(0)?.id?:0
                        getPlaceList()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseResult<CityDTO?>>, t: Throwable) {
               // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }

    fun getPlaceList(){
        homeApi.getHomeAllPlaceType(mPlacePageNum,PAGE_SIZE,defaultCityId).enqueue(object :
            Callback<ResponseResult<PlaceDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<PlaceDTO?>>,
                response: Response<ResponseResult<PlaceDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    placeMaxPage=response.body()?.data?.page?:1
                    mDataPlace.value=response.body()?.data?.list
                    LogUtils.d("lklklklk","placeMaxPage=${response.body()?.data?.page}")
                }
            }
            override fun onFailure(call: Call<ResponseResult<PlaceDTO?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }

    fun getHomeTravelProducts(){
        homeApi.getHomeTravelProducts(mTravelProductPageNum,PAGE_SIZE).enqueue(object :
            Callback<ResponseResult<TravelProductDTO?>> {
            override fun onResponse(
                call: Call<ResponseResult<TravelProductDTO?>>,
                response: Response<ResponseResult<TravelProductDTO?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    travelProductMaxPage=response.body()?.data?.page?:1
                    mTravelProducts.value=response.body()?.data?.list
                    LogUtils.d("lklklklk","travelProductMaxPage=${response.body()?.data?.page}")
                }
            }

            override fun onFailure(call: Call<ResponseResult<TravelProductDTO?>>, t: Throwable) {
                // SmartToast.classic().showInCenter(t.message ?: "请重新尝试")

            }
        })
    }
    fun myTrip(){
        ARouter.getInstance().build(ARouterPathList.HOME_CHAT)
            .navigation(SmartActivityUtils.getTopActivity())
    }

    fun addFavorite(objectType: ObjectType, id:Long, callback: (Boolean) -> Unit){
        HomeRepository.homeRepository.addFavorite(objectType, id, callback)
    }
    fun cancelFavorite(objectType: ObjectType, id:Long, callback: (Boolean) -> Unit){
        HomeRepository.homeRepository.cancelFavorite(objectType, id, callback)
    }
}