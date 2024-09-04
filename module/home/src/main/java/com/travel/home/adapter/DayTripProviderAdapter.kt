package com.travel.home.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.travel.home.R
import com.travel.home.databinding.ProviderCityBinding
import com.travel.home.databinding.ProviderDayTripBinding

class DayTripProviderAdapter: BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.THIRD_TYPE
    override val layoutId: Int
        get() = R.layout.provider_day_trip

    private var mDayTripListAdapter: DayTripListAdapter? = null
    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {
        val binding = DataBindingUtil.bind<ProviderDayTripBinding>(helper.itemView)
        binding?.apply {
            initRv(binding)
            executePendingBindings()
        }
    }
    private fun initRv(binding:ProviderDayTripBinding) {
        binding.rvTrip.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mDayTripListAdapter = DayTripListAdapter()
            adapter = mDayTripListAdapter
            mDayTripListAdapter?.setList(listOf("", "", ""))
        }
    }
}