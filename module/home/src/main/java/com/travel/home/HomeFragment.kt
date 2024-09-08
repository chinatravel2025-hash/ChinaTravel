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
import com.aws.bean.util.GsonUtil
import com.example.base.utils.LogUtils
import com.travel.home.adapter.BaseMultiQuickItem
import com.travel.home.adapter.HomeProviderMultiAdapter
import com.travel.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var homeVM: HomeViewModel
    private var mHomeAdapter: HomeProviderMultiAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, null, false)
        binding.lifecycleOwner = this
        homeVM = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm=homeVM
        initRv()
        return binding.root
    }


    private fun initRv(){
        binding.rvHome.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = manager
            mHomeAdapter=HomeProviderMultiAdapter()
            adapter=mHomeAdapter
            val tempdata = mutableListOf<BaseMultiQuickItem>()
            tempdata.add(BaseMultiQuickItem(1,"11111"))
            tempdata.add(BaseMultiQuickItem(2,"22222"))
            tempdata.add(BaseMultiQuickItem(3,"33333"))
            tempdata.add(BaseMultiQuickItem(4,"44444"))
            tempdata.add(BaseMultiQuickItem(5,"55555"))
            mHomeAdapter?.setList(tempdata)

            LogUtils.d("lklklkl","data=${GsonUtil.toJson(tempdata)}")
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}