package com.example.base.common

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItemV2
import com.amap.api.services.poisearch.PoiResultV2
import com.amap.api.services.poisearch.PoiSearchV2
import com.example.base.base.App
import com.example.base.utils.LogUtils
import com.example.base.utils.TLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList


/**
 * 定位帮助类
 * @author li yi
 */
@SuppressLint("StaticFieldLeak")
object LocationHelper : PoiSearchV2.OnPoiSearchListener {
    var poiList = MutableLiveData(ArrayList<PoiItemV2>())
    var searchPoiList = MutableLiveData(ArrayList<PoiItemV2>())
    var aMapLocation = MutableLiveData<AMapLocation>()
    private var normalSearch: Int? = 0
    var pageNum: Int = 1
    var searchNoMoreData = false
    private var pageSize = 10
    private lateinit var poiSearch: PoiSearchV2
    private lateinit var mLocationClient: AMapLocationClient
    private val mLocationListener = AMapLocationListener { aMapLocation ->
        if (aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {
                //可在其中解析amapLocation获取相应内容。
                CoroutineScope(Dispatchers.Main).launch {
                    this@LocationHelper.aMapLocation.value = aMapLocation
                }
                poiSearch()

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtils.e(
                    "AmapError", "location Error, ErrCode:"
                            + aMapLocation.errorCode + ", errInfo:"
                            + aMapLocation.errorInfo
                )
            }
        }
    }

    @JvmStatic
    fun init() {
        AMapLocationClient.updatePrivacyShow(App.getContext(), true, true)
        AMapLocationClient.updatePrivacyAgree(App.getContext(), true)
        //声明定位回调监听器
        mLocationClient = AMapLocationClient(App.getContext())
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener)
        //声明AMapLocationClientOption对象
        var mLocationOption = AMapLocationClientOption()
        //获取一次定位结果：
        // 该方法默认为false。
        mLocationOption.isOnceLocation = true
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.isOnceLocationLatest = true
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //低功耗模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        val option = AMapLocationClientOption()
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        //option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationClient.setLocationOption(option)

    }

    @JvmStatic
    fun startLocation() {
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation()
        mLocationClient.startLocation()
    }

    @JvmStatic
    fun destroy() {
        mLocationClient.stopLocation()
        mLocationClient.onDestroy()
    }

    fun getLatitude(): Double {
        return aMapLocation.value?.latitude ?: 0.0
    }

    fun getLongitude(): Double {
        return  aMapLocation.value?.longitude ?: 0.0
    }

    fun getCityCode(): String {
        val code = aMapLocation.value?.adCode ?: "0"
        if(TextUtils.isEmpty(code)){
            return "0"
        }
        return if (code.length >= 4) code.substring(0, 4) else code
    }


    /**
     * 国家名
     */
    fun getCountryName(): String {
        return aMapLocation.value?.country ?: ""
    }


    fun getProvice(): String {

        return aMapLocation.value?.province ?: ""
    }


    fun getCity(): String {

        return aMapLocation.value?.city ?: ""
    }


    fun getArea(): String {
        return aMapLocation.value?.district ?: ""
    }


    fun poiSearch() {
        normalSearch = 0
        aMapLocation.value?.run {
            //keyWord表示搜索字符串，
            // 第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
            // cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            val query = PoiSearchV2.Query("", "", "")
            query.pageSize = pageSize // 设置每页最多返回多少条poiitem
            query.pageNum = 1 //设置查询页码
            poiSearch = PoiSearchV2(App.getContext(), query)
            poiSearch.bound =
                PoiSearchV2.SearchBound(LatLonPoint(latitude, longitude), 1000) //设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(this@LocationHelper)
            poiSearch.searchPOIAsyn()
        }

    }

    fun poiSearch(keyWord: String, pageNum: Int) {
        if (pageNum == 1) {
            searchNoMoreData = false
        }
        this.pageNum = pageNum
        normalSearch = 1
        aMapLocation?.run {
            //keyWord表示搜索字符串，
            // 第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
            // cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            val query = PoiSearchV2.Query(keyWord, "", "")
            query.pageSize = pageSize // 设置每页最多返回多少条poiitem
            query.pageNum = pageNum //设置查询页码
            query.queryString
            poiSearch = PoiSearchV2(App.getContext(), query)
            poiSearch.setOnPoiSearchListener(this@LocationHelper)
            poiSearch.searchPOIAsyn()
        }

    }


    override fun onPoiSearched(p0: PoiResultV2?, p1: Int) {
        p0?.pois?.let { pois ->
            CoroutineScope(Dispatchers.Main).launch {
                when (normalSearch) {
                    0 -> {
                        poiList.value = pois
                    }

                    1 -> {
                        //判断是否有更多的数据
                        searchNoMoreData = pageSize > pois.size
                        //处理分页
                        searchPoiList.value = pois
                    }
                }

            }
        }

        destroy()
    }

    override fun onPoiItemSearched(p0: PoiItemV2?, p1: Int) {


    }
}