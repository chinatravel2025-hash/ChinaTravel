package com.travel.user.ui.preferences

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.BaseStatusBarActivity
import com.example.base.base.User
import com.example.base.toast.NormalDialog
import com.example.commponent.ui.dialog.OnClickLeftListener
import com.example.commponent.ui.dialog.OnClickRightListener
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityPreferencesAccountBinding
import com.travel.user.vm.UserPreferencesAccountVM

@Route(path = ARouterPathList.USER_PREFERENCES_ACCOUNT)
class UserPreferencesAccountActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityPreferencesAccountBinding
    private lateinit var mVM: UserPreferencesAccountVM
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.user_activity_preferences_account
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserPreferencesAccountVM::class.java]
        binding = mBaseBinding as UserActivityPreferencesAccountBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        binding.ac=this
        
        // 自动填入用户昵称
        binding.edName.setText(User.userName)
    }
    
    fun send() {
        val inputText = binding.edName.text?.toString()?.trim()
        if (inputText.isNullOrEmpty()) {
            SmartToast.classic().showInCenter("Please enter your nickname")
        } else {
            showConfirmDialog()
        }
    }
    
    private fun showConfirmDialog() {
        NormalDialog.Builder()
            .setSubTitle("All data will be cleared and cannot be recovered after closing your account. Are you sure you want to close your account?")
            .setLeftText("Cancel")
            .setRightText("Close")
            .setLeftTextColor(Color.RED)
            .setRightTextColor(Color.GRAY)
            .setLeftListener(object : OnClickLeftListener {
                override fun onLeft(content: String?) {
                    // 取消，不做任何操作
                }
            })
            .setRightListener(object : OnClickRightListener {
                override fun onRight(content: String?) {
                    // 确认关闭账户
                    closeAccount()
                }
            })
            .build()
            .show(supportFragmentManager, "CloseAccountDialog")
    }
    
    private fun closeAccount() {
        mVM.closeUser { success ->
            if (success) {
                SmartToast.classic().showInCenter("Deactivated successfully")
                // 退出登录，清除用户信息
                User.logout()
                // 跳转到登录页，关闭其他页面
                ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation()
                finish()
            } else {
                SmartToast.classic().showInCenter("Network error, please try again")
            }
        }
    }


    fun uploadPic() {


    }





}