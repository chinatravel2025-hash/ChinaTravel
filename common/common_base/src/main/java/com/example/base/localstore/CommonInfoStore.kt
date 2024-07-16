package com.example.base.localstore

import com.example.base.base.User


object CommonInfoStore {

    private val IS_FIRST_VISIT_APP_KEY = "IS_FIRST_VISIT_APP_KEY"

    private val APP_INSTALL_EVENT_KEY = "APP_INSTALL_EVENT_KEY"



    fun saveInviteUrl(inviteUrl: String?) {
        MMKVSpUtils.putString(
            MMKVConstanst.APP_COMMON_INFO,
            inviteUrl,
            false
        )
    }





    fun getInviteUrl(): String {
        return MMKVSpUtils.getString(
            MMKVConstanst.APP_COMMON_INFO,
            "", false
        )
    }



    //是否首次访问应用
    fun isFirstVisitApp(defaultV: Boolean):Boolean{
        return MMKVSpUtils.getBoolean(IS_FIRST_VISIT_APP_KEY,defaultV)
    }

    fun setFirstVistApp(v: Boolean){
        MMKVSpUtils.putBoolean(IS_FIRST_VISIT_APP_KEY,v)
    }



    //是否已发送appInstall
    fun hasSendAppInstallEvent(defaultV: Boolean):Boolean{
        return MMKVSpUtils.getBoolean(APP_INSTALL_EVENT_KEY,defaultV)
    }

    fun setAppInstallEvent(v: Boolean){
        MMKVSpUtils.putBoolean(APP_INSTALL_EVENT_KEY,v)
    }








}