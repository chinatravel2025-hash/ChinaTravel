package com.example.base.utils

import com.aws.bean.util.GsonUtil
import com.example.base.base.bean.BlockDTO

object BlockUtils {
    fun getBlockDto(content:String):BlockDTO{
        return GsonUtil.getInstance().fromJson(content,BlockDTO::class.java)
    }
    
}