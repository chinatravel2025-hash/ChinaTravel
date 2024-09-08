package com.travel.home.adapter


import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding


class CityListAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeCityListBinding>>(
        R.layout.item_home_city_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeCityListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            root.setOnClickListener {
                ARouter.getInstance().build(ARouterPathList.HOME_CITY)
                    .navigation()
            }

            executePendingBindings()
        }
    }





}

