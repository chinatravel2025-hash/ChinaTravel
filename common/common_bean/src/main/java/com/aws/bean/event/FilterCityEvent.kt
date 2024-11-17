package com.aws.bean.event


data class FilterCityEvent(
    val cityId: String,
)

data class FinishPageEvent(
    val from: String,
)