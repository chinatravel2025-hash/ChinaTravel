package com.travel.home.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
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
            root.setOnClickListener {
                ARouter.getInstance().build(ARouterPathList.HOME_SIGHTSEEING_DETAIL)
                    .withLong("placeId",item.id?:0L)
                    .withInt("placeType",item.type?:3)
                    .navigation(SmartActivityUtils.getTopActivity())


            }
            executePendingBindings()
        }
    }





}

