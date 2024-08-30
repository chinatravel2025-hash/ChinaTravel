package com.travel.user.ui.verification

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.china.travel.widget.edtext.TravelVerifyEditText
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.DisplayUtils
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.KeyBoardUtil
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailAddressBinding
import com.travel.user.databinding.UserActivityVerificationCodeBinding
import com.travel.user.databinding.UserActivityVerificationCodeEmailBinding
import com.travel.user.vm.UserEmailVerificationVM
import com.travel.user.vm.UserVerificationCodeVM

@Route(path = ARouterPathList.USER_VERIFICATION_EMAIL)
class UserEmailVerificationActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityVerificationCodeEmailBinding
    private lateinit var mVM: UserEmailVerificationVM
    private var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.user_activity_verification_code_email
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserEmailVerificationVM::class.java]
        binding = mBaseBinding as UserActivityVerificationCodeEmailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm=mVM
        binding.ac=this
        mVM.startCountDown()
        registerInputListener()
        verityListener()
    }



    private fun verityListener(){
        binding.verityTxt.setTextChangedListener(object : TravelVerifyEditText.TextChangedListener{
            override fun textChanged(changeText: CharSequence?) {

            }
            override fun textCompleted(text: CharSequence?) {
                text?.toString()?.let {
                   mVM.navigationMain(it,supportFragmentManager)
                }
            }

        })
    }
    private fun registerInputListener() {
        val h240 = DisplayUtils.dp2px(this, 191f)
        binding.apply {
            softInputListener = InputMonitorHelpUtils.softInputListener(this@UserEmailVerificationActivity, object :
                InputMonitorHelpUtils.SoftInputListener {
                override fun onSoftKeyBoardVisible(visible: Boolean, keyBroadHeight: Int) {
                    if (visible) {
                        infoCard.translationY = (-keyBroadHeight + h240).toFloat()
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
        binding.verityTxt.let { ed ->
            KeyBoardUtil.hideKeyBoard(this, ed)
        }
        removeInputListener()
    }



}