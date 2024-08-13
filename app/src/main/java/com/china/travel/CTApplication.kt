package com.china.travel

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.china.travel.init.RouteModule
import com.example.base.base.App
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.ProcessUtils
import com.example.http.API
import com.example.http.APIService



class CTApplication : Application() {
    private var isInit = false


    override fun onCreate() {
        super.onCreate()
        instance = this
        initMainProcess(ProcessUtils.isMainProcess(this))
    }



    private fun initMainProcess(isMain: Boolean) {
        MultiDex.install(this)
        //进行MMKV初始化
      //  TLog.setEnable(BuildConfig.DEBUG)
        MMKVSpUtils.init(this)
        App.initApp(this)
        App.init(this, "");
       // val environment = MMKVSpUtils.getString(MMKVConstanst.YSM_ENVIRONMENT, "dev")
     //   API.env=environment
        APIService.initRetrofit("")
        RouteModule.init(this)

    }


    override fun onTerminate() {
        super.onTerminate()

    }
    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

    }




    companion object {
        var instance: CTApplication? = null

    }
}