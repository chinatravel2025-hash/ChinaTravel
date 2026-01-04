package com.example.base.utils

import android.app.Activity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.peanutmusic.base.R

/**
 * 照片和视频权限披露说明工具类
 * 用于在请求照片和视频权限前向用户说明权限用途，符合Google Play的显著披露要求
 */
object PhotoVideoPermissionDisclosureHelper {

    /**
     * 显示照片和视频权限说明对话框
     * 在请求照片和视频权限前调用，向用户说明为什么需要这些权限
     * 
     * @param activity Activity上下文
     * @param permissionType 权限类型：CAMERA（相机）或 GALLERY（相册）
     * @param onAllow 用户点击"允许"按钮时的回调，在此回调中请求系统权限
     * @param onDeny 用户点击"拒绝"按钮时的回调（可选）
     * @return AlertDialog实例
     */
    fun showPhotoVideoPermissionDisclosure(
        activity: Activity,
        permissionType: PermissionType,
        onAllow: () -> Unit,
        onDeny: (() -> Unit)? = null
    ): AlertDialog {
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dialog_photo_video_permission_disclosure,
            null
        )
        
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val tvContent = dialogView.findViewById<TextView>(R.id.tv_content)
        val btnAllow = dialogView.findViewById<Button>(R.id.btn_allow)
        val btnDeny = dialogView.findViewById<Button>(R.id.btn_deny)
        
        tvTitle.text = when (permissionType) {
            PermissionType.CAMERA -> "Camera Permission Request"
            PermissionType.GALLERY -> "Photo & Video Permission Request"
        }
        tvContent.text = getPhotoVideoPermissionDescription(permissionType)
        
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
     * 获取照片和视频权限说明文本
     */
    private fun getPhotoVideoPermissionDescription(permissionType: PermissionType): String {
        return when (permissionType) {
            PermissionType.CAMERA -> {
                "ChinaTravel needs access to your camera to enable the following features:\n\n" +
                "• Take Photos: Capture photos to share with travel guides during chat conversations\n" +
                "• Upload Images: Take pictures when submitting support requests\n" +
                "• Profile Pictures: Set your profile picture by taking a photo\n\n" +
                "Camera access is only used when you actively choose to take a photo. " +
                "Your photos are only used within the app and are never shared with third parties."
            }
            PermissionType.GALLERY -> {
                "ChinaTravel needs access to your photos and videos to enable the following features:\n\n" +
                "• Share Images: Select photos from your gallery to share with travel guides during chat conversations\n" +
                "• Upload Images: Choose images from your gallery when submitting support requests\n" +
                "• Profile Pictures: Set your profile picture by selecting an image from your gallery\n\n" +
                "Gallery access is only used when you actively choose to select photos or videos. " +
                "Your media files are only used within the app and are never shared with third parties."
            }
        }
    }
    
    /**
     * 权限类型枚举
     */
    enum class PermissionType {
        CAMERA,      // 相机权限
        GALLERY      // 相册权限（照片和视频）
    }
}



