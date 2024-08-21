package com.travel.guide.common

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.msg.V2TCustomBean
import com.example.base.msg.V2TTextBean
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.LogUtils
import com.example.base.utils.cloudCustomDataToBean
import com.example.base.utils.toLiteBean
import com.example.base.utils.toTUBean
import com.tencent.imsdk.v2.V2TIMMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object V2TMsgHomeCacheManager {

    private val msgTime = HashMap<String, Long>()
    private val msgLoadingSet = HashSet<String>()
    var cacheLoadingDone = MutableLiveData(false)
    val msgCacheSet = HashSet<String>()

    /**
     * init缓存
     */
    fun searchAllMsgFromDB() {
        V2TMessageManager.replyMap.clear()
        /*val size = CommonRespository.repository.friendConversations.value?.size ?: 0
        CommonRespository.repository.friendConversations.value?.forEach {
            val userId = CommonIMManager.convertConversationIdToUserId(it.conversationId)
            if (!msgLoadingSet.contains(it.conversationId)) {
                msgLoadingSet.add(it.conversationId)
                V2TMessageManager.searchMsgFromDB(
                    it.isGroup,
                    userId,
                    null
                ) { list, isHasNextPage ->
                    if (list?.isNotEmpty() == true) {
                        addDataAndIndexOf(userId, list, it.isGroup, false) { models ->
                            V2TMessageManager.msgModuleMap[it.conversationId] = models
                            if (cacheLoadingDone.value != true && V2TMessageManager.msgModuleMap.size == size) {
                                LogUtils.i(
                                    "V2TMsgHomeCacheManager",
                                    "cacheLoadingDone.value = true"
                                )
                                CoroutineScope(Dispatchers.Main).launch {
                                    cacheLoadingDone.value = true
                                }
                            } else {
                            }
                            null
                        }
                    } else {
                        V2TMessageManager.msgModuleMap[it.conversationId] = arrayListOf()
                    }
                }
            }
        }*/
    }

    /**
     * 缓存
     */
    @Synchronized
    @SuppressLint("NotifyDataSetChanged")
    fun addDataAndIndexOf(
        userId: String,
        list: MutableList<V2TIMMessage>,
        isGroup: Boolean,
        isTopic: Boolean,
        callback: ((ArrayList<TUIMessageBean>) -> Unit?)?
    ) {
        var conversationId = CommonIMManager.convertUserIdToConversationId(isGroup, userId)
        var isResult = V2TMessageManager.replyMap[conversationId] ?: false
        var msgModules = ArrayList<TUIMessageBean>()
        val liteIds = mutableListOf<String>()
        val liteSelf = HashMap<String, Boolean>()
        list.forEachIndexed { index, message ->
            msgCacheSet.add(message.msgID)
            message.cloudCustomDataToBean()?.let { bean ->
                if (bean.ours_quote_id?.isNotEmpty() == true) {
                    liteIds.add(bean.ours_quote_id ?: "")
                    liteSelf[bean.ours_quote_id ?: ""] = message.isSelf
                }
            }
            if (!isResult && !message.isSelf) {
                isResult = true
            }
        }
        V2TMessageManager.replyMap[conversationId] = isResult
        V2TMessageManager.loadMessages(liteIds) { lites ->
            CoroutineScope(Dispatchers.IO).launch {
                val liteList = HashMap<String, TUIMessageBean>()
                lites?.forEach { msg ->
                    liteList[msg.msgID ?: ""] =
                        (msg.toLiteBean(isGroup, liteSelf[msg.msgID ?: ""] ?: false))
                }
                list.sortedBy { it.timestamp }.forEach { bean ->
                    msgModules.addAll(
                        notifyInserted(
                            conversationId,
                            false,
                            bean.toTUBean(isGroup, isTopic),
                            liteList,
                            msgModules
                        )
                    )
                }
                //LogUtils.e("isAddMsgTime","${conversationId}--")
                callback?.invoke(msgModules)
            }
            null
        }
    }


    fun notifyInserted(
        conversationId: String,
        isAdd: Boolean,
        v2tBean: TUIMessageBean,
        liteList: HashMap<String, TUIMessageBean>,
        msgModules: ArrayList<TUIMessageBean>
    ): ArrayList<TUIMessageBean> {
        var msgList = ArrayList<TUIMessageBean>()
        var timeBean: TUIMessageBean? = null
        var liteMessage: TUIMessageBean? = null
        val prvMsgTime = msgTime[conversationId] ?: 0L
        val m = v2tBean.message?.timestamp ?: 0
        //LogUtils.i("isAddMsgTime","${conversationId}--${v2tBean.message?.textElem?.text?:""} prvMsgTime:${prvMsgTime}!!!!msgTime - prvMsgTime:${m - prvMsgTime}")
        val timeMsg = V2TMessageManager.isAddMsgTime(
            prvMsgTime,
            m,
        )
        //LogUtils.w("isAddMsgTime","${conversationId}--${v2tBean.message?.textElem?.text?:""} m:${m}")
        msgTime[conversationId] = m
        if (timeMsg != null) {
            //LogUtils.i("isAddMsgTime","${conversationId}--${v2tBean.message?.textElem?.text?:""} timeMsg != null")
            timeBean = V2TCustomBean.Builder().setMessage(timeMsg).setGroup(v2tBean.isGroup)
                .build()
        }
        if (v2tBean.message?.status != V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {//性能考虑
            v2tBean.message?.cloudCustomDataToBean()?.let { bean ->
                if (bean.ours_quote_id?.isNotEmpty() == true) {
                    if (liteList.containsKey(bean.ours_quote_id)) {
                        liteList[bean.ours_quote_id ?: ""]?.let {
                            liteMessage = it
                        }
                    } else {
                        msgModules.add(
                            0, V2TTextBean.Builder()
                                .setMsgType(if (v2tBean.message?.isSelf == true) TUIMessageBean.TYPE_MSG_SELF_TXT_LITE else TUIMessageBean.TYPE_MSG_TA_TXT_LITE)
                                .setGroup(v2tBean.isGroup)
                                .build()
                        )
                    }
                }
            }
        }
        if (isAdd) {
            timeBean?.let { msgList.add(it) }
            msgList.add(v2tBean)
            liteMessage?.let { msgList.add(it) }
        } else {
            liteMessage?.let { msgList.add(0, it) }
            msgList.add(0, v2tBean)
            timeBean?.let { msgList.add(0, it) }
        }
        return msgList
    }
}
