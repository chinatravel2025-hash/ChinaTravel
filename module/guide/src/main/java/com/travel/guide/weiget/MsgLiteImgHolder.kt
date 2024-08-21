package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.TextViewBindingAdapter
import com.example.base.databing.ImageViewBindAdapter
import com.example.base.databing.clickWithTrigger
import com.example.base.msg.i.TUIMessageBean
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import com.travel.guide.databinding.ChatLiteLeftImgItemBinding
import com.travel.guide.databinding.ChatLiteRightImgItemBinding
import com.travel.guide.utils.V2THolderMessageUtil
import java.io.File

//height/width * 26 + 21dp
class MsgLiteImgHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
    var clickListener: OnChatItemClickListener? = null
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        clickListener = cl
        if (position >= 0) {
            list[position].let { vo ->
                if (binding is ChatLiteLeftImgItemBinding) {
                    initDataView(
                        position,
                        list,
                        binding.flPlay,
                        binding.tvContent,
                        binding.ivImg
                    )
                }
                if (binding is ChatLiteRightImgItemBinding) {
                    initDataView(
                        position,
                        list,
                        binding.flPlay,
                        binding.tvContent,
                        binding.ivImg
                    )
                }
                binding.executePendingBindings()
            }
        }
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        flPlay: View,
        tvContent: TextView,
        ivImg: ImageView
    ) {

        list[position].let { vo ->
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE ||
                vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO
            ) {
                vo.message?.let { msg ->
                    tvContent.let {
                        TextViewBindingAdapter.setText(
                            it,
                            "${if (msg.isSelf) "我" else ((if (msg.friendRemark?.isNotEmpty() == true) msg.friendRemark else msg.nickName) ?: "")}："
                        )
                    }

                    val isVideo = msg.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO
                    var thumbnailUrl =
                        if (isVideo) msg.videoElem?.snapshotPath else msg.imageElem?.path
                    flPlay.visibility =
                        if (isVideo) View.VISIBLE else View.GONE
                    val localUrl = if (isVideo) msg.videoElem?.videoPath else msg.imageElem?.path
                    if (!isVideo && thumbnailUrl.isNullOrEmpty() && msg.imageElem?.imageList?.isNotEmpty() == true) {
                        thumbnailUrl = msg.imageElem.imageList[msg.imageElem.imageList.size - 1].url
                    }
                    val file = File(localUrl ?: "")
                    var isDownloaded = file.exists()
                    ImageViewBindAdapter.setImageDrawable(
                        ivImg,
                        thumbnailUrl ?: "",
                        R.mipmap.bg_error,
                        isCircle = false
                    )
                    if (isVideo && !isDownloaded) {
                        V2THolderMessageUtil.downloadFile(vo)
                    }
                    ivImg.clickWithTrigger {
                        var isDownloaded = File(localUrl ?: "").exists()
                        if (isVideo && !isDownloaded) {
                            V2THolderMessageUtil.downloadFile(vo)
                        }
                        clickListener?.onContentClick(vo.userId, position, list)

                    }

                }

            }
        }
    }
}
