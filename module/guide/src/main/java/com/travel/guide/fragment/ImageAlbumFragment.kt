/*
package com.travel.guide.fragment

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.aws.module_home.R
import com.alis.commponent_widget.comm.URLFragmentHandler
import com.aws.module_home.databinding.AlbumFragmentBinding
import com.aws.module_home.navigator.PageSkipController
import com.travel.guide.fragment.VideoPlayerFragment
import com.travel.guide.viewmodel.ImageAlbumViewModel
import com.example.base.base.App
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.event.VideoPageEvent
import com.example.base.utils.getReadImagePath
import com.example.base.utils.getReadVideoPath
import com.example.commponent.ui.base.fragment.BasePageFragment
import com.example.commponent.ui.fragment.ImageItemFragment
import com.example.commponent.ui.toast.ToastHelper
import com.example.commponent.ui.utils.LoadHelper
import com.example.router.ARouterPathList
import com.king.zxing.decode.DecodeImgCallback
import com.king.zxing.decode.DecodeImgThread
import com.king.zxing.decode.DecodeResult
import com.mondyxue.xrouter.constant.RouteType
import com.tencent.imsdk.v2.V2TIMMessage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

@Route(path = ARouterPathList.HOME_CHAT_ALBUM, extras = RouteType.Fragment)
class ImageAlbumFragment : BasePageFragment(), ImageItemFragment.ImageItemListener {
    private lateinit var mBinding: AlbumFragmentBinding
    private lateinit var mViewModel: ImageAlbumViewModel
    private var emMessages = ArrayList<V2TIMMessage>()

    @JvmField
    @Autowired(name = "index")
    var index: Int? = null

    */
/**
     * 对方id
     *//*

    @JvmField
    @Autowired(name = "userId")
    var userId: String? = null


    //默认长按不解析二维码.
    @JvmField
    @Autowired(name = "isDecodeQRCode")
    var isDecodeQRCode = false

    override val rootLayout: Int
        get() = R.layout.album_fragment
    override val ivBack: Int
        get() = R.id.iv_back
    override val tvTitle: Int
        get() = 0


    override fun initView(dataBinding: ViewDataBinding) {
        isCanOnAnimation = false
        if ((dataBinding is AlbumFragmentBinding)) {
            mBinding = dataBinding
            mViewModel = ViewModelProvider(this)[ImageAlbumViewModel::class.java]
            mBinding.lifecycleOwner = this
            mBinding.vm = mViewModel
            initVp()
        }
    }


    private val _imageEventListener = object : ImageItemFragment.ImageEventListener {
        override fun onShare(tag: Any?) {
            if (tag is Int) {
                if (V2TMessageManager.doubleCheckerMap.value != null && emMessages != null && emMessages!!.size > tag) {
                    val message = emMessages[tag]
                    V2TMessageManager.doubleCheckerMap.value?.clear()
                    V2TMessageManager.doubleCheckerMap.value?.put(message.msgID, message)
                    PageSkipController.navigatorShareChooseFriendPage()

                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initVp() {
        val adapter = object : FragmentStatePagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                val body = emMessages[position]
                return when (body.elemType) {
                    V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
                        ImageItemFragment.newInstance(body.imageElem?.getReadImagePath()?: "", isVideo = false, isDecodeQRCode).apply {
                            mImageItemListener = this@ImageAlbumFragment
                            imageEventListener = _imageEventListener
                            tagObject = position
                        }
                    }
                    V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {

                        VideoPlayerFragment.newInstance(
                            userId ?: "",
                            body.videoElem?.getReadVideoPath() ?: "",
                            position,
                            index == position
                        ).apply {
                            mImageItemListener = this@ImageAlbumFragment
                        }

                    }
                    else->{
                        Fragment()
                    }
                }
            }

            override fun getCount(): Int {
                return emMessages.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return ""
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return super.instantiateItem(container, position)
            }

        }


        mBinding.vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(VideoPageEvent(position))
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        emMessages = V2TMessageManager.resourceMessages
        mBinding.vpContent.adapter = adapter
        if (emMessages.size > (index ?: 0)) {
            setCurrentTabItem(index ?: 0)
        }
    }

    private fun setCurrentTabItem(position: Int) {
        mBinding.vpContent.currentItem = position
        mViewModel.indexTxt.value = "${position / (emMessages?.size ?: 0)}"
    }

    override fun onVideoClickPlayer() {

    }

    override fun onClickClose() {
        onBackPressed()
    }


    override fun onLongClick(url: String) {

        decodeQRCode(url)
    }


    private fun decodeQRCode(url: String) {

        val fragments = childFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is ImageItemFragment) {
                if (fragment.url.equals(url)) {

                    val decodeBmp = fragment.getFillBitmap()
                    if (decodeBmp == null) {
                        ToastHelper.createToastToFail(requireContext(), "QR code not recognized")
                        return
                    }

                    LoadHelper.showLoading(requireContext(), "Parsing...")
                    val decodeImgThread= DecodeImgThread(fragment.getFillBitmap(),object : DecodeImgCallback{
                        override fun onImageDecodeSuccess(result: DecodeResult) {
                            MainScope().launch {
                                result?.apply {
                                    URLFragmentHandler.handleURL(result.result)
                                    LoadHelper.dismissLoading()
                                }
                            }
                        }

                        override fun onImageDecodeFailed() {
                            MainScope().launch {
                                ToastHelper.createToastToFail(App.getApp(), "未识别到二维码")
                                LoadHelper.dismissLoading()
                            }
                        }

                    })

                    decodeImgThread.start()
                    break
                }
            }
        }

    }


}*/
