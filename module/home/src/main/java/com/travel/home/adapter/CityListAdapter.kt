package com.travel.home.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding


class CityListAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeCityListBinding>>(
        R.layout.item_home_city_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeCityListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {

            executePendingBindings()
        }
    }





}

