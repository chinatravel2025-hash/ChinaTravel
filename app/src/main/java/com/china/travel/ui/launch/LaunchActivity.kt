package com.china.travel.ui.launch

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.util.GsonUtil
import com.china.travel.R
import com.china.travel.databinding.ActivityLaunchBinding
import com.example.base.base.BaseStatusBarActivity
import com.example.base.base.User
import com.example.base.localstore.MMKVConstanst
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.StatusBarUtil
import com.example.router.ARouterPathList

class LaunchActivity : BaseStatusBarActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override val ivBack: Int
        get() = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_launch
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        binding = mBaseBinding as ActivityLaunchBinding
        setContentView(binding.root)
        
        // 检查是否已同意用户协议和隐私条款
        val hasAgreed = MMKVSpUtils.getBoolean(MMKVConstanst.USER_AGREEMENT_ACCEPTED, false)
        
        if (!hasAgreed) {
            // 显示用户协议和隐私条款确认弹窗
            showUserAgreementDialog()
        } else {
            // 已同意，继续执行原有逻辑
            continueInit()
        }
    }
    
    private fun showUserAgreementDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_user_agreement, null)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val tvContent = dialogView.findViewById<TextView>(R.id.tv_content)
        val btnDisagree = dialogView.findViewById<Button>(R.id.btn_disagree)
        val btnAgree = dialogView.findViewById<Button>(R.id.btn_agree)
        
        tvTitle.text = "User Agreement and Privacy Policy"
        
        // 设置内容文本，包含可点击的链接
        val contentText = "Welcome to ChinaTravel! Please read the \"User Agreement\" and \"ChinaTravel Privacy Policy\" carefully. Clicking the \"Agree and Continue\" button means you agree to the above agreements and the following terms: To provide you with caching services, we will request system storage permissions."
        val spannableString = SpannableString(contentText)
        
        // 设置《用户协议》为蓝色可点击
        val userAgreementStart = contentText.indexOf("User Agreement")
        val userAgreementEnd = userAgreementStart + "User Agreement".length
        if (userAgreementStart >= 0) {
            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // 跳转到用户协议页面
                    ARouter.getInstance()
                        .build(ARouterPathList.WEB_HOME)
                        .withString("url", "https://chinatravel2025-hash.github.io/ChinaTravel/user_agreement.html")
                        .withString("title", "User Agreement")
                        .withBoolean("hideActionBar", false)
                        .navigation(this@LaunchActivity)
                }
                
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }, userAgreementStart, userAgreementEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        
        // 设置《ChinaTravel隐私条款》为蓝色可点击
        val privacyPolicyStart = contentText.indexOf("ChinaTravel Privacy Policy")
        val privacyPolicyEnd = privacyPolicyStart + "ChinaTravel Privacy Policy".length
        if (privacyPolicyStart >= 0) {
            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // 跳转到隐私政策页面
                    ARouter.getInstance()
                        .build(ARouterPathList.WEB_HOME)
                        .withString("url", "https://chinatravel2025-hash.github.io/ChinaTravel/privacy_policy.html")
                        .withString("title", "Privacy Policy")
                        .withBoolean("hideActionBar", false)
                        .navigation(this@LaunchActivity)
                }
                
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }, privacyPolicyStart, privacyPolicyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        
        tvContent.text = spannableString
        tvContent.movementMethod = LinkMovementMethod.getInstance()
        tvContent.highlightColor = Color.TRANSPARENT
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        
        btnDisagree.setOnClickListener {
            // 点击不同意，退出APP
            finish()
            System.exit(0)
        }
        
        btnAgree.setOnClickListener {
            // 点击同意并继续，记录到MMKV
            MMKVSpUtils.putBoolean(MMKVConstanst.USER_AGREEMENT_ACCEPTED, true)
            dialog.dismiss()
            // 继续执行原有逻辑
            continueInit()
        }
        
        dialog.show()
    }
    
    private fun continueInit() {
        binding.lifecycleOwner = this
        HomeRepository.homeRepository.getAppConfig()
        binding.ivCover.postDelayed({
            if (User.isLogin) {
                var path = if (User.userName.isNullOrEmpty()) ARouterPathList.USER_SET_NICKNAME else ARouterPathList.APP_MAIN
                ARouter.getInstance().build(path)
                    .navigation(this, object : NavCallback() {
                        override fun onArrival(p0: Postcard?) {
                            finish()
                        }
                    })

            } else {
                ARouter.getInstance().build(ARouterPathList.USER_REGISTER)
                    .navigation(this, object : NavCallback() {
                        override fun onArrival(p0: Postcard?) {
                            finish()
                        }

                    })
            }
        }, 500)
    }


}