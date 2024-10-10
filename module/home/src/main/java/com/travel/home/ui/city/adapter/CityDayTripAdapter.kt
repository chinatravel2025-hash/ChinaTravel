package com.travel.home.ui.city.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemCityDayTripListBinding


class CityDayTripAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemCityDayTripListBinding>>(
        R.layout.item_city_day_trip_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCityDayTripListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            root.setOnClickListener {
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_trip")
                ARouter.getInstance().build(ARouterPathList.HOME_TRIP_DETAIL)
                    .withOptionsCompat(option)
                    .withLong("cityId",0)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }





}

