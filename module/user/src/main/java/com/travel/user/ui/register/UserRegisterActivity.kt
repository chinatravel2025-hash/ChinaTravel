package com.travel.user.ui.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity

import com.travel.user.databinding.UserActivityRegisterBinding
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.vm.UserRegisterVM

@Route(path = ARouterPathList.USER_REGISTER)
class UserRegisterActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityRegisterBinding
    private lateinit var mVM: UserRegisterVM
    override val ivBack: Int
        get() = 0
    override fun getLayoutId(): Int {
        return R.layout.user_activity_register
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserRegisterVM::class.java]
        binding = mBaseBinding as UserActivityRegisterBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm=mVM

    }



}