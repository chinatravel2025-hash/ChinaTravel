package com.travel.home.ui.city.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemCityRestaurantListBinding


class CityRestaurantAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemCityRestaurantListBinding>>(
        R.layout.item_city_restaurant_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCityRestaurantListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            root.setOnClickListener {
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_restaurant")
                ARouter.getInstance().build(ARouterPathList.HOME_RESTAURANT_DETAIL)
                    .withOptionsCompat(option)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }





}

