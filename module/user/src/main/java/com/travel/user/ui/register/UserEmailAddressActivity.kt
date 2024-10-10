package com.travel.user.ui.register

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.dialog.LoginDialog
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.DisplayUtils
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.KeyBoardUtil
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailAddressBinding
import com.travel.user.vm.UserEmailAddressVM


@Route(path = ARouterPathList.USER_EMAIL_ADDRESS)
class UserEmailAddressActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityEmailAddressBinding
    private lateinit var mVM: UserEmailAddressVM
    private var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    override fun getLayoutId(): Int {
        return R.layout.user_activity_email_address
    }
    override val ivBack: Int
        get() = R.id.iv_back
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[UserEmailAddressVM::class.java]
        binding = mBaseBinding as UserActivityEmailAddressBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        binding.ac = this
        registerInputListener()


    }

    fun clearContent() {
        binding.edEmail.setText("")
    }




    private fun registerInputListener() {
        val h240 = DisplayUtils.dp2px(this, 240f)
        binding.apply {
            softInputListener = InputMonitorHelpUtils.softInputListener(this@UserEmailAddressActivity, object :
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
        binding.edEmail.let { ed ->
            KeyBoardUtil.hideKeyBoard(this, ed)
        }
        removeInputListener()
    }

    fun navigationVerificationEmail(){
        mVM.emailContent.value?.let { mail->
            LoginRepository.loginRepository.fetchCaptcha(mail){
                if (it){
                    ARouter.getInstance().build(ARouterPathList.USER_VERIFICATION_CODE)
                        .withString("mail",mail)
                        .navigation()
                }else{
                    val show = LoginDialog("$mail is already registered. Sign in with this Email?") {
                        ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
                            .withString("mail",mail)
                            .navigation()
                        null
                    }
                    show.show(supportFragmentManager, "LoginDialog")


                    //邮箱已经注册了
                }
            }
        }

    }

}