package com.travel.home.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.HomeActivityCityBinding
import com.travel.home.vm.HomeCityViewModel


@Route(path = ARouterPathList.HOME_CITY)
class HomeCityActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityCityBinding
    private lateinit var mVM: HomeCityViewModel
    override val ivBack: Int
        get() = 0
    override fun getLayoutId(): Int {
        return R.layout.home_activity_city
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[HomeCityViewModel::class.java]
        binding = mBaseBinding as HomeActivityCityBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
    }









}