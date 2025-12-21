package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.TextViewBindingAdapter
import com.example.base.databing.clickWithTrigger
import com.example.base.msg.i.TUIMessageBean
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.ChatLiteLeftAudioItemBinding
import com.travel.guide.databinding.ChatLiteRightAudioItemBinding
import com.travel.guide.utils.V2THolderMessageUtil

//33dp
class MsgLiteAudioHolder(binding: ViewDataBinding): MsgBaseHolder(binding) {
    var clickListener: OnChatItemClickListener? = null

    @SuppressLint("SetTextI18n")
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages : ArrayList<V2TIMMessage>
    ) {
        clickListener = cl
        if (position >= 0) {
            if(binding is ChatLiteLeftAudioItemBinding){
                initDataView(
                    position,
                    list,
                    binding.tvContent,
                    binding.tvSecond
                )
            }
            if(binding is ChatLiteRightAudioItemBinding){
                initDataView(
                    position,
                    list,
                    binding.tvContent,
                    binding.tvSecond
                )
            }
            binding.executePendingBindings()
        }
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvContent: TextView,
        tvSecond: TextView
    ) {
        list[position].let { vo ->
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_SOUND) {
                vo.message?.let { msg ->
                    tvSecond.text = "${msg.soundElem?.duration}'"
                    tvContent.let {
                        TextViewBindingAdapter.setText(
                            it,
                            "${if (msg.isSelf) "Me" else ((if (msg.friendRemark?.isNotEmpty() == true) msg.friendRemark else msg.nickName) ?: "")}: "
                        )
                    }

                    tvContent.clickWithTrigger {
                        V2THolderMessageUtil.liteOpenMessage(vo.userId,msg)
                    }
                    binding.executePendingBindings()
                }

            }
        }
    }

}
