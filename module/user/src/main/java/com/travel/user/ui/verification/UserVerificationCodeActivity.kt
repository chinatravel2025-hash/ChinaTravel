package com.travel.user.ui.verification

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.edtext.TravelVerifyEditText
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.DisplayUtils
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.KeyBoardUtil
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailAddressBinding
import com.travel.user.databinding.UserActivityVerificationCodeBinding
import com.travel.user.vm.UserVerificationCodeVM

@Route(path = ARouterPathList.USER_VERIFICATION_CODE)
class UserVerificationCodeActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityVerificationCodeBinding
    private lateinit var mVM: UserVerificationCodeVM
    private var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.user_activity_verification_code
    }

    @JvmField
    @Autowired
    var mail: String? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[UserVerificationCodeVM::class.java]
        binding = mBaseBinding as UserActivityVerificationCodeBinding
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
                   mVM.navigationLogin(mail?:"",it,this@UserVerificationCodeActivity)
                }
            }

        })
    }
    private fun registerInputListener() {
        val h240 = DisplayUtils.dp2px(this, 240f)
        binding.apply {
            softInputListener = InputMonitorHelpUtils.softInputListener(this@UserVerificationCodeActivity, object :
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