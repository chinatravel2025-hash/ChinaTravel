package com.travel.guide.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.aws.bean.util.GsonUtil
import com.example.base.base.App
import com.travel.guide.viewmodel.SingleChatViewModel
import com.travel.guide.controller.SingleChatFragmentController
import com.travel.guide.weiget.MsgBaseHolder
import com.travel.guide.weiget.IMChatAdapter
import com.example.base.base.User
import com.example.base.common.v2t.ICallback
import com.example.base.common.v2t.IMCallback
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.event.*
import com.example.base.msg.i.TUIMessageBean
import com.example.base.toast.ToastHelper
import com.example.base.utils.LogUtils
import com.example.base.weiget.LongTimerAndMoveButton
import com.example.base.weiget.OursLinearLayoutManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import com.travel.guide.common.LoginRepository
import com.travel.guide.databinding.FragmentSingleChatBinding
import com.travel.guide.utils.GlideUtils
import org.greenrobot.eventbus.EventBus
import kotlin.collections.ArrayList


class SingleChatFragment : Fragment(), MsgBaseHolder.OnChatItemClickListener,
    LongTimerAndMoveButton.LongListener, ICallback {

    companion object {
        const val CAMERA_REQUEST_CODE = 998
        const val VIDEO_REQUEST_CODE = 999
        const val QUICK_CAPTURE_CODE = 500

    }

    private lateinit var binding: FragmentSingleChatBinding
    private lateinit var mAdapter: IMChatAdapter
    private lateinit var mViewModel: SingleChatViewModel
    private lateinit var mController: SingleChatFragmentController

    protected lateinit var inflater: LayoutInflater

    val rootLayout: Int
        get() = R.layout.fragment_single_chat
    val ivBack: Int
        get() = R.id.iv_back

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        binding =
            DataBindingUtil.inflate(inflater, rootLayout, container, false)
        //EventBus.getDefault().register(this)
        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[SingleChatViewModel::class.java]
        binding.apply {
            binding = this@apply
            binding.vm = mViewModel
            binding.lifecycleOwner = this@SingleChatFragment
            binding.f = this@SingleChatFragment
            binding.viewBottomChat.f = this@SingleChatFragment
            binding.viewBottomChat.vm = mViewModel
        }
        IMCallback.addICallback(this)
        mViewModel.isSmallWindow.value = false
        initView()
        initList()
        return binding.root
    }

    fun login1() {
        User.oursId = "2000"
        User.hxToken =
            "eJyrVgrxCdYrSy1SslIy0jNQ0gHzM1NS80oy0zIhwgYGMPHilOzEgoLMFCUrQzOgqIklkITIpFYUZBalAsVNTU1BGiCiJZm5IDFzIxNjQzMzC2OoKZnpQGOjtE2LXQ08EkNj9IOq0irCKirDwh29Mwwqq4xNDV39s5IsTVxzQgPzil20y22VagGX4zAi"
        LoginRepository.repository.imLogin()
    }

    fun login2() {

        User.oursId = "2001"
        User.hxToken =
            "eJyrVgrxCdYrSy1SslIy0jNQ0gHzM1NS80oy0zIhwgYGhlDx4pTsxIKCzBQlK0MzAwMDE0sgCZFJrSjILEoFipuamgI1QEVLMnNBYuZGJsYmhpZGRlBTMtOBxkZUlZlmuFWallSZmpcG*xXnm5qVFuVZZCUllfv5hYfF6JvlmnnnmfhXppRl2yrVAgC2kjEo"
        LoginRepository.repository.imLogin()
    }


    private fun initView() {
        binding.rvList.let { rvList ->
            val layoutManager = OursLinearLayoutManager(requireActivity())
            layoutManager.orientation = RecyclerView.VERTICAL
            rvList.layoutManager = layoutManager
            mAdapter = IMChatAdapter(inflater, layoutManager, rvList).apply {
                callbackListener = this@SingleChatFragment
            }

            rvList.adapter = mAdapter
            mController =
                SingleChatFragmentController.newInstance(
                    this,
                    binding,
                    mViewModel,
                    mAdapter,
                    User.oursId ?: "",//todo
                    layoutManager
                )
            mController.bindEditText()
            User.hxToken
            binding.viewBottomChat.ltb.apply {
                mLongListener = this@SingleChatFragment
                initMax(60)
            }
            binding.viewBottomChat.ivOwer.apply {
                User.headPic.let {
                    GlideUtils.loadNormalPic(this.context, it, this)
                }
            }

            V2TMessageManager.doubleCheckerMode.observe(viewLifecycleOwner) { doubleCheckerMode ->
                mViewModel.isDoubleClickMode.value = doubleCheckerMode
                mController.notifyDataSetChanged()
            }
            mViewModel.loadData()

            //mController.registerInputListener()

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initList() {
        /*binding.includeMsg.rlError.visibility = View.GONE
        binding.includeMsg.llNetworkError.visibility = View.GONE
        binding.includeMsg.ivLoading.visibility = View.GONE*/
        LoginRepository.repository.addIMLoginStatusListener(imLoginListener)
        mController.initList()

    }


    fun onSend() {
        mController.onSend()
    }

    fun onSendImg(type: Int) {
        mController.onSendImg(type)
    }


    fun onChangeVideo() {
        mViewModel.isVoiceType.value = if (mViewModel.isVoiceType.value == 0) 1 else 0
    }

    fun onCancelDoubleClick() {
        mController.onCancelDoubleClick()
    }

    fun onDeleteClick() {
        mController.onDeleteClick()
    }

    fun onRelayClick() {
        mController.onRelayClick()
    }

    fun onScrollUp() {
        mController.onScrollUp()
    }

    fun onCancelQuoteClick() {
        mController.onCancelQuoteClick()
    }

    fun onSetting() {
        mController.onSetting()
    }

    fun onBack() {
        onBackPressed()
    }

    fun onXunMiChangeMode() {
        if (mViewModel.isChatType.value == 1) {
            mViewModel.isChatType.value = 2
        } else if (mViewModel.isChatType.value == 2) {
            mViewModel.isChatType.value = 1
        }
    }

    val imLoginListener = object : LoginRepository.IMLoginStatusListener {

        override fun onSuccess() {
            ToastHelper.createToastToSuccess(App.getContext(), "${User.oursId} login onSuccess")
        }

        override fun onFailed(code: Int, message: String) {
            ToastHelper.createToastToSuccess(
                App.getContext(),
                "${User.oursId} login onFailed,message:$message"
            )

        }

        override fun logining() {

        }
    }

    fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mController.onFragmentResult(requestCode, resultCode, data)
    }

    fun onBackPressed() {

    }

    override fun onDestroy() {
        super.onDestroy()
        //mController.onDestroy()
        //EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        IMCallback.removeICallback(this)
        mController.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onHeadClick(userId: String) {
        mController.onHeadClick(userId)
    }

    override fun onHeadLongClick(userId: String) {

    }

    override fun onContentClick(
        userId: String,
        position: Int,
        list: ArrayList<TUIMessageBean>
    ) {
        mController.onContentClick(userId, position, list)
    }

    override fun onProgress(progress: Int) {
        mController.onProgress(progress)
    }

    override fun onTouchStart() {
        mController.onTouchStart()
    }

    override fun onTouchError() {
        mController.onTouchError()
    }

    override fun onTouchStop(isChange: Boolean, isSand: Boolean, progress: Long) {
        mController.onTouchStop(isChange, isSand, progress)
    }

    override fun onTouchChange(isTop: Boolean) {
        mController.onTouchChange(isTop)
    }


    override fun onContentLongClick(
        userId: String,
        position: Int,
        list: ArrayList<TUIMessageBean>,
        view: View
    ) {
        mController.onContentLongClick(userId, position, list, view)
    }

    override fun onErrorClick(vo: TUIMessageBean) {
        mController.onErrorClick(vo)
    }

    override fun onLoadMore(vo: TUIMessageBean) {

    }

    override fun revokeMessage(userId: String, message: V2TIMMessage, msgBean: TUIMessageBean?) {
        mController.revokeMessage(userId, message)
    }

    override fun onMessageReceived(message: TUIMessageBean, isSender: Boolean) {
        mController.onMessageReceived(message, isSender)
    }

    override fun onMessageProgress(bean: TUIMessageBean) {
        mController.onMessageProgress(bean)
    }
}