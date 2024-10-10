package com.travel.home.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.TravelProductItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityListBinding
import com.travel.home.databinding.ItemHomeDayTripListBinding


class CityListAdapter(
   val  listener: CityListAdapterClickListener
) :
    BaseQuickAdapter<CityItem, BaseDataBindingHolder<ItemHomeCityListBinding>>(
        R.layout.item_home_city_list
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeCityListBinding>,
        item: CityItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            root.setOnClickListener {
       val option=  ActivityOptionsCompat.makeSceneTransitionAnimation(SmartActivityUtils.getTopActivity(),ivBg,"share_city")
                ARouter.getInstance().build(ARouterPathList.HOME_CITY_DETAIL)
                    .withOptionsCompat(option)
                    .withLong("cityId",item.id?:0)
                    .navigation(SmartActivityUtils.getTopActivity())
            }
            labelList.setLabels(item.tags) { _, _, data -> data?.tag?.tag ?: "" }

            ivLike.setOnClickListener {
                listener.cancelCityLike(holder.layoutPosition,item)
            }
            ivUnlike.setOnClickListener {
                listener.addCityLike(holder.layoutPosition,item)
            }
            executePendingBindings()
        }
    }


    override fun convert(holder: BaseDataBindingHolder<ItemHomeCityListBinding>, item: CityItem, payloads: List<Any>) {
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

