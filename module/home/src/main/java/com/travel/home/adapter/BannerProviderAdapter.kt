package com.travel.home.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.travel.home.R
import com.travel.home.databinding.ProviderBannerBinding

class BannerProviderAdapter: BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.FIRST_TYPE
    override val layoutId: Int
        get() = R.layout.provider_banner

    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {
        val binding = DataBindingUtil.bind<ProviderBannerBinding>(helper.itemView)
        binding?.apply {
            initBanner(binding)
            executePendingBindings()
        }
    }

    private fun initBanner(binding: ProviderBannerBinding){
        binding.banner.setAdapter(HomeBannerAdapter(listOf()))
        binding.banner.setIndicator(binding.circleIndicator,false)

    }
}
