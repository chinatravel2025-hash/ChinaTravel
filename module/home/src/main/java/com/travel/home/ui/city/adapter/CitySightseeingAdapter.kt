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
import com.travel.home.databinding.ItemCitySightseeingListBinding


class CitySightseeingAdapter(
    val listener: ThingClickListener
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemCitySightseeingListBinding>>(
        R.layout.item_city_sightseeing_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCitySightseeingListBinding>,
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
                val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_sightseeing")
                ARouter.getInstance().build(ARouterPathList.HOME_SIGHTSEEING_DETAIL)
                    .withOptionsCompat(option)
                    .withLong("placeId",item.id?:0L)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemCitySightseeingListBinding>, item: PlaceItem, payloads: List<Any>) {
        for (p in payloads) {
            val payload = p as Int
            if (payload == ITEM_SIGHT_PAYLOAD) {
                holder.setVisible(R.id.iv_like, item.is_like==1)
                holder.setVisible(R.id.iv_unlike, item.is_like==0)

            }
        }
    }

    companion object{
        val ITEM_SIGHT_PAYLOAD: Int = 12

    }



}

