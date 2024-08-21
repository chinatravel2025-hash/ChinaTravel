package com.travel.guide.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.base.base.User
import com.example.base.localstore.MMKVSpUtils

open class ChatViewModel : ViewModel() {

    var isInputType = MutableLiveData(false)

    var isDoubleClickHasValue = MutableLiveData(false)

    var isVoiceType = MutableLiveData(0)

    var isTouchTopType = MutableLiveData(false)

    var isDoubleClickMode = MutableLiveData(false)

    var isOursMode = MutableLiveData(true)
    var nickname = MutableLiveData("")

    var headPic = MutableLiveData("")

    var newMsgCount = MutableLiveData(0)

    var newMsgStartIndex = MutableLiveData(0)

    var isQuote = MutableLiveData(false)

    var quoteTxt = MutableLiveData("")

    var quoteMsgId = MutableLiveData("")

    var avatar = MutableLiveData("")

    var showBg = MutableLiveData("")

    var isVip = MutableLiveData(0)

    var isDialogMode = MutableLiveData(false)

    var isSmallWindow = MutableLiveData(true)

    /**
     * 是否是话题
     */
    var isTopic = MutableLiveData(false)
    /**
     * 是否是群聊
     */
    var isGroup = MutableLiveData(false)

    val voiceTypeKey = "VoiceType_${User.ridString}"
}