package com.travel.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem
import com.china.travel.widget.widget.SmartRefreshFoot
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.utils.LogUtils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

import com.travel.home.adapter.CityListAdapter
import com.travel.home.adapter.CityListAdapterClickListener
import com.travel.home.adapter.DayTripListAdapter
import com.travel.home.adapter.HomeBannerAdapter
import com.travel.home.adapter.ThingClickListener
import com.travel.home.adapter.ThingsListAdapter
import com.travel.home.adapter.TravelProductClickListener
import com.travel.home.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), CityListAdapterClickListener, TravelProductClickListener, ThingClickListener {

    private lateinit var binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var homeVM: HomeViewModel
    private var mHomeBannerAdapter: HomeBannerAdapter? = null
    private var mCityListAdapter: CityListAdapter? = null
    private var mDayTripListAdapter: DayTripListAdapter? = null
    private var mThingsListAdapter: ThingsListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, null, false)
        binding.lifecycleOwner = this
        homeVM = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeVM
        initRv()
        initBanner()
        initObserve()
        homeVM.getBannerList()
        homeVM.getCityList()
        homeVM.getHomeTravelProducts()
        return binding.root
    }

    private fun initObserve() {
        homeVM.let { h ->
            h.mDataBanner.observe(viewLifecycleOwner) {

                mHomeBannerAdapter?.setDatas(it)
            }

            h.mDataCity.observe(viewLifecycleOwner) {
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
            h.mTravelProducts.observe(viewLifecycleOwner) {
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
            h.mDataPlace.observe(viewLifecycleOwner) {
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

    private fun initBanner() {
        mHomeBannerAdapter = HomeBannerAdapter(listOf())
        binding.banner.setAdapter(mHomeBannerAdapter)
        binding.banner.setIndicator(binding.circleIndicator, false)

    }

    private fun initRv() {

        binding.refreshLayoutCity.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mCityListAdapter?.data?.size ?: 0) < homeVM.cityMaxNum) {
                    homeVM.mCityPageNum += 1
                    homeVM.getCityList()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvCity.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityListAdapter = CityListAdapter(this@HomeFragment)
            adapter = mCityListAdapter
        }
        binding.refreshLayoutDay.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mDayTripListAdapter?.data?.size ?: 0) < homeVM.travelProductMaxNum) {
                    homeVM.mTravelProductPageNum += 1
                    homeVM.getHomeTravelProducts()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvTrip.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mDayTripListAdapter = DayTripListAdapter(this@HomeFragment)
            adapter = mDayTripListAdapter
        }
        binding.refreshLayoutThing.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener {
                if ((mThingsListAdapter?.data?.size ?: 0) < homeVM.placeMaxNum) {
                    homeVM.mPlacePageNum += 1
                    homeVM.getPlaceList()
                }
            }
            setRefreshFooter(RefreshFooterWrapper(SmartRefreshFoot(context)), -1, -2);
        }
        binding.rvThings.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mThingsListAdapter = ThingsListAdapter(this@HomeFragment)
            adapter = mThingsListAdapter
        }

    }


    override fun addCityLike(position: Int, item: CityItem) {
        homeVM.addFavorite(ObjectType.CITY, item.id ?: 0) {
            item.is_like = 1
            mCityListAdapter?.notifyItemChanged(position, CityListAdapter.ITEM_1_PAYLOAD)
        }
    }

    override fun cancelCityLike(position: Int, item: CityItem) {
        homeVM.cancelFavorite(ObjectType.CITY, item.id ?: 0) {
            item.is_like = 0
            mCityListAdapter?.notifyItemChanged(position, CityListAdapter.ITEM_1_PAYLOAD)
        }
    }

    override fun addProductLike(position: Int, item: TravelProductItem) {
        homeVM.addFavorite(ObjectType.TRAVEL_PRODUCTS, item.id ?: 0) {
            item.is_like = 1
            mDayTripListAdapter?.notifyItemChanged(position, DayTripListAdapter.ITEM_0_PAYLOAD)
        }
    }

    override fun cancelProductLike(position: Int, item: TravelProductItem) {
        homeVM.cancelFavorite(ObjectType.TRAVEL_PRODUCTS, item.id ?: 0) {
            item.is_like = 0
            mDayTripListAdapter?.notifyItemChanged(position, DayTripListAdapter.ITEM_0_PAYLOAD)
        }
    }

    override fun addThingLike(position: Int, item: PlaceItem) {
        val type = when (item.type) {
            1 -> {
                ObjectType.SIGHT
            }

            2 -> {
                ObjectType.SHOP
            }

            else -> {
                ObjectType.RESTAURANT
            }
        }
        homeVM.addFavorite(type, item.id ?: 0) {
            item.is_like = 1
            mThingsListAdapter?.notifyItemChanged(position, ThingsListAdapter.ITEM_2_PAYLOAD)
        }
    }

    override fun cancelThingLike(position: Int, item: PlaceItem) {
        val type = when (item.type) {
            1 -> {
                ObjectType.SIGHT
            }

            2 -> {
                ObjectType.SHOP
            }

            else -> {
                ObjectType.RESTAURANT
            }
        }
        homeVM.cancelFavorite(type, item.id ?: 0) {
            item.is_like = 0
            mThingsListAdapter?.notifyItemChanged(position, ThingsListAdapter.ITEM_2_PAYLOAD)
        }
    }


}