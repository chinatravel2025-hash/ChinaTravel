package com.travel.guide.viewmodel

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.base.base.User
import com.example.base.localstore.MMKVConstanst
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.BirthdayUtil
import com.example.http.api.*
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleChatViewModel : ChatViewModel() {

    var isChatType = MutableLiveData(1)
    var age = MutableLiveData("")
    var sex = MutableLiveData(0)
    var isNotFriend = MutableLiveData(false)

    //默认为空
    var content = MutableLiveData("")

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.Main).launch {
                /*if (item != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    nickname.value = item?.displayName ?: ""
                    headPic.value = item?.avatarUrl ?: ""
                    avatar.value = item?.bodyData ?: ""
                    sex.value = item?.gener ?: 0
                    age.value = BirthdayUtil.birthdayToAge(item.birthday ?: "")
                }
            }*/
                /*UserRoleRepository.repository.getUserInfo(userId) { user, bg ->
                user?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (item != null) {
                            //这两个字段腾讯数据库拿，所以以腾讯云为准.
                            //item?.nickName = user.nickname ?: ""
                            //item?.avatarUrl = user.headPic ?: ""
                            item.bodyData = user.avatar ?: ""
                            item?.birthday = user.birthday ?: ""
                            item?.spaceData = user.spaceData ?: ""
                        }
                    }
                    if (nickname.value.isNullOrEmpty()) {
                        nickname.value = user.nickname ?: ""
                    }
                    if(headPic.value == "")
                        headPic.value = user.headPic ?: ""
                    avatar.value = user.avatar ?: ""
                    sex.value = user.gender
                    age.value = BirthdayUtil.birthdayToAge(user.birthday ?: "")
                    showBg.value = bg
                    isVip.value = user.memberStatus

                    if(isSelf.value == false){
                        emoSwitchState.value = user.esCommentSwitch
                        getNowEmoState(userId)
                    }
                }
                null*/
            }
        }

    }

}