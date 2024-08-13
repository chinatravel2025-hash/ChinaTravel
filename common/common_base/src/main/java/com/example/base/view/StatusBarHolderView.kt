package com.example.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.base.utils.StatusBarUtil

/**
 * StatusBar占位View
 */
class StatusBarHolderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, style: Int = -1
) : View(context, attrs, style) {

    private val statusBarHeight by lazy {
        StatusBarUtil.getStatusBarHeight(context)
    }

    init {
        fitsSystemWindows = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                statusBarHeight, MeasureSpec.AT_MOST
            )
        )
    }
}