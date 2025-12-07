package com.example.base.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

object PrivacyPolicyDialogHelper {

    /**
     * 获取隐私政策内容文本
     */
    fun getPrivacyPolicyContent(): String {
        return "1. Privacy Policy\n" +
                "ChinaTravel respects and protects the personal privacy rights of all users We will collect use, and disclose your personal information in accordance with this Privacy Policy and treat your data with the highest degree of diligence and prudence. Unless otherwise stated, we will not disclose or provide your information to any unrelated third party without your prior consent. This policy may be updated from time to time. By agreeing to our Terms of Service, you are deemed to have accepted all terms herein.\n" +
                "2. Scope of Application\n" +
                "This Privacy Policy applies to all personal information collected, used, and processed by us when you use the ChinaTravel App, including the information you provide when using the App's customized travel services, and the information automatically collected during your use of the App.\n" +
                "3. The following permissions will be requested when using the app:\n" +
                "(1) Camera (to take photos for user avatar or upload images);\n" +
                "(2) Storage permission (to read and save images from gallery);\n" +
                "(3) Location permission (to provide location-based services and map features);\n" +
                "(4) Network status (to check network connectivity);\n" +
                "(5) Microphone (for voice messages in chat features);\n" +
                "(6) Notifications (to send you important updates and messages);"
    }

    /**
     * 显示隐私政策Dialog（用于Profile页面，只有一个Close按钮）
     * @param fragment Fragment上下文
     * @param layoutId dialog布局资源ID
     * @param titleId 标题TextView资源ID
     * @param contentId 内容TextView资源ID
     * @param closeButtonId 关闭按钮资源ID
     * @return AlertDialog实例，用于在Fragment销毁时关闭
     */
    fun showPrivacyPolicyDialog(
        fragment: Fragment,
        layoutId: Int,
        titleId: Int,
        contentId: Int,
        closeButtonId: Int
    ): AlertDialog? {
        val context = fragment.requireContext()
        val dialogView = LayoutInflater.from(context).inflate(layoutId, null)
        val tvTitle = dialogView.findViewById<TextView>(titleId)
        val tvContent = dialogView.findViewById<TextView>(contentId)
        val btnClose = dialogView.findViewById<Button>(closeButtonId)

        tvTitle.text = "Privacy Policy"
        tvContent.text = getPrivacyPolicyContent()

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        return dialog
    }

    /**
     * 显示用户协议Dialog（用于LaunchActivity，有Disagree和Agree按钮）
     * @param context Activity上下文
     * @param layoutId dialog布局资源ID
     * @param titleId 标题TextView资源ID
     * @param contentId 内容TextView资源ID
     * @param disagreeButtonId 不同意按钮资源ID
     * @param agreeButtonId 同意按钮资源ID
     * @param onDisagreeClick 点击不同意时的回调
     * @param onAgreeClick 点击同意时的回调
     * @return AlertDialog实例
     */
    fun showUserAgreementDialog(
        context: Context,
        layoutId: Int,
        titleId: Int,
        contentId: Int,
        disagreeButtonId: Int,
        agreeButtonId: Int,
        onDisagreeClick: () -> Unit,
        onAgreeClick: () -> Unit
    ): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(layoutId, null)
        val tvTitle = dialogView.findViewById<TextView>(titleId)
        val tvContent = dialogView.findViewById<TextView>(contentId)
        val btnDisagree = dialogView.findViewById<Button>(disagreeButtonId)
        val btnAgree = dialogView.findViewById<Button>(agreeButtonId)

        tvTitle.text = "Privacy Policy"
        tvContent.text = getPrivacyPolicyContent()

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnDisagree.setOnClickListener {
            onDisagreeClick()
        }

        btnAgree.setOnClickListener {
            dialog.dismiss()
            onAgreeClick()
        }

        dialog.show()
        return dialog
    }
}

