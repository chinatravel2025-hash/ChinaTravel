package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.aws.bean.entities.home.CityItem
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
import com.travel.home.databinding.HomeActivityRestaurantDetailBinding
import com.travel.home.vm.HomeRestaurantDetailViewModel


@Route(path = ARouterPathList.HOME_RESTAURANT_DETAIL)
class HomeRestaurantDetailActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityRestaurantDetailBinding
    private lateinit var mVM: HomeRestaurantDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_restaurant_detail
    }

    @JvmField
    @Autowired
    var placeId: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeRestaurantDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityRestaurantDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initAboutContent()
        binding.banner.setAdapter(NormalBannerAdapter(listOf("", "")))
        mVM.getPlaceList(PlaceType.RESTAURANT,placeId?:0)
        binding.space.onCreate(savedInstanceState)
        initMap()
        initObserve()
        binding.btnNext.setOnClickListener {
            ARouter.getInstance().build(ARouterPathList.HOME_CHAT)
                .navigation(SmartActivityUtils.getTopActivity())
        }

    }
    private fun initObserve(){
        mVM.mDataPlace.observe(this){ city->
            binding.banner.setAdapter(NormalBannerAdapter(city.pic_url_list))
        }
    }
    private fun initMap() {
        val latLng = LatLng(31.238068, 121.501654)
        //标记 https://blog.csdn.net/w794840800/article/details/80017220
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.apply {
            myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
            showMyLocation(true)
        }
        binding.space.map.apply {
            setMapLanguage(AMap.ENGLISH)
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isScaleControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.setAllGesturesEnabled(false)
            isMyLocationEnabled = true
            setMyLocationStyle(myLocationStyle)
            //城市 15
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            invalidate();// 刷新地图
        }
    }



    private fun initAboutContent(){

        val sss= "Shanghai is the economic, financial, commercial, and cultural " +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            " boasting the world’s busiest container port. The city i"
        val readMoreOption = ReadMoreOption.Builder(this)
            .textLength(3,ReadMoreOption.TYPE_LINE)
            .moreLabel("Read more")
            .lessLabel("  Read less")
            .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .expandAnimation(true)
            .build()
        readMoreOption.addReadMoreTo(binding.tvAboutContent,sss)

    }


    override fun onResume() {
        super.onResume()
        binding.space.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.space.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.space.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.space.onDestroy()
    }






}