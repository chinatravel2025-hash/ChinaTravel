package com.travel.home.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeRecommendListBinding
import com.travel.home.databinding.ItemHomeRecommendTripListBinding


class RecommendTripListAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeRecommendTripListBinding>>(
        R.layout.item_home_recommend_trip_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeRecommendTripListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {

            executePendingBindings()
        }
    }





}

