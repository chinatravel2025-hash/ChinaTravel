package com.aws.bean.entities.user


data class OrderDTO(
    var count: Int? = 0,
    var list: List<OrderItem> = listOf(),
    var page: Int? = 0,
    var size: Int? = 0
)
data class OrderItem(
    var id: Long? = 0,
    var pay_time: String? =null,
    var place_id: Long? = 0,
    var price: Double? =null,
    var status: Int? = 0,
    var trip: TripDTO? = null,
    var user_id: Long? = 0
){
    fun priceStr():String{
        return "$price"
    }
}

data class TripDTO(
    var car: String? = "",
    var guide_name: String? = "",
    var guide_phone: String? = "",
    var id: Long? = 0,
    var out_time: String? = "",
    var price: Double? = null,
    var status: Int? = 0,
    var title: String? = "",
    var trip: String? = "",
    var userId: Long? = 0
)