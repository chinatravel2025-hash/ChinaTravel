package com.travel.home.ui.favorite.adapter


import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.TravelProductClickListener
import com.travel.home.databinding.ItemHomeDayTripListBinding
import com.travel.home.databinding.ItemHomeFavoriteTripBinding


class FavoriteTripAdapter(
    val listener: TravelProductClickListener
) :
    BaseQuickAdapter<TravelProductItem, BaseDataBindingHolder<ItemHomeFavoriteTripBinding>>(
        R.layout.item_home_favorite_trip
    ) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeFavoriteTripBinding>,
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
            root.setOnClickListener {
             //   val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_trip")
                ARouter.getInstance().build(ARouterPathList.HOME_TRIP_DETAIL)
                 //   .withOptionsCompat(option)
                    .withLong("tripId",item.id?:0L)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            executePendingBindings()
        }
    }

    override fun convert(holder: BaseDataBindingHolder<ItemHomeFavoriteTripBinding>, item: TravelProductItem, payloads: List<Any>) {
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

