package com.travel.home.ui.city.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.ThingClickListener
import com.travel.home.databinding.ItemCityLocalShopListBinding


class CityLocalShopAdapter(
    val listener: ThingClickListener
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemCityLocalShopListBinding>>(
        R.layout.item_city_local_shop_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCityLocalShopListBinding>,
        item: PlaceItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            ivLike.setOnClickListener {
                listener.cancelThingLike(holder.layoutPosition,item)
            }
            ivUnlike.setOnClickListener {
                listener.addThingLike(holder.layoutPosition,item)
            }

            root.setOnClickListener {
               // val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_shop")
                ARouter.getInstance().build(ARouterPathList.HOME_SHOP_DETAIL)
                   // .withOptionsCompat(option)
                    .withLong("placeId",item.id?:0L)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemCityLocalShopListBinding>, item: PlaceItem, payloads: List<Any>) {
        for (p in payloads) {
            val payload = p as Int
            if (payload == ITEM_SHOP_PAYLOAD) {
                holder.setVisible(R.id.iv_like, item.is_like==1)
                holder.setVisible(R.id.iv_unlike, item.is_like==0)

            }
        }
    }

    companion object{
        val ITEM_SHOP_PAYLOAD: Int = 3

    }


}

