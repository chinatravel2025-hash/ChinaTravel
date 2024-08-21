package com.example.base.common.v2t.im.conversation.interfaces;


import com.example.base.common.v2t.im.conversation.bean.ConversationInfo;

import java.util.List;

public interface IConversationListAdapter {
    /**
     * 获取适配器的条目数据，返回的是ConversationInfo对象或其子对象
     *
     * Get the entry data of the adapter, which returns the ConversationInfo object or its sub-objects
     *
     * @param position
     * @return ConversationInfo
     */
    ConversationInfo getItem(int position);

    void onLoadingStateChanged(boolean isLoading);

    void onDataSourceChanged(List<ConversationInfo> conversationInfoList);

    void onViewNeedRefresh();

    void onItemRemoved(int position);

    void onItemInserted(int position);

    void onItemChanged(int position);

    void onItemRangeChanged(int startPosition, int count);

    void onConversationChanged(List<ConversationInfo> conversationInfoList);
}
