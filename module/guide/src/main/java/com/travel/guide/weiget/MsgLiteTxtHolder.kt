package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.base.msg.i.TUIMessageBean
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import com.travel.guide.databinding.ChatLiteLeftTxtItemBinding
import com.travel.guide.databinding.ChatLiteRightTxtItemBinding

//41
class MsgLiteTxtHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
    var clickListener: OnChatItemClickListener? = null
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages : ArrayList<V2TIMMessage>
    ) {
        clickListener = cl
        if (position >= 0) {
            list[position].let { vo ->
                if (binding is ChatLiteRightTxtItemBinding) {
                    initDataView(
                        position,
                        list,
                        binding.tvContent
                    )
                }
                if (binding is ChatLiteLeftTxtItemBinding) {
                    initDataView(
                        position,
                        list,
                        binding.tvContent
                    )
                }
            }
            binding.executePendingBindings()
        }
    }

    @SuppressLint("SetTextI18n")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvContent: TextView
    ) {
        list[position].let { vo ->
            if (vo.message?.status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED){
                tvContent.text = tvContent.context.getString(R.string.lite_chat_revoke)
                return
            }
            tvContent.text = tvContent.context.getString(R.string.lite_not_has)
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_TEXT) {
                vo.message?.let {msg->
                    tvContent.text = "${if (msg.isSelf) "Me" else msg.nickName}: ${msg.textElem?.text ?: ""}"
                    initDoubleCheckerView(
                        msg.sender,
                        position,
                        vo.message,
                        list,
                        null,
                        tvContent,
                        binding.root,
                        null,
                        null,
                        clickListener
                    )
                }
            }
        }
    }
}
