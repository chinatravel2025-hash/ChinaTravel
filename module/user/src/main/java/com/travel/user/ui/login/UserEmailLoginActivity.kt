package com.travel.user.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.DisplayUtils
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.KeyBoardUtil
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailLoginBinding
import com.travel.user.databinding.UserActivityPasswordSetBinding
import com.travel.user.vm.UserEmailLoginVM
import com.travel.user.vm.UserPasswordSettingVM


@Route(path = ARouterPathList.USER_EMAIL_LOGIN)
class UserEmailLoginActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityEmailLoginBinding
    private lateinit var mVM: UserEmailLoginVM
    private var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.user_activity_email_login
    }
    @JvmField
    @Autowired
    var mail: String? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[UserEmailLoginVM::class.java]
        binding = mBaseBinding as UserActivityEmailLoginBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        binding.ac=this
        mail?.let {
            mVM.email.value=it
        }
        registerInputListener()
        //默认不显示密码
        binding.edConfirm.apply {
            transformationMethod = PasswordTransformationMethod.getInstance()
            setSelection(this.text?.length?:0)
        }


    }

    fun showOrHiddenPwd() {
        val showPwd = mVM.showPw.value ?: false
        if (!showPwd) {
            mVM.showPw.value = true
            binding.edConfirm.apply {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                setSelection(this.text?.length?:0)
            }
        } else {
            mVM.showPw.value = false
            binding.edConfirm.apply {
                transformationMethod = PasswordTransformationMethod.getInstance()
                setSelection(this.text?.length?:0)
            }
        }
    }
    private fun registerInputListener() {
        val h147 = DisplayUtils.dp2px(this, 147f)
        binding.apply {
            softInputListener = InputMonitorHelpUtils.softInputListener(this@UserEmailLoginActivity, object :
                InputMonitorHelpUtils.SoftInputListener {
                override fun onSoftKeyBoardVisible(visible: Boolean, keyBroadHeight: Int) {
                    if (visible) {
                        infoCard.translationY = (-keyBroadHeight + h147).toFloat()
                    } else {
                        infoCard.translationY = 0f
                    }
                }
            })
        }
    }
    private fun removeInputListener() {
        InputMonitorHelpUtils.removeSoftInputListener(this, softInputListener)
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.edConfirm.let { ed ->
            KeyBoardUtil.hideKeyBoard(this, ed)
        }
        removeInputListener()
    }

}