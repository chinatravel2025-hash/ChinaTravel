package com.example.base.ext

import android.content.Context
import android.util.TypedValue
import android.view.View


fun View.setVisible(_visibility:Int){
    if(this.visibility == _visibility){
        return
    }
    this.visibility = _visibility
}

fun <T> Boolean?.matchValue(valueTrue: T, valueFalse: T): T {
    return if (this == true) valueTrue else valueFalse
}