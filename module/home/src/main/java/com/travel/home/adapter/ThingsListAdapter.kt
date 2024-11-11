package com.travel.home.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
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
            root.setOnClickListener {
                when (item.type){
                    1->{
                    //    val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_sightseeing")
                        ARouter.getInstance().build(ARouterPathList.HOME_SIGHTSEEING_DETAIL)
                        //    .withOptionsCompat(option)
                            .withLong("placeId",item.id?:0L)
                            .navigation(SmartActivityUtils.getTopActivity())

                    }
                    2->{

                       // val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_shop")
                        ARouter.getInstance().build(ARouterPathList.HOME_SHOP_DETAIL)
                       //     .withOptionsCompat(option)
                            .withLong("placeId",item.id?:0L)
                            .navigation(SmartActivityUtils.getTopActivity())
                    }
                    else->{
                    //    val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivCover,"share_restaurant")
                        ARouter.getInstance().build(ARouterPathList.HOME_RESTAURANT_DETAIL)
                         //   .withOptionsCompat(option)
                            .withLong("placeId",item.id?:0L)
                            .navigation(SmartActivityUtils.getTopActivity())
                    }
                }


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

