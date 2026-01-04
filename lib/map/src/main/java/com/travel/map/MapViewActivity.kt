package com.travel.map

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.BlockUtils
import com.example.base.utils.doNothing
import com.example.router.ARouterPathList
import com.travel.adapter.DayListAdapter
import com.travel.adapter.TripPagerAdapter
import com.travel.map.databinding.ModuleMapActivityMapViewBinding
import com.youth.banner.listener.OnPageChangeListener

@Route(path = ARouterPathList.MAP_HOME_VIEW)
class MapViewActivity: BaseStatusBarActivity() {
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.module_map_activity_map_view
    }
    private lateinit var binding: ModuleMapActivityMapViewBinding
    private var mTripPagerAdapter :TripPagerAdapter?=null
    private var mDayListAdapter :DayListAdapter?=null

    @JvmField
    @Autowired
    var id: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        binding = mBaseBinding as ModuleMapActivityMapViewBinding
        setContentView(binding.root)
        binding.mapView.onCreate(savedInstanceState)
        initMap()
        //initPager()
    }

    private fun initPager(){
        binding.rvDay.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mDayListAdapter=DayListAdapter{ position->
                binding.viewPager.setCurrentItem(position,true)
            }
            adapter=mDayListAdapter
            mDayListAdapter?.setList(listOf("Day1","Day2","Day3","Day4"))
        }

        binding.viewPager.apply {
            offscreenPageLimit=4
            mTripPagerAdapter= TripPagerAdapter()
            adapter=mTripPagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                  doNothing
                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(p0: Int) {
                    doNothing
                }

            })
            pageMargin=15
        }
        mTripPagerAdapter?.setData(listOf("","","","","",""))
    }

    private fun initMap() {
        if(id == 0L || !BlockUtils.locMap.containsKey(id)){
            return
        }
        val builder = LatLngBounds.builder()
        val latLngs= mutableListOf<LatLng>()
        val info = BlockUtils.locMap[id?:0]
        var latLng: LatLng? = null
        info?.forEachIndexed { index, locationBean ->
            if(index == 0){
                latLng = LatLng(locationBean.lon?:0.0, locationBean.lat?:0.0)
            }
            builder.include(LatLng(locationBean.lon?:0.0, locationBean.lat?:0.0))
            latLngs.add(LatLng(locationBean.lon?:0.0, locationBean.lat?:0.0))
        }
        for (latLng in latLngs) {
            binding.mapView.map.addMarker(MarkerOptions()
                .position(latLng)
                .snippet("DefaultMarker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))) // 标记的图标
            // 可以添加监听器等操作
        }

        binding.mapView.map.apply {
            setMapLanguage(AMap.ENGLISH)
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isScaleControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            isMyLocationEnabled = true
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
         //   moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),30))
        }
    }
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}