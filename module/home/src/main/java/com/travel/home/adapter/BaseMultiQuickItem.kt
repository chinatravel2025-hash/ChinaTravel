package com.travel.home.adapter

import com.chad.library.adapter.base.entity.MultiItemEntity

class BaseMultiQuickItem(override val itemType: Int,val data :String) :MultiItemEntity {
    companion object{
        const val FIRST_TYPE=1
        const val SECOND_TYPE=2
        const val THIRD_TYPE=3
        const val FOURTH_TYPE=4
        const val FIFTH_TYPE=5
    }
}