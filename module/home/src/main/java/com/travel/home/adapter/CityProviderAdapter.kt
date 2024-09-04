package com.travel.home.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.base.utils.LogUtils
import com.travel.home.R
import com.travel.home.databinding.ProviderBannerBinding
import com.travel.home.databinding.ProviderCityBinding

class CityProviderAdapter : BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.SECOND_TYPE
    override val layoutId: Int
        get() = R.layout.provider_city

    private var mCityListAdapter: CityListAdapter? = null
    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {

        val binding = DataBindingUtil.bind<ProviderCityBinding>(helper.itemView)
        binding?.apply {

            initRv(binding)
            executePendingBindings()
        }
    }

    private fun initRv(binding:ProviderCityBinding) {
        binding.rvCity.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mCityListAdapter = CityListAdapter()
            adapter = mCityListAdapter
            mCityListAdapter?.setList(listOf("", "", ""))
        }
    }
}