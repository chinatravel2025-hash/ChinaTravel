package com.travel.home.adapter


import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding
import com.travel.home.databinding.ItemHomeDayTripListBinding
import com.travel.home.databinding.ItemHomeThingsListBinding


class ThingsListAdapter(
    val listener: ThingClickListener
) :
    BaseQuickAdapter<PlaceItem, BaseDataBindingHolder<ItemHomeThingsListBinding>>(
        R.layout.item_home_things_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeThingsListBinding>,
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
            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemHomeThingsListBinding>, item: PlaceItem, payloads: List<Any>) {
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

