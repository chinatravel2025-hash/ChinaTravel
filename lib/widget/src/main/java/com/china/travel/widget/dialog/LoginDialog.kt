package com.china.travel.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.china.travel.widget.R
import com.china.travel.widget.databinding.DialogLoginBinding
import com.china.travel.widget.databinding.DialogStatusBinding
import com.example.base.utils.DisplayUtils

class LoginDialog(val content:String,var callback: (() -> Unit?)):DialogFragment() {
    lateinit var binding: DialogLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_login,
            container,
            false
        )
        binding.tvContent.text=content
        //[Email地址] is already registed. Sign in with this Email?"
        binding.btnText.setOnClickListener {
            callback.invoke()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }


        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        val dialog = Dialog(requireContext(), R.style.ui_style_dialog_center)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 设置Content前设定
          dialog.setCanceledOnTouchOutside(false) // 外部点击取消
        //不能按返回键退出
        dialog.setCancelable(false)
        val window = dialog.window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes
        lp?.gravity = Gravity.CENTER
        lp?.width = DisplayUtils.getScreenWidth(context) - DisplayUtils.dp2px(
            context,
            32f
        )// WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.setBackgroundDrawableResource(com.example.peanutmusic.base.R.color.transparent)
        window?.attributes = lp
        return dialog
    }
}