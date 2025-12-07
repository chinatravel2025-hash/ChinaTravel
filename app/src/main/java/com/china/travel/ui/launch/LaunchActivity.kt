package com.china.travel.ui.launch

import android.content.Intent
import android.os.Bundle
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
import com.example.base.utils.PrivacyPolicyDialogHelper
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
        PrivacyPolicyDialogHelper.showUserAgreementDialog(
            context = this,
            layoutId = R.layout.dialog_user_agreement,
            titleId = R.id.tv_title,
            contentId = R.id.tv_content,
            disagreeButtonId = R.id.btn_disagree,
            agreeButtonId = R.id.btn_agree,
            onDisagreeClick = {
                // 点击不同意，退出APP
                finish()
                System.exit(0)
            },
            onAgreeClick = {
                // 点击同意并继续，记录到MMKV
                MMKVSpUtils.putBoolean(MMKVConstanst.USER_AGREEMENT_ACCEPTED, true)
                // 继续执行原有逻辑
                continueInit()
            }
        )
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