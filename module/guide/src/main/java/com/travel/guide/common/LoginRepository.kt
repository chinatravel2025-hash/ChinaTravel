package com.travel.guide.common

import ChatAPI
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.aws.bean.util.GsonUtil
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.App
import com.example.base.base.SDKConstant
import com.example.base.base.User
import com.example.base.base.UserInfo
import com.example.base.common.v2t.V2TGroupManager
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.common.v2t.im.commom.util.MessageBuilder
import com.example.base.common.v2t.im.core.interfaces.IMUICallback
import com.example.base.common.v2t.im.core.interfaces.UILoginListener
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.LogUtils.e
import com.example.base.utils.ThreadUtils
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.google.gson.JsonSyntaxException
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo
import com.travel.guide.api.ChatImpApi
import com.travel.guide.api.IMChatInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {


    companion object {
        val repository = LoginRepository()
        val TAG = "LoginRepository"
        val IMCHATLIST = "im_chat_list"
    }


    private val imConnectionListeners = arrayListOf<IMConnectionListener>()
    private val imLoginStatusListeners = arrayListOf<IMLoginStatusListener>()
    private val loginStatusListeners = arrayListOf<LoginStatusListener>()
    private val kickedOfflineListeners = arrayListOf<KickedOfflineListener>()


    private val loginAPI = RequestManager.build(ChatImpApi().host()).create(ChatAPI::class.java)
    val imInfo = MutableLiveData<IMChatInfo?>(null)
    var mGroupId = ""


    interface KickedOfflineListener {
        fun onKickedOff()
    }

    fun addKicedOffLineListener(listener: KickedOfflineListener) {
        if (kickedOfflineListeners.contains(listener)) {
            return
        }
        kickedOfflineListeners.add(listener)
    }

    fun removeKicedOffLineListener(listener: KickedOfflineListener) {
        kickedOfflineListeners.remove(listener)
    }


    interface IMConnectionListener {
        fun onSuccess() {}
        fun onConnecting() {}
        fun onConnectFailed(code: Int, error: String) {}
    }

    interface LoginStatusListener {

        companion object {
            val COMMOM_FAILED = -1
        }

        //logOnSuccess().
        fun logOnSuccess() {}

        fun logOnFailed(code: Int, message: String) {}

        //登录中
        fun logining() {}

        fun logouting() {}

        fun logoutFailed(code: Int, message: String) {}

        fun logoutSuccess() {}
    }

    fun logining() {
        for (l in loginStatusListeners) {
            l.logining()
        }
    }

    fun loginOnSuccess() {
        for (l in loginStatusListeners) {
            l.logOnSuccess()
        }
        LogUtils.i(TAG, "loginOnSuccess 成功")
    }

    fun loginOnFailed(code: Int, message: String) {
        for (l in loginStatusListeners) {
            l.logOnFailed(code, message)
        }
    }

    interface IMLoginStatusListener {

        //在im 登录操作之前，或许我们首先是清理一遍数据.
        fun beforeLogin() {}

        fun onSuccess() {}

        fun onFailed(code: Int, message: String) {}

        //登录中
        fun logining() {}

        fun onLoginOutSuccess() {}

        fun onLoginOutFailed(code: Int, message: String?) {}
    }

    fun imLogining() {
        for (l in imLoginStatusListeners) {
            l.logining()
        }
    }

    fun imLoginOnSuccess() {
        for (l in imLoginStatusListeners) {
            l.onSuccess()
        }
    }

    fun imLoginOnFailed(code: Int, message: String) {
        for (l in imLoginStatusListeners) {
            l.onFailed(code, message)
        }
    }


    fun addIMConnectionListener(listener: IMConnectionListener) {
        if (imConnectionListeners.contains(listener)) {
            return
        }
        imConnectionListeners.add(listener)
    }

    fun removeIMConnectionListener(listener: IMConnectionListener) {
        imConnectionListeners.remove(listener)
    }

    fun addIMLoginStatusListener(listener: IMLoginStatusListener) {
        if (imLoginStatusListeners.contains(listener)) {
            return
        }
        imLoginStatusListeners.add(listener)
    }

    fun removeIMLoginStatusListener(listener: IMLoginStatusListener) {
        imLoginStatusListeners.remove(listener)
    }

    fun addLoginStatusListener(listener: LoginStatusListener) {
        if (loginStatusListeners.contains(listener)) {
            return
        }
        loginStatusListeners.add(listener)
    }

    fun removeLoginStatusListener(listener: LoginStatusListener) {
        loginStatusListeners.remove(listener)
    }

    private var isFirstTryImlogin = true
    private val imLoginListener = object : UILoginListener() {
        override fun onConnectSuccess() {
            super.onConnectSuccess()
            LogUtils.i(TAG, "IMLogin onConnectSuccess")
            //已登录发起重试.
            if (isCurrentUserLogined()) {
                //FriendsRepository.repository.initFriendRespository()
            } else {  //如果没有登录重试重连.
                if (isFirstTryImlogin) {
                    isFirstTryImlogin = false
                    return
                }
                tryImLogin()
            }
            for (l in imConnectionListeners) {
                l.onSuccess()
            }
        }

        override fun onConnectFailed(code: Int, error: String?) {
            super.onConnectFailed(code, error)
            LogUtils.i(TAG, "onConnectFailed code = ${code} , error = ${error ?: ""}")
            for (l in imConnectionListeners) {
                l.onConnectFailed(code, error ?: "Unknown error")
            }
        }

        override fun onConnecting() {
            super.onConnecting()
            LogUtils.i(TAG, "onConnecting")
            for (l in imConnectionListeners) {
                l.onConnecting()
            }
        }

        override fun onKickedOffline() {
            super.onKickedOffline()
            //相同的用户id 才会被踢掉.
            if (TextUtils.equals(CommonIMManager.getLoginUser(), User.uid)) {

            }
            LogUtils.i(
                TAG,
                "IMLogin 被踢用户 loginUser = ${CommonIMManager.getLoginUser()} ridStr = ${User.uid}"
            )

            for (l in kickedOfflineListeners) {
                l.onKickedOff()
            }
        }

        override fun onUserSigExpired() {
            super.onUserSigExpired()
        }
    }

    //init 函数和变量谁在前.
    init {
        CommonIMManager.addLoginListener(imLoginListener)
    }


    fun isCurrentUserLogined(): Boolean {

        if (CommonIMManager.isLogin() && TextUtils.equals(
                CommonIMManager.getLoginUser(),
                User.uid
            )
        ) {
            return true
        }

        return false
    }


    /**
     * im登录，正常启动后走账号切换和退登流程，再登录就是这个； app 刚启动时候走quickImLogin 流程...为了快速获取数据初始化.
     */
    fun imLogin() {
        //getChatList {}
        if (!User.isLogin) {
            return
        }

        tryImLogin()
    }

    private var isIMLogining = false
    fun tryImLogin() {

        if (!User.isLogin || User.imLoginStatus.value == true) {
            LogUtils.i(TAG, "IMLogin tryImLogin imgLoginStatus = ${User.imLoginStatus.value} ")
            return
        }


        LogUtils.i(TAG, "IMLogin tryImLogin isIMLogining = ${isIMLogining} ")

        if (isIMLogining) {
            return
        }
        isIMLogining = true

        LogUtils.i(TAG, "IMLogin tryImLogin ")
        if (isCurrentUserLogined()) {
            LogUtils.i(TAG, "IMLogin hasLogin = true 直接执行下面的.")
            afterIMLogin()
            isIMLogining = false
            return
        }

        Thread {
            var userSig = User.hxToken
            /*if (imLoginInfoStr != "") {
                val imLoginInfo = GsonUtil.fromJson(imLoginInfoStr, IMLoginInfo::class.java)
                if (imLoginInfo != null && !TextUtils.isEmpty(imLoginInfo.userId)) {
                    userId = imLoginInfo.userId
                    userSig = imLoginInfo.userSig
                }
            }*/
            if (TextUtils.isEmpty(userSig)) {
                //获取token2  腾讯云登录放在这里
                /*var imInfo = GrpcTask.newInstance<EmptyReqV2, ImTokenRespV2>(
                    EmptyReqV2.newBuilder()
                        .build()
                ).startPostSync(GrpcClassName.UserGrpc, "imTokenV2")
                if (imInfo != null) {
                    userId = imInfo.data?.userName ?: ""
                    userSig = imInfo.data?.accessToken ?: ""
                    if (!TextUtils.isEmpty(userSig) && !TextUtils.isEmpty(userSig)) {
                        val _info = IMLoginInfo(userId, userSig)
                        MMKVStore.putString(MMKVConstanst.IM_LOGIN_INFO_KEY, GsonUtil.toJson(_info))
                    }
                }*/
            }

            ThreadUtils.runOnUiThread {
                if (userSig.isEmpty()) {
                    imLoginOnFailed(-1, "Failed to get login information")
                    isIMLogining = false
                    LogUtils.i(TAG, "IMLogin failed  用户签名信息为null")
                    return@runOnUiThread
                }

                CommonIMManager.login(App.getContext(), User.uid, userSig,
                    SDKConstant.HX_APP_ID_TEST, uiIMUICallback = object :
                        IMUICallback() {
                        override fun onSuccess() {
                            //去掉eventbus 使用liveData 和 接口监听去做事件处理
                            User.imLoginStatus.value = true
                            LogUtils.i(TAG, "IMLogin Success tryImLogin")
                            afterIMLogin()
                            isIMLogining = false
                        }

                        override fun sdkInitFinish(isSuccess: Boolean) {
                            if (isSuccess) {
                                imLogining()
                            }
                        }

                        override fun onError(errorCode: Int, errorMessage: String?) {
                            User.imLoginStatus.value = false
                            isIMLogining = false
                            LogUtils.i(
                                TAG,
                                "IMLogin failed errorCode = ${errorCode} error = ${errorMessage} " +
                                        " userSig = ${userSig}"
                            )
                            imLoginOnFailed(errorCode, errorMessage ?: "Unknown error")
                        }
                    })
            }
        }.start()
    }

    private fun afterIMLogin() {
        LogUtils.i(TAG, "TIMPushManager afterIMLogin")
        getChatList {
            if (it) {
                val callback: ((String) -> Unit?) = { groupId ->
                    //拉人进群
                    mGroupId = groupId
                    val ids = ArrayList<String>()
                    imInfo.value?.list?.forEach { info ->
                        ids.add(info.customer_id ?: "")
                    }
                    V2TGroupManager.inviteUserToGroup(groupId, ids) {
                        imLoginOnSuccess()
                    }
                }
                //登录成功后获取群组列表
                V2TGroupManager.getJoinedGroupList { groupList ->
                    if (groupList?.isNotEmpty() == true) {
                        //群组列表size>0 直接拉客服id
                        callback.invoke(groupList[0].groupID)
                    } else {
                        //群组列表size<=0 去创建
                        val users = ArrayList<V2TIMCreateGroupMemberInfo>().apply {
                            imInfo.value?.list?.forEach { info ->
                                add(V2TIMCreateGroupMemberInfo().apply {
                                    setUserID(info.customer_id ?: "")
                                })
                            }
                        }
                        if (users.isNotEmpty()) {
                            V2TGroupManager.createGroup("User${User.uid}", "", users) { groupId ->
                                //群组创建成功 直接拉客服id
                                if (groupId?.isNotEmpty() == true) {
                                    callback.invoke(groupId)
                                } else {
                                    imLoginOnFailed(-3, "Group creation failed")
                                }
                            }
                        } else {
                            imLoginOnFailed(-2, "Failed to get customer service list")
                        }
                    }

                }
            }
        }
    }

    /**
     * 获取客服人员
     */
    fun getChatList(callback: (Boolean) -> Unit) {
        if (imInfo.value?.list?.isNotEmpty() == true) {
            callback.invoke(true)
            return
        }
        try {
            MMKVSpUtils.getString(IMCHATLIST, "")?.let { info ->
                if (info.isNotEmpty()) {
                    imInfo.value = GsonUtil.fromJson(info, IMChatInfo::class.java)
                }
            }
        } catch (e: Exception) {

        }
        loginAPI.customerList().enqueue(object :
            Callback<ResponseResult<IMChatInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<IMChatInfo?>>,
                response: Response<ResponseResult<IMChatInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    imInfo.value = response.body()?.data
                    imInfo.value?.let { imChatInfo ->
                        MMKVSpUtils.putString(IMCHATLIST, GsonUtil.toJson(imChatInfo))
                    }
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }

            override fun onFailure(call: Call<ResponseResult<IMChatInfo?>>, t: Throwable) {
                callback.invoke(imInfo.value?.list?.isNotEmpty() == true)
            }
        })
    }


    private var isImLogin = false

    /**
     * im 快速登录..为了提前拉到首页列表数据.
     */
    fun quickIMLogin() {
        /*        if (!User.isLoginRole) {
                    return
                }
                val imLoginInfoStr = MMKVStore.getString(MMKVConstanst.IM_LOGIN_INFO_KEY)
                if (imLoginInfoStr != "") {
                    val imLoginInfo = GsonUtil.fromJson(imLoginInfoStr, IMLoginInfo::class.java)
                    if (imLoginInfo != null && !TextUtils.isEmpty(imLoginInfo.userId) && !TextUtils.isEmpty(
                            imLoginInfo.userSig
                        )
                    ) {
                        var userId = imLoginInfo.userId
                        var userSig = imLoginInfo.userSig
                        CommonIMManager.login(App.getContext(), userId, userSig,
                            SDKConstant.HX_APP_ID_TEST, uiIMUICallback = object :
                                IMUICallback() {
                                override fun onSuccess() {
                                    //去掉eventbus 使用liveData 和 接口监听去做事件处理
                                    User.imgLoginStatus.value = true
                                    LogUtils.i(TAG, "IMLogin Success")
                                    isImLogin = true
                                }

                                override fun onError(errorCode: Int, errorMessage: String?) {
                                    User.imgLoginStatus.value = false
                                    isImLogin = false
                                    LogUtils.i(
                                        TAG,
                                        "IMLogin failed errorCode = ${errorCode} error = ${errorMessage} userId = ${userId}" +
                                                " userSig = ${userSig}"
                                    )
                                    imLoginOnFailed(errorCode, errorMessage ?: "Unknown error")
                                }
                            })
                    }
                    friendPresenter = FriendConversationPresenter()
                    friendPresenter?.preLoad()
                }*/
    }


    fun imLoginOut(callback: ((isSuccess: Boolean, code: Int, message: String?) -> Unit?)? = null) {
        /*CommonIMManager.loginOut(object : IMUICallback() {
            override fun onSuccess() {
                LogUtils.e(TAG, "IMLogin loginOut success")
                V2TMPush.unRegisterPush(App.getContext())
                for (l in imLoginStatusListeners) {
                    l.onLoginOutSuccess()
                }
                callback?.invoke(true, 0, "")
            }

            override fun onError(errorCode: Int, errorMessage: String?) {
                LogUtils.e(TAG, "IMLogin loginOut failed errorCode = ${errorCode} ,errorMessage = ${errorMessage}")
                callback?.invoke(false, errorCode, errorMessage)
                for (l in imLoginStatusListeners) {
                    l.onLoginOutFailed(errorCode, errorMessage)
                }
            }
        })*/
    }

}