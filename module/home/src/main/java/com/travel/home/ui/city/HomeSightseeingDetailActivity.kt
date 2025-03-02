package com.travel.home.ui.city

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.LatLngBounds
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.PlaceType
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.BlockUtils
import com.example.base.utils.ResourceUtils
import com.example.base.utils.SmartActivityUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.adapter.PlaceBlockAdapter
import com.travel.home.databinding.HomeActivitySightseeingDetailBinding
import com.travel.home.view.ScenicSpotBottomSheetDialog
import com.travel.home.vm.HomeSightDetailViewModel


@Route(path = ARouterPathList.HOME_SIGHTSEEING_DETAIL)
class HomeSightseeingDetailActivity : BaseStatusBarActivity(), LocationSource, AMapLocationListener {

    private lateinit var binding: HomeActivitySightseeingDetailBinding
    private lateinit var mVM: HomeSightDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_sightseeing_detail
    }
    private var mPlaceBlockAdapter: PlaceBlockAdapter? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    @JvmField
    @Autowired
    var placeType: Int = 1 // 1 sight 2 shop 3.restaurant

    @JvmField
    @Autowired
    var placeId: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeSightDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivitySightseeingDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.ac=this
        binding.vm=mVM
        initObserve()
        initPlaceContent(savedInstanceState)
        mVM.getPlaceList(placeType,placeId?:0)
        //initMap()
        binding.btnNext.setOnClickListener {
            ARouter.getInstance().build(ARouterPathList.HOME_CHAT)
                .navigation(SmartActivityUtils.getTopActivity())
        }
    }
    private fun initObserve(){
        mVM.mDataPlace.observe(this){ city->
            binding.banner.setAdapter(NormalBannerAdapter(city.pic_url_list))
            val data = BlockUtils.getBlocksList(city?.introduce?:"")
            mPlaceBlockAdapter?.setList(data)
        }
    }

    private fun initPlaceContent(savedInstanceState: Bundle?) {
        binding.rvPlaceContent.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = manager
            mPlaceBlockAdapter = PlaceBlockAdapter(this@HomeSightseeingDetailActivity, savedInstanceState = savedInstanceState)
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

    override fun activate(listener: LocationSource.OnLocationChangedListener?) {
        mListener = listener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(this)
            mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient?.setLocationListener(this@HomeSightseeingDetailActivity)
            //设置为高精度定位模式
            mLocationOption?.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
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