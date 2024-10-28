package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem
import com.aws.bean.util.GsonUtil
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.BlockUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.ResourceUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.DayTripListAdapter
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.adapter.PlaceBlockAdapter
import com.travel.home.adapter.ThingClickListener
import com.travel.home.adapter.ThingsListAdapter
import com.travel.home.adapter.TravelProductClickListener
import com.travel.home.databinding.HomeActivityCityDetailBinding
import com.travel.home.ui.city.adapter.CityColumnListAdapter
import com.travel.home.ui.city.adapter.CityDayTripAdapter
import com.travel.home.ui.city.adapter.CityDayTripAdapter.Companion.ITEM_TRIP_PAYLOAD
import com.travel.home.ui.city.adapter.CityLocalShopAdapter
import com.travel.home.ui.city.adapter.CityLocalShopAdapter.Companion.ITEM_SHOP_PAYLOAD
import com.travel.home.ui.city.adapter.CityRestaurantAdapter
import com.travel.home.ui.city.adapter.CityRestaurantAdapter.Companion.ITEM_RESTAURANT_PAYLOAD
import com.travel.home.ui.city.adapter.CitySightseeingAdapter
import com.travel.home.ui.city.adapter.CitySightseeingAdapter.Companion.ITEM_SIGHT_PAYLOAD
import com.travel.home.vm.HomeCityDetailViewModel


@Route(path = ARouterPathList.HOME_CITY_DETAIL)
class  HomeCityDetailActivity : BaseStatusBarActivity(), TravelProductClickListener, ThingClickListener {

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

    private var mPlaceBlockAdapter: PlaceBlockAdapter? = null
    @JvmField
    @Autowired
    var cityId: Long? = 0L
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
        mVM.getPlaceList(ObjectType.SHOP, cityId ?: 0)
        mVM.getPlaceList(ObjectType.RESTAURANT, cityId ?: 0)
        initObserve()

    }
    private fun initObserve(){
        mVM.mCityDetail.observe(this){ city->
            val data =BlockUtils.getBlocksList(city?.about?:"")
            //LogUtils.d("msmakdsakj","dat= ${GsonUtil.toJson(data)}")
           mPlaceBlockAdapter?.setList(data)
        }
        mVM.mTravelProducts.observe(this){
            mCityDayTripAdapter?.setList(it)
        }
        mVM.mSightPlace.observe(this){
            mCitySightseeingAdapter?.setList(it)
        }
        mVM.mShopPlace.observe(this){
            mCityLocalShopAdapter?.setList(it)
        }
        mVM.mRestaurantPlace.observe(this){
            mCityRestaurantAdapter?.setList(it)
        }
    }
    private fun initScrollView() {
        var y = 0
        var y2 = 0
        var y3 = 0
        var h = 0
        var h2 = 0
        var h3 = 0
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
            mCityDayTripAdapter = CityDayTripAdapter(this@HomeCityDetailActivity)
            adapter = mCityDayTripAdapter
        }
        binding.rvSight.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCitySightseeingAdapter = CitySightseeingAdapter(this@HomeCityDetailActivity)
            adapter = mCitySightseeingAdapter
        }
        binding.rvShop.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityLocalShopAdapter = CityLocalShopAdapter(this@HomeCityDetailActivity)
            adapter = mCityLocalShopAdapter
        }
        binding.rvRestaurant.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityRestaurantAdapter = CityRestaurantAdapter(this@HomeCityDetailActivity)
            adapter = mCityRestaurantAdapter
        }


    }

    private fun initAboutContent() {
        binding.rvAboutContent.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = manager
            mPlaceBlockAdapter = PlaceBlockAdapter()
            adapter = mPlaceBlockAdapter
        }

    }

    override fun addProductLike(position: Int, item: TravelProductItem) {
        mVM.addFavorite(ObjectType.TRAVEL_PRODUCTS, item.id ?: 0) {
            item.is_like = 1
            mCityDayTripAdapter?.notifyItemChanged(position, ITEM_TRIP_PAYLOAD)
        }
    }

    override fun cancelProductLike(position: Int, item: TravelProductItem) {
        mVM.cancelFavorite(ObjectType.TRAVEL_PRODUCTS, item.id ?: 0) {
            item.is_like = 0
            mCityDayTripAdapter?.notifyItemChanged(position, ITEM_TRIP_PAYLOAD)
        }
    }

    override fun addThingLike(position: Int, item: PlaceItem) {
        var type: ObjectType = when (item.type) {
            1 -> { ObjectType.SIGHT }
            2 -> { ObjectType.SHOP }
            else -> { ObjectType.RESTAURANT }
        }
        mVM.addFavorite(type, item.id ?: 0) {
            item.is_like = 1
            when(item.type){
                1->{
                    mCitySightseeingAdapter?.notifyItemChanged(position, ITEM_SIGHT_PAYLOAD)
                }
                2->{
                    mCityLocalShopAdapter?.notifyItemChanged(position, ITEM_SHOP_PAYLOAD)
                }
                else->{
                    mCityRestaurantAdapter?.notifyItemChanged(position, ITEM_RESTAURANT_PAYLOAD)
                }
            }

        }
    }

    override fun cancelThingLike(position: Int, item: PlaceItem) {
        var type: ObjectType = when (item.type) {
            1 -> { ObjectType.SIGHT }
            2 -> { ObjectType.SHOP }
            else -> { ObjectType.RESTAURANT }
        }
        mVM.cancelFavorite(type, item.id ?: 0) {
            item.is_like = 0
            when(item.type){
                1->{
                    mCitySightseeingAdapter?.notifyItemChanged(position, ITEM_SIGHT_PAYLOAD)
                }
                2->{
                    mCityLocalShopAdapter?.notifyItemChanged(position, ITEM_SHOP_PAYLOAD)
                }
                else->{
                    mCityRestaurantAdapter?.notifyItemChanged(position, ITEM_RESTAURANT_PAYLOAD)
                }
            }
        }
    }


}