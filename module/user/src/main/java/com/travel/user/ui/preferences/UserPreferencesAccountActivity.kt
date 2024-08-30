package com.travel.user.ui.preferences

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity
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

    }


    fun uploadPic() {


    }





}