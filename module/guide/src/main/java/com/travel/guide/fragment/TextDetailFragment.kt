/*
package com.travel.guide.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.aws.module_home.R
import com.aws.module_home.databinding.FragmentTextDetailBinding
import com.aws.module_home.ui.im.emo.EmoFilter
import com.aws.module_home.ui.im.emo.EmoKeyboardUtils
import com.travel.guide.viewmodel.TextDetailViewModel
import com.example.commponent.ui.base.fragment.BasePageFragment
import com.example.router.ARouterPathList
import com.mondyxue.xrouter.constant.RouteType

@Route(path = ARouterPathList.HOME_CHAT_DETAIL, extras = RouteType.Fragment)
class TextDetailFragment : BasePageFragment() {
    private lateinit var mBinding: FragmentTextDetailBinding
    private lateinit var mViewModel: TextDetailViewModel

    */
/**
     * 文本
     *//*

    @JvmField
    @Autowired(name = "content")
    var content: String? = null

    override val rootLayout: Int
        get() = R.layout.fragment_text_detail
    override val ivBack: Int
        get() = R.id.iv_back
    override val tvTitle: Int
        get() = 0

    override fun initView(dataBinding: ViewDataBinding) {
        if ((dataBinding is FragmentTextDetailBinding)) {
            isCanOnAnimation = false
            mBinding = dataBinding
            mViewModel = ViewModelProvider(this)[TextDetailViewModel::class.java]
            mBinding.lifecycleOwner = this
            mBinding.vm = mViewModel
//            mViewModel.detailText.value = content ?: ""
            mBinding.tvContent.text = EmoFilter.getEmoSpannable(content,EmoKeyboardUtils.getFontHeight(mBinding.tvContent))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}*/
