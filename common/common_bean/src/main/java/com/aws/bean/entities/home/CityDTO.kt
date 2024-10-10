package com.aws.bean.entities.home

import java.io.Serializable


data class CityDTO(
    var count: Int? = 0,
    var list: List<CityItem> = listOf(),
    var page: Int? = 0,
    var size: Int? = 0
): Serializable
data class CityItem(
    var id: Long? = null,
    var city_name: String? = "",
    var about: String? = "",
    var pic_url_list:  List<String> = listOf(),
    var like_num: Int? = 0,
    var is_show: Int? = 0,
    var priority: Int? = 0,
    var status: Int? = 0,
    var tags: List<TagDTO?>? = listOf(),
    var is_like: Int? = 0,

): Serializable



