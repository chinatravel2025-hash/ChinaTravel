package com.travel.home.adapter


import com.aws.bean.entities.home.PlaceItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeRecommendListBinding
import com.travel.home.databinding.ItemHomeSearchRecommendListBinding


class SearchRecommendListAdapter(
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemHomeSearchRecommendListBinding>>(
        R.layout.item_home_search_recommend_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeSearchRecommendListBinding>,
        item: PlaceItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            executePendingBindings()
        }
    }





}

