package com.travel.home.adapter

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BannerProviderAdapter: BaseItemProvider<BaseMultiQuickItem?>() {
    override val itemViewType: Int
        get() = BaseMultiQuickItem.FIRST_TYPE
    override val layoutId: Int
        get() = TODO("Not yet implemented")

    override fun convert(helper: BaseViewHolder, item: BaseMultiQuickItem?) {
    }
}