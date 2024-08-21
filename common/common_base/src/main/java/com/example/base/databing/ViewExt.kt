package com.example.base.databing

import android.graphics.Color
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionManager
import com.example.base.base.ktx.dp
import com.example.base.utils.TLog
import com.google.android.material.transition.MaterialContainerTransform


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



fun <T : View> T.withTrigger(delay: Long = 800): T {
    triggerDelay = delay
    return this
}

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
//    ClickUtils.applyPressedViewAlpha(this,0.8f)
    if (clickEnable()) {
        block(it as T)
    }
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认800毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 800, block: (T) -> Unit) {
    // 此处是点击后按钮背景透明度变化 可参考blankj开源工具类
//    ClickUtils.applyPressedViewAlpha(this,0.6f)
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}
@BindingAdapter("oncClickWithTrigger")
fun oncClickWithTrigger(view: View,listener:View.OnClickListener){
        view.clickWithTrigger {
            listener.onClick(view)
        }
}


/**
 *  app:expandTouchArea="@{`20 10 50 20`}"
 */
@BindingAdapter("expandTouchArea")
fun expandTouchArea(view: View, size: String) {
    view.postDelayed({
        val bounds = Rect()
        view.getHitRect(bounds)
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        /*
        *  size 举例 `2` or `2 4` or `2 4 6 8`
         */
        val mSize = size.trim()
        val ss = mSize.split(" ")
        when (ss.size) {
            1 -> {
                val sdp = (ss[0].toIntOrNull() ?: 0).dp
                left = sdp
                top = sdp
                right = sdp
                bottom = sdp
            }
            2 -> {
                val sdp = (ss[0].toIntOrNull() ?: 0).dp
                val sdp1 = (ss[1].toIntOrNull() ?: 0).dp
                left = sdp
                top = sdp1
                right = sdp
                bottom = sdp1
            }
            4 -> {
                left = (ss[0].toIntOrNull() ?: 0).dp
                top = (ss[1].toIntOrNull() ?: 0).dp
                right = (ss[2].toIntOrNull() ?: 0).dp
                bottom = (ss[3].toIntOrNull() ?: 0).dp
            }
            else -> {
                return@postDelayed
            }
        }
        bounds.left -= left
        bounds.top -= top
        bounds.right += right
        bounds.bottom += bottom
        val mTouchDelegate = TouchDelegate(bounds, view);
        val p = view.parent
        if (p is ViewGroup) {
            p.touchDelegate = mTouchDelegate;
        }
    }, 100)
}






