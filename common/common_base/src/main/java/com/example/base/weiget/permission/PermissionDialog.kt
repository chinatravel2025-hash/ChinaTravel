package com.example.base.weiget.permission

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.peanutmusic.base.R
import com.lxj.xpopup.core.PositionPopupView

class PermissionDialog(context: Context,val title:String,val content:String) : PositionPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_permission_content
    }

    override fun onCreate() {
        super.onCreate()
        val flContainer = findViewById<LinearLayout>(R.id.fl_container)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvContent = findViewById<TextView>(R.id.tv_content)
       tvTitle.text=title
       tvContent.text=content
       // flContainer.setSkinBackgroundResource(ColorRes.color_bg_item_pop)
    }
}