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
import com.travel.home.adapter.RecommendListAdapter
import com.travel.home.databinding.FragmentHomeRecommendBinding
import com.travel.home.vm.HomeRecommendPlaceViewModel
import com.travel.home.vm.HomeRecommendViewModel
import com.zyyoona7.itemdecoration.RecyclerViewDivider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeRecommendPlaceFragment : Fragment(), OnLoadMoreListener {


    private lateinit var binding: FragmentHomeRecommendBinding
    private var mAdapter: RecommendListAdapter? = null
    private lateinit var mVM: HomeRecommendPlaceViewModel
    var cityId =0L
    var type =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home_recommend, null, false)
        binding.lifecycleOwner = this
        mVM = ViewModelProvider(this)[HomeRecommendPlaceViewModel::class.java]

        EventBus.getDefault().register(this)
        arguments?.let {
            cityId = it.getLong(FILTER_CITY)
             type = it.getString(FILTER_TYPE) ?: ""
        }
        initRv()
        initObserve()
        mVM.getPlaceList(type)
        return binding.root
    }
    private fun initObserve(){
        mVM.mDataPlace.observe(viewLifecycleOwner) {
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
    private fun initRv() {
        val itemDecoration = RecyclerViewDivider.linear()
            .dividerSize(22.dp2px())
            .hideLastDivider()
            .build()
        binding.apply {
            smartRefreshLayout.setEnableRefresh(true)
            smartRefreshLayout.setEnableLoadMore(true)
            smartRefreshLayout.setOnLoadMoreListener(this@HomeRecommendPlaceFragment)
            mAdapter = RecommendListAdapter()
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            listRv.layoutManager = manager
            listRv.addItemDecoration(itemDecoration)
            listRv.adapter = mAdapter
        }

    }

    companion object {
        private const val FILTER_CITY = "filter_city"
        private const val FILTER_TYPE = "filter_type"
        fun newInstance(cityId: Long,type: String?): HomeRecommendPlaceFragment {
            val fragment = HomeRecommendPlaceFragment()
            fragment.arguments = Bundle().apply {
                putLong(FILTER_CITY, cityId)
                putString(FILTER_TYPE, type)
            }
            return fragment
        }
    }

    override fun onLoadMore(p0: RefreshLayout) {
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