package com.travel.adapter


import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.travel.map.R
import com.travel.map.databinding.ItemRvDayBinding


class DayListAdapter(
    val callback:(Int)->Unit
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemRvDayBinding>>(
        R.layout.item_rv_day
    ) {
    private var selectPosition=0

    fun quickSelect(position:Int){
        selectPosition=position
        notifyDataSetChanged()
    }
    override fun convert(
        holder: BaseDataBindingHolder<ItemRvDayBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            tvDay.text=item
            select=selectPosition==holder.layoutPosition
            root.setOnClickListener {
                callback.invoke(holder.layoutPosition)
                quickSelect(holder.layoutPosition)
            }

            executePendingBindings()
        }
    }









}

