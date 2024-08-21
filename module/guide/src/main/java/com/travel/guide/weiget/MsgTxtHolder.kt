package com.travel.guide.weiget

import android.text.TextUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.ViewDataBinding
import com.example.base.base.User
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.LogUtils
import com.example.base.utils.cloudCustomDataToBean
import com.example.base.utils.setHidden
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.ChatLeftTxtItemBinding
import com.travel.guide.databinding.ChatRightTxtItemBinding
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode
//count/200 *14 + 66
class MsgTxtHolder(binding: ViewDataBinding): MsgBaseHolder(binding) {
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
                if(binding is ChatLeftTxtItemBinding){
                    initDataView(
                        position,
                        list,
                        binding.tvName,
                        binding.tvContent,
                        binding.flContent,
                        binding.ivEmoGif,
                        binding.llEmoGif,
                        binding.includeAvatar.ivAvatar,
                        binding.ivCb,
                        binding.ivError,
                        binding.lavLoading,
                        binding.includeAvatar.vipTag
                    )
                }
                if(binding is ChatRightTxtItemBinding){
                    initDataView(
                        position,
                        list,
                        null,
                        binding.tvContent,
                        binding.flContent,
                        binding.ivEmoGif,
                        binding.llEmoGif,
                        binding.includeAvatar.ivAvatar,
                        binding.ivCb,
                        binding.ivError,
                        binding.lavLoading,
                        binding.includeAvatar.vipTag
                    )
                }
            }
            binding.executePendingBindings()
        }
    }

    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvName: TextView?,
        tvContent: TextView,
        flContent: FrameLayout,
        ivEmoGif: ImageView,
        llEmoGif: LinearLayoutCompat,
        ivAvatar: ImageView,
        ivCb: ImageView,
        ivError: ImageView,
        lav: PAGImageView,
        vipTag: PAGImageView,
    ) {
        list[position].let { vo ->
            setProgress(vo,ivError,lav)
            initView(vo,tvName,ivAvatar)
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_TEXT) {
                vo.message?.let {msg->

                    val content = msg.textElem?.text ?: ""

                    tvContent.text = content
                    val isSend = msg.isSelf
                    initDoubleCheckerView(
                        msg.sender?: "",
                        position,
                        vo.message,
                        list,
                        ivAvatar,
                        flContent,
                        binding.root,
                        ivCb,
                        ivError,
                        clickListener
                    )
                }
            }
        }
    }
}
