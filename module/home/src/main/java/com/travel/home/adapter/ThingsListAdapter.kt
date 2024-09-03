package com.travel.home.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding
import com.travel.home.databinding.ItemHomeDayTripListBinding
import com.travel.home.databinding.ItemHomeThingsListBinding


class ThingsListAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeThingsListBinding>>(
        R.layout.item_home_things_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeThingsListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {

            executePendingBindings()
        }
    }





}

