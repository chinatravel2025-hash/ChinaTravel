package com.example.base.ext

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform


fun View.setVisible(_visibility:Int){
    if(this.visibility == _visibility){
        return
    }
    this.visibility = _visibility
}

fun <T> Boolean?.matchValue(valueTrue: T, valueFalse: T): T {
    return if (this == true) valueTrue else valueFalse
}
fun View.convert(
    parentView: ViewGroup,
    endView: View,
    duration: Long = 400L, // 默认转变事件
) {
    MaterialContainerTransform(context, true).also {
        // 设置转变过程中颜色 (默认灰色)
        it.scrimColor = Color.TRANSPARENT
        it.duration = duration

        // 设置开始view
        it.startView = this
        // 设置结束view
        it.endView = endView
        it.addTarget(endView)
    }.also { transition ->
        TransitionManager.beginDelayedTransition(parentView, transition)
        this.visibility = View.INVISIBLE
        endView.visibility = View.VISIBLE
    }
}