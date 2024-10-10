package com.travel.home.ui.city.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemCitySightseeingListBinding


class CitySightseeingAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemCitySightseeingListBinding>>(
        R.layout.item_city_sightseeing_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCitySightseeingListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            root.setOnClickListener {
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_sightseeing")
                ARouter.getInstance().build(ARouterPathList.HOME_SIGHTSEEING_DETAIL)
                    .withOptionsCompat(option)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }





}

