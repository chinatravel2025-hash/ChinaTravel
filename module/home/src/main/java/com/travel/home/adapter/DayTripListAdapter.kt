package com.travel.home.adapter


import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeDayTripListBinding


class DayTripListAdapter(
    val listener: TravelProductClickListener
) :
    BaseQuickAdapter<TravelProductItem, BaseDataBindingHolder<ItemHomeDayTripListBinding>>(
        R.layout.item_home_day_trip_list
    ) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeDayTripListBinding>,
        item: TravelProductItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            ivLike.setOnClickListener {
                listener.cancelProductLike(holder.layoutPosition,item)
            }
            ivUnlike.setOnClickListener {
                listener.addProductLike(holder.layoutPosition,item)
            }

            executePendingBindings()
        }
    }

    override fun convert(holder: BaseDataBindingHolder<ItemHomeDayTripListBinding>, item: TravelProductItem, payloads: List<Any>) {
        for (p in payloads) {
            val payload = p as Int
            if (payload == ITEM_0_PAYLOAD) {
                holder.setVisible(R.id.iv_like, item.is_like==1)
                holder.setVisible(R.id.iv_unlike, item.is_like==0)

            }
        }
    }



    companion object{
        val ITEM_0_PAYLOAD: Int = 100

    }


}

