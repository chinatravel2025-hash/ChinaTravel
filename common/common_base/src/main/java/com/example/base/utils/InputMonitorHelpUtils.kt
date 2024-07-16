package com.example.base.utils;
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
const val DEFAULT_RATIO = 0.8
class InputMonitorHelpUtils {
    interface SoftInputListener {
        fun onSoftKeyBoardVisible(visible: Boolean, keyBroadHeight: Int)
    }
    companion object {
        /**
         * 隐藏软键盘
         */
        private var cacheNavigationHeight = 0
        fun hideSoftInput(context: Context?, editText: EditText) {
            editText.clearFocus()
            val token = editText.windowToken
            if (token != null && context != null) {
                val manager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager?.hideSoftInputFromWindow(token, 0)
            }
        }



        fun showSoftInput(context: Context?, editText: EditText) {
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            if (context != null) {
                val manager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager?.showSoftInput(editText, 0)
            }
        }

        fun softInputListener(
            activity: Activity,
            listener: SoftInputListener
        ): ViewTreeObserver.OnGlobalLayoutListener {
            val globalLayoutListener =
                ViewTreeObserver.OnGlobalLayoutListener { softInputMonitor(activity, listener) }
            activity.window.decorView.viewTreeObserver.addOnGlobalLayoutListener(
                globalLayoutListener
            )
            return globalLayoutListener
        }

        fun removeSoftInputListener(
            activity: Activity,
            globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener?
        ) {
            activity.window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(
                globalLayoutListener
            )
        }

        private fun softInputMonitor(activity: Activity, listener: SoftInputListener) {
            val displayHeight = computeVisibleHeight(activity.window)
            val height = activity.window.decorView.height
            var keyBroadHeight = height - displayHeight - getStateBarHeight(activity.window)
            val visible = displayHeight.toDouble() / height < DEFAULT_RATIO
            if (!visible) {
                cacheNavigationHeight = keyBroadHeight
                keyBroadHeight = 0
            } else {
                keyBroadHeight -= cacheNavigationHeight
            }
            listener.onSoftKeyBoardVisible(visible, keyBroadHeight)
        }

        private fun computeVisibleHeight(window: Window): Int {
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            return r.bottom - r.top
        }
        private fun getStateBarHeight(window: Window): Int {
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            return r.top
        }
    }
}
