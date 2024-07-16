package com.example.base.base

import androidx.lifecycle.MutableLiveData
import com.example.base.base.user.UserDataBaseUtils
import com.example.base.base.user.entity.UserInfo
import com.example.base.localstore.MMKVConstanst
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.AesUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.UserGrowthInfoDataBaseUtils


/**
 * Created by liyi
 */
object User {
    const val TYPE_OPEN_OFF = 0
    const val TYPE_OPEN_ON = 1
    const val TYPE_OPEN_TAKE = 2

    private val TAG = "User.javaClass.simpleName"

    var currentUser: MutableLiveData<UserInfo?> = MutableLiveData<UserInfo?>()



    /**
     * 是否开启密友
     */
    var isOpenMiYou = MutableLiveData(TYPE_OPEN_OFF)

    var isHideLiked = MutableLiveData(TYPE_OPEN_OFF)
    var isHideXunmi = MutableLiveData(TYPE_OPEN_OFF)


    /**
     * IM  登录相关回调
     */
    //IM登录成功 true
    var imgLoginStatus = MutableLiveData(false)
    //IM退出登录，如果退出登录成功 true
    var imOutLoginStatus = MutableLiveData(false)







    /**
     * isLogin是否登录
     */
    val isLogin: Boolean
        get() = roleNum > 0

    /**
     * 是否开启密友筛选
     */
    val isOpenMiYouShield: Boolean
        get() = isOpenMiYou.value == TYPE_OPEN_TAKE

    /**
     * 是否开启收藏屏蔽
     */
    val isOpenMiYouLiked: Boolean
        get() = isHideLiked.value == TYPE_OPEN_ON && isOpenMiYouShield

    /**
     * 是否开启寻觅屏蔽
     */
    val isOpenXunmi: Boolean
        get() = isHideXunmi.value == TYPE_OPEN_ON && isOpenMiYouShield

    val avatar: String
        get() = currentUser.value?.avatar ?: ""

    val token: String
        get() = if (currentUser.value?.accessToken ?.isNotEmpty() == true) AesUtils.decrypt(
            currentUser.value?.accessToken,
            secretKey
        ) else ""
    //get() = currentUser.value?.accessToken ?: ""
    var secretKey: String = ""
    val hxToken: String
        get() = currentUser.value?.hxAccessToken ?: ""

    val uid: Long
        get() = currentUser.value?.userId ?: 0

    val headPic: String
        get() = currentUser.value?.headPic ?: ""

    val ridString: String
        get() = currentUser.value?.ridStr ?: ""

    val oursId: String
        get() = currentUser.value?.oursId ?: ""

    val nickname: String
        get() = currentUser.value?.nickname ?: ""

    val sex: Int
        get() = currentUser.value?.gender ?: 0
    val inviteCode: String
        get() = currentUser.value?.inviteCode ?: ""



    /**
     * isLoginRole是否登录角色
     */
    val isLoginRole: Boolean
        get() = roleNum > 0 && ridString.isNotEmpty()

    val roleNum: Int
        get() = UserDataBaseUtils.getInstance().queryUserInfoCount()

    val birthday: String
        get() = currentUser.value?.birthday ?: ""

    val age: String
        get() = currentUser.value?.age ?: "0"

    val looks: String
        get() = currentUser.value?.looks ?: ""

    val looksHead: String
        get() = currentUser.value?.looksHead ?: ""

    val spaceData: String
        get() = currentUser.value?.spaceData ?: ""

    val collecteds: Long
        get() = currentUser.value?.collecteds ?: 0L
    val thumbsUpeds: Long
        get() = currentUser.value?.thumbsUpeds ?: 0L

    val fans: Int
        get() = currentUser.value?.fans ?: 0

    val followings: Int
        get() = currentUser.value?.followings ?: 0


    val points: Int
        get() = currentUser.value?.points ?: 0

    val eggs: Int
        get() = currentUser.value?.eggs ?: 0

    val showBg: String
        get() = currentUser.value?.showBg ?: "0"
    val isGM: Boolean
        get() = currentUser.value?.isGM == 1

    fun output() {
        secretKey = MMKVSpUtils.getString(MMKVConstanst.OURS_APP_SECRET, "")
        LogUtils.w("EMClient", "User-output--secretKey:${secretKey}")
        val info = UserDataBaseUtils.getInstance().queryCurrentInfo()
        info?.let { info ->
            currentUser.value = info
        }
    }

    fun logout() {
        LogUtils.w("EMClient", "User-logout")
        UserDataBaseUtils.getInstance().logout()
        currentUser.value = null
    }

    fun clearUserList() {
        UserDataBaseUtils.getInstance().clear()
        UserGrowthInfoDataBaseUtils.getInstance().clear()
    }

}
