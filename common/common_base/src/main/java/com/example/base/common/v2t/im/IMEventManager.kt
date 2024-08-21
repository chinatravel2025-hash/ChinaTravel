package com.example.base.common.v2t.im

import com.example.base.common.v2t.im.core.TUIConstants
import com.example.base.common.v2t.im.core.TUICore

object IMEventManager {


    fun notiyFriendRemarkChange(userId: String, remark: String){
        val map: MutableMap<String, Any> = HashMap()
        map[TUIConstants.TUIContact.FRIEND_ID] = userId
        map[TUIConstants.TUIContact.FRIEND_REMARK] = remark
        TUICore.notifyEvent(
            TUIConstants.TUIContact.EVENT_FRIEND_INFO_CHANGED,
            TUIConstants.TUIContact.EVENT_SUB_KEY_FRIEND_REMARK_CHANGED,
            map
        )
    }

}