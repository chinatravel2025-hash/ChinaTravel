package com.travel.guide.controller


import android.Manifest
import com.travel.guide.weiget.IMChatAdapter
import com.example.base.base.User
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.msg.i.TUIMessageBean
import com.example.base.weiget.OursLinearLayoutManager
import com.permissionx.guolindev.PermissionX
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.FragmentSingleChatBinding
import com.travel.guide.fragment.SingleChatFragment
import com.travel.guide.viewmodel.SingleChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

/**
 * 单聊界面Controller
 * li yi
 */
class SingleChatFragmentController constructor(
    val fragment: SingleChatFragment,
    val binding: FragmentSingleChatBinding,
    val viewModel: SingleChatViewModel,
    val adapter: IMChatAdapter,
    val userId: String?,
    val layoutManager: OursLinearLayoutManager
) : BaseChatFragmentController(fragment, viewModel, adapter, userId, layoutManager) {
    /**
     * 所需的所有权限信息
     */

    companion object {
        @JvmStatic
        fun newInstance(
            activity: SingleChatFragment,
            binding: FragmentSingleChatBinding,
            mViewModel: SingleChatViewModel,
            adapter: IMChatAdapter,
            userId: String?,
            layoutManager: OursLinearLayoutManager
        ) = SingleChatFragmentController(
            activity,
            binding,
            mViewModel,
            adapter,
            userId,
            layoutManager
        )
    }


    init {
        mFragmentWeakReference = WeakReference(fragment)
        mId = userId ?: ""
        mConversationId = "c2c_${mId}"
        mViewModel = viewModel
        mAdapter = adapter
        mLayoutManager = layoutManager
        rvList = binding.rvList

        flRoot = binding.viewBottomChat.flRoot
        ltb = binding.viewBottomChat.ltb
        etContent = binding.viewBottomChat.etContent
        ltb.isDown =
            PermissionX.isGranted(fragment.requireActivity(), Manifest.permission.RECORD_AUDIO)

        V2TMessageManager.msgPageMap.add(mId)


    }

    override fun onHeadClick(userId: String) {

    }

    override fun onDestroy() {
        if(userId == User.ridString){
            V2TMessageManager.clearBubbleToUser(mConversationId,null)
        }
        super.onDestroy()
        V2TMessageManager.msgPageMap.remove(mId)
    }

    fun revokeMessage(mUserId: String, message: V2TIMMessage) {
        if (userId == mUserId) {
            mAdapter.revokeMessage(message, false)
        }
    }

    fun onMessageReceived(messages: TUIMessageBean, isSender: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            mAdapter.addData(
                userId ?: "",
                messages,
                isSender || !rvList.isActionDownMove,
                false,
                false
            )
        }
    }

    fun onMessageProgress(bean: TUIMessageBean) {
        bean.message?.let { message ->
            if (userId == bean.userId) {
                mAdapter.updateProgress(message)
            }
        }
    }
}