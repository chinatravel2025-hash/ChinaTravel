package com.example.base.msg

import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.bodyToIMChatType
import com.tencent.imsdk.v2.V2TIMMessage

class V2TImageBean : TUIMessageBean() {

    class Builder {
        private val bean = V2TImageBean()

        fun build(): V2TImageBean {
            if(bean.msgType == TYPE_MSG_N){
                setMsgType(bean.message?.bodyToIMChatType()?:0)
            }
            return bean
        }

        fun setGroup(isGroup: Boolean): Builder {
            bean.isGroup = isGroup
            return this
        }

        fun setTopic(isTopic: Boolean): Builder {
            bean.isTopic = isTopic
            return this
        }

        fun setUploadStatus(status: Int): Builder {
            bean.uploadStatus = status
            return this
        }

        fun setMessage(v2TIMMessage: V2TIMMessage): Builder {
            bean.message = v2TIMMessage
            return this
        }

        fun setMsgType(type: Int): Builder {
            bean.msgType = type
            return this
        }
    }

}