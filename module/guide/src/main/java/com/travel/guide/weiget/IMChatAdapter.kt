package com.travel.guide.weiget

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aws.bean.util.GsonUtil
import com.travel.guide.databinding.*
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_IMG
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_IMG_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_OURS_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TIME
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_TXT_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VIDEO
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VIDEO_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE_TEXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SYSTEM
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_IMG
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_IMG_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_OURS_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_TXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_TXT_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VIDEO
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VIDEO_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE_TEXT
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.msg.V2TAudioTextBean
import com.example.base.msg.i.TUIMessageBean
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_CARD
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_CARD
import com.example.base.utils.LogUtils
import com.example.base.utils.cloudCustomDataToBean
import com.example.base.utils.localCustomDataToBean
import com.example.base.utils.toLiteBean
import com.example.base.utils.toRevokedTUBean
import com.example.base.weiget.OursLinearLayoutManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.travel.guide.common.V2TMsgHomeCacheManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IMChatAdapter constructor(
    private val inflater: LayoutInflater,
    private val mOursLinearLayoutManager: OursLinearLayoutManager,
    private val recyclerView: RecyclerView
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var msgModules = ArrayList<TUIMessageBean>()
    var msgMap = HashMap<String, MsgBaseHolder>()
    var msgInSet = HashSet<String>()
    var msgPageSet = HashSet<String>()
    var msgLiteMap = HashMap<String, TUIMessageBean>()
    var callbackListener: MsgBaseHolder.OnChatItemClickListener? = null
    private var msgTime = 0L
    var isLoading = false
    var isVip = false
    var isInit = true
    var isLragging = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_MSG_SYSTEM, TYPE_MSG_TIME -> MsgSystemHolder(
                ChatSystemItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_TXT -> MsgTxtHolder(
                ChatLeftTxtItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_TXT_LITE -> MsgLiteTxtHolder(
                ChatLiteLeftTxtItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_TXT_LITE -> MsgLiteTxtHolder(
                ChatLiteRightTxtItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_IMG, TYPE_MSG_SELF_VIDEO -> MsgImgHolder(
                ChatRightImgItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_IMG, TYPE_MSG_TA_VIDEO -> MsgImgHolder(
                ChatLeftImgItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_IMG_LITE, TYPE_MSG_SELF_VIDEO_LITE -> MsgLiteImgHolder(
                ChatLiteRightImgItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_IMG_LITE, TYPE_MSG_TA_VIDEO_LITE -> MsgLiteImgHolder(
                ChatLiteLeftImgItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_VOICE -> MsgAudioHolder(
                ChatRightAudioItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_VOICE_LITE -> MsgLiteAudioHolder(
                ChatLiteRightAudioItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_VOICE_TEXT -> MsgAudioTextHolder(
                ChatRightAudioTextItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_VOICE -> MsgAudioHolder(
                ChatLeftAudioItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_VOICE_LITE -> MsgLiteAudioHolder(
                ChatLiteLeftAudioItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_VOICE_TEXT -> MsgAudioTextHolder(
                ChatLeftAudioTextItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_SELF_CARD -> MsgCardHolder(
                ChatRightCardItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_MSG_TA_CARD -> MsgCardHolder(
                ChatLeftCardItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> MsgTxtHolder(
                ChatRightTxtItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MsgBaseHolder -> {
                val msgID = msgModules[position].message?.msgID ?: ""
                msgMap[msgID] = holder
                holder.initData(position, msgModules, callbackListener, arrayListOf())
                LogUtils.i(TAG," position = ${position} , msg_time = ${msgModules[position].message?.timestamp} msgId = ${msgModules[position].message?.msgID}")
                msgInSet.remove(msgModules[position].message?.msgID)
                if (isLragging && position == 0 && !msgPageSet.contains(msgID)) {
                    LogUtils.e(TAG, "callbackListener--${msgID}")
                    callbackListener?.onLoadMore(msgModules[msgModules.size - 1])
                    msgPageSet.add(msgID)
                }
                if (position == 0) {
                    isInit = false
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return msgModules.size
    }

    override fun getItemViewType(position: Int): Int {
        return msgModules[position].msgType
    }

    @Synchronized
    fun addData(
        userId: String,
        bean: TUIMessageBean,
        isNeedScroll: Boolean = true,
        isGroup: Boolean,
        isTopic: Boolean,
    ) {
        if (userId != bean.userId) {
            return
        }
        val liteIds = mutableListOf<String>()
        val liteSelf = HashMap<String, Boolean>()
        bean.message?.let { message ->
            message.cloudCustomDataToBean()?.let { bean ->
                if (bean.ours_quote_id?.isNotEmpty() == true) {
                    liteIds.add(bean.ours_quote_id ?: "")
                    liteSelf[bean.ours_quote_id ?: ""] = message.isSelf
                }
            }
        }
        V2TMessageManager.loadMessages(liteIds) { lites ->
            CoroutineScope(Dispatchers.IO).launch {
                val liteList = HashMap<String, TUIMessageBean>()
                lites?.forEach { msg ->
                    liteList[msg.msgID ?: ""] =
                        (msg.toLiteBean(isGroup, liteSelf[msg.msgID ?: ""] ?: false))
                }
                CoroutineScope(Dispatchers.Main).launch {
                    msgModules.addAll(V2TMsgHomeCacheManager.notifyInserted(userId,true, bean, liteList,msgModules))
                    notifyItemInserted(itemCount)
                    notifyItemRangeChanged(itemCount, msgModules.size + 1)
                    recyclerView.scrollToPosition(itemCount - 1)
                    if (isNeedScroll) {
                        recyclerView.postDelayed({
                            recyclerView.scrollToPosition(itemCount - 1)
                        }, 200)
                    }
                }

            }
            null
        }
    }

    @Synchronized
    fun updateProgress(message: V2TIMMessage) {
        if (isLoading) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            msgModules.forEachIndexed { index, vo ->
                var msgID = vo.message?.msgID ?: ""
                if (msgID.isEmpty()) {
                    msgID = vo.message?.localCustomDataToBean()?.msgId ?: ""
                }
                if (msgID == message.msgID) {
                    msgModules[index].message = message
                    CoroutineScope(Dispatchers.Main).launch {
                        /*val viewHolder = rv.findViewHolderForAdapterPosition(index)
                        if (viewHolder is MsgBaseHolder) {
                            viewHolder.setProgress(msgModules[index])
                        }*/
                        notifyItemChanged(index)
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Synchronized
    fun revokeMessage(message: V2TIMMessage, isGroup: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            msgModules.apply {
                forEachIndexed { index, vo ->
                    if (vo.message?.msgID == message.msgID) {
                        CoroutineScope(Dispatchers.Main).launch {
//                            msgModules.removeAt(index)
//                            notifyItemRemoved(index)
                            val _itemCount:() -> Int = {
                                 if( (itemCount - 1) >= 0)  itemCount - 1 else 0
                            }

                            msgModules.set(index,message.toRevokedTUBean(isGroup))
                            notifyItemChanged(index)
                            //notifyItemRangeChanged(index, _itemCount.invoke())
                            if (msgModules.size > index + 1 && message.cloudCustomDataToBean()?.ours_quote_id?.isNotEmpty() == true) {
                                msgModules.removeAt(index + 1)
                                notifyItemRemoved(index + 1)
                                notifyItemRangeChanged(index, _itemCount.invoke() )
                            }
//                            msgModules.add(message.toRevokedTUBean(isGroup))
//                            notifyItemInserted(itemCount)
//                            notifyItemRangeChanged(index, _itemCount.invoke() )
                        }

                        return@apply
                    }
                }
            }
        }
    }



    @Synchronized
    fun oursVideoFinish(msgId: String, filePath: String, timeStamp: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            msgModules.forEachIndexed { index, vo ->
                if (vo.message?.msgID == msgId) {
                    msgModules[index].message?.let { message ->
                        val bean = message.localCustomDataToBean()
                        bean.ours_file_time = timeStamp
                        bean.ours_file_path = filePath
                        message.localCustomData = GsonUtil.toJson(bean)
                        msgModules[index].message = message
                        CoroutineScope(Dispatchers.Main).launch {
                            /*val viewHolder = rv.findViewHolderForAdapterPosition(index)
                            if (viewHolder is MsgBaseHolder) {
                                viewHolder.setProgress(msgModules[index])
                            }*/
                            notifyItemChanged(index)
                        }
                    }
                }
            }
        }
    }

    @Synchronized
    fun audioToText(position: Int, isSender: Boolean, msg: V2TIMMessage) {
        if (msgModules[position].isCheck) {
            return
        }
        msgModules[position].isCheck = true
        CoroutineScope(Dispatchers.Main).launch {
            msgModules.add(
                position + 1,
                V2TAudioTextBean.Builder()
                    .setMessage(msg)
                    .setMsgType(if (isSender) TYPE_MSG_SELF_VOICE_TEXT else TYPE_MSG_TA_VOICE_TEXT)
                    .build()
            )
            notifyItemInserted(position + 1)
            notifyItemRangeChanged(position + 1, msgModules.size)
            recyclerView.scrollToPosition(position + 1)
        }
    }

    @Synchronized
    fun removeDataAt(userId: String, position: Int, isDeleteDB: Boolean = false) {
        msgModules[position].message?.let {
            var timeBean: TUIMessageBean? = null
            var quoteBean: TUIMessageBean? = null
            if (position > 0 && msgModules[position - 1].msgType == TYPE_MSG_TIME) {
                timeBean = msgModules[position - 1]
                //notifyItemRemoved(position - 1)
            }

            if (msgModules.size > position + 1 && it.cloudCustomDataToBean()?.ours_quote_id?.isNotEmpty() == true
                && msgModules.size > position + 1
            ) {
                quoteBean = msgModules[position + 1]
                //notifyItemRemoved(position)
            }
            msgModules.removeAt(position)
            timeBean?.let { msgModules.remove(it) }
            quoteBean?.let { msgModules.remove(it) }

            notifyDataSetChanged()
            if (isDeleteDB) {
                V2TMessageManager.deleteMessages(mutableListOf(it), null)
            }
        }

    }

    @Synchronized
    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        msgModules.clear()
        notifyDataSetChanged()
    }

    private val TAG = this.javaClass.simpleName
    fun getVisibleItemMap(): HashMap<String, TUIMessageBean> {
        val msgModulesMap = HashMap<String, TUIMessageBean>()
        //列表可见第一个数据
        var realFirstPosition = mOursLinearLayoutManager.findFirstVisibleItemPosition()
        //列表可见最后一个数据
        val realLastPosition = mOursLinearLayoutManager.findLastVisibleItemPosition()

        if (realFirstPosition >= 0 && realLastPosition >= 0) {
            for (i in realFirstPosition..realLastPosition) { //遍历可见数据
                msgModules[i].let { itemVO ->
                    //记录当前可见数据
                    msgModulesMap.put(itemVO.message?.msgID ?: "", itemVO)
                }
            }
        }
        LogUtils.i(
            TAG,
            "firstPosition = ${realFirstPosition} realLastPosition = ${realLastPosition}"
        )
        return msgModulesMap
    }

}