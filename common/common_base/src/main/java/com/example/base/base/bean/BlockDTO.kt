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
            "paragraph" -> {
                1
            }
            "header" -> {
                0
            }
            "image" -> {
                2
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
)


data class ImgFileBean(
    var url: String? = null,
)