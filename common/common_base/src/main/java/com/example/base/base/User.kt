package com.example.base.base

import androidx.lifecycle.MutableLiveData
import com.aws.bean.util.GsonUtil
import com.example.base.localstore.MMKVConstanst
import com.example.base.localstore.MMKVSpUtils


/**
 * Created by liyi
 */
object User {
    const val TYPE_OPEN_OFF = 0
    const val TYPE_OPEN_ON = 1
    const val TYPE_OPEN_TAKE = 2


    private val TAG = "User.javaClass.simpleName"

    var currentUser: MutableLiveData<UserInfo?> = MutableLiveData<UserInfo?>()


    var tpToken = ""

    /**
     * IM  登录相关回调
     */
    //IM登录成功 true
    var imLoginStatus = MutableLiveData(false)

    //IM退出登录，如果退出登录成功 true
    var imOutLoginStatus = MutableLiveData(false)

    /**
     * isLogin是否登录
     */
    val isLogin: Boolean
        get() = !currentUser.value?.token.isNullOrEmpty()


    val hxToken: String
        get() = currentUser.value?.imToken ?: ""

    val uid: String
        get() = currentUser.value?.uid ?: ""

    val mail: String
        get() = currentUser.value?.mail ?: ""

    val token: String
        get() = tpToken.ifEmpty { currentUser.value?.token ?: "" }

    fun output() {
        val userInfo = MMKVSpUtils.getString(MMKVConstanst.USER_INFO, "")
        if (!userInfo.isNullOrEmpty()) {
            currentUser.value = GsonUtil.fromJson(userInfo, UserInfo::class.java)
        }

    }

    fun logout() {
        currentUser.value = null
        MMKVSpUtils.putString(MMKVConstanst.USER_INFO, "")
        MMKVSpUtils.putString(MMKVConstanst.IM_SIGH, "")
    }


    fun saveUserInfo(userInfo: UserInfo) {
        MMKVSpUtils.putString(MMKVConstanst.USER_INFO, GsonUtil.toJson(userInfo))
        currentUser.value = userInfo
    }

}
