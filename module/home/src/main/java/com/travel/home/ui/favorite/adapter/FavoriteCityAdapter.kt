package com.travel.home.ui.favorite.adapter


import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.CityListAdapterClickListener
import com.travel.home.databinding.ItemHomeCityListBinding
import com.travel.home.databinding.ItemHomeFavoriteCityBinding


class FavoriteCityAdapter(
   val  listener: CityListAdapterClickListener
) :
    BaseQuickAdapter<CityItem, BaseDataBindingHolder<ItemHomeFavoriteCityBinding>>(
        R.layout.item_home_favorite_city
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeFavoriteCityBinding>,
        item: CityItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            root.setOnClickListener {
     //  val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivBg,"share_city")
                ARouter.getInstance().build(ARouterPathList.HOME_CITY_DETAIL)
               //     .withOptionsCompat(option)
                    .withLong("cityId",item.id?:0L)
                    .navigation(SmartActivityUtils.getTopActivity())
            }

            ivLike.setOnClickListener {
                listener.cancelCityLike(holder.layoutPosition,item)
            }
            ivUnlike.setOnClickListener {
                listener.addCityLike(holder.layoutPosition,item)
            }
            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemHomeFavoriteCityBinding>, item: CityItem, payloads: List<Any>) {
        for (p in payloads) {
            val payload = p as Int
            if (payload == ITEM_1_PAYLOAD) {
                holder.setVisible(R.id.iv_like, item.is_like==1)
                holder.setVisible(R.id.iv_unlike, item.is_like==0)
            }
        }
    }



    companion object{
        val ITEM_1_PAYLOAD: Int = 101

    }


}

