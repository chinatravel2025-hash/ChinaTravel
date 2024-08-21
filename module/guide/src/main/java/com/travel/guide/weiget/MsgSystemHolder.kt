package com.travel.guide.weiget

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.example.base.base.User
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.customDataToBean
import com.example.base.utils.customMsgToTxt
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.ChatSystemItemBinding

//40
class MsgSystemHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
    @SuppressLint("SetTextI18n")
    override fun initData(
        position: Int, list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        list[position].let { vo ->
            if (binding is ChatSystemItemBinding) {
                vo.message?.let { msg ->
                    var isSelf = msg.isSelf
                    msg.customDataToBean()?.let { data ->
                        isSelf = data.data?.sender?.ridStr == User.ridString
                    }
                    binding.tvContent.text =
                        vo.customMsgToTxt(isSelf, false)
                }
                binding.executePendingBindings()
            }
        }
    }
}
