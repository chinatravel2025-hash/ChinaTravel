package com.travel.home.ui.city.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.home.R
import com.travel.home.databinding.ItemHomeCityColumnListBinding


class CityColumnListAdapter(
    val callback:(Int)->Unit
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHomeCityColumnListBinding>>(
        R.layout.item_home_city_column_list
    ) {
        private var selectPosition=0
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeCityColumnListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {
            title.text=item
            select=selectPosition==holder.layoutPosition
            root.setOnClickListener {
                callback.invoke(holder.layoutPosition)
                quickSelect(holder.layoutPosition)
            }
            executePendingBindings()
        }
    }

    fun quickSelect(position:Int){
        selectPosition=position
        notifyDataSetChanged()
    }





}

