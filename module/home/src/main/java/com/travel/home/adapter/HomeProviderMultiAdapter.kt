package com.travel.home.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter

class HomeProviderMultiAdapter: BaseProviderMultiAdapter<String?>() {
    override fun getItemType(data: List<String?>, position: Int): Int {
       return 0
    }
    init {

    }
}