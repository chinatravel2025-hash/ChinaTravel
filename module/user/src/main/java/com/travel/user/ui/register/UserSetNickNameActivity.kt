package com.travel.user.ui.register

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.DisplayUtils
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.KeyBoardUtil
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailAddressBinding
import com.travel.user.databinding.UserActivitySetNicknameBinding
import com.travel.user.vm.UserSetNickNameVM

@Route(path = ARouterPathList.USER_SET_NICKNAME)
class UserSetNickNameActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivitySetNicknameBinding
    private lateinit var mVM: UserSetNickNameVM
    private var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.user_activity_set_nickname
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserSetNickNameVM::class.java]
        binding = mBaseBinding as UserActivitySetNicknameBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm=mVM
        registerInputListener()

    }

    fun clearContent(){
        binding.edName.setText("")
    }
    private fun registerInputListener() {
        val h147 = DisplayUtils.dp2px(this, 147f)
        binding.apply {
            softInputListener = InputMonitorHelpUtils.softInputListener(this@UserSetNickNameActivity, object :
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
        binding.edName.let { ed ->
            KeyBoardUtil.hideKeyBoard(this, ed)
        }
        removeInputListener()
    }

}