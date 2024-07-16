package com.example.base.localstore

import android.text.TextUtils
import com.example.base.base.User

object MMKVStore {

    fun putInt(key:String, value:Int ) {
        try {
            if(TextUtils.isEmpty(User.ridString)){
                return
            }
            val _key = "${key}_${User.ridString}"
            MMKVSpUtils.putInt(_key, value )
        } catch (e: Exception) {
        }
    }


    fun getInt(key:String,defaultValue:Int = 0):Int{
        return try {
            if(TextUtils.isEmpty(User.ridString)){
                return 0
            }
            val _key = "${key}_${User.ridString}"
            val num = MMKVSpUtils.getInt(_key, defaultValue)
            num
        } catch (e: Exception) {
            defaultValue
        }
    }


    fun putLong(key:String, value:Long ) {
        try {
            if(TextUtils.isEmpty(User.ridString)){
                return
            }
            val _key = "${key}_${User.ridString}"
            MMKVSpUtils.putLong(_key, value ,true)
        } catch (e: Exception) {
        }
    }

    fun getLong(key:String,defaultValue:Long = 0L):Long{
        return try {
            if(TextUtils.isEmpty(User.ridString)){
                return 0
            }
            val _key = "${key}_${User.ridString}"
            val num = MMKVSpUtils.getLong(_key, defaultValue,true)
            num
        } catch (e: Exception) {
            defaultValue
        }
    }


    fun putString(key:String,value: String ) {
        try {
            if(TextUtils.isEmpty(User.ridString)){
                return
            }
            val _key = "${key}_${User.ridString}"
            MMKVSpUtils.putString(_key, value ,true)
        } catch (e: Exception) {
        }
    }


    fun getString(key:String,defaultValue:String = ""):String{
        return try {
            if(TextUtils.isEmpty(User.ridString)){
                return ""
            }
            val _key = "${key}_${User.ridString}"
            val value = MMKVSpUtils.getString(_key, defaultValue,true)
            value
        } catch (e: Exception) {
            defaultValue
        }
    }


    fun putBoolean(key:String,value: Boolean ) {
        try {
            if(TextUtils.isEmpty(User.ridString)){
                return
            }
            val _key = "${key}_${User.ridString}"
            MMKVSpUtils.putBoolean(_key, value )
        } catch (e: Exception) {
        }
    }


    fun getBoolean(key:String,defaultValue:Boolean = false):Boolean{
        return try {
            if(TextUtils.isEmpty(User.ridString)){
                return false
            }
            val _key = "${key}_${User.ridString}"
            val value = MMKVSpUtils.getBoolean(_key, defaultValue)
            value
        } catch (e: Exception) {
            defaultValue
        }
    }





    fun clearKey(key:String){
        if(TextUtils.isEmpty(User.ridString)){
            return
        }
        val _key = "${key}_${User.ridString}"
        MMKVSpUtils.remove(_key)
    }



}