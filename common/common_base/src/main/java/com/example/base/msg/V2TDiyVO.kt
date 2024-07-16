package com.example.base.msg

import com.example.base.database.friend.entity.ContactItemBean
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_BUSY
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_CANCEL
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_REFUSE
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_SENDER_BUSY
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_SENDER_CANCEL
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_SENDER_REFUSE
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_SENDER_TIMEOUT
import com.example.base.msg.V2TDiyPhoneVO.Companion.STR_PHONE_TIMEOUT
import com.example.base.msg.V2TDiyPhoneVO.Companion.TYPE_PHONE_BUSY
import com.example.base.msg.V2TDiyPhoneVO.Companion.TYPE_PHONE_CANCEL
import com.example.base.msg.V2TDiyPhoneVO.Companion.TYPE_PHONE_CONNECT
import com.example.base.msg.V2TDiyPhoneVO.Companion.TYPE_PHONE_REFUSE
import com.example.base.msg.V2TDiyPhoneVO.Companion.TYPE_PHONE_TIMEOUT
import com.example.base.utils.DateUtils.formatTime
import com.google.gson.internal.LinkedTreeMap

open class V2TDiyDataVO {
    var sender: ContactItemBean? = null
    var userList: MutableList<ContactItemBean>? = null
    //--V2TDiyAddVO
    var source: Int? = null//0站内  1扫码

    var groupId: String? = null//如果是话题，需要有群id

    var content: String? = null//内容
    //--V2TDiyPhoneVO
    var phoneType: Int? = null//0=接听，1对方通话中，2对方已拒绝，3对方超时未操作（timeOut）,4自己主动取消

    var senderType: Int? = null//0=ours电话，1视频电话，2音频通话

    var time: Long? = null//time:通话时长（毫秒)

    //--V2TDiyQAVO
    var wishId: Long? = null//心愿单ID
    var icon: String? = null//图标
    //--V2TDiyPhoneVO
    var qAList: MutableList<LinkedTreeMap<String, Any?>>? = null
    var title: String? = null
    var action: String? = "0"//自定义操作

    //--V2TDiyStateVO
    var type: Int? = 0//13：0=心情，1空间装扮  14：0=删除，1拉黑，2取消拉黑
    var systemEventData:String? = null

}

class V2TDiyAddVO : V2TDiyDataVO() {
    companion object {
        const val create = 2
        const val interior = 0
        const val qc = 1
    }

    class Builder {
        private val vo = V2TDiyAddVO()

        fun build(): V2TDiyAddVO {
            return vo
        }

        fun setSource(source: Int?): Builder {
            vo.source = source
            return this
        }

        fun setGroupId(groupId: String?): Builder {
            vo.groupId = groupId
            return this
        }

        fun setContent(content: String?): Builder {
            vo.content = content
            return this
        }

        fun setSender(sender: ContactItemBean?): Builder {
            vo.sender = sender
            return this
        }

        fun setUserList(userList: MutableList<ContactItemBean>?): Builder {
            vo.userList = userList
            return this
        }
    }
}
class V2TDiyBackVO : V2TDiyDataVO() {
    class Builder {
        private val vo = V2TDiyBackVO()

        fun build(): V2TDiyBackVO {
            return vo
        }


        fun setUserList(userList: ArrayList<ContactItemBean>?): Builder {
            vo.userList = userList
            return this
        }
    }
}
class V2TDiyPhoneVO : V2TDiyDataVO() {
    companion object {
        const val TYPE_SENDER_OURS = 0
        const val TYPE_SENDER_VIDEO = 1
        const val TYPE_SENDER_AUDIO = 2

        const val TYPE_PHONE_CONNECT = 0
        const val TYPE_PHONE_BUSY = 1
        const val TYPE_PHONE_REFUSE = 2
        const val TYPE_PHONE_TIMEOUT = 3
        const val TYPE_PHONE_CANCEL = 4

        const val STR_PHONE_CONNECT = "通话时长"

        const val STR_PHONE_BUSY = "未接听"
        const val STR_PHONE_REFUSE = "已拒绝"
        const val STR_PHONE_TIMEOUT = "未接听"
        const val STR_PHONE_CANCEL = "已取消"

        const val STR_PHONE_SENDER_BUSY = "对方正忙"
        const val STR_PHONE_SENDER_REFUSE = "对方未接听，点击重拨"
        const val STR_PHONE_SENDER_TIMEOUT = "对方正忙"
        const val STR_PHONE_SENDER_CANCEL = "已取消，点击重拨"


        const val STR_TYPE_CONNECT = "STR_TYPE_CONNECT"
        const val STR_TYPE_BUSY = "STR_TYPE_BUSY"
        const val STR_TYPE_REFUSE = "STR_TYPE_REFUSE"
        const val STR_TYPE_TIMEOUT = "STR_TYPE_TIMEOUT"
        const val STR_TYPE_CANCEL = "STR_TYPE_CANCEL"

    }

    class Builder {
        private val vo = V2TDiyPhoneVO()

        fun build(): V2TDiyPhoneVO {
            return vo
        }

        fun setPhoneType(phoneType: Int?): Builder {
            vo.phoneType = phoneType
            return this
        }

        fun setSenderType(senderType: Int?): Builder {
            vo.senderType = senderType
            return this
        }

        fun setTime(time: Long?): Builder {
            vo.time = time
            return this
        }

        fun setUserList(userList: ArrayList<ContactItemBean>?): Builder {
            vo.userList = userList
            return this
        }

        fun setSender(sender: ContactItemBean?): Builder {
            vo.sender = sender
            return this
        }

        fun setAction(action: String?): Builder {
            vo.action = action
            return this
        }
    }
}

fun V2TDiyPhoneVO.doPhoneTypeText(isSend:Boolean): String {
    return when (phoneType) {
        TYPE_PHONE_CONNECT -> {
            "${V2TDiyPhoneVO.STR_PHONE_CONNECT} ${formatTime(time?:0)}"
        }
        TYPE_PHONE_BUSY -> {
            if(isSend) STR_PHONE_SENDER_BUSY else STR_PHONE_BUSY
        }
        TYPE_PHONE_REFUSE -> {
            if(isSend) STR_PHONE_SENDER_REFUSE else STR_PHONE_REFUSE
        }
        TYPE_PHONE_TIMEOUT -> {
            if(isSend) STR_PHONE_SENDER_TIMEOUT else STR_PHONE_TIMEOUT
        }
        TYPE_PHONE_CANCEL -> {
            if(isSend) STR_PHONE_SENDER_CANCEL else STR_PHONE_CANCEL
        }
        else -> "${V2TDiyPhoneVO.STR_PHONE_CONNECT} ${formatTime(time?:0)}"
    }
}

class V2TDiyWishAssistVO : V2TDiyDataVO() {
    class Builder {
        private val vo = V2TDiyWishAssistVO()

        fun build(): V2TDiyWishAssistVO {
            return vo
        }

        fun setWishId(wishId: Long?): Builder {
            vo.wishId = wishId
            return this
        }

        fun setIcon(icon: String?): Builder {
            vo.icon = icon
            return this
        }

        fun setTime(time: Long?): Builder {
            vo.time = time
            return this
        }

    }
}

class V2TDiyStateVO : V2TDiyDataVO() {
    class Builder {
        private val vo = V2TDiyStateVO()

        fun build(): V2TDiyStateVO {
            return vo
        }

        fun setState(type: Int): Builder {
            vo.type = type
            return this
        }

        fun setSender(sender: ContactItemBean?): Builder {
            vo.sender = sender
            return this
        }

        fun setAction(action: String?): Builder {
            vo.action = action
            return this
        }
    }
}

class V2TDiyTimeVO : V2TDiyDataVO() {

    class Builder {
        private val vo = V2TDiyTimeVO()

        fun build(): V2TDiyTimeVO {
            return vo
        }

        fun setTime(time: Long?): Builder {
            vo.time = time
            return this
        }

        fun setUserList(userList: ArrayList<ContactItemBean>?): Builder {
            vo.userList = userList
            return this
        }
    }
}


class V2TDiyQAVO : V2TDiyDataVO() {
    class Builder {
        private val vo = V2TDiyWishAssistVO()

        fun build(): V2TDiyWishAssistVO {
            return vo
        }

        fun setWishId(wishId: Long?): Builder {
            vo.wishId = wishId
            return this
        }

        fun setIcon(icon: String?): Builder {
            vo.icon = icon
            return this
        }

        fun setTime(time: Long?): Builder {
            vo.time = time
            return this
        }

    }
}

