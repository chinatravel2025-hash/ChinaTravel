package com.china.travel.ui.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.china.travel.R
import com.china.travel.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    lateinit var binding: ActivityLaunchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        binding.lifecycleOwner = this


    }


}