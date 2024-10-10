package com.china.travel.widget.dialog
import com.aws.bean.entities.home.CityItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.china.travel.widget.R
import com.china.travel.widget.databinding.ItemFilterPopupBinding


class FilterListAdapter(val  callback:(CityItem)->Unit) :
    BaseQuickAdapter<CityItem, BaseDataBindingHolder<ItemFilterPopupBinding>>(
        R.layout.item_filter_popup
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemFilterPopupBinding>,
        item: CityItem
    ) {
        holder.dataBinding?.apply {
            tvTitle.text=item.city_name
            root.setOnClickListener {
                callback.invoke(item)
            }
            executePendingBindings()
        }
    }





}

