package com.travel.home.ui.favorite.adapter


import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.ThingClickListener
import com.travel.home.databinding.ItemHomeFavoriteThingsBinding
import com.travel.home.databinding.ItemHomeThingsListBinding


class FavoriteThingsAdapter(
    val listener: ThingClickListener
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemHomeFavoriteThingsBinding>>(
        R.layout.item_home_favorite_things
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeFavoriteThingsBinding>,
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

                ARouter.getInstance().build(ARouterPathList.HOME_SIGHTSEEING_DETAIL)
                    .withLong("placeId",item.id?:0L)
                    .withInt("placeType",item.type?:3)
                    .navigation(SmartActivityUtils.getTopActivity())
            }
            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemHomeFavoriteThingsBinding>, item: PlaceItem, payloads: List<Any>) {
        for (p in payloads) {
            val payload = p as Int
            if (payload == ITEM_2_PAYLOAD) {
                holder.setVisible(R.id.iv_like, item.is_like==1)
                holder.setVisible(R.id.iv_unlike, item.is_like==0)

            }
        }
    }

    companion object{
        val ITEM_2_PAYLOAD: Int = 102

    }


}

