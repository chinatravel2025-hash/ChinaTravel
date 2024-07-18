package com.china.travel.ui.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.R
import com.china.travel.databinding.ActivityLaunchBinding
import com.example.router.ARouterPathList

class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        binding.lifecycleOwner = this

        binding.tvTitle.postDelayed({
            ARouter.getInstance().build(ARouterPathList.APP_MAIN)
                .navigation()
        },800)


    }


}