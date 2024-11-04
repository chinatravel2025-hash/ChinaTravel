package com.example.base.utils

import com.example.base.localstore.MMKVSpUtils

object AppConfig {
    var defaultPicUrl ="http://117.50.180.237:8810/fs/img/"
    const val APP_PIC="app_pic_url"
    var picUrl=""
    fun appPicUrl(){
        picUrl=MMKVSpUtils.getString(APP_PIC, defaultPicUrl)
    }

    fun appBaseImg(url:String?):String{
        return "${picUrl}${url}"
    }




}
