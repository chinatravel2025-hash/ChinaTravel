package com.example.base.utils

import com.aws.bean.util.GsonUtil
import com.example.base.base.bean.BlockDTO
import com.example.base.base.bean.BlocksBean
import java.lang.Exception

object BlockUtils {
    fun getBlockDto(content: String): BlockDTO {
        if (content.isEmpty()) {
            return BlockDTO()
        }
        return GsonUtil.getInstance().fromJson(content, BlockDTO::class.java)
    }


    fun getBlocksList(content: String): List<BlocksBean> {
        if (content.isEmpty()) {
            return emptyList()
        }
        val temp = mutableListOf<BlocksBean>()
        try {
            val origin = GsonUtil.getInstance().fromJson(content, BlockDTO::class.java)
            origin?.blocks?.forEach { item ->
                temp.add(item.apply {
                    itemType = item.viewType()
                })
            }
        } catch (e: Exception) {

        }

        return temp
    }

}