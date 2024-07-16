package com.example.base.utils

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.peanutmusic.base.R


object AnimationUtils {
    fun View.loadAnimationAsFragmentIn(context: Context) {
        AnimationUtils.loadAnimation(context, R.anim.anim_fragment_in).also { animation ->
            animation.repeatCount = 0
            startAnimation(animation)
        }
    }

    fun View.loadAnimationAsFragmentOut(context: Context) {
        AnimationUtils.loadAnimation(context, R.anim.anim_fragment_out).also { animation ->
            animation.repeatCount = 0
            startAnimation(animation)
        }
    }

    fun View.loadAnimationAsTopIn(context: Context) {
        AnimationUtils.loadAnimation(context, R.anim.anim_view_top_in).also { animation ->
            animation.repeatCount = 0
            startAnimation(animation)
        }
    }

    fun View.loadAnimationAsTopOut(context: Context, listener: Animation.AnimationListener?) {
        AnimationUtils.loadAnimation(context, R.anim.anim_view_top_out).also { animation ->
            animation.repeatCount = 0
            startAnimation(animation)
            animation.setAnimationListener(listener)
        }
    }

    fun View.loadAnimationAsClearingIn() {
        if (visibility == View.VISIBLE) {
            return
        }
        val animation: Animation = AlphaAnimation(0f, 1f)
        animation.duration = 500
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                isEnabled = true
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        startAnimation(animation)
        visibility = View.VISIBLE
    }

    fun View.loadAnimationAsClearingOut(isGone:Boolean = true) {
        if (visibility != View.VISIBLE) {
            return
        }
        isEnabled = false
        val animation: Animation = AlphaAnimation(1f, 0f)
        animation.duration = 500
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                visibility = if(isGone)View.GONE else View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        startAnimation(animation)
    }
}