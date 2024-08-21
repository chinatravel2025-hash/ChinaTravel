package com.example.base.toast

import com.example.peanutmusic.base.R


data class CommonListBean(
    var textContent:String,
    var textColor:Int= R.color.black,
    var textSize:Int=17,
    var icon:Int?=null,
    var isBold:Boolean?=false,
)
