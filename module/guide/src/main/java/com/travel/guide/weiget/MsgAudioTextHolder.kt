package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.cloudCustomDataToBean
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.ChatLeftAudioTextItemBinding
import com.travel.guide.databinding.ChatRightAudioTextItemBinding

//((200/(count*15)) * 15)+16 dp
class MsgAudioTextHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {

    var clickListener: OnChatItemClickListener? = null
    @SuppressLint("SetTextI18n")
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        super.initData(position, list, cl, resourceMessages)
        clickListener = cl
        if (position >= 0) {
            if (binding is ChatLeftAudioTextItemBinding) {
                initDataView(
                    position,
                    list,
                    binding.tvText

                )
            }
            if (binding is ChatRightAudioTextItemBinding) {
                initDataView(
                    position,
                    list,
                    binding.tvText
                )
            }
            binding.executePendingBindings()
        }
    }

    @SuppressLint("SetTextI18n")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvText: TextView?,
    ) {
        list[position].let { vo ->
            tvText?.text = vo.message?.cloudCustomDataToBean()?.ours_voice_text?:""
            binding.executePendingBindings()
        }
    }


}
