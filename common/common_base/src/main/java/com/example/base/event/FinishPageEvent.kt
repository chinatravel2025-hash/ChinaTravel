package com.example.base.event

/**
 * 连续移除多个页面
 */
data class FinishPageEvent(
 //page 页面的名称
 var pages:List<String>
)
