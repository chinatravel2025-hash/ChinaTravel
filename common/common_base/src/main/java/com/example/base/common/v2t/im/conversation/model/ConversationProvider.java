package com.example.base.common.v2t.im.conversation.model;

import android.text.TextUtils;

import com.example.base.common.v2t.im.conversation.bean.ConversationInfo;
import com.example.base.common.v2t.im.conversation.bean.ConversationUserStatusBean;
import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.example.base.common.v2t.im.conversation.util.ConversationUtils;
import com.example.base.common.v2t.im.conversation.util.ErrorMessageConverter;
import com.example.base.common.v2t.im.conversation.util.TUIConversationUtils;
import com.example.base.utils.LogUtils;
import com.tencent.imsdk.BaseConstants;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationListFilter;
import com.tencent.imsdk.v2.V2TIMConversationOperationResult;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMUserStatus;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConversationProvider {
    private static final String TAG = ConversationProvider.class.getSimpleName();

    protected boolean isFinished = false;
    protected long nextLoadSeq = 0L;


    protected boolean isMarkFinished = false;
    protected long nextMarkSeq = 0L;

    public long getNextMarkSeq() {
        return nextMarkSeq;
    }

    private List<ConversationInfo> markConversationInfoList = new ArrayList<>();
    private List<ConversationInfo> _conversationInfoList = new ArrayList<>();
    private HashMap<String, V2TIMConversation> markUnreadMap = new HashMap<>();

    public void loadConversation(long startSeq, int loadCount, final IUIKitCallback<List<ConversationInfo>> callBack) {
        LogUtils.i(TAG, "loadConversation startSeq " + startSeq + " loadCount " + loadCount);

        V2TIMManager.getConversationManager().getConversationList(startSeq, loadCount, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(
                    TAG, "loadConversation getConversationList error, code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                TUIConversationUtils.callbackOnError(callBack, code, desc);
            }

            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> v2TIMConversationList = v2TIMConversationResult.getConversationList();
                List<ConversationInfo> conversationInfoList = ConversationUtils.convertV2TIMConversationList(v2TIMConversationList);
                LogUtils.i(TAG,
                    "loadConversation getConversationList success size " + conversationInfoList.size() + " nextSeq " + v2TIMConversationResult.getNextSeq()
                        + " isFinished " + v2TIMConversationResult.isFinished());
                if (!conversationInfoList.isEmpty()) {
                    LogUtils.i(TAG,
                        "loadConversation getConversationList success first " + conversationInfoList.get(0) + " last "
                            + conversationInfoList.get(conversationInfoList.size() - 1));
                }
                isFinished = v2TIMConversationResult.isFinished();
                nextLoadSeq = v2TIMConversationResult.getNextSeq();
                TUIConversationUtils.callbackOnSuccess(callBack, conversationInfoList);
            }
        });
    }

    public void loadMoreConversation(int loadCount, IUIKitCallback<List<ConversationInfo>> callBack) {
        if (isFinished) {
            return;
        }
        loadConversation(nextLoadSeq, loadCount, callBack);
    }




    public void getMarkConversationList(
            final V2TIMConversationListFilter filter, long nextSeq, int count, IUIKitCallback<List<ConversationInfo>> callback) {

        V2TIMManager.getConversationManager().getConversationListByFilter(filter, nextSeq, count, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> conversationList = v2TIMConversationResult.getConversationList();
                List<ConversationInfo> conversationInfoList = ConversationUtils.convertV2TIMConversationList(conversationList);

                isMarkFinished = v2TIMConversationResult.isFinished();
                nextMarkSeq = v2TIMConversationResult.getNextSeq();
                TUIConversationUtils.callbackOnSuccess(callback, conversationInfoList);
            }

            @Override
            public void onError(int code, String desc) {
                TUIConversationUtils.callbackOnError(callback,code, desc);
                LogUtils.e(TAG, "getMarkConversationList error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }


    public void loadMoreMarkConversation(final V2TIMConversationListFilter filter,int loadCount, IUIKitCallback<List<ConversationInfo>> callBack) {
        if (isMarkFinished) {
            return;
        }
        getMarkConversationList(filter,nextMarkSeq, loadCount, callBack);
    }




    public void getAllMarkConversationList(
            final V2TIMConversationListFilter filter, long nextSeq, int count, boolean fromStart, IUIKitCallback<List<ConversationInfo>> callback) {
        if (fromStart) {
            markConversationInfoList.clear();
        }
        V2TIMManager.getConversationManager().getConversationListByFilter(filter, nextSeq, count, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> conversationList = v2TIMConversationResult.getConversationList();
                List<ConversationInfo> conversationInfoList = ConversationUtils.convertV2TIMConversationList(conversationList);
                markConversationInfoList.addAll(conversationInfoList);
                if (!v2TIMConversationResult.isFinished()) {
                    getAllMarkConversationList(filter, v2TIMConversationResult.getNextSeq(), count, false, callback);
                } else {
                    if (callback != null) {
                        callback.onSuccess(markConversationInfoList);
                    }
                }

            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getMarkConversationList error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }




    public void loadAllConversations(IUIKitCallback<List<ConversationInfo>> callback){
        if(isFinished){
            List<ConversationInfo> data = new ArrayList<>();
            data.addAll(_conversationInfoList);
            callback.onSuccess(data);
            _conversationInfoList.clear();
            return;
        }
        loadConversation(nextLoadSeq, 100, new IUIKitCallback<List<ConversationInfo>>() {
            @Override
            public void onError(int errCode, String errMsg) {
                super.onError(errCode, errMsg);
                _conversationInfoList.clear();
                callback.onError(errCode,errMsg);
            }

            @Override
            public void onSuccess(List<ConversationInfo> data) {
                super.onSuccess(data);
                _conversationInfoList.addAll(data);
                loadAllConversations(callback);
            }
        });
    }

    public boolean isLoadFinished() {
        return isFinished;
    }


    public boolean isMarkFinished(){
        return isMarkFinished;
    }

    public static void getConversation(String conversationID, IUIKitCallback<ConversationInfo> callback) {
        V2TIMManager.getConversationManager().getConversation(conversationID, new V2TIMValueCallback<V2TIMConversation>() {
            @Override
            public void onSuccess(V2TIMConversation v2TIMConversation) {
                ConversationInfo conversationInfo = ConversationUtils.convertV2TIMConversation(v2TIMConversation);
                TUIConversationUtils.callbackOnSuccess(callback, conversationInfo);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.v(TAG, "getConversation error, code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                TUIConversationUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void getTotalUnreadMessageCount(IUIKitCallback<Long> callBack) {
        // 更新消息未读总数
        V2TIMManager.getConversationManager().getTotalUnreadMessageCount(new V2TIMValueCallback<Long>() {
            @Override
            public void onSuccess(Long count) {
                TUIConversationUtils.callbackOnSuccess(callBack, count);
            }

            @Override
            public void onError(int code, String desc) {}
        });
    }

    public static void setConversationTop(String conversationId, boolean isTop, IUIKitCallback<Void> callBack) {
        V2TIMManager.getConversationManager().pinConversation(conversationId, isTop, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                TUIConversationUtils.callbackOnSuccess(callBack, null);
            }

            @Override
            public void onError(int code, String desc) {
                TUIConversationUtils.callbackOnError(callBack, code, desc);
            }
        });
    }

    public static void markConversations(String conversationID, boolean isFold, IUIKitCallback<Void> callback) {
        List<String> conversationIDList = new ArrayList<>();
        conversationIDList.add(conversationID);
        V2TIMManager.getConversationManager().markConversation(
            conversationIDList, V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_FOLD, isFold, new V2TIMValueCallback<List<V2TIMConversationOperationResult>>() {
                @Override
                public void onSuccess(List<V2TIMConversationOperationResult> v2TIMConversationOperationResults) {
                    if (v2TIMConversationOperationResults.size() == 0) {
                        return;
                    }
                    V2TIMConversationOperationResult result = v2TIMConversationOperationResults.get(0);
                    if (result.getResultCode() == BaseConstants.ERR_SUCC) {
                        TUIConversationUtils.callbackOnSuccess(callback, null);
                    } else {
                        TUIConversationUtils.callbackOnError(callback, result.getResultCode(), result.getResultInfo());
                    }
                }

                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(TAG, "markConversationFold error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                    TUIConversationUtils.callbackOnError(callback, code, desc);
                }
            });
    }

    public static void markConversations(List<String> conversationIDList, long markType, boolean enableMark, IUIKitCallback<Void> callback) {

        V2TIMManager.getConversationManager().markConversation(
                conversationIDList, markType, enableMark, new V2TIMValueCallback<List<V2TIMConversationOperationResult>>() {
                    @Override
                    public void onSuccess(List<V2TIMConversationOperationResult> v2TIMConversationOperationResults) {
                        if (v2TIMConversationOperationResults.size() == 0) {
                            return;
                        }
                        V2TIMConversationOperationResult result = v2TIMConversationOperationResults.get(0);
                        if (result.getResultCode() == BaseConstants.ERR_SUCC) {
                            TUIConversationUtils.callbackOnSuccess(callback, null);
                        } else {
                            TUIConversationUtils.callbackOnError(callback, result.getResultCode(), result.getResultInfo());
                        }
                    }

                    @Override
                    public void onError(int code, String desc) {
                        LogUtils.e(TAG, "markConversationFold error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                        TUIConversationUtils.callbackOnError(callback, code, desc);
                    }
                });
    }

    public void markConversationHidden(String conversationID, boolean isHidden, IUIKitCallback<Void> callback) {
        List<String> conversationIDList = new ArrayList<>();
        if (!TextUtils.isEmpty(conversationID)) {
            conversationIDList.add(conversationID);
        }
        V2TIMManager.getConversationManager().markConversation(conversationIDList, V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_HIDE, isHidden,
            new V2TIMValueCallback<List<V2TIMConversationOperationResult>>() {
                @Override
                public void onSuccess(List<V2TIMConversationOperationResult> v2TIMConversationOperationResults) {
                    if (v2TIMConversationOperationResults.size() == 0) {
                        return;
                    }
                    V2TIMConversationOperationResult result = v2TIMConversationOperationResults.get(0);
                    if (result.getResultCode() == BaseConstants.ERR_SUCC) {
                        TUIConversationUtils.callbackOnSuccess(callback, null);
                    } else {
                        TUIConversationUtils.callbackOnError(callback, result.getResultCode(), result.getResultInfo());
                    }
                }

                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(TAG, "markConversationHidden error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                    TUIConversationUtils.callbackOnError(callback, code, desc);
                }
            });
    }

    public void markConversationUnread(ConversationInfo conversationInfo, boolean markUnread, IUIKitCallback<Void> callback) {
        List<String> conversationIDList = new ArrayList<>();
        if (!TextUtils.isEmpty(conversationInfo.getConversationId())) {
            conversationIDList.add(conversationInfo.getConversationId());
        }
        if (!markUnread && conversationInfo.getUnRead() > 0) {
            V2TIMManager.getConversationManager().cleanConversationUnreadMessageCount(conversationInfo.getConversationId(), 0, 0, new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    LogUtils.i(TAG, "markConversationUnread->cleanConversationUnreadMessageCount success");
                }

                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(
                        TAG, "cleanConversationUnreadMessageCount error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                }
            });
        }

        if (markUnread != conversationInfo.isMarkUnread()) {
            V2TIMManager.getConversationManager().markConversation(conversationIDList, V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_UNREAD, markUnread,
                new V2TIMValueCallback<List<V2TIMConversationOperationResult>>() {
                    @Override
                    public void onSuccess(List<V2TIMConversationOperationResult> v2TIMConversationOperationResults) {
                        if (v2TIMConversationOperationResults.size() == 0) {
                            return;
                        }
                        V2TIMConversationOperationResult result = v2TIMConversationOperationResults.get(0);
                        if (result.getResultCode() == BaseConstants.ERR_SUCC) {
                            TUIConversationUtils.callbackOnSuccess(callback, null);
                        } else {
                            TUIConversationUtils.callbackOnError(callback, result.getResultCode(), result.getResultInfo());
                        }
                    }

                    @Override
                    public void onError(int code, String desc) {
                        LogUtils.e(TAG, "markConversationUnread error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                        TUIConversationUtils.callbackOnError(callback, code, desc);
                    }
                });
        }
    }

    public static void deleteConversation(String conversationId, IUIKitCallback<Void> callBack) {
        V2TIMManager.getConversationManager().deleteConversation(conversationId, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "deleteConversation error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                TUIConversationUtils.callbackOnError(callBack, code, desc);
            }

            @Override
            public void onSuccess() {
                LogUtils.i(TAG, "deleteConversation success");
                TUIConversationUtils.callbackOnSuccess(callBack, null);
            }
        });
    }

    public static void clearHistoryMessage(String userId, boolean isGroup, IUIKitCallback<Void> callBack) {
        if (isGroup) {
            V2TIMManager.getMessageManager().clearGroupHistoryMessage(userId, new V2TIMCallback() {
                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(TAG, "clearConversationMessage error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                    TUIConversationUtils.callbackOnError(callBack, code, desc);
                }

                @Override
                public void onSuccess() {
                    LogUtils.i(TAG, "clearConversationMessage success");
                    TUIConversationUtils.callbackOnSuccess(callBack, null);
                }
            });
        } else {
            V2TIMManager.getMessageManager().clearC2CHistoryMessage(userId, new V2TIMCallback() {
                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(TAG, "clearConversationMessage error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                    TUIConversationUtils.callbackOnError(callBack, code, desc);
                }

                @Override
                public void onSuccess() {
                    LogUtils.i(TAG, "clearConversationMessage success");
                    TUIConversationUtils.callbackOnSuccess(callBack, null);
                }
            });
        }
    }

    public static void getGroupMemberIconList(String groupId, int iconCount, IUIKitCallback<List<Object>> callback) {
        V2TIMManager.getGroupManager().getGroupMemberList(
            groupId, V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL, 0, new V2TIMValueCallback<V2TIMGroupMemberInfoResult>() {
                @Override
                public void onError(int code, String desc) {
                    TUIConversationUtils.callbackOnError(callback, code, desc);
                    LogUtils.e("ConversationIconView",
                        "getGroupMemberList failed! groupID:" + groupId + "|code:" + code + "|desc: " + ErrorMessageConverter.convertIMError(code, desc));
                }

                @Override
                public void onSuccess(V2TIMGroupMemberInfoResult v2TIMGroupMemberInfoResult) {
                    List<V2TIMGroupMemberFullInfo> v2TIMGroupMemberFullInfoList = v2TIMGroupMemberInfoResult.getMemberInfoList();
                    int faceSize = Math.min(v2TIMGroupMemberFullInfoList.size(), iconCount);
                    final List<Object> urlList = new ArrayList<>();
                    for (int i = 0; i < faceSize; i++) {
                        V2TIMGroupMemberFullInfo v2TIMGroupMemberFullInfo = v2TIMGroupMemberFullInfoList.get(i);
                        urlList.add(v2TIMGroupMemberFullInfo.getFaceUrl());
                    }
                    TUIConversationUtils.callbackOnSuccess(callback, urlList);
                }
            });
    }

    public void loadConversationUserStatus(List<ConversationInfo> dataSource, IUIKitCallback<Map<String, ConversationUserStatusBean>> callback) {
        if (dataSource == null || dataSource.size() == 0) {
            LogUtils.d(TAG, "loadConversationUserStatus datasource is null");
            return;
        }

        List<String> userList = new ArrayList<>();
        for (ConversationInfo itemBean : dataSource) {
            if (itemBean.isGroup()) {
                continue;
            }
            userList.add(itemBean.getId());
        }
        if (userList.isEmpty()) {
            LogUtils.d(TAG, "loadConversationUserStatus userList is empty");
            return;
        }
        V2TIMManager.getInstance().getUserStatus(userList, new V2TIMValueCallback<List<V2TIMUserStatus>>() {
            @Override
            public void onSuccess(List<V2TIMUserStatus> v2TIMUserStatuses) {
                LogUtils.i(TAG, "getUserStatus success");
                Map<String, ConversationUserStatusBean> userStatusBeanMap = new HashMap<>();
                for (V2TIMUserStatus item : v2TIMUserStatuses) {
                    ConversationUserStatusBean conversationUserStatusBean = new ConversationUserStatusBean();
                    conversationUserStatusBean.setV2TIMUserStatus(item);
                    userStatusBeanMap.put(item.getUserID(), conversationUserStatusBean);
                }
                TUIConversationUtils.callbackOnSuccess(callback, userStatusBeanMap);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getUserStatus error code = " + code + ",des = " + desc);
                TUIConversationUtils.callbackOnError(callback, code, desc);
//                if (code == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT && TUIConversationConfig.getInstance().isShowUserStatus()
//                    && BuildConfig.DEBUG) {
//                    ToastUtil.toastLongMessage(desc);
//                }
            }
        });
    }

    public void subscribeConversationUserStatus(List<String> userIdList, IUIKitCallback<Void> callback) {
        if (userIdList == null || userIdList.size() == 0) {
            LogUtils.e(TAG, "subscribeConversationUserStatus userId is null");
            TUIConversationUtils.callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "userid list is null");
            return;
        }

        V2TIMManager.getInstance().subscribeUserStatus(userIdList, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                TUIConversationUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "subscribeConversationUserStatus error code = " + code + ",des = " + desc);
                TUIConversationUtils.callbackOnError(callback, code, desc);
            }
        });
    }


    public void clearUnreadMessage(String conversationId,IUIKitCallback<Void> callback) {
        V2TIMManager.getConversationManager().cleanConversationUnreadMessageCount(conversationId, 0, 0, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                LogUtils.i(TAG, "clearAllUnreadMessage success");
                TUIConversationUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.i(TAG, "clearAllUnreadMessage error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                TUIConversationUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void clearAllUnreadMessage(IUIKitCallback<Void> callback) {
        V2TIMManager.getConversationManager().cleanConversationUnreadMessageCount("", 0, 0, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                LogUtils.i(TAG, "clearAllUnreadMessage success");
                TUIConversationUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.i(TAG, "clearAllUnreadMessage error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                TUIConversationUtils.callbackOnError(callback, code, desc);
            }
        });

        V2TIMConversationListFilter filter = new V2TIMConversationListFilter();
        filter.setMarkType(V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_UNREAD);
        getMarkUnreadConversationList(filter, 0, 100, true, new V2TIMValueCallback<HashMap<String, V2TIMConversation>>() {
            @Override
            public void onSuccess(HashMap<String, V2TIMConversation> stringV2TIMConversationHashMap) {
                if (stringV2TIMConversationHashMap.size() == 0) {
                    return;
                }
                List<String> unreadConversationIDList = new ArrayList<>();
                Iterator<Map.Entry<String, V2TIMConversation>> iterator = markUnreadMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, V2TIMConversation> entry = iterator.next();
                    unreadConversationIDList.add(entry.getKey());
                }

                V2TIMManager.getConversationManager().markConversation(unreadConversationIDList, V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_UNREAD, false,
                    new V2TIMValueCallback<List<V2TIMConversationOperationResult>>() {
                        @Override
                        public void onSuccess(List<V2TIMConversationOperationResult> v2TIMConversationOperationResults) {
                            for (V2TIMConversationOperationResult result : v2TIMConversationOperationResults) {
                                if (result.getResultCode() == BaseConstants.ERR_SUCC) {
                                    V2TIMConversation v2TIMConversation = markUnreadMap.get(result.getConversationID());
                                    if (!v2TIMConversation.getMarkList().contains(V2TIMConversation.V2TIM_CONVERSATION_MARK_TYPE_HIDE)) {
                                        markUnreadMap.remove(result.getConversationID());
                                    }
                                }
                            }
                            TUIConversationUtils.callbackOnSuccess(callback, null);
                        }

                        @Override
                        public void onError(int code, String desc) {
                            LogUtils.e(TAG,
                                "triggerClearAllUnreadMessage->markConversation error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                            TUIConversationUtils.callbackOnError(callback, code, desc);
                        }
                    });
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG,
                    "triggerClearAllUnreadMessage->getMarkUnreadConversationList error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }

    private void getMarkUnreadConversationList(
        V2TIMConversationListFilter filter, long nextSeq, int count, boolean fromStart, V2TIMValueCallback<HashMap<String, V2TIMConversation>> callback) {
        if (fromStart) {
            markUnreadMap.clear();
        }
        V2TIMManager.getConversationManager().getConversationListByFilter(filter, nextSeq, count, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> conversationList = v2TIMConversationResult.getConversationList();
                for (V2TIMConversation conversation : conversationList) {
                    markUnreadMap.put(conversation.getConversationID(), conversation);
                }

                if (!v2TIMConversationResult.isFinished()) {
                    getMarkUnreadConversationList(filter, v2TIMConversationResult.getNextSeq(), count, false, callback);
                } else {
                    if (callback != null) {
                        callback.onSuccess(markUnreadMap);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getMarkUnreadConversationList error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }
}