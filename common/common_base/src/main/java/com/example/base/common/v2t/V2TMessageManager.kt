package com.example.base.common.v2t

import androidx.lifecycle.MutableLiveData
import com.aws.bean.msg.V2SendTmsgExt
import com.aws.bean.msg.V2TMsgExtCloud
import com.aws.bean.msg.V2TMsgExtLocal
import com.aws.bean.util.GsonUtil
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.event.ChatCMDMsgType
import com.example.base.msg.V2TDiyTimeVO
import com.example.base.msg.V2TMsgCustom
import com.example.base.msg.i.TUIMessageBean
import com.example.base.msg.i.TUIMessageBean.Companion.MSG_STATUS_SENDING
import com.example.base.msg.i.TUIMessageBean.Companion.MSG_STATUS_SEND_FAIL
import com.example.base.msg.i.TUIMessageBean.Companion.MSG_STATUS_SEND_SUCCESS
import com.example.base.utils.FileUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.VideoMsgUtil
import com.example.base.utils.cloudCustomDataToBean
import com.example.base.utils.localCustomDataToBean
import com.example.base.utils.setPushDesc
import com.example.base.utils.toLiteBean
import com.example.base.utils.toTUBean
import com.tencent.imsdk.v2.V2TIMCallback
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMMessageSearchParam
import com.tencent.imsdk.v2.V2TIMMessageSearchResult
import com.tencent.imsdk.v2.V2TIMOfflinePushInfo
import com.tencent.imsdk.v2.V2TIMSendCallback
import com.tencent.imsdk.v2.V2TIMValueCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


object V2TMessageManager {

    var doubleCheckerMode = MutableLiveData(false)
    var doubleCheckerName = MutableLiveData("")
    var doubleCheckerMap = MutableLiveData(LinkedHashMap<String, V2TIMMessage>())
    var msgPageMap = HashSet<String>()
    var t = 0L
    var pushData = ""

    var resourceMessages = ArrayList<V2TIMMessage>()
    var msgModuleMap = HashMap<String, ArrayList<TUIMessageBean>>()
    var replyMap = HashMap<String, Boolean>()


    fun deleteCacheMessages(
        conversationId: String,
        msgIds: List<String>?,
        isClearAll: Boolean = false
    ) {
        if (msgModuleMap.containsKey(conversationId)) {
            if (isClearAll) {
                msgModuleMap.remove(conversationId)
            } else {
                val msgs = msgModuleMap.get(conversationId)
                if (msgs != null && !msgs.isEmpty()) {
                    msgIds?.let {
                        for (msgId in msgIds) {
                            msgs.removeIf { it.id == msgId }
                            if (msgs.isEmpty()) {
                                break
                            }
                        }
                    }
                }

            }
        }
    }

    fun searchMsgFromDB(
        isGroup: Boolean,
        userId: String,
        lastMsg: V2TIMMessage?,
        callback: ((MutableList<V2TIMMessage>?, Boolean) -> Unit?)?
    ) {
        val size = 20
        val callback = object : V2TIMValueCallback<MutableList<V2TIMMessage>?> {
            override fun onSuccess(list: MutableList<V2TIMMessage>?) {
                callback?.invoke(list, (list?.size ?: 0) >= size)
            }

            override fun onError(code: Int, desc: String) {
                callback?.invoke(mutableListOf(), false)
            }
        }
        if (isGroup) {
            V2TIMManager.getMessageManager()
                .getGroupHistoryMessageList(userId, size, lastMsg, callback)
        } else {
            V2TIMManager.getMessageManager()
                .getC2CHistoryMessageList(userId, size, lastMsg, callback)
        }

    }

    fun searchMsgList(
        keywords: String,
        pageSize: Int,
        callback: ((MutableList<TUIMessageBean>) -> Unit?)?
    ) {
        searchMsgList("", keywords, pageSize, callback)
    }

    fun searchMsgList(
        conversationId: String,
        keywords: String,
        pageSize: Int,
        callback: ((MutableList<TUIMessageBean>) -> Unit?)?
    ) {

        val v2TIMMessageSearchParam = V2TIMMessageSearchParam()
        v2TIMMessageSearchParam.keywordList = arrayListOf(keywords)
        v2TIMMessageSearchParam.pageSize = pageSize
        v2TIMMessageSearchParam.pageIndex = 0
        v2TIMMessageSearchParam.setKeywordListMatchType(V2TIMMessageSearchParam.V2TIM_KEYWORD_LIST_MATCH_TYPE_OR)
        if (conversationId.isNotEmpty()) {
            v2TIMMessageSearchParam.setConversationID(conversationId)
        }
        V2TIMManager.getMessageManager().searchLocalMessages(
            v2TIMMessageSearchParam,
            object : V2TIMValueCallback<V2TIMMessageSearchResult> {
                override fun onSuccess(v2TIMMessageSearchResult: V2TIMMessageSearchResult) {
                    if (v2TIMMessageSearchResult.messageSearchResultItems.isNullOrEmpty()) {
                        callback?.invoke(mutableListOf())
                        return
                    }
                    v2TIMMessageSearchResult.messageSearchResultItems?.apply {
                        forEach {
                            callback?.invoke(it.toTUBean(false))
                            return@apply
                        }
                    }
                }

                override fun onError(code: Int, desc: String) {
                    callback?.invoke(mutableListOf())
                }
            })
    }

    fun isAddMsgTime(prvMsgTime: Long, msgTime: Long): V2TIMMessage? {
        val message = V2TIMManager.getMessageManager().createCustomMessage(
            GsonUtil.toJson(
                V2TMsgCustom(
                    ChatCMDMsgType.TYPE_MSG_SYSTEM_TIME,
                    V2TDiyTimeVO.Builder().setTime(msgTime).build()
                )
            ).toByteArray()
        )
        return if ((prvMsgTime == 0L) || msgTime - prvMsgTime > 120) {//间隔大于两分钟 添加一条时间系统消息
            message
        } else {
            null
        }
    }

    fun addMsgFail(
        isGroup: Boolean,
        isTopic: Boolean,
        conversationId: String,
        sender: String,
    ): V2TIMMessage {
        val message = V2TIMManager.getMessageManager().createCustomMessage(
            GsonUtil.toJson(
                V2TMsgCustom(ChatCMDMsgType.TYPE_MSG_SEND_FAIL, null)
            ).toByteArray()
        )
        saveMessage(message, isGroup, isTopic, conversationId, sender)
        return message
    }

    fun loadMessage(messageId: String, callback: ((List<V2TIMMessage>?) -> Unit?)?) {
        loadMessages(mutableListOf(messageId), callback)
    }

    fun loadMessages(messageIds: MutableList<String>, callback: ((List<V2TIMMessage>?) -> Unit?)?) {
        V2TIMManager.getMessageManager().findMessages(messageIds,
            object : V2TIMValueCallback<List<V2TIMMessage>> {
                override fun onSuccess(t: List<V2TIMMessage>?) {
                    callback?.invoke(t)
                }

                override fun onError(code: Int, desc: String?) {
                    callback?.invoke(null)
                }
            })
    }

    fun sendText(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        content: String,
        ext: String?,
        quoteMsgId: String?,
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = V2TIMManager.getMessageManager().createTextMessage(content)
        msg.cloudCustomData =
            GsonUtil.toJson(V2TMsgExtCloud(ours_quote_id = quoteMsgId, ours_at_id = ext))
        sendMessage(to, showName, msg, isGroup, isTopic)
    }

    fun sendText(
        to: String,
        showName: String,
        content: String,
        isGroup: Boolean = false,
        isTopic: Boolean = false,
        extObj: V2SendTmsgExt?
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = V2TIMManager.getMessageManager().createTextMessage(content)
        extObj?.let {
            msg.cloudCustomData = GsonUtil.toJson(extObj)
        }
        sendMessage(to, showName, msg, isGroup, isTopic)
    }

    fun sendAtText(
        to: String,
        showName: String,
        content: String,
        isTopic: Boolean,
        atUserList: ArrayList<String>,
        ext: String?,
        quoteMsgId: String?,
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = V2TIMManager.getMessageManager().createAtSignedGroupMessage(
            V2TIMManager.getMessageManager().createTextMessage(content),
            atUserList
        )
        msg.cloudCustomData =
            GsonUtil.toJson(V2TMsgExtCloud(ours_quote_id = quoteMsgId, ours_at_id = ext))
        sendMessage(to, showName, msg, isGroup = true, isTopic = isTopic, false, false)
    }

    fun createImgMsg(path: String): V2TIMMessage? {
        val p = FileUtils.getImageSize(path)
        val uuid = UUID.randomUUID().toString()
        val message = V2TIMManager.getMessageManager().createImageMessage(path)
        message.localCustomData = GsonUtil.toJson(V2TMsgExtLocal(uuid, 0, -1).apply {
            ours_width = p.first
            ours_height = p.second
        })
        return message
    }

    fun sendImg(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        path: String,
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = createImgMsg(path)
        if (msg != null)
            sendMessage(to, showName, msg, isGroup, isTopic)
    }

    fun createVideoMsg(path: String): V2TIMMessage? {
        return VideoMsgUtil.buildVideoMessage(path)
    }

    fun sendVideo(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        path: String,
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = createVideoMsg(path) ?: return
        sendMessage(to, showName, msg, isGroup, isTopic)
    }

    fun sendVoiceMsg(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        path: String,
        duration: Int,
        actionList: String,
        audioText: String,
    ) {
        if (to.isEmpty()) {
            return
        }
        val msg = V2TIMManager.getMessageManager().createSoundMessage(path, duration)
        msg.cloudCustomData = GsonUtil.toJson(V2TMsgExtCloud("", "", actionList, audioText))
        sendMessage(to, showName, msg, isGroup, isTopic)
    }


    fun sendDiyMsg(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        bean: V2TMsgCustom
    ) {
        sendDiyMsg(to, showName, isGroup, isTopic, bean, null)
    }

    fun sendDiyMsg(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        bean: V2TMsgCustom,
        oursUrl: String? = null
    ) {
        if (to.isEmpty()) {
            return
        }

        val msg = V2TIMManager.getMessageManager()
            .createCustomMessage(GsonUtil.toJson(bean).toByteArray())
        sendMessage(to, showName, msg, isGroup, isTopic, false, true, oursUrl)
    }

    /**
     * 发送自定消息和扩展版本
     */
    fun sendCustomMsgAndExt(
        to: String,
        showName: String,
        isGroup: Boolean,
        isTopic: Boolean,
        customStr: String,
        extStr: String?
    ) {
        if (to.isEmpty()) {
            return
        }

        val message = V2TIMManager.getMessageManager()
            .createCustomMessage(customStr.toByteArray())
        extStr?.apply {
            message.cloudCustomData = this
        }

        sendMessage(to, showName, message, isGroup, isTopic)
    }


    /**
     * 发送自定消息和扩展版本
     */
    fun sendCMDCustomMsgAndExt(
        to: String,
        isGroup: Boolean,
        customStr: String,
        extStr: String?
    ) {
        if (to.isEmpty()) {
            return
        }

        val message = V2TIMManager.getMessageManager()
            .createCustomMessage(customStr.toByteArray())
        extStr?.apply {
            message.cloudCustomData = this
        }

        val v2Callback = object : V2TIMSendCallback<V2TIMMessage> {
            override fun onSuccess(m: V2TIMMessage?) {
                deleteMessages(mutableListOf(message), null)
            }

            override fun onError(code: Int, desc: String?) {
                deleteMessages(mutableListOf(message), null)
            }

            override fun onProgress(progress: Int) {
            }
        }
        V2TIMManager.getMessageManager().sendMessage(
            message, to, if (isGroup) to else "", 0,
            true, null, v2Callback
        )
    }

    fun sendDiyCMDMsg(
        userId: String,
        isGroup: Boolean,
        toJsonBean: String,
    ) {
        val message = V2TIMManager.getMessageManager()
            .createCustomMessage(toJsonBean.toByteArray())
        val v2Callback = object : V2TIMSendCallback<V2TIMMessage> {
            override fun onSuccess(m: V2TIMMessage?) {
                deleteMessages(mutableListOf(message), null)
            }

            override fun onError(code: Int, desc: String?) {
                deleteMessages(mutableListOf(message), null)
            }

            override fun onProgress(progress: Int) {
            }
        }

        V2TIMManager.getMessageManager().sendMessage(
            message, if (isGroup) userId else null, if (isGroup) userId else "", 0,
            false, V2TIMOfflinePushInfo(), v2Callback
        )
    }

    fun saveDiyMsg(
        userId: String,
        isGroup: Boolean,
        isTopic: Boolean,
        bean: V2TMsgCustom,
        sender: String,
    ) {
        val msg = V2TIMManager.getMessageManager()
            .createCustomMessage(GsonUtil.toJson(bean).toByteArray())
        saveMessage(msg, isGroup, isTopic, userId, sender)
    }

    fun saveMessage(
        message: V2TIMMessage,
        isGroup: Boolean,
        isTopic: Boolean,
        to: String,
        sender: String,
    ) {
        val uuid = UUID.randomUUID().toString()
        val v2Callback = object : V2TIMSendCallback<V2TIMMessage> {
            override fun onSuccess(m: V2TIMMessage?) {
                m?.let {
                    if (message.localCustomData.isNullOrEmpty()) {
                        message.localCustomData = GsonUtil.toJson(V2TMsgExtLocal(uuid, 100, -1))
                    } else {
                        message.localCustomDataToBean().apply {
                            msgId = uuid
                            ours_upload_progress = 100
                            ours_download_progress = -1
                            message.localCustomData = GsonUtil.toJson(this)
                        }
                    }
                    IMCallback.sendMessage(
                        message.toTUBean(
                            isGroup,
                            isTopic,
                            MSG_STATUS_SEND_SUCCESS
                        ), true
                    )
                }
            }

            override fun onError(code: Int, desc: String?) {
                if (message.localCustomData.isNullOrEmpty()) {
                    message.localCustomData = GsonUtil.toJson(V2TMsgExtLocal(uuid, -1, -1))
                } else {
                    message.localCustomDataToBean().apply {
                        msgId = uuid
                        ours_upload_progress = 100
                        ours_download_progress = -1
                        message.localCustomData = GsonUtil.toJson(this)
                    }
                }
                IMCallback.sendMessage(
                    message.toTUBean(isGroup, isTopic, MSG_STATUS_SEND_FAIL),
                    true
                )
            }

            override fun onProgress(progress: Int) {
            }
        }
        if (isGroup) {
            V2TIMManager.getMessageManager()
                .insertGroupMessageToLocalStorage(message, to, sender, v2Callback)
        } else {
            V2TIMManager.getMessageManager()
                .insertC2CMessageToLocalStorage(message, to, sender, v2Callback)
        }
    }

    fun transmitSendMessage(
        toId: String,
        showName: String,
        message: V2TIMMessage,
        isGroup: Boolean,
        isTopic: Boolean
    ) {
        val newMessage = V2TIMManager.getMessageManager().createForwardMessage(message)
        newMessage.cloudCustomData = message.cloudCustomData
        sendMessage(
            toId,
            showName,
            newMessage,
            isGroup,
            isTopic,
            false
        )
    }

    fun sendMessage(
        toId: String,
        showName: String,
        message: V2TIMMessage,
        isGroup: Boolean,
        isTopic: Boolean
    ) {
        sendMessage(toId, showName, message, isGroup, isTopic, false)
    }

    fun sendMessage(
        toId: String,
        showName: String,
        message: V2TIMMessage,
        isGroup: Boolean,
        isTopic: Boolean,
        isRetry: Boolean,
        isGroupTarget: Boolean = true,
        oursUrl: String? = null,
    ) {
        val uuid = UUID.randomUUID().toString()
        if (message.localCustomData.isNullOrEmpty()) {
            message.localCustomData = GsonUtil.toJson(V2TMsgExtLocal(uuid, 0, -1))
        } else {
            message.localCustomDataToBean().apply {
                msgId = uuid
                ours_upload_progress = 100
                ours_download_progress = -1
                message.localCustomData = GsonUtil.toJson(this)
            }
        }
        val bean = message.toTUBean(isGroup, isTopic, MSG_STATUS_SENDING)
        val v2Callback = object : V2TIMSendCallback<V2TIMMessage> {
            override fun onSuccess(m: V2TIMMessage?) {
                m?.let {
                    message.localCustomDataToBean().apply {
                        msgId = uuid
                        ours_upload_progress = 100
                        ours_download_progress = -1
                        message.localCustomData = GsonUtil.toJson(this)
                    }
                    bean.uploadStatus = MSG_STATUS_SEND_SUCCESS
                    IMCallback.sendMessageProgress(bean)
                }
            }

            override fun onError(code: Int, desc: String?) {
                message.localCustomDataToBean().apply {
                    msgId = uuid
                    ours_upload_progress = -1
                    ours_download_progress = -1
                    message.localCustomData = GsonUtil.toJson(this)
                }
                bean.uploadStatus = MSG_STATUS_SEND_FAIL
                IMCallback.sendMessageProgress(bean)
            }

            override fun onProgress(progress: Int) {
                message.localCustomDataToBean().apply {
                    msgId = uuid
                    ours_upload_progress = progress
                    ours_download_progress = -1
                    message.localCustomData = GsonUtil.toJson(this)
                }
                bean.uploadStatus = MSG_STATUS_SENDING
                IMCallback.sendMessageProgress(bean)
            }
        }

        V2TIMManager.getMessageManager().sendMessage(
            message, null, if (isGroup) toId else "", 0,
            false, message.setPushDesc(toId, showName, isGroup, isTopic, oursUrl), v2Callback
        )

        if (isRetry) {
            IMCallback.sendMessageProgress(bean)
        } else {
            IMCallback.sendMessage(bean, true)
        }
    }

    fun clearBubbleToUser(conversationID: String, callback: ((String?) -> Unit?)?) {
        if (conversationID.isEmpty()) {
            return
        }
        V2TIMManager.getConversationManager()
            .cleanConversationUnreadMessageCount(conversationID, 0, 0,
                object : V2TIMCallback {
                    override fun onError(code: Int, desc: String) {}

                    override fun onSuccess() {
                        callback?.invoke(conversationID)
                    }
                })
        //NotificationRepository.repository.delOursAllMsgCount(1)
    }

    fun clearBubbleToUser(
        conversationID: String,
        messageId: String,
        callback: ((String?) -> Unit?)?
    ) {
        if (conversationID.isEmpty()) {
            return
        }
        V2TIMManager.getConversationManager()
            .cleanConversationUnreadMessageCount(conversationID, 0, 0,
                object : V2TIMCallback {
                    override fun onError(code: Int, desc: String) {}

                    override fun onSuccess() {
                        callback?.invoke(conversationID)
                    }
                })
        //NotificationRepository.repository.delOursAllMsgCount(1)
    }

    fun revokeMessage(conversationId: String, message: V2TIMMessage) {
        V2TIMManager.getMessageManager().revokeMessage(message, object : V2TIMCallback {
            override fun onError(code: Int, desc: String?) {

            }

            override fun onSuccess() {
                //IMCallback.revokeMessage(conversationId, message)
            }
        })
    }

    fun clearConversation(userId: String, isGroup: Boolean, callback: ((Boolean) -> Unit?)?) {
        if (isGroup) {
            V2TIMManager.getMessageManager()
                .clearGroupHistoryMessage(userId, object : V2TIMCallback {
                    override fun onError(code: Int, desc: String) {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }

                    override fun onSuccess() {
                        val conversationId =
                            CommonIMManager.convertUserIdToConversationId(true, userId ?: "")
                        msgModuleMap.remove(conversationId)
                        deleteCacheMessages(conversationId, null, true)
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }
                })
        } else {
            V2TIMManager.getMessageManager().clearC2CHistoryMessage(userId, object : V2TIMCallback {
                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }

                override fun onSuccess() {
                    val conversationId =
                        CommonIMManager.convertUserIdToConversationId(false, userId ?: "")
                    msgModuleMap.remove(conversationId)
                    deleteCacheMessages(conversationId, null, true)
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }
            })
        }
    }

    fun invite(
        to: String,
        data: String,
        callback: ((Boolean) -> Unit?)?
    ): String {
        LogUtils.i("V2TIMSignalingListener", "invite!!!")
        if (to.isEmpty()) {
            return ""
        }
        val info = V2TIMOfflinePushInfo()
        info.desc = "[Voice Call]"
        return V2TIMManager.getSignalingManager()
            .invite(to,
                data,
                true,
                info,
                30,
                object : V2TIMCallback {
                    override fun onSuccess() {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }

                    override fun onError(errorCode: Int, errorMsg: String) {

                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }
                }) ?: ""
    }

    fun accept(
        inviteID: String,
        data: String,
        callback: ((Boolean) -> Unit?)?
    ) {
        LogUtils.i("V2TIMSignalingListener", "accept--${inviteID}!!!")
        if (inviteID.isEmpty()) {
            return
        }
        V2TIMManager.getSignalingManager()
            .accept(inviteID,
                data,
                object : V2TIMCallback {
                    override fun onSuccess() {

                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }

                    override fun onError(errorCode: Int, errorMsg: String) {

                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }
                })
    }

    fun reject(
        inviteID: String,
        data: String,
        callback: ((Boolean) -> Unit?)?
    ) {
        LogUtils.i("V2TIMSignalingListener", "reject--${inviteID}!!!")
        if (inviteID.isEmpty()) {
            callback?.invoke(false)
            return
        }
        V2TIMManager.getSignalingManager()
            .reject(inviteID,
                data,
                object : V2TIMCallback {
                    override fun onSuccess() {

                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }

                    override fun onError(errorCode: Int, errorMsg: String) {

                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }
                })
    }

    fun cancel(
        inviteID: String,
        data: String,
        callback: ((Boolean) -> Unit?)?
    ) {

        LogUtils.i("V2TIMSignalingListener", "cancel--${inviteID}!!!")
        if (inviteID.isEmpty()) {
            callback?.invoke(false)
            return
        }
        V2TIMManager.getSignalingManager()
            .cancel(inviteID,
                data,
                object : V2TIMCallback {
                    override fun onSuccess() {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }

                    override fun onError(errorCode: Int, errorMsg: String) {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }
                })
    }

    fun deleteMessages(
        messages: MutableList<V2TIMMessage>,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getMessageManager()
            .deleteMessages(messages,
                object : V2TIMCallback {
                    override fun onError(code: Int, desc: String) {
                        callback?.invoke(false)
                    }

                    override fun onSuccess() {
                        callback?.invoke(true)
                    }
                })
    }

    fun deleteMessages(
        conversationId: String,
        messages: MutableList<V2TIMMessage>,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getMessageManager()
            .deleteMessages(messages,
                object : V2TIMCallback {
                    override fun onError(code: Int, desc: String) {
                        callback?.invoke(false)
                    }

                    override fun onSuccess() {

                        callback?.invoke(true)
                    }
                })
    }
}