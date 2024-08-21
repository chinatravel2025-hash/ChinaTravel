package com.example.base.weiget

import android.content.Context
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewConfiguration
import android.view.MotionEvent
import androidx.core.view.MotionEventCompat
import kotlin.math.abs

/**
 * 多层嵌套问题
 */
open class MultiRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {
    private val mTouchSlop: Int
    private var mScrollPointerId = 0
    private var mInitialTouchX = 0
    private var mInitialTouchY = 0

    init {
        val vc = ViewConfiguration.get(context)
        mTouchSlop = vc.scaledTouchSlop
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
      val a=  e.action
      val ai = e.actionIndex
        //仿造源码
        val action = MotionEventCompat.getActionMasked(e)
        val actionIndex = MotionEventCompat.getActionIndex(e)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                mInitialTouchX = (e.x + 0.5f).toInt()
                mInitialTouchY = (e.y + 0.5f).toInt()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = e.getPointerId(actionIndex)
                mInitialTouchX = (e.x + 0.5f).toInt()
                mInitialTouchY = (e.y + 0.5f).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(mScrollPointerId)
                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                var startScroll = false
                if (scrollState != SCROLL_STATE_DRAGGING) {
                    val dx = x - mInitialTouchX
                    val dy = y - mInitialTouchY

                    //这边增加拦截的难度，当滑动的水平距离大于滑动的垂直距离时，才拦截，当然了，如果自身又能竖直滑动的情况，就不判断两个滑动距离大小
                    if (layoutManager?.canScrollHorizontally()==true && abs(dx) > mTouchSlop
                        && (layoutManager?.canScrollVertically()==true || abs(dx) > abs(dy))
                    ) {
                        startScroll = true
                    }
                    //这里是当滑动的水平距离小于滑动的垂直距离时，才拦截，如果自身又能水平滑动的情况，就不判断两个滑动距离大小
                    if (layoutManager?.canScrollVertically()==true  && abs(dy) > mTouchSlop
                        && (layoutManager?.canScrollHorizontally()==true || abs(dy) > abs(dx))
                    ) {
                        startScroll = true
                    }
                }
                return startScroll && super.onInterceptTouchEvent(e)
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}