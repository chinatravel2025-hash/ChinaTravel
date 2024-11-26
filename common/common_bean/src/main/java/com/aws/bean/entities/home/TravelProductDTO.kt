package com.aws.bean.entities.home

import java.io.Serializable

data class TravelProductDTO(
    var count: Int? = 0,
    var list: List<TravelProductItem> = listOf(),
    var page: Int? = 0,
    var size: Int? = 0
): Serializable
data class TravelProductItem(
    var id: Long? = null,
    var introduce: String? = "",
    var about: String? = "",
    var is_like: Int? = 0,
    var like_num: Int? = 0,
    var pic_url_list: List<String>? = listOf(),
    var price: Float? =null,
    var tags: List<TagDTO>? = listOf(),
    var title: String? = "",
    var trip_rate: Float? = null
): Serializable{
    fun hotLevel():String{
        return "$trip_rate"
    }
}

data class TagDTO(
    var id: Long? = null,
    var priority: Int? = 0,
    var tag: TagItem? = null,
    var tag_id: Long? = null,
    var travel_product_id: Long? = null
): Serializable

data class TagItem(
    var id: Long? = null,
    var tag: String? = ""
): Serializable