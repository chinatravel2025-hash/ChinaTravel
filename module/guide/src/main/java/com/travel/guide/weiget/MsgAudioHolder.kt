package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.base.databing.ImageViewBindAdapter
import com.example.base.databing.clickWithTrigger
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.cloudCustomDataToBean
import com.example.base.utils.isDownload
import com.example.base.utils.localCustomDataToBean
import com.example.base.utils.setHidden
import com.example.base.utils.setLocalUnread
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import com.travel.guide.databinding.ChatLeftAudioItemBinding
import com.travel.guide.databinding.ChatRightAudioItemBinding
import com.travel.guide.utils.V2THolderMessageUtil
import org.libpag.PAGImageView

//68dp
class MsgAudioHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
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
            if (binding is ChatLeftAudioItemBinding) {
                initDataView(
                    position,
                    list,
                    binding.tvName,
                    binding.tvTime,
                    binding.includeAvatar.ivAvatar,
                    null,
                    binding.ivCb,
                    binding.llContent,
                    binding.cvSpot,
                    binding.ivError,
                    binding.lavLoading,
                    binding.includeAvatar.vipTag,
                    null
                )
            }
            if (binding is ChatRightAudioItemBinding) {
                initDataView(
                    position,
                    list,
                    null,
                    binding.tvTime,
                    binding.includeAvatar.ivAvatar,
                    null,
                    binding.ivCb,
                    binding.llContent,
                    binding.cvSpot,
                    binding.ivError,
                    binding.lavLoading,
                    binding.includeAvatar.vipTag,
                    null
                )
            }
            binding.executePendingBindings()
        }
    }
    @SuppressLint("SetTextI18n")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvName: TextView?,
        tvTime: TextView,
        ivAvatar: ImageView,
        ivUserAvatar: ImageView?,
        ivCb: ImageView,
        llContent: LinearLayout,
        cvSpot: View,
        ivError: ImageView,
        lav: PAGImageView,
        vipTag: PAGImageView,
        ivToVideo: ImageView? = null
    ) {
        list[position].let { vo ->
            val isSend = vo.message?.isSelf == true
            setProgress(vo, ivError, lav).let {
                if (it in 1..99) {
                    return
                }
            }
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_SOUND) {
                vo.message?.let { message ->
                    initView(vo, tvName, ivAvatar)
                    tvTime.text = "${message.soundElem?.duration}'"
                    //opbv.setSecond(message.soundElem?.duration ?: 1)
                    val localBean = message.localCustomDataToBean()
                    ivToVideo?.setHidden(true)
                    cvSpot.setHidden(true)
                    localBean.ours_unread?.let {ours_unread->
                        cvSpot.setHidden(!(ours_unread && !isSend))
                    }
                    localBean.ours_file_path?.let {ours_file_path->
                        ivToVideo?.setHidden(
                            !(ours_file_path.isNotEmpty() &&
                                    System.currentTimeMillis() / 1000 - (localBean.ours_file_time
                                ?: 0) < 43200)
                        )
                    }
                    if (!message.soundElem.isDownload()) {
                        V2THolderMessageUtil.downloadFile(vo)
                    }
                    ivUserAvatar?.let {
                        ImageViewBindAdapter.setImageDrawable(
                            it,
                            message.faceUrl ?: "",
                            isCircle = true
                        )
                    }

                    initDoubleCheckerView(
                        message.sender,
                        position,
                        vo.message,
                        list,
                        ivAvatar,
                        llContent,
                        binding.root,
                        ivCb,
                        cvSpot,
                        ivError,
                        clickListener
                    )
                    llContent.clickWithTrigger {
                        if (isDoubleCheckMode()) {
                            if (isDoubleCheckerMap(
                                    position,
                                    vo.message
                                )
                            ) {
                                ivCb.setImageResource(R.mipmap.img_tik_24_o_selected_main)
                            } else {
                                ivCb.setImageResource(R.mipmap.img_tik_24_o_unselected_gray)
                            }

                            return@clickWithTrigger
                        }
                        if (!message.soundElem.isDownload()) {
                            V2THolderMessageUtil.downloadFile(vo)
                        }
                        cvSpot.visibility = View.INVISIBLE
                        message.setLocalUnread(false)
                        /*V2TMessageManager.clearBubbleToUser(
                            vo.conversationId,
                            message.msgID
                        )*/
                        if (message.cloudCustomDataToBean()?.ours_voice_ext?.isNotEmpty() == true) {
                            clickListener?.onContentClick(message.sender, position, list)
                            return@clickWithTrigger
                        }
                        //AIUIRepository.repository.startPcmPlayer(message,opbv)
                    }
                    binding.executePendingBindings()
                }

            }
        }
    }


}
