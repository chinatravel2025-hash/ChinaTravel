package com.travel.home.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.travel.home.R
import com.travel.home.databinding.ProviderCityBinding
import com.travel.home.databinding.ProviderThingsBinding

class ThingsProviderAdapter: BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.FOURTH_TYPE
    override val layoutId: Int
        get() = R.layout.provider_things

    private var mThingsListAdapter: ThingsListAdapter? = null
    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {
        val binding = DataBindingUtil.bind<ProviderThingsBinding>(helper.itemView)
        binding?.apply {
            initRv(binding)
            executePendingBindings()
        }
    }
    private fun initRv(binding:ProviderThingsBinding) {
        binding.rvThings.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            mThingsListAdapter = ThingsListAdapter()
            adapter = mThingsListAdapter
            mThingsListAdapter?.setList(listOf("", "", ""))
        }
    }
}