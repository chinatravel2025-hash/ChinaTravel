package com.travel.user.ui.order.adapter


import com.aws.bean.entities.user.OrderItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.travel.user.R
import com.travel.user.databinding.ItemOrdersListBinding


interface OrderListItemOrchestrator {
    fun navigateToDetail(item: String)
}
class UserOrderAdapter( val orchestrator:OrderListItemOrchestrator
) :
    BaseQuickAdapter<OrderItem, BaseDataBindingHolder<ItemOrdersListBinding>>(
        R.layout.item_orders_list,
    ) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemOrdersListBinding>,
        item: OrderItem
    ) {
        holder.dataBinding?.apply {
            vm=item
            executePendingBindings()
        }
    }

}

