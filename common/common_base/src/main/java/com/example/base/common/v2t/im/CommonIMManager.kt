package com.example.base.common.v2t.im

import android.content.Context
import android.text.TextUtils
import android.util.Pair
import com.example.base.common.v2t.im.chat.presenter.ChatPresenter
import com.example.base.common.v2t.im.conversation.bean.ConversationInfo
import com.example.base.common.v2t.im.conversation.model.ConversationProvider
import com.example.base.common.v2t.im.conversation.util.ConversationUtils
import com.example.base.common.v2t.im.core.UILogin
import com.example.base.common.v2t.im.core.interfaces.IMUICallback
import com.example.base.common.v2t.im.core.interfaces.TUILoginConfig
import com.example.base.common.v2t.im.core.interfaces.UILoginListener
import com.example.base.common.v2t.im.friend.TUIContactService
import com.example.base.common.v2t.im.friend.bean.FriendApplicationBean
import com.example.base.common.v2t.im.friend.bean.FriendGroupInfo
import com.example.base.common.v2t.im.friend.bean.FriendOperationResult
import com.example.base.common.v2t.im.friend.bean.OursFriendCheckResult
import com.example.base.common.v2t.im.friend.interfaces.ContactEventListener
import com.example.base.common.v2t.im.friend.model.ContactProvider
import com.example.base.common.v2t.im.interfaces.IUIKitCallback
import com.example.base.database.friend.entity.ContactItemBean
import com.example.base.utils.LogUtils
import com.tencent.imsdk.v2.V2TIMCallback
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.imsdk.v2.V2TIMConversationResult
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMValueCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object CommonIMManager {

    private val TAG = CommonIMManager.javaClass.simpleName

    //初始化各种模块

    /**
     * 登录相关模块
     */
    fun login(
        context: Context,
        userId: String,
        userSig: String,
        appId: Int,
        uiIMUICallback: IMUICallback? = null
    ) {
        val tuiLoginConfig = TUILoginConfig()
        tuiLoginConfig.logLevel = TUILoginConfig.TUI_LOG_INFO
        UILogin.login(context, appId, userId, userSig, tuiLoginConfig, uiIMUICallback)
    }

    fun loginOut(uiIMUICallback: IMUICallback? = null) {
        UILogin.logout(uiIMUICallback)
    }


    fun addLoginListener(uiLoginListener: UILoginListener) {
        UILogin.addLoginListener(uiLoginListener)
    }

    fun removeLoginListener(uiLoginListener: UILoginListener) {
        UILogin.removeLoginListener(uiLoginListener)
    }


    //用户是否已登录
    fun isLogin():Boolean{
        return UILogin.isUserLogined()
    }

    fun getLoginUser():String{
        return UILogin.getLoginUser()
    }

    /**
     * 拿到这个模块可以做更多操作
     * 登录，退登
     * 踢下线,, 连接中，连接失败等操作都在.
     */
    fun getUILogin(): UILogin {
        return UILogin.getInstance()
    }


    /**
     * 判断是否是群组
     */
    fun isGroupByConversationId(conversationId: String): Boolean {
        if (conversationId.startsWith("group_")) {
            return true
        } else {
            return false
        }
    }


    /**
     * conversationType
     * 1: 单聊
     * 2： 群组
     *
     */
    fun convertUserIdToConversationId(isGroup: Boolean, userId: String): String {
        if (userId.startsWith("c2c_") || userId.startsWith("group_")) {
            return userId
        }
        var conversationId = if (!isGroup) {
            "c2c_$userId"
        } else {
            "group_$userId"
        }
        return conversationId
    }

    /**
     *
     */
    fun convertConversationIdToUserId(conversationId: String): String {
        if (conversationId.startsWith("c2c_")) {
            return conversationId.substring(4)
        } else if (conversationId.startsWith("group_")) {
            return conversationId.substring(6)
        }
        return conversationId
    }


    /**
     * 获取第一页会话信息
     */
    fun getFirstConversation(callback: ((MutableList<V2TIMConversation>?) -> Unit?)?) {
        V2TIMManager.getConversationManager()
            .getConversationList(0, 100, object : V2TIMValueCallback<V2TIMConversationResult> {
                override fun onSuccess(t: V2TIMConversationResult?) {
                    callback?.invoke(t?.conversationList)
                }

                override fun onError(code: Int, desc: String) {
                    callback?.invoke(null)
                }
            })
    }

    /**
     * 获取会话信息
     */
    fun getConversationById(conversationId: String, callback: ((ConversationInfo?) -> Unit?)?) {
        V2TIMManager.getConversationManager()
            .getConversation(conversationId, object : V2TIMValueCallback<V2TIMConversation?> {
                override fun onSuccess(v2TIMConversation: V2TIMConversation?) {
                    callback?.invoke(ConversationUtils.convertV2TIMConversation(v2TIMConversation))
                }

                override fun onError(code: Int, desc: String) {
                    callback?.invoke(null)
                }
            })

    }


    /**
     * 通过ids 获取 会话列表.
     */
    fun getConversationByIds(
        conversationIds: List<String>,
        callback: ((List<ConversationInfo>?) -> Unit?)?
    ) {
        V2TIMManager.getConversationManager()
            .getConversationList(
                conversationIds,
                object : V2TIMValueCallback<List<V2TIMConversation>> {
                    override fun onSuccess(v2TIMConversation: List<V2TIMConversation>) {
                        callback?.invoke(
                            ConversationUtils.convertV2TIMConversationList(
                                v2TIMConversation
                            )
                        )
                    }

                    override fun onError(code: Int, desc: String) {
                        callback?.invoke(null)
                    }
                })

    }


    /**
     * 获取所有会话列表
     */
    fun getAllConversationList(callback: IUIKitCallback<MutableList<ConversationInfo>>) {
        ConversationProvider().loadAllConversations(callback)
    }


    fun deleteConversation(conversationId: String, callBack: IUIKitCallback<Void>? = null) {
        ConversationProvider.deleteConversation(conversationId, callBack)
    }


    /**
     * 设置消息免打扰
     */
    fun setC2CReceiveMessageOpt(
        userIds: List<String>,
        isOpen: Boolean,
        callback: ((isSuccess: Boolean) -> Unit?)? = null
    ) {
        V2TIMManager.getMessageManager().setC2CReceiveMessageOpt(
            userIds,
            if (isOpen) V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE else V2TIMMessage.V2TIM_RECEIVE_MESSAGE,
            object : V2TIMCallback {
                override fun onSuccess() {
                    callback?.invoke(true)
                }

                override fun onError(code: Int, desc: String) {
                    callback?.invoke(false)
                }
            })

    }

    fun setGroupReceiveMessageOpt(
        groupId: String,
        isOpen: Boolean,
        callback: ((isSuccess: Boolean) -> Unit?)? = null
    ) {
        V2TIMManager.getMessageManager().setGroupReceiveMessageOpt(
            groupId,
            if (isOpen) V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE else V2TIMMessage.V2TIM_RECEIVE_MESSAGE,
            object : V2TIMCallback {
                override fun onSuccess() {
                    callback?.invoke(true)
                }

                override fun onError(code: Int, desc: String) {
                    callback?.invoke(false)
                }
            })

    }


    /**
     * 标记会话
     * enable 标记: true，取消标记：false
     * callback:
     */
    fun markConversations(
        conversationIds: List<String>,
        markType: Long,
        enable: Boolean,
        callback: IUIKitCallback<Void>?
    ) {
        ConversationProvider.markConversations(conversationIds, markType, enable, callback)
    }


    /**
     *置顶或取消置顶
     * isTop: true 置顶, false 取消置顶
     */
    fun setTopConverstaion(
        conversationId: String, isTop: Boolean, isGroup: Boolean,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getConversationManager()
            .pinConversation(
                convertUserIdToConversationId(isGroup, conversationId),
                isTop,
                object : V2TIMCallback {
                    override fun onSuccess() {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(true)
                        }
                    }

                    override fun onError(code: Int, desc: String) {
                        CoroutineScope(Dispatchers.Main).launch {
                            callback?.invoke(false)
                        }
                    }
                })
    }


    /**
     * 清除未读消息
     */
    fun clearUnreadMessageCount(conversationId: String, callback: IUIKitCallback<Void?>?) {

        if(TextUtils.isEmpty(conversationId)){
            callback?.onSuccess(null);
            return
        }
        ConversationProvider().clearUnreadMessage(conversationId, callback)
    }


    /**
     * 联系人相关的一些事件监听，比如添加到好友,好友删除监听
     */
    fun addContactEventListener(contactEventListener: ContactEventListener) {
        TUIContactService.getInstance().addContactEventListener(contactEventListener)
    }

    /**
     * 联系人事件监听删除.
     */
    fun removeContactEventListener(contactEventListener: ContactEventListener) {
        TUIContactService.getInstance().removeContactEventListener(contactEventListener)
    }

    /**
     * 添加好友
     */
    fun addFriend(
        userId: String,
        addWording: String? = null,
        addSource: String,
        friendGroup: String? = null,
        remark: String? = null,
        callback: IUIKitCallback<Pair<Int, String>>
    ) {
        ContactProvider.addFriend(userId, addWording, addSource, friendGroup, remark, callback)
    }


    /**
     * 删除一些人.
     */
    fun deleteFriend(userIds: List<String>, callback: IUIKitCallback<Void>) {
        ContactProvider.deleteFriend(userIds, callback)
    }


    /**
     * 拉黑某人
     */
    fun addToBlackList(ids: List<String>, callback: IUIKitCallback<Void>?) {
        ContactProvider.addToBlackList(ids, callback)
    }


    /**
     * 恢复
     */
    fun deleteFromBlackList(userIds: List<String>, callback: IUIKitCallback<Void>) {
        ContactProvider.deleteFromBlackList(userIds, callback)
    }


    /**
     * 接收好友申请
     * 双边会收到onFriendListAdded 回调.
     */
    fun acceptFriendApplication(
        friendApplicationBean: FriendApplicationBean?,
        callback: IUIKitCallback<Void?>?
    ) {
        ContactProvider().acceptFriendApplication(
            friendApplicationBean,
            FriendApplicationBean.FRIEND_ACCEPT_AGREE_AND_ADD, callback
        )

    }

    /**
     * 拒绝好友申请
     * onFriendApplicationListDeleted 双边会收到这个回调
     */
    fun refuseFriendApplication(
        friendApplicationBean: FriendApplicationBean?,
        callback: IUIKitCallback<Void?>?
    ) {
        ContactProvider().refuseFriendApplication(friendApplicationBean, callback)
    }

    /**
     * 拒绝好友申请
     * onFriendApplicationListDeleted 双边会收到这个回调
     */
    fun deleteFriendApplication(
        friendApplicationBean: FriendApplicationBean?,
        callback: IUIKitCallback<Void?>?
    ) {
        ContactProvider().deleteFriendApplication(friendApplicationBean, callback)
    }


    /**
     * 加载申请的好友列表
     */
    fun loadFriendApplicationList(callback: IUIKitCallback<List<FriendApplicationBean>>) {
        ContactProvider().loadFriendApplicationList(callback)
    }


    fun checkFriend(
        userIds: List<String>,
        relationType: Int,
        callback: IUIKitCallback<List<OursFriendCheckResult>>
    ) {
        ContactProvider.checkFriend(userIds, relationType, callback)
    }

    /**
     * 加载好友列表.
     */
    fun loadFriendList(callback: IUIKitCallback<List<ContactItemBean>>) {
        ContactProvider().loadFriendListDataAsync(callback)
    }

    /**
     * 黑名单列表.
     */
    fun loadBlackList(callback: IUIKitCallback<List<ContactItemBean>>) {
        ContactProvider.loadBlackListData(callback)
    }

    /**
     * 获取指定好友的信息
     */
    fun getFriendInfos(userIds: List<String>, callback: IUIKitCallback<List<ContactItemBean>>) {
        ContactProvider().getFriendInfos(userIds, callback)
    }


    /**
     * 修改好友备注
     */
    fun setFriendRemark(userId: String, remark: String, callback: IUIKitCallback<Void>) {
        ContactProvider.setFriendRemark(userId, remark, callback)
    }


    /**
     * 修改好友自定义字段
     */
    fun setFriendCustomInfo(
        userId: String,
        customInfo: HashMap<String, ByteArray>,
        callback: IUIKitCallback<Void>
    ) {
        ContactProvider.setFriendInfo(userId, customInfo, callback)
    }





    /**
     * 新建好友分组
     * @param userIDList
     * @param groupName
     * @param callback
     */
    fun newFriendGroup(
        userIDList: List<String?>,
        groupName: String,
        callback: IUIKitCallback<List<FriendOperationResult?>>?
    ) {
        ContactProvider.newFriendGroup(userIDList, groupName, callback)
    }

    /*
     * 删除好友分组
     */
    fun deleteFriendGroup(friendGroupList: List<String?>?, callback: IUIKitCallback<Void?>?) {
        ContactProvider.deleteFriendGroup(friendGroupList, callback)
    }


    /**
     * 重命名好友分组
     * @param oldName
     * @param newName
     * @param callback
     */
    fun renameFriendGroup(oldName: String, newName: String, callback: IUIKitCallback<Void?>?) {
        ContactProvider.renameFriendGroup(oldName, newName, callback)
    }


    /**
     * 获取好友分组
     * @param groupNames
     */
    fun getFriendGroups(
        groupNames: List<String>?,
        callback: IUIKitCallback<List<FriendGroupInfo>>?
    ) {
        ContactProvider.getFriendGroups(groupNames, callback)
    }


    /**
     * 添加好友到分组
     * @param groupName
     * @param userIds
     * @param callback
     */
    fun addFriendsToGroup(
        groupName: String,
        userIds: List<String>,
        callback: IUIKitCallback<List<FriendOperationResult>>?
    ) {
        ContactProvider.addFriendsToGroup(groupName, userIds, callback)
    }


    /**
     * 从分组中删除好友
     * @param userIDList
     * @param groupName
     */
    fun deleteFriendsFromFriendGroup(
        userIDList: List<String>,
        groupName: String,
        callback: IUIKitCallback<List<FriendOperationResult>>?
    ) {
        ContactProvider.deleteFriendsFromFriendGroup(userIDList, groupName, callback)
    }








    /**
     * 搜索好友
     */
    fun searchFriends(
        keyWords: List<String>,
        callback: IUIKitCallback<MutableList<ContactItemBean>>
    ) {
        ContactProvider.searchFriends(keyWords, callback)
    }


    fun testAddFriend() {
        //liuzhiming
        //@USR#Xh405EhPodM  。。。
        //@USR#5M5JjG_UXSs 吴关俊
        //@USR#sme5T67e2Yc
        addFriend(
            "@USR#sme5T67e2Yc",
            addSource = "测试用途",
            callback = object : IUIKitCallback<Pair<Int, String>>() {
                override fun onError(errCode: Int, errMsg: String?) {
                    super.onError(errCode, errMsg)
                }

                override fun onSuccess(data: Pair<Int, String>?) {
                    super.onSuccess(data)
                    LogUtils.i(TAG, "code = ${data?.first} message = ${data?.second}")
                }
            })
    }

    fun getUserContacts(userIds: List<String>, callback: IUIKitCallback<List<ContactItemBean>>) {
        ContactProvider().getUserInfo(userIds, callback)
    }

    fun testGetUsersInfo() {
        /**
         * liuzhiming
         * //@USR#5M5JjG_UXSs 吴关俊
         * //@USR#Xh405EhPodM 另一个 吧...
         * //@USR#sme5T67e2Yc 吴关俊2号.
         */
        getUserContacts(arrayListOf(
            "liuzhiming",
            "@USR#5M5JjG_UXSs",
            "@USR#Xh405EhPodM",
            "@USR#sme5T67e2Yc"
        ),
            object : IUIKitCallback<List<ContactItemBean>>() {

                override fun onSuccess(data: List<ContactItemBean>?) {
                    super.onSuccess(data)
                }

                override fun onError(errCode: Int, errMsg: String?) {
                    super.onError(errCode, errMsg)
                }
            })
    }

    /**
     * 好友模块
     * 拿到这个模块可以获取所有你想要的事件监听.
     */
    private var chatPresenter: ChatPresenter? = null
    fun getChatPresenter(): ChatPresenter {
        synchronized(this) {
            if (chatPresenter == null) {
                chatPresenter = ChatPresenter()
            }
        }
        return chatPresenter!!
    }


    /**
     * 群组管理模块
     */
}