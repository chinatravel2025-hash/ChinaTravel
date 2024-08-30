package com.travel.user.ui.order.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.user.R
import com.travel.user.databinding.ItemOrdersListBinding


interface OrderListItemOrchestrator {
    fun navigateToDetail(item: String)
}
class UserOrderAdapter( val orchestrator:OrderListItemOrchestrator
) :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemOrdersListBinding>>(
        R.layout.item_orders_list,
    ) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemOrdersListBinding>,
        item: String
    ) {
        holder.dataBinding?.apply {

            executePendingBindings()
        }
    }

}

