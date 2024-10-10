package com.travel.home.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.aws.bean.entities.home.PlaceItem
import com.example.base.base.BaseStatusBarActivity
import com.example.base.ext.dp2px

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.SearchRecommendListAdapter
import com.travel.home.adapter.ThingsListAdapter
import com.travel.home.databinding.HomeActivityCityBinding
import com.travel.home.databinding.HomeActivitySearchBinding
import com.travel.home.vm.HomeCityViewModel
import com.travel.home.vm.HomeSearchViewModel
import com.zyyoona7.itemdecoration.RecyclerViewDivider


@Route(path = ARouterPathList.HOME_SEARCH)
class HomeSearchActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivitySearchBinding
    private lateinit var mVM: HomeSearchViewModel
    private var mSearchRecommendListAdapter: SearchRecommendListAdapter? = null

    override val ivBack: Int
        get() = 0
    override fun getLayoutId(): Int {
        return R.layout.home_activity_search
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[HomeSearchViewModel::class.java]
        binding = mBaseBinding as HomeActivitySearchBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initRecommendRv()
    }




    @SuppressLint("SuspiciousIndentation")
    private fun initRecommendRv(){
        val itemDecoration=  RecyclerViewDivider.linear()
            .dividerSize(22.dp2px())
            .hideLastDivider()
            .build()
        binding.apply {
            mSearchRecommendListAdapter = SearchRecommendListAdapter()
            val manager = LinearLayoutManager(this@HomeSearchActivity)
            manager.orientation = LinearLayoutManager.VERTICAL
            rvRecommend.layoutManager = manager
            rvRecommend.addItemDecoration(itemDecoration)
            rvRecommend.adapter=mSearchRecommendListAdapter
            mSearchRecommendListAdapter?.setList(listOf(PlaceItem(),PlaceItem(),PlaceItem()))
        }

    }




}