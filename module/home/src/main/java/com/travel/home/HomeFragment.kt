package com.travel.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ours.ui.home.HomeViewModel
import com.travel.home.adapter.CityListAdapter
import com.travel.home.adapter.DayTripListAdapter
import com.travel.home.adapter.HomeBannerAdapter
import com.travel.home.adapter.ThingsListAdapter
import com.travel.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var homeVM: HomeViewModel
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
        initBanner()
        initRv()
        return binding.root
    }

    private fun initBanner(){
        binding.banner.setAdapter(HomeBannerAdapter(listOf("","","")))
        binding.banner.setIndicator(binding.circleIndicator,false)

    }
    private fun initRv(){
        binding.rvCity.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityListAdapter=CityListAdapter()
            adapter=mCityListAdapter
            mCityListAdapter?.setList(listOf("","",""))
        }
        binding.rvTrip.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mDayTripListAdapter=DayTripListAdapter()
            adapter=mDayTripListAdapter
            mDayTripListAdapter?.setList(listOf("","",""))
        }
        binding.rvThings.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mThingsListAdapter=ThingsListAdapter()
            adapter=mThingsListAdapter
            mThingsListAdapter?.setList(listOf("","",""))
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
    }
}