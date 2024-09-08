package com.travel.home.ui.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.HomeActivityCityBinding
import com.travel.home.databinding.HomeActivitySearchBinding
import com.travel.home.vm.HomeCityViewModel
import com.travel.home.vm.HomeSearchViewModel


@Route(path = ARouterPathList.HOME_SEARCH)
class HomeSearchActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivitySearchBinding
    private lateinit var mVM: HomeSearchViewModel
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
    }









}