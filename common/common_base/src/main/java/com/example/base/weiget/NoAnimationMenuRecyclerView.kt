package com.example.base.weiget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.base.utils.SolftInputUtil
import kotlin.math.abs

open class NoAnimationMenuRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    private var startX=0
    private var startY=0
    init{
        initView()
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val y = endY - startY
                val disY = abs(y)
                if (disX > disY) {
                    //横向
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(startX - endX))
                } else {
                    //纵向
                    //top
                    if(!canScrollVertically(-1)){
                        parent.requestDisallowInterceptTouchEvent(y<0)
                    }else{
                        //bottom
                        if(canScrollVertically(1)){
                            parent.requestDisallowInterceptTouchEvent(true)
                        }else{
                            parent.requestDisallowInterceptTouchEvent(canScrollVertically(startX - endX))
                        }

                    }

                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initView() {
        if (itemAnimator is SimpleItemAnimator) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
        }

    }
}