package com.travel.user.ui.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityEmailAddressBinding
import com.travel.user.databinding.UserActivitySetNicknameBinding
import com.travel.user.vm.UserSetNickNameVM

@Route(path = ARouterPathList.USER_SET_NICKNAME)
class UserSetNickNameActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivitySetNicknameBinding
    private lateinit var mVM: UserSetNickNameVM
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



    }

    fun clearContent(){
        binding.edEmail.setText("")
    }

}