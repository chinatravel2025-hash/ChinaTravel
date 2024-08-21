package com.travel.guide.weiget

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.base.base.App
import com.example.base.databing.ImageViewBindAdapter
import com.example.base.databing.clickWithTrigger
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.DateUtils
import com.example.base.utils.DisplayUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.ScreenUtils
import com.example.base.utils.getImage
import com.example.base.utils.getReadImagePath
import com.example.base.utils.getReadSnapshotPath
import com.example.base.utils.getReadVideoPath
import com.example.base.utils.isHidden
import com.example.base.utils.localCustomDataToBean
import com.example.base.utils.setHidden
import com.example.base.weiget.CircularProgressView
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import com.travel.guide.databinding.ChatLeftImgItemBinding
import com.travel.guide.databinding.ChatRightImgItemBinding
import com.travel.guide.utils.V2THolderMessageUtil
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode
import java.io.File

// height/width * 100 + 14 dp
class MsgImgHolder(binding: ViewDataBinding) : MsgBaseHolder(binding) {
    var clickListener: OnChatItemClickListener? = null
    val TAG: String = MsgImgHolder::class.java.simpleName
    override fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        super.initData(position, list, cl, resourceMessages)
        clickListener = cl
        if (position >= 0) {
            if (binding is ChatLeftImgItemBinding) {
                initDataView(
                    position,
                    list,
                    binding.tvName,
                    binding.flPlay,
                    binding.includeAvatar.ivAvatar,
                    binding.ivCb,
                    binding.ivImg,
                    binding.cvImg,
                    binding.pbDownload,
                    binding.ivError,
                    binding.lavLoading,
                    binding.includeAvatar.vipTag,
                    binding.flImg,
                    binding.tvTime,
                )
            }
            if (binding is ChatRightImgItemBinding) {
                initDataView(
                    position,
                    list,
                    null,
                    binding.flPlay,
                    binding.includeAvatar.ivAvatar,
                    binding.ivCb,
                    binding.ivImg,
                    binding.cvImg,
                    binding.pbDownload,
                    binding.ivError,
                    binding.lavLoading,
                    binding.includeAvatar.vipTag,
                    binding.flImg,
                    binding.tvTime,
                )
            }
            binding.executePendingBindings()
        }
    }

    fun initDataView(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        tvName: TextView?,
        flPlay: View,
        ivAvatar: ImageView,
        ivCb: ImageView,
        ivImg: ImageView,
        cvImg: View,
        pb: CircularProgressView,
        ivError: View,
        lav: PAGImageView,
        vipTag: PAGImageView,
        flImg: View,
        tvTime: TextView,
    ) {
        list[position].let { vo ->
            val isSend = vo.message?.isSelf == true
            setProgress(vo, ivError, lav, pb).also {
                if (it in 1..99) {
                    return
                }
            }
            if (vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE ||
                vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO
            ) {
                vo.message?.let { msg ->
                    initView(vo, tvName, ivAvatar)
                    val isVideo = vo.message?.elemType == V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO
                    cvImg.setHidden(pb.isHidden())
                    flPlay.setHidden(!(isVideo && pb.isHidden()))
                    tvTime.text = if (isVideo) DateUtils.second2Time(
                        (msg.videoElem?.duration ?: 0).toLong()
                    ) else ""
                    val localUrl =
                        if (isVideo) msg.videoElem?.getReadVideoPath() else msg.imageElem?.getReadImagePath()
                    val thumbnailUrl =
                        if (isVideo) msg.videoElem?.getReadSnapshotPath() else msg.imageElem?.getReadImagePath()
                    val d = DisplayUtils.dp2px(App.getContext(), 100f)
                    var width = d
                    var height = d

                    msg.localCustomDataToBean().let { local ->
                        val w = (if ((local.ours_width
                                ?: 0) > 0
                        ) local.ours_width else ((if (isVideo) msg.videoElem?.snapshotWidth else msg.imageElem?.getImage()?.width)))
                            ?: 100
                        val h = (if ((local.ours_height
                                ?: 0) > 0
                        ) local.ours_height else ((if (isVideo) msg.videoElem?.snapshotHeight else msg.imageElem?.getImage()?.height)))
                            ?: 100
                        if (w > 0 && h > 0) {
                            if (h > w) {
                                val base = h.toFloat() / w.toFloat()
                                height = (base * height).toInt()
                                width = (height / base).toInt()
                            } else {
                                val base = w.toFloat() / h.toFloat()
                                width = (base * width).toInt()
                                if (width >= ScreenUtils.getScreenWidth(ivImg.context) - d) {
                                    width = ScreenUtils.getScreenWidth(ivImg.context) - d
                                }
                                height = (width / base).toInt()
                            }
                        }
                    }
                    var isUpdate = false
                    val layoutParams = flImg.layoutParams
                    LogUtils.i(
                        TAG,
                        "width = ${width} ; layoutParams.width = ${layoutParams.width} ; height = height "
                    )
                    if (layoutParams.width != width) {
                        layoutParams.width = width
                        isUpdate = true
                    }
                    if (layoutParams.height != height) {
                        layoutParams.height = height
                        isUpdate = true
                    }
                    if (isUpdate) {
                        flImg.layoutParams = layoutParams
                    }
                    val file = File(localUrl ?: "")
                    var isDownloaded = file.exists()
                    ImageViewBindAdapter.setImageDrawable(
                        ivImg,
                        thumbnailUrl ?: "",
                        R.mipmap.bg_msg,
                        R.mipmap.bg_error,
                        isCircle = false
                    )
                    initDoubleCheckerView(
                        msg.sender,
                        position,
                        vo.message,
                        list,
                        ivAvatar,
                        ivImg,
                        binding.root,
                        ivCb,
                        ivError,
                        clickListener
                    )
                    if (isVideo && !isDownloaded) {
                        V2THolderMessageUtil.downloadFile(vo)
                    }
                    ivImg.clickWithTrigger {
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
                        var isDownloaded = File(localUrl ?: "").exists()
                        if (isVideo && !isDownloaded) {
                            V2THolderMessageUtil.downloadFile(vo)
                        }
                        clickListener?.onContentClick(msg.sender, position, list)

                    }
                }

            }
        }
    }

}
