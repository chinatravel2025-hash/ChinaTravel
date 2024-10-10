package com.travel.home.adapter


import com.aws.bean.entities.home.PlaceItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeRecommendListBinding


class RecommendListAdapter(
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemHomeRecommendListBinding>>(
        R.layout.item_home_recommend_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeRecommendListBinding>,
        item: PlaceItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            executePendingBindings()
        }
    }





}

