package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.AppConfig
import com.example.base.utils.customDataToBean
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.databinding.ChatLeftCardItemBinding
import com.travel.guide.databinding.ChatRightCardItemBinding
import com.travel.guide.utils.GlideUtils
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode

//148dp
class MsgCardHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
    var clickListener: OnChatItemClickListener? = null
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        clickListener = cl
        if (position >= 0) {
            if (binding is ChatLeftCardItemBinding) {
                initDataView(
                    position,
                    list,
                    binding.tvName,
                    binding.tvTitle,
                    binding.tvAbout,
                    binding.includeAvatar.ivAvatar,
                    binding.ivBg,
                )
            }
            if (binding is ChatRightCardItemBinding) {
                initDataView(
                    position,
                    list,
                    null,
                    binding.tvTitle,
                    binding.tvAbout,
                    binding.includeAvatar.ivAvatar,
                    binding.ivBg,
                )
            }
        }
        binding.executePendingBindings()
    }

    @SuppressLint("SetTextI18n")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvName: TextView?,
        tvTitle: TextView,
        tvAbout: TextView,
        ivAvatar: ImageView,
        ivBg: ImageView
    ) {
        list[position].let { vo ->
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
                vo.message?.let { message ->
                    initView(vo, tvName, ivAvatar)
                    message.customDataToBean()?.run {

                        tvTitle.text = title ?: ""
                        tvAbout.text = about ?: ""
                        val isSend = message.isSelf
                        image?.forEach {
                            if(it.toString().isEmpty()){
                                GlideUtils.loadNormalPic(itemView.context, AppConfig.appBaseImg(it.toString()), ivBg)
                            }
                        }

                        initDoubleCheckerView(
                            message.sender,
                            position,
                            vo.message,
                            list,
                            ivAvatar,
                            ivBg,
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

}
