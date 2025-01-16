package com.example.base.base.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class BlockDTO(
    var blocks: List<BlocksBean>? = null,
)


data class BlocksBean(
    var id: String? = null,
    /**
     * 类型只有三种
     */
    var type: String? = null,
    var data: BlocksInner? = null,
    override var itemType: Int,
) : MultiItemEntity {
    fun viewType(): Int {
        return when (type) {
            //正文
            "paragraph" -> {
                1
            }
            //大标题（下划线）
            "header" -> {
                0
            }

            "image" -> {
                2
            }
            //行程标题
            "tripTitle" -> {
                3
            }
            //行程内容
            "tripContent" -> {
                4
            }
            //关于
            "intro" -> {
                5
            }
            //地图
            "location"-> {
                6
            }

            else -> {
                1
            }
        }
    }
}

data class BlocksInner(
    var text: String? = null,
    var level: Int? = null,
    var file: ImgFileBean? = null,
    var location: LocationBean? = null,
)


data class ImgFileBean(
    var url: String? = null,
)

data class LocationBean(
    var lat: Double? = null,
    var lon: Double? = null,
)