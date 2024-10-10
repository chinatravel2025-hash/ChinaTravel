package com.travel.home.ui.city.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemCityLocalShopListBinding


class CityLocalShopAdapter(
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemCityLocalShopListBinding>>(
        R.layout.item_city_local_shop_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCityLocalShopListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            root.setOnClickListener {
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_shop")
                ARouter.getInstance().build(ARouterPathList.HOME_SHOP_DETAIL)
                    .withOptionsCompat(option)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }





}

