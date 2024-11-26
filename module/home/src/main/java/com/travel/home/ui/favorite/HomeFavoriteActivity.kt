package com.travel.home.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem
import com.china.travel.widget.widget.SmartRefreshFoot
import com.example.base.base.BaseStatusBarActivity
import com.example.base.ext.dp2px

import com.example.router.ARouterPathList
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper
import com.travel.home.R
import com.travel.home.adapter.CityListAdapter
import com.travel.home.adapter.CityListAdapterClickListener
import com.travel.home.adapter.DayTripListAdapter
import com.travel.home.adapter.SearchRecommendListAdapter
import com.travel.home.adapter.ThingClickListener
import com.travel.home.adapter.ThingsListAdapter
import com.travel.home.adapter.TravelProductClickListener
import com.travel.home.databinding.HomeActivityFavoriteBinding
import com.travel.home.databinding.HomeActivitySearchBinding
import com.travel.home.ui.favorite.adapter.FavoriteCityAdapter
import com.travel.home.ui.favorite.adapter.FavoriteThingsAdapter
import com.travel.home.ui.favorite.adapter.FavoriteTripAdapter
import com.travel.home.vm.HomeFavoriteViewModel
import com.zyyoona7.itemdecoration.RecyclerViewDivider


@Route(path = ARouterPathList.HOME_FAVORITE)
class HomeFavoriteActivity : BaseStatusBarActivity(), CityListAdapterClickListener, TravelProductClickListener, ThingClickListener {

    private lateinit var binding: HomeActivityFavoriteBinding
    private lateinit var mVM: HomeFavoriteViewModel

    private var mCityListAdapter: FavoriteCityAdapter? = null
    private var mDayTripListAdapter: FavoriteTripAdapter? = null
    private var mThingsListAdapter: FavoriteThingsAdapter? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_favorite
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[HomeFavoriteViewModel::class.java]
        binding = mBaseBinding as HomeActivityFavoriteBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initRv()
        initObserve()
        mVM.getFavoriteCityList()
        mVM.getFavoritePlace()
        mVM.getFavoriteTravelProducts()
    }
    private fun initObserve() {
        mVM.let { h ->

            h.mDataCity.observe(this) {
                if (h.mCityPageNum == 1) {
                    mCityListAdapter?.setList(it)
                } else {
                    mCityListAdapter?.addData(it)
                }
                if ((mCityListAdapter?.data?.size ?: 0) < h.cityMaxNum) {
                    binding.refreshLayoutCity.finishLoadMore()
                } else {
                    binding.refreshLayoutCity.finishLoadMoreWithNoMoreData()
                }
            }
            h.mTravelProducts.observe(this) {
                if (h.mTravelProductPageNum == 1) {
                    mDayTripListAdapter?.setList(it)
                } else {
                    mDayTripListAdapter?.addData(it)
                }
                if ((mDayTripListAdapter?.data?.size ?: 0) < h.travelProductMaxNum) {
                    binding.refreshLayoutDay.finishLoadMore()
                } else {
                    binding.refreshLayoutDay.finishLoadMoreWithNoMoreData()
                }
            }
            h.mDataPlace.observe(this) {
                if (h.mPlacePageNum == 1) {
                    mThingsListAdapter?.setList(it)
                } else {
                    mThingsListAdapter?.addData(it)
                }
                if ((mThingsListAdapter?.data?.size ?: 0) < h.placeMaxNum) {
                    binding.refreshLayoutThing.finishLoadMore()
                } else {
                    binding.refreshLayoutThing.finishLoadMoreWithNoMoreData()
                }
            }
        }

    }
    private fun initRv() {

        binding.refreshLayoutCity.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mCityListAdapter?.data?.size?:0) < mVM.cityMaxNum) {
                    mVM.mCityPageNum += 1
                    mVM.getFavoriteCityList()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvCity.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityListAdapter = FavoriteCityAdapter(this@HomeFavoriteActivity)
            adapter = mCityListAdapter
        }
        binding.refreshLayoutDay.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mDayTripListAdapter?.data?.size?:0)  < mVM.travelProductMaxNum) {
                    mVM.mTravelProductPageNum += 1
                    mVM.getFavoriteTravelProducts()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvTrip.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mDayTripListAdapter = FavoriteTripAdapter(this@HomeFavoriteActivity)
            adapter = mDayTripListAdapter
        }
        binding.refreshLayoutThing.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mThingsListAdapter?.data?.size?:0)< mVM.placeMaxNum) {
                    mVM.mPlacePageNum += 1
                    mVM.getFavoritePlace()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvThings.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mThingsListAdapter = FavoriteThingsAdapter(this@HomeFavoriteActivity)
            adapter = mThingsListAdapter
        }

    }

    override fun addCityLike(position: Int, item: CityItem) {

    }

    override fun cancelCityLike(position: Int, item: CityItem) {

    }

    override fun addProductLike(position: Int, item: TravelProductItem) {

    }

    override fun cancelProductLike(position: Int, item: TravelProductItem) {

    }

    override fun addThingLike(position: Int, item: PlaceItem) {

    }

    override fun cancelThingLike(position: Int, item: PlaceItem) {

    }


}