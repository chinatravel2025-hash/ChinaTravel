package com.travel.home.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding
import com.travel.home.databinding.ItemHomeDayTripListBinding


class DayTripListAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeDayTripListBinding>>(
        R.layout.item_home_day_trip_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeDayTripListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {

            executePendingBindings()
        }
    }





}

