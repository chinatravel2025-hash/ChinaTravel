package com.travel.home.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.travel.home.R

class TailorProviderAdapter: BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.FIFTH_TYPE
    override val layoutId: Int
        get() = R.layout.provider_tailor

    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {
    }
}