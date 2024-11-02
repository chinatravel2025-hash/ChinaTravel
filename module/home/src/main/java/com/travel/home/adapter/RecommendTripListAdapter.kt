package com.travel.home.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemHomeRecommendListBinding
import com.travel.home.databinding.ItemHomeRecommendTripListBinding


class RecommendTripListAdapter(
) :
    BaseQuickAdapter<TravelProductItem, BaseDataBindingHolder<ItemHomeRecommendTripListBinding>>(
        R.layout.item_home_recommend_trip_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeRecommendTripListBinding>,
        item: TravelProductItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            root.setOnClickListener {
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_trip")
                ARouter.getInstance().build(ARouterPathList.HOME_TRIP_DETAIL)
                    .withOptionsCompat(option)
                    .withLong("tripId",item.id?:0L)
                    .navigation(SmartActivityUtils.getTopActivity())
            }
            executePendingBindings()
        }
    }





}

