package com.example.base.utils

import com.example.base.localstore.MMKVSpUtils

object AppConfig {
    var defaultPicUrl ="https://app-api.chunhuo.net/fs/img/"
    const val APP_PIC="app_pic_url"
    var picUrl=""
    fun appPicUrl(){
        picUrl=defaultPicUrl
    }

    fun appBaseImg(url:String?):String{
        return "${picUrl}${url}"
    }




}
