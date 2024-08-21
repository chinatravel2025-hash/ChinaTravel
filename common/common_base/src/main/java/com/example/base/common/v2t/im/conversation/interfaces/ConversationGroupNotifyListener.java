package com.example.base.common.v2t.im.conversation.interfaces;

import com.example.base.common.v2t.im.conversation.bean.ConversationGroupBean;

import java.util.List;

public interface ConversationGroupNotifyListener {
    void notifyGroupsAdd(List<ConversationGroupBean> beans);

    void notifyMarkGroupsAdd(List<ConversationGroupBean> beans);

    void notifyGroupAdd(ConversationGroupBean bean);

    void notifyGroupDelete(String groupName);

    void notifyGroupRename(String oldName, String newName);

    void notifyGroupUnreadMessageCountChanged(String groupName, long totalUnreadCount);
}
