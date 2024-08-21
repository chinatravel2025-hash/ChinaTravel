package com.example.base.common.v2t

import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.LogUtils
import com.example.base.utils.toRevokedTUBean
import com.tencent.imsdk.v2.V2TIMGroupChangeInfo
import com.tencent.imsdk.v2.V2TIMGroupListener
import com.tencent.imsdk.v2.V2TIMGroupMemberChangeInfo
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage

interface ICallback {
    fun onMessageReceived(messages: TUIMessageBean, isSender: Boolean = false)
    fun onMessageProgress(bean: TUIMessageBean) {}

    fun openMessage(conversationId: String, message: V2TIMMessage) {}
    fun revokeMessage(userId: String, message: V2TIMMessage, msgBean: TUIMessageBean?) {}
}

// 聚合埋点类
class IMCallback {

    companion object {

        val TAG = "IMCallback"

        @JvmStatic
        private val allV2TCallback: MutableList<ICallback> = mutableListOf()

        @JvmStatic
        private val imGroupCallbacks: MutableList<IMGroupCallback> = mutableListOf()

        private var groupListener: V2TIMGroupListener? = null

        @JvmStatic
        fun addICallback(
            callback: ICallback
        ) {
            allV2TCallback.add(callback)
        }

        @JvmStatic
        fun addIMGroupCallback(
            callback: IMGroupCallback
        ) {
            imGroupCallbacks.add(callback)
            if (groupListener == null) {
                groupListener = object : V2TIMGroupListener() {
                    override fun onMemberEnter(
                        groupID: String,
                        memberList: List<V2TIMGroupMemberInfo>
                    ) {
                        imGroupCallbacks.forEach { it.onMemberEnter(groupID, memberList) }
                    }

                    override fun onMemberLeave(groupID: String, member: V2TIMGroupMemberInfo) {
                        imGroupCallbacks.forEach { it.onMemberLeave(groupID, member) }
                    }

                    override fun onMemberInvited(
                        groupID: String,
                        opUser: V2TIMGroupMemberInfo,
                        memberList: List<V2TIMGroupMemberInfo>
                    ) {
                        imGroupCallbacks.forEach { it.onMemberInvited(groupID, opUser, memberList) }
                    }

                    override fun onMemberKicked(
                        groupID: String,
                        opUser: V2TIMGroupMemberInfo,
                        memberList: List<V2TIMGroupMemberInfo>
                    ) {
                        imGroupCallbacks.forEach { it.onMemberKicked(groupID, opUser, memberList) }
                    }

                    override fun onMemberInfoChanged(
                        groupID: String,
                        v2TIMGroupMemberChangeInfoList: List<V2TIMGroupMemberChangeInfo>
                    ) {
                    }

                    override fun onGroupCreated(groupID: String) {
                    }

                    override fun onGroupDismissed(groupID: String, opUser: V2TIMGroupMemberInfo) {
                        imGroupCallbacks.forEach { it.onGroupDismissed(groupID, opUser) }
                    }

                    override fun onGroupRecycled(groupID: String, opUser: V2TIMGroupMemberInfo) {
                        imGroupCallbacks.forEach { it.onGroupDismissed(groupID, opUser) }
                    }

                    override fun onGroupInfoChanged(
                        groupID: String,
                        changeInfos: List<V2TIMGroupChangeInfo>
                    ) {
                        imGroupCallbacks.forEach { it.onGroupInfoChanged(groupID) }
                    }

                    override fun onReceiveJoinApplication(
                        groupID: String,
                        member: V2TIMGroupMemberInfo,
                        opReason: String
                    ) {
                        imGroupCallbacks.forEach {
                            it.onReceiveJoinApplication(
                                groupID,
                                member,
                                opReason
                            )
                        }
                    }

                    override fun onApplicationProcessed(
                        groupID: String,
                        opUser: V2TIMGroupMemberInfo,
                        isAgreeJoin: Boolean,
                        opReason: String
                    ) {
                        imGroupCallbacks.forEach {
                            it.onApplicationProcessed(
                                groupID,
                                opUser,
                                isAgreeJoin,
                                opReason
                            )
                        }
                    }

                    override fun onGrantAdministrator(
                        groupID: String,
                        opUser: V2TIMGroupMemberInfo,
                        memberList: List<V2TIMGroupMemberInfo>
                    ) {
                        imGroupCallbacks.forEach {
                            it.onGrantAdministrator(
                                groupID,
                                opUser,
                                memberList
                            )
                        }
                    }

                    override fun onRevokeAdministrator(
                        groupID: String,
                        opUser: V2TIMGroupMemberInfo,
                        memberList: List<V2TIMGroupMemberInfo>
                    ) {
                        imGroupCallbacks.forEach {
                            it.onRevokeAdministrator(
                                groupID,
                                opUser,
                                memberList
                            )
                        }
                    }

                    override fun onQuitFromGroup(groupID: String) {
                        imGroupCallbacks.forEach { it.onQuitFromGroup(groupID) }
                    }

                    override fun onReceiveRESTCustomData(groupID: String, customData: ByteArray) {
                        super.onReceiveRESTCustomData(groupID, customData)
                    }

                    override fun onGroupAttributeChanged(
                        groupID: String,
                        groupAttributeMap: Map<String, String>
                    ) {
                        imGroupCallbacks.forEach { it.onGroupInfoChanged(groupID) }
                    }
                }
                V2TIMManager.getInstance().addGroupListener(groupListener)
            }
        }

        @JvmStatic
        fun removeIMGroupCallback(
            callback: IMGroupCallback
        ) {
            imGroupCallbacks.remove(callback)
        }

        @JvmStatic
        fun removeICallback(
            callback: ICallback
        ) {
            allV2TCallback.remove(callback)
        }

        @JvmStatic
        fun sendMessage(
            message: TUIMessageBean, isSender: Boolean = false
        ) {
            val conversationId = message.conversationId
            if (V2TMessageManager.msgModuleMap.containsKey(conversationId)) {
                V2TMessageManager.msgModuleMap[conversationId]?.add(message)
            } else {
                V2TMessageManager.msgModuleMap[conversationId] = arrayListOf(message)
            }
            if(message.message?.isSelf == false){
                V2TMessageManager.replyMap[conversationId] = true
            }
            allV2TCallback.forEach { it.onMessageReceived(message, isSender) }
        }

        @JvmStatic
        fun sendMessageProgress(
            bean: TUIMessageBean
        ) {
            allV2TCallback.forEach {
                it.onMessageProgress(bean)
            }
        }

        @JvmStatic
        fun openMessage(conversationId: String, message: V2TIMMessage) {
            allV2TCallback.forEach { it.openMessage(conversationId, message) }
        }

        @JvmStatic
        fun revokeMessage(userId: String, message: V2TIMMessage,msgBean: TUIMessageBean) {
            val conversationId = msgBean.conversationId
            LogUtils.i(TAG,"IMCallback 需要撤回的消息ID = ${msgBean.message?.msgID}")
            if (V2TMessageManager.msgModuleMap.containsKey(conversationId)) {

                //撤回消息，那么一定存在，否则是不存在的.
                V2TMessageManager.msgModuleMap[conversationId]?.let {
                    it.forEachIndexed{
                        index, tuiMessageBean ->
                        if(tuiMessageBean.message?.msgID != null && message?.msgID != null){
                            if(tuiMessageBean.message?.msgID == message?.msgID){
                                it.set(index, message.toRevokedTUBean(msgBean.isGroup))
                                LogUtils.i(TAG,"IMCallback 已撤回的消息ID = ${message?.msgID}")
                                return@forEachIndexed
                            }
                        }
                    }
                }
                //V2TMessageManager.msgModuleMap[conversationId]?.add(msgBean)
            } else {
                //V2TMessageManager.msgModuleMap[conversationId] = arrayListOf(message.toRevokedTUBean(msgBean.isGroup))
            }
            allV2TCallback.forEach { it.revokeMessage(userId, message,msgBean) }
        }
    }
}
