package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.util.GsonUtil
import com.aws.bean.util.ObjectTypeUtil
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.LogUtils
import com.example.base.utils.ResourceUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.databinding.HomeActivityCityDetailBinding
import com.travel.home.ui.city.adapter.CityColumnListAdapter
import com.travel.home.ui.city.adapter.CityDayTripAdapter
import com.travel.home.ui.city.adapter.CityLocalShopAdapter
import com.travel.home.ui.city.adapter.CityRestaurantAdapter
import com.travel.home.ui.city.adapter.CitySightseeingAdapter
import com.travel.home.vm.HomeCityDetailViewModel


@Route(path = ARouterPathList.HOME_CITY_DETAIL)
class HomeCityDetailActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityCityDetailBinding
    private lateinit var mVM: HomeCityDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.home_activity_city_detail
    }

    private var mCityColumnListAdapter: CityColumnListAdapter? = null
    private var mCityDayTripAdapter: CityDayTripAdapter? = null
    private var mCitySightseeingAdapter: CitySightseeingAdapter? = null
    private var mCityLocalShopAdapter: CityLocalShopAdapter? = null
    private var mCityRestaurantAdapter: CityRestaurantAdapter? = null


    @JvmField
    @Autowired
    var cityId: Long? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeCityDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityCityDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        initRv()
        initAboutContent()
        initScrollView()
        binding.banner.setAdapter(NormalBannerAdapter(listOf("", "")))
        mVM.getHomeCityDetail(cityId ?: 0)
        mVM.getHomeTravelProducts()
        mVM.getPlaceList(ObjectType.SIGHT, cityId ?: 0)
        mVM.getPlaceList(ObjectType.SHOP, cityId?: 0)
        mVM.getPlaceList(ObjectType.RESTAURANT, cityId ?: 0)
    }
    private fun initScrollView() {
        var y=0
        var y2=0
        var y3=0
        var h=0
        var h2=0
        var h3=0
        binding.tvDayTrip.apply {
            post {
                val locationDay = IntArray(2)
                getLocationOnScreen(locationDay)
                 y = locationDay[1]
                 h = y / 2
            }
        }
        binding.tvSight.apply {
            post {
                val locationSight = IntArray(2)
                getLocationOnScreen(locationSight)
                 y2 = locationSight[1]
                 h2 = (y2 - y) / 2
            }
        }
        binding.tvShop.apply {
            post {
                val locationShop = IntArray(2)
                getLocationOnScreen(locationShop)
                 y3 = locationShop[1]
                 h3 = (y3 - y2) / 2
            }
        }
        binding.apply {
            nestScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
//                LogUtils.d("lsjsks", "sy = $scrollY")
//                LogUtils.d("lsjsks", "y = $y")
//                LogUtils.d("lsjsks", "y2 = $y2")
//                LogUtils.d("lsjsks", "y3 = $y3")
//                LogUtils.d("lsjsks", "h = $h")
//                LogUtils.d("lsjsks", "h2 = $h2")
//                LogUtils.d("lsjsks", "h3 = $h3")
//                LogUtils.d("lsjsks", "1 =0 -- $h")
//                LogUtils.d("lsjsks", "2 =$h ---${y + h2}")
//                LogUtils.d("lsjsks", "2 =${y + h2} ---${y2 + h3}")
                when (scrollY) {
                    in 0 until h -> {
                        mCityColumnListAdapter?.quickSelect(0)
                    }
                    in h until (y + h2) -> {
                        mCityColumnListAdapter?.quickSelect(1)
                    }
                    in (y + h2) until (y2 + h3) -> {
                        mCityColumnListAdapter?.quickSelect(2)
                    }
                    else -> {
                        mCityColumnListAdapter?.quickSelect(3)
                    }
                }
            })
        }
    }


    private fun initRv() {
        binding.rvTop.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityColumnListAdapter = CityColumnListAdapter {
                when (it) {
                    0 -> {
                        binding.nestScrollView.fullScroll(NestedScrollView.FOCUS_UP)
                    }

                    1 -> {
                        binding.nestScrollView.scrollTo(0, binding.tvDayTrip.top)
                    }

                    2 -> {
                        binding.nestScrollView.scrollTo(0, binding.tvSight.top)
                    }

                    3 -> {
                        binding.nestScrollView.scrollTo(0, binding.tvShop.top)
                    }

                    else -> {
                        binding.nestScrollView.fullScroll(NestedScrollView.FOCUS_DOWN)
                    }
                }


            }
            adapter = mCityColumnListAdapter
            mCityColumnListAdapter?.setList(listOf("Overview", "Day Trips", "Sightseeing", "Local shop", "Restaurant"))
        }

        binding.rvDayTrip.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityDayTripAdapter = CityDayTripAdapter()
            adapter = mCityDayTripAdapter
            mCityDayTripAdapter?.setList(listOf("", "", "", "", ""))
        }
        binding.rvSight.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCitySightseeingAdapter = CitySightseeingAdapter()
            adapter = mCitySightseeingAdapter
            mCitySightseeingAdapter?.setList(listOf("", "", "", "", ""))
        }
        binding.rvShop.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityLocalShopAdapter = CityLocalShopAdapter()
            adapter = mCityLocalShopAdapter
            mCityLocalShopAdapter?.setList(listOf("", "", "", "", ""))
        }
        binding.rvRestaurant.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityRestaurantAdapter = CityRestaurantAdapter()
            adapter = mCityRestaurantAdapter
            mCityRestaurantAdapter?.setList(listOf("", "", "", "", ""))
        }


    }

    private fun initAboutContent() {
        val about = ""//city?.about
        val readMoreOption = ReadMoreOption.Builder(this)
            .textLength(3, ReadMoreOption.TYPE_LINE)
            .moreLabel("Read more")
            .lessLabel("  Read less")
            .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .expandAnimation(true)
            .build()
        readMoreOption.addReadMoreTo(binding.tvAboutContent, about)

    }


}