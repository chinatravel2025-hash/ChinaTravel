package com.travel.home.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aws.bean.event.FilterCityEvent
import com.example.base.ext.dp2px
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.travel.home.R
import com.travel.home.adapter.RecommendListAdapter
import com.travel.home.adapter.RecommendTripListAdapter
import com.travel.home.databinding.FragmentHomeRecommendBinding
import com.travel.home.databinding.FragmentHomeRecommendTripBinding
import com.zyyoona7.itemdecoration.RecyclerViewDivider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeRecommendTripFragment : Fragment(), OnLoadMoreListener, OnRefreshListener {


    private lateinit var binding: FragmentHomeRecommendTripBinding
    private var mAdapter: RecommendTripListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home_recommend_trip, null, false)
        binding.lifecycleOwner = this
        arguments?.let {
           val city =it.getString(FILTER) ?: ""
        }
        EventBus.getDefault().register(this)
        initRv()
        return binding.root
    }

    private fun initRv(){
        val itemDecoration=  RecyclerViewDivider.linear()
            .dividerSize(22.dp2px())
            .hideLastDivider()
            .build()
        binding.apply {
            smartRefreshLayout.setEnableRefresh(true)
            smartRefreshLayout.setEnableLoadMore(true)
            smartRefreshLayout.setOnLoadMoreListener(this@HomeRecommendTripFragment)
            smartRefreshLayout.setOnRefreshListener(this@HomeRecommendTripFragment)
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
        fun newInstance(cityId: String?): HomeRecommendTripFragment {
            val fragment = HomeRecommendTripFragment()
            fragment.arguments = Bundle().apply {
                putString(FILTER, cityId)
            }
            return fragment
        }
    }

    override fun onLoadMore(p0: RefreshLayout) {
    }

    override fun onRefresh(p0: RefreshLayout) {
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