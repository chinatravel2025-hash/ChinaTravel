package com.example.base.common.v2t.im.chat;

import android.content.Context;
import android.text.TextUtils;


import com.example.base.common.v2t.im.commom.bean.TUIMessageBean;
import com.example.base.common.v2t.im.core.TUIConstants;
import com.example.base.common.v2t.im.core.TUICore;
import com.example.base.common.v2t.im.core.interfaces.ITUIObjectFactory;
import com.example.base.common.v2t.im.core.interfaces.TUIInitializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class UIChatService implements TUIInitializer, IUIChatService, ITUIObjectFactory {
    public static final String TAG = UIChatService.class.getSimpleName();
    private static UIChatService instance;

    public static UIChatService getInstance() {
        return instance;
    }

    private Context appContext;


    private final Map<String, Class<? extends TUIMessageBean>> customMessageMap = new HashMap<>();
    private final Set<Class<? extends TUIMessageBean>> extensionMessageClass = new HashSet<>();

    @Override
    public void init(Context context) {
        instance = this;
        appContext = context;
        initService();
        initEvent();
        initEventListener();
        initDataStore();
    }


    private void initEventListener(){

    }

    private void initService() {
        TUICore.registerService(TUIConstants.TUIChat.SERVICE_NAME, this);
    }

    private void initDataStore() {

    }


    private void initEvent() {
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_GROUP_INFO_CHANGED, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_EXIT_GROUP, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_MEMBER_KICKED_GROUP, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_GROUP_DISMISS, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_JOIN_GROUP, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_INVITED_GROUP, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_GROUP_RECYCLE, this);
        TUICore.registerEvent(TUIConstants.TUIContact.EVENT_FRIEND_INFO_CHANGED, TUIConstants.TUIContact.EVENT_SUB_KEY_FRIEND_REMARK_CHANGED, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.EVENT_GROUP, TUIConstants.TUIGroup.EVENT_SUB_KEY_CLEAR_MESSAGE, this);
        TUICore.registerEvent(TUIConstants.TUIContact.EVENT_USER, TUIConstants.TUIContact.EVENT_SUB_KEY_CLEAR_MESSAGE, this);
        TUICore.registerEvent(TUIConstants.TUIConversation.EVENT_UNREAD, TUIConstants.TUIConversation.EVENT_SUB_KEY_UNREAD_CHANGED, this);
        TUICore.registerEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGIN_SUCCESS, this);
        TUICore.registerEvent(UIChatConstants.EVENT_KEY_MESSAGE_STATUS_CHANGED, UIChatConstants.EVENT_SUB_KEY_MESSAGE_SEND, this);
        TUICore.registerEvent(UIChatConstants.EVENT_KEY_OFFLINE_MESSAGE_PRIVATE_RING, UIChatConstants.EVENT_SUB_KEY_OFFLINE_MESSAGE_PRIVATE_RING, this);
        TUICore.registerEvent(
            TUIConstants.TUIChat.EVENT_KEY_MESSAGE_EVENT, TUIConstants.TUIChat.EVENT_SUB_KEY_MESSAGE_INFO_CHANGED, this);
        TUICore.registerEvent(TUIConstants.TUIGroup.Event.GroupApplication.KEY_GROUP_APPLICATION,
            TUIConstants.TUIGroup.Event.GroupApplication.SUB_KEY_GROUP_APPLICATION_NUM_CHANGED, this);
    }

    @Override
    public Object onCall(String method, Map<String, Object> param) {

        return null;
    }

    @Override
    public void onNotifyEvent(String key, String subKey, Map<String, Object> param) {
        if (TextUtils.equals(key, TUIConstants.TUIGroup.EVENT_GROUP)) {
        } else if (key.equals(TUIConstants.TUIContact.EVENT_USER)) {
            handleContactUserEvent(subKey, param);
        } else if (key.equals(TUIConstants.TUIContact.EVENT_FRIEND_INFO_CHANGED)) {
            handleFriendInfChangedEvent(subKey, param);
        } else if (key.equals(TUIConstants.TUIConversation.EVENT_UNREAD)) {
            handleUnreadChangedEvent(subKey, param);
        } else if (TextUtils.equals(key, TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED)) {
        } else if (TextUtils.equals(key, UIChatConstants.EVENT_KEY_MESSAGE_STATUS_CHANGED)) {
            handleMessageStatusChangedEvent(subKey, param);
        } else if (TextUtils.equals(key, UIChatConstants.EVENT_KEY_OFFLINE_MESSAGE_PRIVATE_RING)) {
            handleOfflineRingEvent(subKey, param);
        } else if (TextUtils.equals(key, TUIConstants.TUIChat.EVENT_KEY_MESSAGE_EVENT)) {
        } else if (TextUtils.equals(TUIConstants.TUIGroup.Event.GroupApplication.KEY_GROUP_APPLICATION, key)) {

        }
    }

    private void handleOfflineRingEvent(String subKey, Map<String, Object> param) {
        if (TextUtils.equals(subKey, UIChatConstants.EVENT_SUB_KEY_OFFLINE_MESSAGE_PRIVATE_RING)) {
            Boolean isPrivateRing = (Boolean) param.get(UIChatConstants.OFFLINE_MESSAGE_PRIVATE_RING);
            //TUICore.callService(TUIConstants.TIMPush.SERVICE_NAME, TUIConstants.TIMPush.METHOD_CONFIG_FCM_PRIVATE_RING, paramRing);
        }
    }

    private void handleMessageStatusChangedEvent(String subKey, Map<String, Object> param) {
        if (TextUtils.equals(subKey, UIChatConstants.EVENT_SUB_KEY_MESSAGE_SEND)) {
            Object msgBeanObj = param.get(UIChatConstants.MESSAGE_BEAN);
            if (msgBeanObj instanceof TUIMessageBean) {

            }
        }
    }

    private void handleUnreadChangedEvent(String subKey, Map<String, Object> param) {
        if (subKey.equals(TUIConstants.TUIConversation.EVENT_SUB_KEY_UNREAD_CHANGED)) {
            long unreadCount = (long) param.get(TUIConstants.TUIConversation.TOTAL_UNREAD_COUNT);

        }
    }

    private void handleFriendInfChangedEvent(String subKey, Map<String, Object> param) {
        if (subKey.equals(TUIConstants.TUIContact.EVENT_SUB_KEY_FRIEND_REMARK_CHANGED)) {
            if (param == null || param.isEmpty()) {
                return;
            }

        }
    }

    private void handleContactUserEvent(String subKey, Map<String, Object> param) {
        if (subKey.equals(TUIConstants.TUIContact.EVENT_SUB_KEY_CLEAR_MESSAGE)) {
            if (param == null || param.isEmpty()) {
                return;
            }
        }

    }


    @Override
    public Object onCreateObject(String objectName, Map<String, Object> param) {
        return null;
    }
}
