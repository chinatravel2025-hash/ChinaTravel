package com.travel.user.ui.preferences

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.BaseStatusBarActivity
import com.example.router.ARouterPathList
import com.travel.user.R
import com.travel.user.databinding.UserActivityPreferencesAccountBinding
import com.travel.user.databinding.UserActivityPreferencesHomeBinding
import com.travel.user.vm.UserPreferencesAccountVM

@Route(path = ARouterPathList.USER_PREFERENCES_HOME)
class UserPreferencesActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivityPreferencesHomeBinding
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.user_activity_preferences_home
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding = mBaseBinding as UserActivityPreferencesHomeBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.ac=this

    }


    fun navigationPreferences(){
        ARouter.getInstance().build(ARouterPathList.USER_PREFERENCES_ACCOUNT)
            .navigation()
    }





}