package com.travel.home.ui.city

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.LatLngBounds
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.BlockUtils
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.adapter.PlaceBlockAdapter
import com.travel.home.databinding.HomeActivityTripDetailBinding
import com.travel.home.vm.HomeTripDetailViewModel


@Route(path = ARouterPathList.HOME_TRIP_DETAIL)
class HomeTripDetailActivity : BaseStatusBarActivity(), LocationSource, AMapLocationListener {

    private lateinit var binding: HomeActivityTripDetailBinding
    private lateinit var mVM: HomeTripDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.home_activity_trip_detail
    }

    @JvmField
    @Autowired
    var tripId: Long? = 0L

    private var mListener: OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var mPlaceBlockAdapter: PlaceBlockAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeTripDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityTripDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        initObserve()
        initTripContent(savedInstanceState)
        mVM.getHomeTravelProducts(tripId ?: 0)
        //initMap()
        binding.btnNext.setOnClickListener {
            ARouter.getInstance().build(ARouterPathList.HOME_CHAT)
                .navigation(SmartActivityUtils.getTopActivity())
        }
  
    }

    private fun initObserve() {
        mVM.mTravelProduct.observe(this) { city ->
            binding.banner.setAdapter(NormalBannerAdapter(city.pic_url_list))
            val data = BlockUtils.getBlocksList(city?.introduce?:"")
            mPlaceBlockAdapter?.setList(data)
        }
    }
/*    private fun initMap() {
        //val latLng = LatLng(31.075867780515686, 121.59554847645956)
        //  val latLng = LatLng(31.238068, 121.501654)
        //标记 https://blog.csdn.net/w794840800/article/details/80017220

        val builder = LatLngBounds.builder()
        builder.include(LatLng(31.234521, 121.530699))
        builder.include(LatLng(31.075867780515686, 121.59554847645956))
        builder.include(LatLng(31.238068, 121.501654))

        val latLngs = mutableListOf<LatLng>()
        latLngs.add(LatLng(31.234521, 121.530699)) // 上海市政府
        latLngs.add(LatLng(31.075867780515686, 121.59554847645956)) // 北京天安门
        latLngs.add(LatLng(31.238068, 121.501654)) // 天津市政府
//    val markerView=    LayoutInflater.from(this).inflate(com.china.travel.widget.R.layout.marker_layout,binding.space,false)
//        val markerOptions = MarkerOptions()
//        markerOptions.apply {
//            position(latLng)
//            snippet("DefaultMarker")
//            icon(BitmapDescriptorFactory.fromView(markerView))
//            draggable(false)
//                .visible(true)
//        }
        // binding.space.map.addMarker(markerOptions)

        for (latLng in latLngs) {
            binding.space.map.addMarker(MarkerOptions()
                .position(latLng)
                .snippet("DefaultMarker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))) // 标记的图标
            // 可以添加监听器等操作
        }

        val myLocationStyle = MyLocationStyle()
        myLocationStyle.apply {
            myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
            showMyLocation(true)
            myLocationIcon(BitmapDescriptorFactory.fromResource
                (com.china.travel.widget.R.mipmap.gps_point))
        }
        binding.space.map.apply {
            setMapLanguage(AMap.ENGLISH)
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isScaleControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.setAllGesturesEnabled(false)
            setLocationSource(this@HomeTripDetailActivity)

            isMyLocationEnabled = true
            setMyLocationStyle(myLocationStyle)

            //城市 15
            //  moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50))
            //    invalidate();// 刷新地图
        }
    }*/


    private fun initTripContent(savedInstanceState: Bundle?) {
        binding.rvTripContent.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = manager
            mPlaceBlockAdapter = PlaceBlockAdapter(this@HomeTripDetailActivity,savedInstanceState)
            adapter = mPlaceBlockAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        if(mPlaceBlockAdapter?.mapView?.isActivated == true){
            mPlaceBlockAdapter?.mapView?.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mPlaceBlockAdapter?.mapView?.onPause()
        deactivate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPlaceBlockAdapter?.mapView?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlaceBlockAdapter?.mapView?.onDestroy()
        mlocationClient?.onDestroy()
    }

    override fun activate(listener: OnLocationChangedListener?) {
        mListener = listener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(this)
            mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient?.setLocationListener(this@HomeTripDetailActivity)
            //设置为高精度定位模式
            mLocationOption?.setLocationMode(AMapLocationMode.Hight_Accuracy)
            //设置定位参数
            mlocationClient?.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient?.startLocation()
        }
    }

    override fun deactivate() {
        mListener = null
        mlocationClient?.stopLocation()
        mlocationClient?.onDestroy()
        mlocationClient = null
    }

    override fun onLocationChanged(p0: AMapLocation?) {

    }


}