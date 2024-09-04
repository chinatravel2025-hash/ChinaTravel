package com.travel.home.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter

class HomeProviderMultiAdapter: BaseProviderMultiAdapter<BaseMultiQuickItem?>() {
    override fun getItemType(data: List<BaseMultiQuickItem?>, position: Int): Int {
       return data[position]?.itemType?:BaseMultiQuickItem.FIRST_TYPE
    }
    init {
        addItemProvider(BannerProviderAdapter())
        addItemProvider(CityProviderAdapter())
        addItemProvider(DayTripProviderAdapter())
        addItemProvider(ThingsProviderAdapter())
        addItemProvider(TailorProviderAdapter())
    }
}