package com.travel.guide.common

import android.text.TextUtils
import com.example.base.base.App
import com.example.base.base.SDKConstant
import com.example.base.base.User
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.common.v2t.im.core.interfaces.IMUICallback
import com.example.base.common.v2t.im.core.interfaces.UILoginListener
import com.example.base.localstore.MMKVConstanst
import com.example.base.utils.LogUtils
import com.example.base.utils.ThreadUtils

class LoginRepository {


    companion object {
        val repository = LoginRepository()
        val TAG = "LoginRepository"
    }


    private val imConnectionListeners = arrayListOf<IMConnectionListener>()
    private val imLoginStatusListeners = arrayListOf<IMLoginStatusListener>()
    private val loginStatusListeners = arrayListOf<LoginStatusListener>()
    private val kickedOfflineListeners = arrayListOf<KickedOfflineListener>()


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
            if(isCurrentUserLogined()){
                //FriendsRepository.repository.initFriendRespository()
            }else {  //如果没有登录重试重连.
                if(isFirstTryImlogin){
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
                l.onConnectFailed(code, error ?: "未知错误")
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
            if(TextUtils.equals(CommonIMManager.getLoginUser(),User.ridString)){

            }
            LogUtils.i(TAG, "IMLogin 被踢用户 loginUser = ${CommonIMManager.getLoginUser()} ridStr = ${User.ridString}")

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


    fun isCurrentUserLogined():Boolean{

        if(CommonIMManager.isLogin() && TextUtils.equals(CommonIMManager.getLoginUser(),User.ridString)){
            return true
        }

        return false
    }




    /**
     * im登录，正常启动后走账号切换和退登流程，再登录就是这个； app 刚启动时候走quickImLogin 流程...为了快速获取数据初始化.
     */
    fun imLogin() {
        /*if (!User.isLoginRole) {
            return
        }
*/
        tryImLogin()
    }

    private var isIMLogining = false
    fun tryImLogin(){
/*
        if (!User.isLoginRole) {
            return
        }
*/

        LogUtils.i(TAG, "IMLogin tryImLogin isIMLogining = ${isIMLogining} ")

        if(isIMLogining){
           return
        }
        isIMLogining = true

        LogUtils.i(TAG, "IMLogin tryImLogin ")
        if(isCurrentUserLogined()){
            LogUtils.i(TAG, "IMLogin hasLogin = true 直接执行下面的.")
            afterIMLogin()
            isIMLogining = false
            return
        }

        Thread{
           // val imLoginInfoStr = MMKVStore.getString(MMKVConstanst.IM_LOGIN_INFO_KEY)
          //  var userId = User.oursId//todo 测试
            var userSig = User.hxToken//todo 测试
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
                    imLoginOnFailed(-1, "未获取到登录信息")
                    isIMLogining = false
                    LogUtils.i(TAG, "IMLogin failed  用户签名信息为null")
                    return@runOnUiThread
                }

                CommonIMManager.login(App.getContext(), "", userSig,
                    SDKConstant.HX_APP_ID_TEST, uiIMUICallback = object :
                        IMUICallback() {
                        override fun onSuccess() {
                            //去掉eventbus 使用liveData 和 接口监听去做事件处理
                            User.imgLoginStatus.value = true
                            LogUtils.i(TAG, "IMLogin Success tryImLogin")
                            afterIMLogin()
                            isIMLogining = false
                        }

                        override fun sdkInitFinish(isSuccess: Boolean) {
                            if(isSuccess){
                                imLogining()
                            }
                        }

                        override fun onError(errorCode: Int, errorMessage: String?) {
                            User.imgLoginStatus.value = false
                            isIMLogining = false
                            LogUtils.i(
                                TAG,
                                "IMLogin failed errorCode = ${errorCode} error = ${errorMessage} " +
                                        " userSig = ${userSig}"
                            )
                            imLoginOnFailed(errorCode, errorMessage ?: "未知错误")
                        }
                    })
            }
        }.start()
    }

    private fun afterIMLogin() {
        LogUtils.i(TAG, "TIMPushManager afterIMLogin")
        imLoginOnSuccess()
        //V2TMPush.registerPush(App.getContext())
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
                            imLoginOnFailed(errorCode, errorMessage ?: "未知错误")
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