package com.travel.home.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aws.bean.event.FilterCityEvent
import com.example.base.ext.dp2px
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.travel.home.R
import com.travel.home.adapter.RecommendTripListAdapter
import com.travel.home.databinding.FragmentHomeRecommendTripBinding
import com.travel.home.vm.HomeRecommendTripViewModel
import com.zyyoona7.itemdecoration.RecyclerViewDivider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeRecommendTripFragment : Fragment(), OnLoadMoreListener {


    private lateinit var binding: FragmentHomeRecommendTripBinding
    private var mAdapter: RecommendTripListAdapter? = null

    private lateinit var mVM: HomeRecommendTripViewModel
    var cityId =0L
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home_recommend_trip, null, false)
        binding.lifecycleOwner = this
        mVM = ViewModelProvider(this)[HomeRecommendTripViewModel::class.java]
        arguments?.let {
            cityId =it.getLong(FILTER)
        }
        EventBus.getDefault().register(this)
        initRv()
        initObserve()
        mVM.getHomeTravelProducts()
        return binding.root
    }
    private fun initObserve(){
        mVM.mTravelProducts.observe(viewLifecycleOwner) {
            if (mVM.mPlacePageNum == 1) {
                mAdapter?.setList(it)
            } else {
                mAdapter?.addData(it)
            }
            if (mVM.currentSize < mVM.maxSize) {
                binding.smartRefreshLayout.finishLoadMore()
            } else {
                binding.smartRefreshLayout.finishLoadMoreWithNoMoreData()
            }
        }
    }

    private fun initRv(){
        val itemDecoration=  RecyclerViewDivider.linear()
            .dividerSize(22.dp2px())
            .hideLastDivider()
            .build()
        binding.apply {
            smartRefreshLayout.setEnableRefresh(false)
            smartRefreshLayout.setEnableLoadMore(true)
            smartRefreshLayout.setOnLoadMoreListener(this@HomeRecommendTripFragment)
            mAdapter = RecommendTripListAdapter()
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            listRv.layoutManager = manager
            listRv.addItemDecoration(itemDecoration)
            listRv.adapter=mAdapter
        }

    }

    companion object {
        private const val FILTER = "filter_city"
        fun newInstance(cityId: Long): HomeRecommendTripFragment {
            val fragment = HomeRecommendTripFragment()
            fragment.arguments = Bundle().apply {
                putLong(FILTER, cityId)
            }
            return fragment
        }
    }

    override fun onLoadMore(p0: RefreshLayout) {
        mVM.getHomeTravelProducts()
    }



    //切换了城市通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFilterCityEvent(event: FilterCityEvent) {

    }
    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}