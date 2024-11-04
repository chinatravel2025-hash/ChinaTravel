package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.base.common.SkinResourceManager
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.databing.ImageViewBindAdapter
import com.example.base.databing.clickWithTrigger
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.localCustomDataToBean
import com.example.base.utils.setHidden
import com.example.base.weiget.CircularProgressView
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.R
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode

open class MsgBaseHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    var resourceMessages: ArrayList<V2TIMMessage>? = null

    interface OnChatItemClickListener {
        fun onHeadClick(userId: String)

        fun onHeadLongClick(userId: String)

        fun onContentClick(userId: String, position: Int, list: ArrayList<TUIMessageBean>)
        fun onContentLongClick(
            userId: String,
            position: Int,
            list: ArrayList<TUIMessageBean>,
            view: View
        )

        fun onErrorClick(vo: TUIMessageBean)

        fun onLoadMore(vo: TUIMessageBean)
    }

    protected var ivError: View? = null
    protected var lav: PAGImageView? = null
    open fun initData(
        position: Int,
        list: ArrayList<TUIMessageBean>,
        cl: OnChatItemClickListener?,
        resourceMessages: ArrayList<V2TIMMessage>
    ) {
        this.resourceMessages = resourceMessages
    }

    protected fun isDoubleCheckMode(): Boolean {
        return V2TMessageManager.doubleCheckerMode.value == true
    }

    protected fun isDoubleCheckerMap(position: Int, message: V2TIMMessage?): Boolean {
        if (message == null) {
            return false
        }
        if (isDoubleCheckMode()) {
            return if (!doubleCheckerContainsKey(message.msgID)) {
                V2TMessageManager.doubleCheckerMap.value?.put(message.msgID, message)
                true
            } else {
                V2TMessageManager.doubleCheckerMap.value?.remove(message.msgID)
                false
            }
        }
        return false
    }

    protected fun doubleCheckerContainsKey(id: String): Boolean {
        return V2TMessageManager.doubleCheckerMap.value?.containsKey(id) ?: false
    }

    protected fun initDoubleCheckerView(
        userId: String,
        position: Int,
        emMessage: V2TIMMessage?,
        list: ArrayList<TUIMessageBean>,
        ivAvatar: ImageView?,
        contentView: View?,
        rootView: View,
        ivCb: ImageView?,
        ivError: View?,
        clickListener: OnChatItemClickListener? = null
    ) {
        initDoubleCheckerView(
            userId, position, emMessage, list, ivAvatar, contentView, rootView, ivCb,
            null, ivError, clickListener
        )
    }

    protected fun initDoubleCheckerView(
        userId: String,
        position: Int,
        emMessage: V2TIMMessage?,
        list: ArrayList<TUIMessageBean>,
        ivAvatar: ImageView?,
        contentView: View?,
        rootView: View,
        ivCb: ImageView?,
        cvSpot: View?,
        ivError: View?,
        clickListener: OnChatItemClickListener? = null
    ) {
        this.ivError = ivError
        this.lav = lav
        ivAvatar?.clickWithTrigger {
            clickListener?.onHeadClick(userId)
        }

        ivAvatar?.setOnLongClickListener {
            clickListener?.onHeadLongClick(userId)
            true
        }

        contentView?.clickWithTrigger {
            if (isDoubleCheckMode()) {
                ivCb?.setImageResource(
                    if (isDoubleCheckerMap(
                            position,
                            emMessage
                        )
                    )
                        R.mipmap.img_tik_24_o_selected_main
                    else
                        R.mipmap.img_tik_24_o_unselected_gray
                )
                return@clickWithTrigger
            }
            cvSpot?.visibility = View.INVISIBLE
            clickListener?.onContentClick(userId, position, list)
        }
        contentView?.setOnLongClickListener {
            if (isDoubleCheckMode()) {
                return@setOnLongClickListener true
            }
            clickListener?.onContentLongClick(userId, position, list, it)
            true
        }
        rootView.clickWithTrigger {
            if (isDoubleCheckMode()) {
                contentView?.performClick()
            }

        }

        ivError?.clickWithTrigger {
            clickListener?.onErrorClick(list[position])
        }
        ivCb?.visibility =
            if (isDoubleCheckMode()) View.VISIBLE else View.GONE
        ivCb?.setImageResource(
            if (doubleCheckerContainsKey(
                    emMessage?.msgID ?: ""
                )
            ) R.mipmap.img_tik_24_o_selected_main
            else R.mipmap.img_tik_24_o_unselected_gray
        )

    }

    fun setProgress(vo: TUIMessageBean) {
        this.ivError = ivError
        this.lav = lav
        setProgress(vo, ivError, lav, null)
    }

    protected fun setProgress(
        vo: TUIMessageBean,
        ivError: View?,
        lav: PAGImageView?
    ): Int {
        return setProgress(vo, ivError, lav, null)
    }

    protected fun setProgress(
        vo: TUIMessageBean,
        ivError: View?,
        lav: PAGImageView?,
        cv: CircularProgressView?
    ): Int {
        if (vo.message == null) {
            return -1
        }
        var isSend = false
        var progress = if (isSend) -1 else 100
        vo.message?.let {
            isSend = it.isSelf == true
            progress = getProgress(vo)
            ivError?.setHidden(progress != -1)
            lav?.setHidden((cv != null || progress == -1 || progress == 100))
            cv?.setHidden((progress == -1 || progress == 100))
            cv?.progress = progress
            lav?.let {
                SkinResourceManager.setPGAAnimation(
                    lav,
                    "msg_loading",
                    true,
                    -1,
                    PAGScaleMode.Zoom
                )
            }
            if (cv != null) {
                /*lav?.setSkinPagAnimation("ours/msg_loading", repeatCount = -1, scaleMode = PAGScaleMode.Zoom)
                if (lav?.isVisible == true) {
                    if (lav?.isAnimating == true) {
                        return progress
                    }else{
                        MainScope().launch {
                            lav.playAnimation()
                        }
                    }
                } else {
                    if (lav?.isAnimating == true) {
                        lav?.cancelAnimation()
                    } else {

                    }
                }*/
            }

        }
        return progress
    }

    protected fun getProgress(vo: TUIMessageBean): Int {
        if (vo.message == null) {
            return -1
        }
        var isSend = vo.message?.isSelf == true
        var progress = if (isSend) -1 else 100
        vo.message?.localCustomDataToBean()?.let { localCustomData ->
            progress = when {
                !isSend && !vo.isDownload -> (localCustomData.ours_download_progress ?: -1)
                isSend && !vo.isUpload -> (localCustomData.ours_upload_progress ?: -1)
                isSend && vo.uploadStatus == TUIMessageBean.MSG_STATUS_SEND_FAIL -> -1
                else -> 100
            }
        }
        return progress
    }

    @SuppressLint("RestrictedApi")
    protected fun initView(
        bean: TUIMessageBean,
        tvName: TextView?,
        ivAvatar: ImageView,
    ) {
        bean.message?.let { message ->
            val isSend = message.isSelf
            tvName?.visibility =
                if (bean.isGroup && !isSend) View.VISIBLE else View.GONE
            ImageViewBindAdapter.setImageDrawable(
                ivAvatar, message.faceUrl ?: "",
                R.mipmap.ic_default_head_icon, isCircle = true
            )

            var showName = ""
            if (!TextUtils.isEmpty(message.nameCard)) {
                showName = message.nameCard
            } else if (!TextUtils.isEmpty(message.friendRemark)) {
                showName = message.friendRemark
            } else {
                showName = message.nickName
            }

            tvName?.let { TextViewBindingAdapter.setText(it, showName) }
        }
    }


}
