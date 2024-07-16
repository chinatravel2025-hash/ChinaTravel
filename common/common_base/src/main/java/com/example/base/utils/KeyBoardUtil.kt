package com.example.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment

object KeyBoardUtil {


    fun hideKeyBoard(context:Context?,et:EditText){
        context?.let {
            val imm: InputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(
                    et.applicationWindowToken, 0
                )
            }
        }
    }

    fun hideKeyBoard(context:Context?,dialog:DialogFragment){
        context?.let {
            val imm: InputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(
                    dialog.view?.windowToken, 0
                )
            }
        }
    }

    fun showKeyBoard(context:Context?){
        context?.let {
            val imm: InputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                //这个才能强制弹出
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
             //   imm.showSoftInput(et,0)
            }
        }
    }


}