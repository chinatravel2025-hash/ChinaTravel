package com.example.base.toast

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.peanutmusic.base.R

object ToastDialog {
    fun getView(context: Context, content: String, type: Int):View {
        val view = LayoutInflater.from(context).inflate(R.layout.toast_dialog, null)
        val tvContent = view.findViewById<TextView>(R.id.tv_content)
        val img = view.findViewById<ImageView>(R.id.img)
        if (!TextUtils.isEmpty(content)) {
            tvContent.text = content
        }
        tvContent.gravity = Gravity.CENTER
        img.visibility = View.VISIBLE
        when (type) {
            ToastHelper.TOAST_HTTP_ERROR -> img.setImageResource(R.mipmap.toast_ic_fail)
            ToastHelper.TOAST_FAIL -> img.setImageResource(R.mipmap.toast_ic_fail)
         ToastHelper.TOAST_HTTP_SUCCESS -> img.setImageResource(R.mipmap.toast_ic_success)
            /* ToastHelper.TOAST_HTTP_DOWNLOAD -> img.setImageResource(R.mipmap.)*/
            ToastHelper.TOAST_TXT -> {
                tvContent.gravity = Gravity.CENTER
                img.visibility = View.GONE
            }
            else -> {
            }
        }
        return view
    }
}