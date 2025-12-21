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

                ChatCMDMsgType.TYPE_MSG_SYSTEM_TRIPS -> {
                    if (isSelf) TYPE_MSG_SELF_CARD else TYPE_MSG_TA_CARD
                }

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
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_QUIT -> "You left the group"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_KICKED -> "You were removed from the group"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_SET_ADMIN -> "Group owner set admin"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_CANCEL_ADMIN -> "Group owner removed admin"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_GROUP_INFO_CHANGE -> "Group info changed"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_MEMBER_INFO_CHANGE -> "Group member info changed"
            else -> "Group settings changed"
        }
    }
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return "${nickName} revoked a message"
    }
    return when (elemType) {
        V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
            textElem?.text ?: ""
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
            "[Image]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
            "[Video]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
            if (cloudCustomDataToBean()?.ours_voice_ext?.isNotEmpty() == true) "[OURS Message]" else "[Voice]"
        }

        V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM -> {
            val bean = customDataToBean()
            if (bean != null) {
                when (bean?.type ?: 0) {
                    ChatCMDMsgType.TYPE_MSG_TOPIC_NEW -> {
                        return "[New Topic Message]"
                    }

                    ChatCMDMsgType.TYPE_MSG_WISH_ASSIST -> {
                        return "[Wishlist Assist]"
                    }

                    ChatCMDMsgType.TYPE_MSG_CARD -> {
                        return "[Personal Card]"
                    }

                    /*ChatCMDMsgType.TYPE_MSG_PHONE -> {
                        if (bean.data != null) {
                            val data = bean.data
                            when (data?.phoneType) {
                                1 -> "[OURS Call]"
                                2 -> "[Video Call]"
                                else -> "[Voice Call]"
                            }
                        } else {
                            "[Unknown Message Type]"
                        }
                    }*/

                    else -> {
                        return customMsgToTxt(isSend, isGroup, isTopic)
                    }
                }
            } else {
                "[Unknown Message Type]"
            }
        }

        else -> {
            "[Unknown Message Type]"
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
    return "[Unknown Message Type]"
}

fun V2TIMMessage.customMsgToTxt(
    isSend: Boolean,
    isGroup: Boolean,
    isTopic: Boolean
): String {
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS) {
        return when(groupTipsElem.type){
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_QUIT -> "You left the group"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_KICKED -> "You were removed from the group"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_SET_ADMIN -> "Group owner set admin"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_CANCEL_ADMIN -> "Group owner removed admin"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_GROUP_INFO_CHANGE -> "Group info changed"
            V2TIMGroupTipsElem.V2TIM_GROUP_TIPS_TYPE_MEMBER_INFO_CHANGE -> "Group member info changed"
            else -> "Group settings changed"
        }
    }
    if (status == V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED) {
        return "${nickName} revoked a message"
    }
    val customBean = customDataToBean()
    if (elemType == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
        when (customBean?.type) {
            ChatCMDMsgType.TYPE_MSG_OZ_QA, ChatCMDMsgType.TYPE_MSG_OZ_TEXT -> {
                return "[Question]"
            }

            ChatCMDMsgType.TYPE_MSG_OZ_SEND_TEXT -> {
                return "[Question]"
            }

            ChatCMDMsgType.TYPE_MSG_SEND_FAIL -> {
                return "Message sent but was rejected by the recipient."
            }
            ChatCMDMsgType.TYPE_MSG_SYSTEM_TRIPS -> {
                return "[Trip Message]"
            }
            ChatCMDMsgType.TYPE_MSG_SYSTEM_TIME -> {
                customBean.data?.let { data ->
                    return WxTimeUtil.getNewChatTime(data.time ?: 0L)
                }
            }

        }
    }
    return "[Unknown Message Type]"
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
    var oursNewUrl = if(isGroup) "ours-meta://app/home/group/chat?id=${toId}" else "ours-meta://app/home/single/chat?id=${User.uid}"
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
   // info.title = if (isGroup) showName else User.nickname
   // info.desc = if (isGroup) "${User.nickname}：${content}" else content
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
    return if (isGroup) "${if (isSend) "Me" else getShowName(this)}: ${content}" else content
}

fun V2TIMMessage.getConversationDesc(isTopic: Boolean): String {
    var isSend = isSelf
/*
    customDataToBean()?.let { data ->
        isSend = data.data?.sender?.ridStr == User.uid
    }
*/

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

