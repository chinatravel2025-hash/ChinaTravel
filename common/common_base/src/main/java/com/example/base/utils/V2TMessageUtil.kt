package com.example.base.utils


import android.text.TextUtils
import com.aws.bean.msg.V2TMsgExtCloud
import com.aws.bean.msg.V2TMsgExtLocal
import com.aws.bean.util.GsonUtil
import com.example.base.base.App
import com.example.base.base.User
import com.example.base.event.ChatCMDMsgType
import com.example.base.event.ChatCMDMsgType.Companion.TYPE_MSG_SYSTEM_TIME

import com.example.base.msg.V2TCustomBean
import com.example.base.msg.V2TDiyAddVO
import com.example.base.msg.V2TDiyDataVO
import com.example.base.msg.V2TDiyPhoneVO
import com.example.base.msg.V2TImageBean
import com.example.base.msg.V2TMsgCustom
import com.example.base.msg.V2TMsgResultCustom
import com.example.base.msg.V2TSoundBean
import com.example.base.msg.V2TTextBean
import com.example.base.msg.V2TVideoBean
import com.example.base.msg.i.TUIMessageBean
import com.example.base.msg.i.TUIMessageBean.Companion.MSG_STATUS_SEND_SUCCESS
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_CHAT_OZ_TEXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_ASSIST
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_CARD
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_IMG
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_IMG_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_OURS
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_OURS_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_PHONE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_TOPIC
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_TXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_TXT_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VIDEO
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VIDEO_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SELF_VOICE_TEXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_SYSTEM
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_ASSIST
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_CARD
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_IMG
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_IMG_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_OURS
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_OURS_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_PHONE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_QA
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_TOPIC
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_TXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_TXT_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VIDEO
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VIDEO_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE_LITE
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TA_VOICE_TEXT
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TIME
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_OZ_CHANGE_MODE_MSG
import com.tencent.imsdk.v2.V2TIMGroupTipsElem
import com.tencent.imsdk.v2.V2TIMImageElem
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMMessageSearchResultItem
import com.tencent.imsdk.v2.V2TIMOfflinePushInfo
import com.tencent.imsdk.v2.V2TIMSoundElem
import com.tencent.imsdk.v2.V2TIMVideoElem
import org.json.JSONObject
import java.io.File


/**
 * 转换类
 */
fun V2TIMMessage.bodyToIMChatType(): Int {
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return TYPE_MSG_SYSTEM
    }
    return when (elemType) {
        V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS -> {
            TYPE_MSG_SYSTEM
        }
        V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
            if (isSelf) TYPE_MSG_SELF_TXT else TYPE_MSG_TA_TXT
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
            if (isSelf) TYPE_MSG_SELF_IMG else TYPE_MSG_TA_IMG
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
            if (isSelf) TYPE_MSG_SELF_VIDEO else TYPE_MSG_TA_VIDEO
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
            if (cloudCustomDataToBean()?.ours_voice_ext?.isNotEmpty() == true) {
                if (isSelf) TYPE_MSG_SELF_OURS else TYPE_MSG_TA_OURS
            } else {
                if (isSelf) TYPE_MSG_SELF_VOICE else TYPE_MSG_TA_VOICE
            }
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM -> {
            when (customDataToBean()?.type ?: 0) {
                TYPE_MSG_SYSTEM_TIME -> {
                    TYPE_MSG_TIME
                }

                ChatCMDMsgType.TYPE_MSG_TOPIC_NEW -> {
                    if (isSelf) TYPE_MSG_SELF_TOPIC else TYPE_MSG_TA_TOPIC
                }

                ChatCMDMsgType.TYPE_MSG_PHONE -> {
                    if (isSelf) TYPE_MSG_SELF_PHONE else TYPE_MSG_TA_PHONE
                }

                ChatCMDMsgType.TYPE_MSG_WISH_ASSIST -> {
                    if (isSelf) TYPE_MSG_SELF_ASSIST else TYPE_MSG_TA_ASSIST
                }

                ChatCMDMsgType.TYPE_MSG_OZ_QA -> {
                    TYPE_MSG_TA_QA
                }

                ChatCMDMsgType.TYPE_MSG_OZ_TEXT -> {
                    TYPE_MSG_CHAT_OZ_TEXT
                }

                ChatCMDMsgType.TYPE_MSG_CARD -> {
                    if (isSelf) TYPE_MSG_SELF_CARD else TYPE_MSG_TA_CARD
                }

                ChatCMDMsgType.TYPE_MSG_OZ_SEND_TEXT -> {
                    TYPE_OZ_CHANGE_MODE_MSG
                }

                else -> {
                    TYPE_MSG_SYSTEM
                }
            }
        }

        else -> {
            TYPE_MSG_SELF_TXT
        }
    }
}

fun V2TIMMessage.localCustomDataToBean(): V2TMsgExtLocal {
    if (localCustomData.isNullOrEmpty()) {//卸载重装后
        return V2TMsgExtLocal("", 100, 100)
    }
    return try {
        GsonUtil.fromJson(localCustomData ?: "{}", V2TMsgExtLocal::class.java)
    } catch (e: Exception) {
        return V2TMsgExtLocal("", 100, 100)
    }
}

fun V2TIMMessage.cloudCustomDataToBean(): V2TMsgExtCloud? {
    if (cloudCustomData.isNullOrEmpty()) {
        return null
    }
    return try {
        GsonUtil.fromJson(cloudCustomData ?: "{}", V2TMsgExtCloud::class.java)
    } catch (e: Exception) {
        null
    }
}

fun V2TIMMessage.customDataToMap(): V2TMsgResultCustom? {
    return try {
        GsonUtil.fromJson(
            String(customElem?.data ?: byteArrayOf()),
            V2TMsgResultCustom::class.java
        )
    } catch (e: Exception) {
        null
    }
}

fun V2TIMMessage.toId(): String {
    return (if (groupID?.isNotEmpty() == true) groupID else userID) ?: ""
}

fun V2TIMMessage.customDataToBean(): V2TMsgCustom? {
    if (customElem?.data == null) {
        return null
    }
    if (elemType != V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        return null
    }
    return try {
        GsonUtil.fromJson(
            String(customElem?.data ?: byteArrayOf()),
            V2TMsgCustom::class.java
        )
    } catch (e: Exception) {
        null
    }
}

fun V2TCustomBean.customDataToBean(): V2TMsgCustom? {
    return try {
        GsonUtil.fromJson(
            String(message?.customElem?.data ?: byteArrayOf()),
            V2TMsgCustom::class.java
        )
    } catch (e: Exception) {
        null
    }
}

fun V2TIMMessage.toLiteBean(isGroup: Boolean, isSelf: Boolean): TUIMessageBean {
    return when (elemType) {
        V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
            V2TImageBean.Builder()
                .setMsgType(if (isSelf) TYPE_MSG_SELF_IMG_LITE else TYPE_MSG_TA_IMG_LITE)
                .setMessage(this)
                .setGroup(isGroup)
                .build()
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
            V2TVideoBean.Builder()
                .setMsgType(if (isSelf) TYPE_MSG_SELF_VIDEO_LITE else TYPE_MSG_TA_VIDEO_LITE)
                .setMessage(this)
                .setGroup(isGroup)
                .build()
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
            V2TSoundBean.Builder()
                .setMsgType(if (isSelf) TYPE_MSG_SELF_VOICE_LITE else TYPE_MSG_TA_VOICE_LITE)
                .setMessage(this)
                .setGroup(isGroup)
                .build()
        }

        else -> {
            V2TTextBean.Builder()
                .setMsgType(if (isSelf) TYPE_MSG_SELF_TXT_LITE else TYPE_MSG_TA_TXT_LITE)
                .setMessage(this)
                .setGroup(isGroup)
                .build()

        }
    }
}

fun V2TIMMessage.toRevokedTUBean(isGroup: Boolean): TUIMessageBean {
    return V2TCustomBean.Builder()
        .setMessage(this)
        .setGroup(isGroup)
        .setMsgType(TYPE_MSG_SYSTEM)
        .setStatus(V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED)
        .build()
}

fun V2TIMMessage.toTUBean(
    isGroup: Boolean,
    isTopic: Boolean,
    status: Int = MSG_STATUS_SEND_SUCCESS
): TUIMessageBean {
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return V2TCustomBean.Builder()
            .setMessage(this)
            .setGroup(isGroup)
            .setTopic(isTopic)
            .setStatus(status)
            .build()
    }
    return when (elemType) {

        V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
            V2TImageBean.Builder()
                .setMessage(this)
                .setGroup(isGroup)
                .setTopic(isTopic)
                .setUploadStatus(status)
                .build()
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
            V2TVideoBean.Builder()
                .setMessage(this)
                .setGroup(isGroup)
                .setTopic(isTopic)
                .setUploadStatus(status)
                .build()
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
            V2TSoundBean.Builder()
                .setMessage(this)
                .setGroup(isGroup)
                .setTopic(isTopic)
                .setUploadStatus(status)
                .build()
        }


        V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
            V2TTextBean.Builder()
                .setMessage(this)
                .setGroup(isGroup)
                .setTopic(isTopic)
                .setStatus(status)
                .build()
        }

        else -> {
            V2TCustomBean.Builder()
                .setMessage(this)
                .setGroup(isGroup)
                .setTopic(isTopic)
                .setStatus(status)
                .build()

        }
    }
}

fun V2TIMMessage.isIgnore(): Int {//0过滤 1不过滤 2过滤但不删除

    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        try {
            JSONObject(String(customElem?.data ?: byteArrayOf())).let {
                if (it.has("inviteID")) {
                    return 2
                }
                return if (!it.has("type")) 0 else 1
            }
        } catch (e: Exception) {
        }
    }
    return 1
}

fun V2TIMMessageSearchResultItem.toTUBean(
    isTopic: Boolean
): MutableList<TUIMessageBean> {
    val list = mutableListOf<TUIMessageBean>()
    messageList?.forEach {
        list.add(it.toTUBean(it.groupID?.isNotEmpty() == true, isTopic))
    }
    return list
}


fun V2TIMMessage.toTitle(isSend: Boolean, isGroup: Boolean, isTopic: Boolean): String {

    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS) {
        return when(groupTipsElem.type){
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_QUIT -> "你已退出群聊"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_KICKED -> "你已被移出群聊"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_SET_ADMIN -> "群主设置管理员"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_CANCEL_ADMIN -> "群主取消管理员"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_GROUP_INFO_CHANGE -> "群信息已变更"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_MEMBER_INFO_CHANGE -> "群成员信息变更"
            else -> "群设置已变更"
        }
    }
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return "${nickName}撤回了一条消息"
    }
    return when (elemType) {
        V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
            textElem?.text ?: ""
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
            "[图片]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
            "[视频]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
            if (cloudCustomDataToBean()?.ours_voice_ext?.isNotEmpty() == true) "[OURS消息]" else "[语音]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM -> {
            val bean = customDataToBean()
            if (bean != null) {
                when (bean?.type ?: 0) {
                    ChatCMDMsgType.TYPE_MSG_TOPIC_NEW -> {
                        return "[新话题消息]"
                    }

                    ChatCMDMsgType.TYPE_MSG_WISH_ASSIST -> {
                        return "[心愿单助力]"
                    }

                    ChatCMDMsgType.TYPE_MSG_CARD -> {
                        return "[个人名片]"
                    }

                    ChatCMDMsgType.TYPE_MSG_PHONE -> {
                        if (bean.data != null) {
                            val data = bean.data
                            when (data?.phoneType) {
                                1 -> "[OURS通话]"
                                2 -> "[视频通话]"
                                else -> "[语音通话]"
                            }
                        } else {
                            "[未知消息类型]"
                        }
                    }

                    else -> {
                        return customMsgToTxt(isSend, isGroup, isTopic)
                    }
                }
            } else {
                "[未知消息类型]"
            }
        }

        else -> {
            "[未知消息类型]"
        }

    }
}

fun TUIMessageBean.customMsgToTxt(
    isSend: Boolean,
    isTopic: Boolean
): String {
    message?.let { message ->
        return message.customMsgToTxt(isSend, isGroup, isTopic)
    }
    return "未知消息类型"
}

fun V2TIMMessage.customMsgToTxt(
    isSend: Boolean,
    isGroup: Boolean,
    isTopic: Boolean
): String {
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS) {
        return when(groupTipsElem.type){
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_QUIT -> "你已退出群聊"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_KICKED -> "你已被移出群聊"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_SET_ADMIN -> "群主设置管理员"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_CANCEL_ADMIN -> "群主取消管理员"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_GROUP_INFO_CHANGE -> "群信息已变更"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_MEMBER_INFO_CHANGE -> "群成员信息变更"
            else -> "群设置已变更"
        }
    }
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return "${nickName}撤回了一条消息"
    }
    val customBean = customDataToBean()
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        val groupType = if (isTopic) "话题" else "群"
        when (customBean?.type) {
            ChatCMDMsgType.TYPE_MSG_OZ_QA, ChatCMDMsgType.TYPE_MSG_OZ_TEXT -> {
                return "[提问]"
            }

            ChatCMDMsgType.TYPE_MSG_OZ_SEND_TEXT -> {
                return "[提问]"
            }

            ChatCMDMsgType.TYPE_MSG_SEND_FAIL -> {
                return "消息已发出，但被对方拒收了。"
            }

            ChatCMDMsgType.TYPE_MSG_SYSTEM_TIME -> {
                customBean.data?.let { data ->
                    return WxTimeUtil.getNewChatTime(data.time ?: 0L)
                }
            }

            ChatCMDMsgType.TYPE_MSG_SYSTEM_ADD -> {

                customBean.data?.let { data ->
                    val source = data?.source ?: 0
                    data.userList?.let { userList ->
                        when (isGroup) {
                            false -> {
                                userList?.forEach {
                                    //return if (isSend) "你们已经是好友了，打个招呼吧。" else "对方已同意了你的好友申请，现在可以开始聊天了。"
                                    return "你们已经是好友了，打个招呼吧。"
                                }
                            }

                            else -> {
                                when (source) {
                                    V2TDiyAddVO.qc -> {
                                        return "${if (isSend) "你" else data.sender?.nickName ?: "XX"}通过扫码加入${groupType}${if (isTopic) "" else "聊"}。"
                                    }

                                    else -> {
                                        var isHasSelf = false
                                        var endContent = ""
                                        var isFlag = false

                                        userList?.forEach {
                                            if (it.ridStr == User.ridString) {
                                                isHasSelf = true
                                            } else {
                                                endContent += if (isFlag) "、\"${it.nickName ?: ""}\"" else "\"${it.nickName ?: ""}\""
                                                isFlag = true
                                            }
                                        }
                                        var content =
                                            if (isSend) "你" else "\"${data?.sender?.nickName ?: ""}\""
                                        if (userList.isEmpty()) {
                                            content += "加入${groupType}${if (isTopic) "" else "聊"}。"
                                        } else {
                                            if (isSend) {
                                                content += "${if (endContent.isNotEmpty()) "邀请${endContent}" else "已"}加入${groupType}${if (isTopic) "" else "聊"}。"

                                            } else {
                                                content += if (isHasSelf) {//包含自己
                                                    if (source == V2TDiyAddVO.create) {
                                                        "邀请你加入${groupType}${if (isTopic) "" else "聊"}${
                                                            when {
                                                                userList.size > 0 -> "，${groupType}${if (isTopic) "" else "聊"}参与人还有：$endContent"
                                                                else -> ""
                                                            }
                                                        }"
                                                    } else {
                                                        "邀请你${if (endContent.isNotEmpty()) "和" else ""}${endContent}加入${groupType}${if (isTopic) "" else "聊"}"
                                                    }
                                                } else if (userList?.isNotEmpty() == true && data?.sender?.ridStr == userList.get(
                                                        0
                                                    ).ridStr
                                                ) {
                                                    "加入${groupType}${if (isTopic) "" else "聊"}"
                                                } else {
                                                    "邀请${endContent}加入${groupType}${if (isTopic) "" else "聊"}"
                                                }
                                            }
                                        }
                                        return content
                                    }
                                }

                            }
                        }
                    }
                }
            }


            ChatCMDMsgType.TYPE_MSG_APPLY -> {
                customBean.data?.let { data ->
                    data.userList?.let { userList ->
                        return if (isSend) "你申请添加Ta为好友。" else "${data.sender?.nickName?:"Ta"}向你发起了好友申请。"
                    }
                }
            }

            ChatCMDMsgType.TYPE_MSG_PHONE -> {
                customBean.data?.let { data ->
                    return doPhoneTypeText(data)
                }
            }

            ChatCMDMsgType.TYPE_MSG_GROUP_CHOSE -> {
                return "${groupType}已解散。"
            }

            else -> {
                customBean?.data?.let { data ->
                    when (customBean.type) {
                        ChatCMDMsgType.TYPE_MSG_GROUP_EXIT_OUT -> {
                            var names = ""
                            data.userList?.forEach { user ->
                                val isSelf = user.ridStr == User.ridString
                                if (isSelf) {
                                    return "你已经被移出${groupType}${if (isTopic) "" else "聊"}。"
                                }
                                names += if (names.isNotEmpty()) ",${user.nickName}" else user.nickName
                            }

                            return "你已将${names}移出${groupType}${if (isTopic) "" else "聊"}。"

                        }

                        ChatCMDMsgType.TYPE_MSG_FALLBACK -> {
                            var nickname =
                                if (data.sender?.ridStr == User.ridString) "你" else data.sender?.nickName
                                    ?: ""
                            return "${nickname}撤回了一条消息。"
                        }

                        else -> {

                            val content = data.content ?: ""
                            when (customBean.type) {
                                ChatCMDMsgType.TYPE_MSG_SYSTEM_NOTICE -> {
                                    return "${if (isSend) "你" else data.sender?.nickName ?: ""}将公告更改为：${content}。"
                                }

                                ChatCMDMsgType.TYPE_MSG_GROUP_NAME -> {
                                    return "${if (isSend) "你" else data.sender?.nickName ?: ""}将${groupType}名更改为：${content}。"
                                }

                                ChatCMDMsgType.TYPE_MSG_GROUP_EXIT_ACTIVE -> {
                                    return "${if (isSend) "你" else data.sender?.nickName ?: ""}已经退出${groupType}${if (isTopic) "" else "聊"}。"
                                }

                                ChatCMDMsgType.TYPE_MSG_GROUP_SET_ADMIN -> {
                                    return "${if (isSend) "你" else data.sender?.nickName}已经被设置为管理员。"
                                }

                                ChatCMDMsgType.TYPE_MSG_GROUP_OUT_ADMIN -> {
                                    return "${if (isSend) "你" else data.sender?.nickName}已经被取消管理员身份。"
                                }

                                ChatCMDMsgType.TYPE_MSG_GROUP_OWNER -> {
                                    return if(data.userList?.isNotEmpty() == true){
                                        "${if (data.userList?.get(0)?.ridStr == User.ridString) "你" else data.userList?.get(0)?.nickName}已成为新的群主。"
                                    }else{
                                        "群主已经变更。"
                                    }
                                }

                                ChatCMDMsgType.TYPE_MSG_TOPIC_NEW -> {
                                    return "#${content}"
                                }

                                else -> {}
                            }
                        }
                    }
                }

            }
        }
    }
    return "未知消息类型"
}

fun V2TIMMessage.isApplyFriend(): Boolean {
    val customBean = customDataToBean()
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        when (customBean?.type) {
            ChatCMDMsgType.TYPE_MSG_APPLY->{
                return true
            }
        }}
    return false
}

fun V2TIMMessage.doPhoneTypeText(data: V2TDiyDataVO): String {
    return when (data.phoneType) {
        V2TDiyPhoneVO.TYPE_PHONE_CONNECT -> {
            "${V2TDiyPhoneVO.STR_PHONE_CONNECT} ${DateUtils.formatTime(data.time ?: 0)}"
        }

        V2TDiyPhoneVO.TYPE_PHONE_BUSY -> {
            if (isSelf) V2TDiyPhoneVO.STR_PHONE_SENDER_BUSY else V2TDiyPhoneVO.STR_PHONE_BUSY
        }

        V2TDiyPhoneVO.TYPE_PHONE_REFUSE -> {
            if (isSelf) V2TDiyPhoneVO.STR_PHONE_SENDER_REFUSE else V2TDiyPhoneVO.STR_PHONE_REFUSE
        }

        V2TDiyPhoneVO.TYPE_PHONE_TIMEOUT -> {
            if (isSelf) V2TDiyPhoneVO.STR_PHONE_SENDER_TIMEOUT else V2TDiyPhoneVO.STR_PHONE_TIMEOUT
        }

        V2TDiyPhoneVO.TYPE_PHONE_CANCEL -> {
            if (isSelf) V2TDiyPhoneVO.STR_PHONE_SENDER_CANCEL else V2TDiyPhoneVO.STR_PHONE_CANCEL
        }

        else -> "${V2TDiyPhoneVO.STR_PHONE_CONNECT} ${DateUtils.formatTime(data.time ?: 0)}"
    }
}

fun V2TIMMessage.setPushDesc(
    toId: String,
    showName:String,
    isGroup: Boolean,
    isTopic: Boolean,
    oursUrl:String?
): V2TIMOfflinePushInfo {
    var oursNewUrl = if(isGroup) "ours-meta://app/home/group/chat?id=${toId}" else "ours-meta://app/home/single/chat?id=${User.ridString}"
    val info = V2TIMOfflinePushInfo()
    info.setAndroidHuaWeiCategory("IM")
    info.setAndroidVIVOCategory("IM")
    info.setAndroidXiaoMiChannelID("118330")
    info.setAndroidOPPOChannelID("OURS_IM_ChannelID")
    /*info.ext = GsonUtil.toJson(
        IOSPushVo(
            if (isGroup) toId else null,
            if (isGroup) null else User.ridString,
            if(oursUrl.isNullOrEmpty()) oursNewUrl else oursUrl
        )
    ).toByteArray()*/
    TLog.d("TUICore","setAndroidXiaoMiChannelID")
    // info.setAndroidOPPOChannelID("OURSIM")
    val content = toTitle(false, isGroup, isTopic)
    info.title = if (isGroup) showName else User.nickname
    info.desc = if (isGroup) "${User.nickname}：${content}" else content
    return info
}


private fun getShowName(message:V2TIMMessage):String{
    var showName = ""
    if(!TextUtils.isEmpty(message.nameCard)){
        showName = message.nameCard
    }else if(!TextUtils.isEmpty(message.friendRemark)){
        showName = message.friendRemark
    }else {
        showName = message.nickName
    }
    return showName
}

fun V2TIMMessage.getPushDesc(isSend: Boolean, isGroup: Boolean, isTopic: Boolean): String {
    val content = toTitle(isSend, isGroup, isTopic)
    return if (isGroup) "${if (isSend) "我" else getShowName(this)}：${content}" else content
}

fun V2TIMMessage.getConversationDesc(isTopic: Boolean): String {
    var isSend = isSelf
    customDataToBean()?.let { data ->
        isSend = data.data?.sender?.ridStr == User.ridString
    }

    return getPushDesc(
        isSend,
        true,
        isTopic
    )
}

fun V2TIMMessage.setLocalUnread(isUnread: Boolean) {
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_SOUND) {
        val localBean = localCustomDataToBean()
        localBean.ours_unread = isUnread
        localBean.ours_download_progress = 100
        localBean.ours_upload_progress = 100
        localCustomData = GsonUtil.toJson(localBean)
    }
}

fun V2TIMMessage.getCmdType(): Int {
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        return customDataToBean()?.type ?: -1
    }
    return -1
}

fun String.inviteeDataToBean(): V2TDiyDataVO? {
    return try {
        GsonUtil.fromJson(
            this,
            V2TDiyDataVO::class.java
        )
    } catch (e: Exception) {
        null
    }
}

fun V2TIMSoundElem.getReadPath(): String {
    if (path?.isNotEmpty() == true) {
        return path
    }
    return getDownloadFile().absolutePath
}

fun V2TIMSoundElem.isDownload(): Boolean {
    return File(FileUtils.getAudioPath(App.getContext(), uuid ?: "")).exists()
}
fun V2TIMSoundElem.getDownloadFile(): File {
    return File(FileUtils.getAudioPath(App.getContext(), uuid ?: ""))
}

fun V2TIMVideoElem.getReadSnapshotPath(): String {
    if (snapshotPath?.isNotEmpty() == true) {
        return snapshotPath
    }
    return FileUtils.getSnapshotPath(App.getContext(), snapshotUUID ?: "")
}

fun V2TIMVideoElem.isSnapshotDownload(): Boolean {
    return File(FileUtils.getSnapshotPath(App.getContext(), snapshotUUID ?: "")).exists()
}

fun V2TIMVideoElem.getReadVideoPath(): String {
    if (videoPath?.isNotEmpty() == true) {
        return videoPath
    }
    return FileUtils.getVideoPath(App.getContext(), videoUUID ?: "")
}

fun V2TIMVideoElem.isDownload(): Boolean {
    return File(FileUtils.getVideoPath(App.getContext(), videoUUID ?: "")).exists()
}

fun V2TIMImageElem.getReadImagePath(): String {
    if (path?.isNotEmpty() == true) {
        return path
    }
    if (imageList.isNotEmpty()) {
        return imageList[0].url
    }
    return ""
}

fun V2TIMImageElem.getImage(): V2TIMImageElem.V2TIMImage? {
    if (imageList.isNotEmpty()) {
        return imageList[0]
    }
    return null
}

fun TUIMessageBean.isExtType(): Boolean {
    return when (msgType) {
        TYPE_MSG_SELF_TXT_LITE, TYPE_MSG_TA_TXT_LITE, TYPE_MSG_SELF_OURS_LITE, TYPE_MSG_TA_OURS_LITE, TYPE_MSG_SELF_IMG_LITE, TYPE_MSG_TA_IMG_LITE, TYPE_MSG_SELF_VIDEO_LITE, TYPE_MSG_TA_VIDEO_LITE, TYPE_MSG_SELF_VOICE_TEXT, TYPE_MSG_TA_VOICE_TEXT  -> {
            true
        }

        else -> {
            false
        }
    }
}

