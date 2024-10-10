package com.aws.bean.entities.home

data class BannerDTO(
    var count: Int? = 0,
    var list: List<BannerItem> = listOf(),
    var page: Int? = 0,
    var size: Int? = 0
)

data class BannerItem(
    var id: Long? = null,
    var linkUrl: String? = "",
    var picUrl: String? = "",
    var priority: Int? = null,
    var status: Int? = null,
    var title: String? = ""
)