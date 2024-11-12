package com.example.base.event


class ChatCMDMsgType {
    companion object {
        /**
         * 字段
         */
        const val TYPE = "type"
        /**
         * 字段
         */
        const val DATA = "data"
        /**
         * 时间消息
         */
        const val TYPE_MSG_SYSTEM_TIME = -1001

        /**
         * 行程消息
         */
        const val TYPE_MSG_SYSTEM_TRIPS = 0

        /**
         * 被添加消息
         */
        const val TYPE_MSG_SYSTEM_ADD = 1000

        /**
         * 申请消息
         */
        const val TYPE_MSG_APPLY = 1001

        /**
         * 群公告消息
         */
        const val TYPE_MSG_SYSTEM_NOTICE = 1002

        /**
         * 名片消息
         */
        const val TYPE_MSG_CARD = 1003

        /**
         * 话题邀请消息
         */
        const val TYPE_MSG_TALK_INVITE = 1004
        /**
         * 撤回消息
         */
        const val TYPE_MSG_FALLBACK = 1005

        /**
         * 群名字消息
         */
        const val TYPE_MSG_GROUP_NAME = 1006

        /**
         * XX已经退出群聊
         */
        const val TYPE_MSG_GROUP_EXIT_ACTIVE = 1007

        /**
         * XX被踢出群
         */
        const val TYPE_MSG_GROUP_EXIT_OUT = 1008

        /**
         * XX已经被设置成管理员
         */
        const val TYPE_MSG_GROUP_SET_ADMIN = 1009

        /**
         * XX已经被取消管理员
         */
        const val TYPE_MSG_GROUP_OUT_ADMIN = 1010

        /**
         * XX成为新群主
         */
        const val TYPE_MSG_GROUP_OWNER = 1011

        /**
         * 群解散
         */
        const val TYPE_MSG_GROUP_CHOSE = 1012
        /**
         * 新话题创建
         */
        const val TYPE_MSG_TOPIC_NEW = 1013
        /**
         * ours通话或视频通话
         */
        const val TYPE_MSG_PHONE = 1014
        /**
         * 心愿单助力消息
         */
        const val TYPE_MSG_WISH_ASSIST = 1015
        /**
         * 后端给我们发自定义推送
         */
        const val TYPE_MSG_OZ_PUSH = 1016
        /**
         * 消息没发送出去
         */
        const val TYPE_MSG_SEND_FAIL = 1017

        /**
         * OURS审核-语音消息
         */
        const val TYPE_MSG_AUDIO = 1018

        /**
         * OURS审核-图片消息
         */
        const val TYPE_MSG_IMG = 1019

        /**
         * OURS审核-视频消息
         */
        const val TYPE_MSG_VIDEO = 1020

        /**
         * 欧仔-QA消息
         */
        const val TYPE_MSG_OZ_QA = 1100

        /**
         * 欧仔-文本消息
         */
        const val TYPE_MSG_OZ_TEXT = 1101

        /**
         * 欧仔-发送消息
         */
        const val TYPE_MSG_OZ_SEND_TEXT = 1200


        /**
         * 后端通知-长连接的推送内容
         */
        const val TYPE_MSG_CMD_NOTICE_LINK = 1300

        /**
         * 后端通知-心情变化
         */
        const val TYPE_MSG_CMD_NOTICE_STATE = 1301

    }

}