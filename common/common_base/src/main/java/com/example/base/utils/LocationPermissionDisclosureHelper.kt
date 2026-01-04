package com.example.base.utils

import android.app.Activity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.peanutmusic.base.R

/**
 * 位置权限披露说明工具类
 * 用于在请求位置权限前向用户说明权限用途，符合Google Play的显著披露要求
 */
object LocationPermissionDisclosureHelper {

    /**
     * 显示位置权限说明对话框
     * 在请求位置权限前调用，向用户说明为什么需要位置权限
     * 
     * @param activity Activity上下文
     * @param onAllow 用户点击"允许"按钮时的回调，在此回调中请求系统权限
     * @param onDeny 用户点击"拒绝"按钮时的回调（可选）
     * @return AlertDialog实例
     */
    fun showLocationPermissionDisclosure(
        activity: Activity,
        onAllow: () -> Unit,
        onDeny: (() -> Unit)? = null
    ): AlertDialog {
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dialog_location_permission_disclosure,
            null
        )
        
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val tvContent = dialogView.findViewById<TextView>(R.id.tv_content)
        val btnAllow = dialogView.findViewById<Button>(R.id.btn_allow)
        val btnDeny = dialogView.findViewById<Button>(R.id.btn_deny)
        
        tvTitle.text = "Location Permission Request"
        tvContent.text = getLocationPermissionDescription()
        
        val dialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        btnAllow.setOnClickListener {
            dialog.dismiss()
            onAllow()
        }
        
        btnDeny.setOnClickListener {
            dialog.dismiss()
            onDeny?.invoke()
        }
        
        dialog.show()
        return dialog
    }
    
    /**
     * 获取位置权限说明文本
     */
    private fun getLocationPermissionDescription(): String {
        return "ChinaTravel needs access to your location to provide the following features:\n\n" +
                "• Interactive Maps: Display attractions, restaurants, and points of interest on maps\n" +
                "• Route Planning: Help you plan travel routes based on your location\n\n" +
                "Your location data is only used within the app and is never shared with third parties. " +
                "Location access is only used when you actively view map content."
    }
}



