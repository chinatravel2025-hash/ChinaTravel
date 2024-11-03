package com.travel.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity

import com.example.router.ARouterPathList
import com.travel.guide.fragment.SingleChatFragment
import com.travel.home.R
import com.travel.home.databinding.HomeActivityChatBinding


@Route(path = ARouterPathList.HOME_CHAT)
class HomeChatActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityChatBinding
    override val ivBack: Int
        get() = 0
    override fun getLayoutId(): Int {
        return R.layout.home_activity_chat
    }

    @SuppressLint("CommitTransaction")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding = mBaseBinding as HomeActivityChatBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, SingleChatFragment())
            .commitNowAllowingStateLoss()
    }









}