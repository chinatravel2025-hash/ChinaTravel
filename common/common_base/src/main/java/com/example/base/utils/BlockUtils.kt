package com.example.base.utils

import com.alibaba.fastjson.JSON
import com.aws.bean.util.GsonUtil
import com.example.base.base.bean.BlockDTO
import com.example.base.base.bean.BlocksBean
import com.example.base.base.bean.LocationBean
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

object BlockUtils {
    val locMap = HashMap<Long, ArrayList<LocationBean>>()
    fun getBlockDto(content: String): BlockDTO {
        if (content.isEmpty()) {
            return BlockDTO()
        }
        return GsonUtil.getInstance().fromJson(content, BlockDTO::class.java)
    }


    fun getBlocksList(id: Long, content: String): List<BlocksBean> {
        if (content.isEmpty()) {
            return emptyList()
        }
        val temp = mutableListOf<BlocksBean>()
        try {
            val origin = GsonUtil.getInstance().fromJson(content, BlockDTO::class.java)
            var locationBeanList = ArrayList<LocationBean>()
            origin?.blocks?.forEach { item ->
                temp.add(item.apply {
                    itemType = item.viewType()
                    this.data?.location?.let { location ->
                        locationBeanList.add(location.copy())
                    }
                })
            }
            if (id > 0 && locationBeanList.isNotEmpty()) {
                locMap[id] = locationBeanList
            }
        } catch (e: Exception) {

        }

        return temp
    }

}