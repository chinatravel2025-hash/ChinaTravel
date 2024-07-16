package com.example.base.utils

import android.util.Log
import com.example.peanutmusic.base.BuildConfig

object LogUtils {


    private  val DEBUG = BuildConfig.DEBUG
    @JvmStatic
    fun d(tag:String,msg: String?) {
        if (DEBUG) {
            Log.d(tag, msg!!)
        }
    }

    @JvmStatic
    fun e(tag:String,msg: String?) {
        if (DEBUG) {
            Log.e(tag, msg!!)
        }
    }

    @JvmStatic
    fun i(tag:String,msg: String?) {
        if (DEBUG) {
            Log.i(tag, msg!!)
        }
    }

    @JvmStatic
    fun v(tag:String,msg: String?) {
        if (DEBUG) {
            Log.v(tag, msg!!)
        }
    }

    @JvmStatic
    fun w(tag:String,msg: String?) {
        if (DEBUG) {
            Log.w(tag, msg!!)
        }
    }

}
