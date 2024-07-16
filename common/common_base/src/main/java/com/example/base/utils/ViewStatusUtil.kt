package com.example.base.utils

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.bumptech.glide.Glide
import com.example.base.base.ktx.dp

object ViewStatusUtil {


    /**
     * 要判断所在的 Fragment 是否已被销毁，您可以通过 View 找到包含该 View 的 Fragment，并检查该 Fragment 的状态。
     */
    fun findFragmentForView(view: View): Fragment? {
        val activity = getActivityFromView(view)
        return findFragmentInActivity(activity, view)
    }

    private fun getActivityFromView(view: View): FragmentActivity? {
        var context = view.context
        while (context is ContextWrapper) {
            if (context is FragmentActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    private fun findFragmentInActivity(activity: FragmentActivity?, view: View): Fragment? {
        if (activity == null) {
            return null
        }
        val fragmentManager = activity.supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment.isAdded && view.isDescendantOf(fragment.requireView())) {
                return fragment
            }
        }
        return null
    }

    private fun View.isDescendantOf(parentView: View): Boolean {
        var currentView: View? = this
        while (currentView != null) {
            if (currentView == parentView) {
                return true
            }
            val parent = currentView.parent
            if (parent is View) {
                currentView = parent
            } else {
                return false
            }
        }
        return false
    }


    fun isViewCanUsed(view:View):Boolean{

        if(view == null){
            return false
        }


        var context = view.context
        if(context is Activity){

            if(context.isDestroyed || context.isFinishing)
                return false
        }

        //移出窗口
        if(!view.isAttachedToWindow){
            return false
        }

        return true

    }

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


}