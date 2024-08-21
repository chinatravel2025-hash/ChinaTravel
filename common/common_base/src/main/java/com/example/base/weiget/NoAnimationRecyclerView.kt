package com.example.base.weiget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.base.utils.SolftInputUtil

open class NoAnimationRecyclerView : RecyclerView {
    var isActionDownMove = false
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        hideSoftKeyboard(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                isActionDownMove = true
            }
            MotionEvent.ACTION_UP -> {}
        }
        hideSoftKeyboard(event)
        return super.onTouchEvent(event)
    }

    open fun hideSoftKeyboard(event: MotionEvent) {
        try {
            if (context is Activity && event.action == MotionEvent.ACTION_DOWN) {
                SolftInputUtil.hideSoftInputFromWindow(context as Activity)
            }
        } catch (e: java.lang.Exception) {
        }
    }
    private fun initView() {
        if (itemAnimator is SimpleItemAnimator) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
        }

    }
}