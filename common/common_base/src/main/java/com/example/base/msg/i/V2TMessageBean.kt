package com.example.base.msg.i

import com.example.base.common.v2t.im.CommonIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import java.io.Serializable

abstract class TUIMessageBean : Serializable {
    var message: V2TIMMessage? = null
    var extra: String? = null
    var id: String? = null
    var isGroup = false
    var isTopic = false
    var uploadStatus = MSG_STATUS_SENDING
    var downloadStatus = MSG_STATUS_NORMAL
    var msgType = TYPE_MSG_N//绘制时type
    var isCheck: Boolean = false

    val isUnread: Boolean
        get() = !(message?.isRead ?: true)


    val userId: String
        get() = (if (isGroup) message?.groupID else message?.userID) ?: ""

    val isUpload: Boolean
        get() = uploadStatus == MSG_STATUS_SEND_SUCCESS

    val isDownload: Boolean
        get() = downloadStatus == MSG_STATUS_SEND_SUCCESS
    val conversationId:String
        get() = CommonIMManager.convertUserIdToConversationId(isGroup,userId)


    companion object {
        /**
         * 消息正常状态
         *
         * message normal
         */
        const val MSG_STATUS_NORMAL = 0

        /**
         * 消息发送中状态
         *
         * message sending
         */
        val MSG_STATUS_SENDING: Int = V2TIMMessage.V2TIM_MSG_STATUS_SENDING

        /**
         * 消息发送成功状态
         *
         * message send success
         */
        val MSG_STATUS_SEND_SUCCESS: Int = V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC

        /**
         * 消息发送失败状态
         *
         * message send failed
         */
        val MSG_STATUS_SEND_FAIL: Int = V2TIMMessage.V2TIM_MSG_STATUS_SEND_FAIL

        /**
         * 消息撤回状态
         *
         * messaage revoked
         */
        val MSG_STATUS_REVOKE: Int = V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED



        /**
         * 空消息未赋值
         */
        const val TYPE_MSG_N = -1
        /**
         * 系统消息
         */
        const val TYPE_MSG_SYSTEM = 0

        /**
         * 时间消息
         */
        const val TYPE_MSG_TIME = 1


        /**
         * 文字消息-我发送的
         */
        const val TYPE_MSG_SELF_TXT = 10

        /**
         * 文字消息-对方发送的
         */
        const val TYPE_MSG_TA_TXT = 11

        /**
         * 文字消息-我发送的-被引用
         */
        const val TYPE_MSG_SELF_TXT_LITE = 12

        /**
         * 文字消息-对方发送的-被引用
         */
        const val TYPE_MSG_TA_TXT_LITE = 13

        /**
         * 图片消息-我发送的
         */
        const val TYPE_MSG_SELF_IMG = 20

        /**
         * 图片消息-对方发送的
         */
        const val TYPE_MSG_TA_IMG = 21

        /**
         * 图片消息-我发送的-被引用
         */
        const val TYPE_MSG_SELF_IMG_LITE = 22

        /**
         * 图片消息-对方发送的-被引用
         */
        const val TYPE_MSG_TA_IMG_LITE = 23

        /**
         * 视频消息-我发送的
         */
        const val TYPE_MSG_SELF_VIDEO = 30

        /**
         * 视频消息-对方发送的
         */
        const val TYPE_MSG_TA_VIDEO = 31

        /**
         * 视频消息-我发送的-被引用
         */
        const val TYPE_MSG_SELF_VIDEO_LITE = 32

        /**
         * 视频消息-对方发送的-被引用
         */
        const val TYPE_MSG_TA_VIDEO_LITE = 33

        /**
         * 语音消息-我发送的
         */
        const val TYPE_MSG_SELF_VOICE = 40

        /**
         * 语音消息-对方发送的
         */
        const val TYPE_MSG_TA_VOICE = 41


        /**
         * 语音消息-我发送的-被引用
         */
        const val TYPE_MSG_SELF_VOICE_LITE = 42

        /**
         * 语音消息-对方发送的-被引用
         */
        const val TYPE_MSG_TA_VOICE_LITE = 43

        /**
         * OURS消息-我发送的
         */
        const val TYPE_MSG_SELF_OURS = 50

        /**
         * 语音消息-我发送的TEXT
         */
        const val TYPE_MSG_SELF_VOICE_TEXT = 44

        /**
         * 语音消息-对方发送的TEXT
         */
        const val TYPE_MSG_TA_VOICE_TEXT = 45

        /**
         * OURS消息-对方发送的
         */
        const val TYPE_MSG_TA_OURS = 51

        /**
         * OURS消息-我发送的-被引用
         */
        const val TYPE_MSG_SELF_OURS_LITE = 52

        /**
         * OURS消息-对方发送的-被引用
         */
        const val TYPE_MSG_TA_OURS_LITE = 53

        /**
         * 话题消息-我发送的
         */
        const val TYPE_MSG_SELF_TOPIC = 60

        /**
         * 话题消息-对方发送的
         */
        const val TYPE_MSG_TA_TOPIC = 61

        /**
         * 电话消息-我发送的
         */
        const val TYPE_MSG_SELF_PHONE = 70

        /**
         * 电话消息-对方发送的
         */
        const val TYPE_MSG_TA_PHONE = 71

        /**
         * 助力消息-我发送的
         */
        const val TYPE_MSG_SELF_ASSIST = 80

        /**
         * 助力消息-对方发送的
         */
        const val TYPE_MSG_TA_ASSIST = 81


        /**
         * QA消息-对方发送的
         */
        const val TYPE_MSG_TA_QA = 90

        /**
         * OZ chat text 消息
         */
        const val TYPE_MSG_CHAT_OZ_TEXT = TYPE_MSG_TA_QA + 1
        /**
         * 明片消息-我发送的
         */
        const val TYPE_MSG_SELF_CARD = 100

        /**
         * 明片消息-对方发送的
         */
        const val TYPE_MSG_TA_CARD = 101

        /**
         * OZ消息发送的
         */
        const val TYPE_OZ_CHANGE_MODE_MSG = 201

    }
}