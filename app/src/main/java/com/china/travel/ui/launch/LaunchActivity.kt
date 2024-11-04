package com.china.travel.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.R
import com.china.travel.databinding.ActivityLaunchBinding
import com.example.base.base.BaseStatusBarActivity
import com.example.base.base.User
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
        binding.lifecycleOwner = this
        HomeRepository.homeRepository.getAppConfig()
        binding.tvTitle.postDelayed({
            if (User.isLogin){
                ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                    .navigation(this, object : NavCallback() {
                        override fun onArrival(p0: Postcard?) {
                         finish()
                        }

                    })
            }else{
                ARouter.getInstance().build(ARouterPathList.USER_REGISTER)
                    .navigation(this, object : NavCallback() {
                        override fun onArrival(p0: Postcard?) {
                            finish()
                        }

                    })
            }
        },500)
    }



}