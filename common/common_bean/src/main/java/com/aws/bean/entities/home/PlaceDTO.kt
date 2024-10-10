package com.aws.bean.entities.home

import java.io.Serializable

data class PlaceDTO(
    var count: Int? = 0,
    var list: List<PlaceItem> = listOf(),
    var page: Int? = 0,
    var size: Int? = 0
): Serializable


data class PlaceItem(
    var about: String? = "",
    var address: String? = "",
    var city_id: Long? = null,
    var id: Long? = null,
    var introduce: String? = "",
    var is_like: Int? = 0,
    var lat: String? = "",
    var lever: Int? = 0,
    var lng: String? = "",
    var pic_url_list: List<String>? = listOf(),
    var tags: List<TagDTO>? = listOf(),
    var title: String? = "",
    var type: Int? = 0
): Serializable

