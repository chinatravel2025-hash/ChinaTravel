package com.example.base.common.v2t.im.conversation.interfaces;

import com.example.base.common.v2t.im.conversation.bean.ConversationInfo;
import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.tencent.imsdk.v2.V2TIMUserStatus;


import java.util.List;

public interface ConversationEventListener {

    void deleteConversation(String chatId, boolean isGroup);

    void clearConversationMessage(String chatId, boolean isGroup);

    void clearFoldMarkAndDeleteConversation(String conversationId);

    void setConversationTop(String chatId, boolean isChecked, IUIKitCallback<Void> iuiKitCallBack);

    boolean isTopConversation(String chatId);

    long getUnreadTotal();

    void onSyncServerFinish();

    void updateTotalUnreadMessageCount(long count);

    void onNewConversation(List<ConversationInfo> conversationList);

    void onConversationChanged(List<ConversationInfo> conversationList);

    void onFriendRemarkChanged(String id, String remark);

    void onUserStatusChanged(List<V2TIMUserStatus> userStatusList);

    void refreshUserStatusFragmentUI();
    
    void onReceiveMessage(String conversationID, boolean isTypingMessage);

    void onMessageSendForHideConversation(String conversationID);

    void onConversationDeleted(List<String> conversationIDList);

}
