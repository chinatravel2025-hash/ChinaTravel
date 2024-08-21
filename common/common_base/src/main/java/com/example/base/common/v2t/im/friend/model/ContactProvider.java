package com.example.base.common.v2t.im.friend.model;

import android.text.TextUtils;
import android.util.Pair;


import com.example.base.common.v2t.im.core.TUIConstants;
import com.example.base.common.v2t.im.friend.TUIContactService;
import com.example.base.common.v2t.im.friend.bean.ContactGroupApplyInfo;
import com.example.base.common.v2t.im.friend.bean.FriendApplicationBean;
import com.example.base.common.v2t.im.friend.bean.FriendGroupInfo;
import com.example.base.common.v2t.im.friend.bean.FriendOperationResult;
import com.example.base.common.v2t.im.friend.bean.GroupInfo;
import com.example.base.common.v2t.im.friend.bean.GroupMemberInfo;
import com.example.base.common.v2t.im.friend.bean.OursFriendCheckResult;
import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.example.base.common.v2t.im.conversation.util.ErrorMessageConverter;
import com.example.base.database.friend.entity.ContactItemBean;
import com.example.base.common.v2t.im.friend.util.ContactUtils;
import com.example.base.utils.ThreadUtils;
import com.example.base.utils.LogUtils;
import com.example.peanutmusic.base.BuildConfig;
import com.example.peanutmusic.base.R;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMFriendAddApplication;
import com.tencent.imsdk.v2.V2TIMFriendApplication;
import com.tencent.imsdk.v2.V2TIMFriendApplicationResult;
import com.tencent.imsdk.v2.V2TIMFriendCheckResult;
import com.tencent.imsdk.v2.V2TIMFriendGroup;
import com.tencent.imsdk.v2.V2TIMFriendInfo;
import com.tencent.imsdk.v2.V2TIMFriendInfoResult;
import com.tencent.imsdk.v2.V2TIMFriendOperationResult;
import com.tencent.imsdk.v2.V2TIMFriendSearchParam;
import com.tencent.imsdk.v2.V2TIMGroupApplication;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfoResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMReceiveMessageOptInfo;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMUserStatus;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactProvider {
    private static final String TAG = ContactProvider.class.getSimpleName();
    private long mNextSeq = 0;

    //获取好友列表
    public void loadFriendListDataAsync(IUIKitCallback<List<ContactItemBean>> callback) {
        LogUtils.i(TAG, "loadFriendListDataAsync");
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                // 压测时数据量比较大，query耗时比较久，所以这里使用新线程来处理
                // The amount of data during the stress test is relatively large, and the query takes a long time, so a new thread is used here to process
                V2TIMManager.getFriendshipManager().getFriendList(new V2TIMValueCallback<List<V2TIMFriendInfo>>() {
                    @Override
                    public void onError(int code, String desc) {
                        LogUtils.e(TAG, "loadFriendListDataAsync err code:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                        ContactUtils.callbackOnError(callback, TAG, code, desc);
                    }

                    @Override
                    public void onSuccess(List<V2TIMFriendInfo> v2TIMFriendInfos) {
                        List<ContactItemBean> contactItemBeanList = new ArrayList<>();
                        LogUtils.i(TAG, "loadFriendListDataAsync->getFriendList:" + v2TIMFriendInfos.size());
                        for (V2TIMFriendInfo timFriendInfo : v2TIMFriendInfos) {
                            ContactItemBean info = new ContactItemBean();
                            info.setFriend(true);
                            //info.covertTIMFriend(timFriendInfo);
                            contactItemBeanList.add(info);
                        }
                        ContactUtils.callbackOnSuccess(callback, contactItemBeanList);
                    }
                });
            }
        });
    }


    public void getFriendInfos(List<String> userIds,IUIKitCallback<List<ContactItemBean>> callback){
        V2TIMManager.getFriendshipManager().getFriendsInfo(userIds, new V2TIMValueCallback<List<V2TIMFriendInfoResult>>() {
            @Override
            public void onSuccess(List<V2TIMFriendInfoResult> v2TIMFriendInfoResults) {
                List<ContactItemBean> contactItemBeanList = new ArrayList<>();

                for (V2TIMFriendInfoResult item : v2TIMFriendInfoResults) {
                    ContactItemBean info = new ContactItemBean();
                    //info.covertTIMFriend(item.getFriendInfo());
                    info.setRelationType(item.getRelation());
                    //V2TIMFriendInfo.V2TIM_FRIEND_TYPE_BOTH
                    contactItemBeanList.add(info);
                }

            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getFriendInfo err code:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }
        });
    }


    /**
     * 修改好友自定字段信息
     * @param userId
     * @param customInfo
     */
    public static void setFriendInfo(String userId, HashMap<String,byte[]> customInfo, IUIKitCallback<Void> callback){
        V2TIMFriendInfo friendInfo = new V2TIMFriendInfo();
        friendInfo.setUserID(userId);
        friendInfo.setFriendCustomInfo(customInfo);
        V2TIMManager.getFriendshipManager().setFriendInfo(friendInfo, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                // 设置好友资料成功
                ContactUtils.callbackOnSuccess(callback,null);
            }

            @Override
            public void onError(int code, String desc) {
                // 设置好友资料失败
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }
        });
    }

    /**
     * 修改好友备注
     * @param userId
     * @param remark
     * @param callback
     */
    public static void setFriendRemark(String userId, String remark, IUIKitCallback<Void> callback){
        V2TIMFriendInfo friendInfo = new V2TIMFriendInfo();
        friendInfo.setUserID(userId);
        friendInfo.setFriendRemark(remark);
        V2TIMManager.getFriendshipManager().setFriendInfo(friendInfo, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                // 设置好友资料成功
                ContactUtils.callbackOnSuccess(callback,null);
            }

            @Override
            public void onError(int code, String desc) {
                // 设置好友资料失败
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }
        });
    }

    public static void checkFriend(List<String> userIds,int relationType,IUIKitCallback<List<OursFriendCheckResult>> callback){

        //V2TIM_FRIEND_TYPE_BOTH
//        public static final int V2TIM_FRIEND_TYPE_SINGLE = 1;
//        public static final int V2TIM_FRIEND_TYPE_BOTH = 2;
        V2TIMManager.getFriendshipManager().checkFriend(userIds, relationType, new V2TIMValueCallback<List<V2TIMFriendCheckResult>>() {
            @Override
            public void onSuccess(List<V2TIMFriendCheckResult> v2TIMFriendCheckResults) {
                List<OursFriendCheckResult> list = new ArrayList();
                // 检查好友关系成功
                for (V2TIMFriendCheckResult checkResult : v2TIMFriendCheckResults) {
                    list.add(ContactUtils.convertFriendCheckResult(checkResult));
                }
                ContactUtils.callbackOnSuccess(callback,list);

            }


            @Override
            public void onError(int code, String desc) {
                // 检查好友关系失败
                ContactUtils.callbackOnError(callback, code,desc);
            }
        });

    }

    public static void loadBlackListData(IUIKitCallback<List<ContactItemBean>> callback) {
        LogUtils.i(TAG, "loadBlackListData");

        V2TIMManager.getFriendshipManager().getBlackList(new V2TIMValueCallback<List<V2TIMFriendInfo>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getBlackList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMFriendInfo> v2TIMFriendInfos) {
                LogUtils.i(TAG, "getBlackList success: " + v2TIMFriendInfos.size());
                if (v2TIMFriendInfos.size() == 0) {
                    LogUtils.i(TAG, "getBlackList success but no data");
                }
                List<ContactItemBean> contactItemBeanList = new ArrayList<>();
                for (V2TIMFriendInfo timFriendInfo : v2TIMFriendInfos) {
                    ContactItemBean info = new ContactItemBean();
                    //info.covertTIMFriend(timFriendInfo).setBlackList(true);
                    contactItemBeanList.add(info);
                }
                ContactUtils.callbackOnSuccess(callback, contactItemBeanList);
            }
        });
    }

    public void loadGroupListData(IUIKitCallback<List<ContactItemBean>> callback) {
        LogUtils.i(TAG, "loadGroupListData");
        V2TIMManager.getGroupManager().getJoinedGroupList(new V2TIMValueCallback<List<V2TIMGroupInfo>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getGroupList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMGroupInfo> v2TIMGroupInfos) {
                LogUtils.i(TAG, "getGroupList success: " + v2TIMGroupInfos.size());
                if (v2TIMGroupInfos.size() == 0) {
                    LogUtils.i(TAG, "getGroupList success but no data");
                }
                List<ContactItemBean> contactItemBeanList = new ArrayList<>();

                for (V2TIMGroupInfo info : v2TIMGroupInfos) {
                    ContactItemBean bean = new ContactItemBean();
                    contactItemBeanList.add(bean.covertTIMGroupBaseInfo(info));
                }
                ContactUtils.callbackOnSuccess(callback, contactItemBeanList);
            }
        });
    }

    public long getNextSeq() {
        return mNextSeq;
    }

    public void setNextSeq(long nextSeq) {
        this.mNextSeq = nextSeq;
    }

    public void loadGroupMembers(String groupId, IUIKitCallback<List<ContactItemBean>> callback) {
        V2TIMManager.getGroupManager().getGroupMemberList(
            groupId, V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL, mNextSeq, new V2TIMValueCallback<V2TIMGroupMemberInfoResult>() {
                @Override
                public void onError(int code, String desc) {
                    ContactUtils.callbackOnError(callback, TAG, code, desc);
                    LogUtils.e(TAG, "loadGroupMembers failed, code: " + code + "|desc: " + ErrorMessageConverter.convertIMError(code, desc));
                }

                @Override
                public void onSuccess(V2TIMGroupMemberInfoResult v2TIMGroupMemberInfoResult) {
                    List<V2TIMGroupMemberFullInfo> members = new ArrayList<>();
                    for (int i = 0; i < v2TIMGroupMemberInfoResult.getMemberInfoList().size(); i++) {
                        if (v2TIMGroupMemberInfoResult.getMemberInfoList().get(i).getUserID().equals(V2TIMManager.getInstance().getLoginUser())) {
                            continue;
                        }
                        members.add(v2TIMGroupMemberInfoResult.getMemberInfoList().get(i));
                    }

                    mNextSeq = v2TIMGroupMemberInfoResult.getNextSeq();
                    List<ContactItemBean> contactItemBeanList = new ArrayList<>();
                    for (V2TIMGroupMemberFullInfo info : members) {
                        ContactItemBean bean = new ContactItemBean();
                        contactItemBeanList.add(bean.covertTIMGroupMemberFullInfo(info));
                    }
                    ContactUtils.callbackOnSuccess(callback, contactItemBeanList);
                }
            });
    }

    public void addFriend(String userId, String addWording, String addSource,IUIKitCallback<Pair<Integer, String>> callback) {
        addFriend(userId, addWording, addSource ,null, null, callback);
    }

    public static void addFriend(String userId, String addWording, String addSource,String friendGroup, String remark, IUIKitCallback<Pair<Integer, String>> callback) {
        V2TIMFriendAddApplication v2TIMFriendAddApplication = new V2TIMFriendAddApplication(userId);
        v2TIMFriendAddApplication.setAddWording(addWording);
        v2TIMFriendAddApplication.setAddType(V2TIMFriendInfo.V2TIM_FRIEND_TYPE_BOTH);
        v2TIMFriendAddApplication.setAddSource(addSource);
        v2TIMFriendAddApplication.setFriendGroup(friendGroup);
        v2TIMFriendAddApplication.setFriendRemark(remark);
        V2TIMManager.getFriendshipManager().addFriend(v2TIMFriendAddApplication, new V2TIMValueCallback<V2TIMFriendOperationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "addFriend err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(V2TIMFriendOperationResult v2TIMFriendOperationResult) {
                LogUtils.i(TAG, "addFriend success code = " + v2TIMFriendOperationResult.getResultCode());
                int code = v2TIMFriendOperationResult.getResultCode();
                String message = v2TIMFriendOperationResult.getResultInfo();
                if(code != 0){
                    message = ErrorMessageConverter.convertIMError(code, message);
                }
                ContactUtils.callbackOnSuccess(callback, new Pair<>(code, message));
            }
        });
    }

    public void joinGroup(String groupId, String addWording, IUIKitCallback<Void> callback) {
        V2TIMManager.getInstance().joinGroup(groupId, addWording, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "addGroup err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess() {
                LogUtils.i(TAG, "addGroup success");
                ContactUtils.callbackOnSuccess(callback, null);
            }
        });
    }

    public void loadFriendApplicationList(IUIKitCallback<List<FriendApplicationBean>> callback) {
        V2TIMManager.getFriendshipManager().getFriendApplicationList(new V2TIMValueCallback<V2TIMFriendApplicationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getPendencyList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(V2TIMFriendApplicationResult v2TIMFriendApplicationResult) {
                List<FriendApplicationBean> applicationBeanList = new ArrayList<>();
                for (V2TIMFriendApplication application : v2TIMFriendApplicationResult.getFriendApplicationList()) {
                    FriendApplicationBean bean = new FriendApplicationBean();
                    bean.convertFromTimFriendApplication(application);
                    applicationBeanList.add(bean);
                }
                ContactUtils.callbackOnSuccess(callback, applicationBeanList);
            }
        });
    }

    public void getFriendApplicationListUnreadCount(IUIKitCallback<Integer> callback) {
        V2TIMManager.getFriendshipManager().getFriendApplicationList(new V2TIMValueCallback<V2TIMFriendApplicationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getPendencyList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(V2TIMFriendApplicationResult v2TIMFriendApplicationResult) {
                ContactUtils.callbackOnSuccess(callback, v2TIMFriendApplicationResult.getUnreadCount());
            }
        });
    }

    private void acceptFriendApplication(V2TIMFriendApplication friendApplication, int responseType, IUIKitCallback<Void> callback) {
        V2TIMManager.getFriendshipManager().acceptFriendApplication(friendApplication, responseType, new V2TIMValueCallback<V2TIMFriendOperationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "acceptFriend err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
            }

            @Override
            public void onSuccess(V2TIMFriendOperationResult v2TIMFriendOperationResult) {
                LogUtils.i(TAG, "acceptFriend success");
                ContactUtils.callbackOnSuccess(callback, null);
            }
        });
    }

    public void acceptFriendApplication(FriendApplicationBean bean, int responseType, IUIKitCallback<Void> callback) {
        V2TIMFriendApplication friendApplication = bean.getFriendApplication();
        acceptFriendApplication(friendApplication, responseType, callback);
    }

    public void getC2CReceiveMessageOpt(List<String> userIdList, IUIKitCallback<Boolean> callback) {
        V2TIMManager.getMessageManager().getC2CReceiveMessageOpt(userIdList, new V2TIMValueCallback<List<V2TIMReceiveMessageOptInfo>>() {
            @Override
            public void onSuccess(List<V2TIMReceiveMessageOptInfo> v2TIMReceiveMessageOptInfos) {
                if (v2TIMReceiveMessageOptInfos == null || v2TIMReceiveMessageOptInfos.isEmpty()) {
                    LogUtils.d(TAG, "getC2CReceiveMessageOpt null");
                    ContactUtils.callbackOnError(callback, TAG, -1, "getC2CReceiveMessageOpt null");
                    return;
                }
                V2TIMReceiveMessageOptInfo v2TIMReceiveMessageOptInfo = v2TIMReceiveMessageOptInfos.get(0);
                int option = v2TIMReceiveMessageOptInfo.getC2CReceiveMessageOpt();

                LogUtils.d(TAG, "getC2CReceiveMessageOpt option = " + option);
                ContactUtils.callbackOnSuccess(callback, option == V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.d(TAG, "getC2CReceiveMessageOpt onError code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }
        });
    }

    public void setC2CReceiveMessageOpt(List<String> userIdList, boolean isReceiveMessage, IUIKitCallback<Void> callback) {
        int option;
        if (isReceiveMessage) {
            option = V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE;
        } else {
            option = V2TIMMessage.V2TIM_RECEIVE_MESSAGE;
        }
        V2TIMManager.getMessageManager().setC2CReceiveMessageOpt(userIdList, option, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                LogUtils.d(TAG, "setC2CReceiveMessageOpt onSuccess");
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.d(TAG, "setC2CReceiveMessageOpt onError code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }
        });
    }

    public void getGroupInfo(List<String> groupIds, IUIKitCallback<List<GroupInfo>> callback) {
        V2TIMManager.getGroupManager().getGroupsInfo(groupIds, new V2TIMValueCallback<List<V2TIMGroupInfoResult>>() {
            @Override
            public void onSuccess(List<V2TIMGroupInfoResult> v2TIMGroupInfoResults) {
                List<GroupInfo> groupInfos = new ArrayList<>();
                for (V2TIMGroupInfoResult result : v2TIMGroupInfoResults) {
                    if (result.getResultCode() != 0) {
                        ContactUtils.callbackOnError(callback, result.getResultCode(), result.getResultMessage());
                        return;
                    }
                    GroupInfo groupInfo = new GroupInfo();
                    groupInfo.setId(result.getGroupInfo().getGroupID());
                    groupInfo.setFaceUrl(result.getGroupInfo().getFaceUrl());
                    groupInfo.setGroupName(result.getGroupInfo().getGroupName());
                    groupInfo.setMemberCount(result.getGroupInfo().getMemberCount());
                    groupInfo.setGroupType(result.getGroupInfo().getGroupType());
                    groupInfos.add(groupInfo);
                }
                ContactUtils.callbackOnSuccess(callback, groupInfos);
            }

            @Override
            public void onError(int code, String desc) {
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void getUserInfo(List<String> userIdList, IUIKitCallback<List<ContactItemBean>> callback) {
        V2TIMManager.getInstance().getUsersInfo(userIdList, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "loadUserProfile err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMUserFullInfo> v2TIMUserFullInfos) {
                List<ContactItemBean> contactItemBeanList = new ArrayList<>();
                for (V2TIMUserFullInfo userFullInfo : v2TIMUserFullInfos) {
                    ContactItemBean contactItemBean = new ContactItemBean();
                    contactItemBean.setNickName(userFullInfo.getNickName());
                    contactItemBean.setRidStr(userFullInfo.getUserID());
                    contactItemBean.setAvatarUrl(userFullInfo.getFaceUrl());
                    contactItemBean.setSignature(userFullInfo.getSelfSignature());
                    contactItemBeanList.add(contactItemBean);
                }
                ContactUtils.callbackOnSuccess(callback, contactItemBeanList);
            }
        });
    }

    public static void isInBlackList(String id, IUIKitCallback<Boolean> callback) {
        V2TIMManager.getFriendshipManager().getBlackList(new V2TIMValueCallback<List<V2TIMFriendInfo>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getBlackList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMFriendInfo> v2TIMFriendInfos) {
                if (v2TIMFriendInfos != null && v2TIMFriendInfos.size() > 0) {
                    for (V2TIMFriendInfo friendInfo : v2TIMFriendInfos) {
                        if (TextUtils.equals(friendInfo.getUserID(), id)) {
                            ContactUtils.callbackOnSuccess(callback, true);
                            return;
                        }
                    }
                }
                ContactUtils.callbackOnSuccess(callback, false);
            }
        });
    }

    public static void isFriend(String id, ContactItemBean bean, IUIKitCallback<Boolean> callback) {
        V2TIMManager.getFriendshipManager().getFriendList(new V2TIMValueCallback<List<V2TIMFriendInfo>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getFriendList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMFriendInfo> v2TIMFriendInfos) {
                if (v2TIMFriendInfos != null && v2TIMFriendInfos.size() > 0) {
                    for (V2TIMFriendInfo friendInfo : v2TIMFriendInfos) {
                        if (TextUtils.equals(friendInfo.getUserID(), id)) {
                            bean.setFriend(true);
                            bean.setRemark(friendInfo.getFriendRemark());
                            bean.setAvatarUrl(friendInfo.getUserProfile().getFaceUrl());
                            ContactUtils.callbackOnSuccess(callback, true);
                            return;
                        }
                    }
                }
                ContactUtils.callbackOnSuccess(callback, false);
            }
        });
    }

    public static void deleteFromBlackList(List<String> idList, IUIKitCallback<Void> callback) {
        V2TIMManager.getFriendshipManager().deleteFromBlackList(idList, new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "deleteBlackList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {
                LogUtils.i(TAG, "deleteBlackList success");
                ContactUtils.callbackOnSuccess(callback, null);
            }
        });
    }

    public static void addToBlackList(List<String> idList, IUIKitCallback<Void> callback) {
        V2TIMManager.getFriendshipManager().addToBlackList(idList, new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "deleteBlackList err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {
                LogUtils.i(TAG, "deleteBlackList success");
                ContactUtils.callbackOnSuccess(callback, null);
            }
        });
    }

    public void modifyRemark(String id, String remark, IUIKitCallback<String> callback) {
        V2TIMFriendInfo v2TIMFriendInfo = new V2TIMFriendInfo();
        v2TIMFriendInfo.setUserID(id);
        v2TIMFriendInfo.setFriendRemark(remark);

        V2TIMManager.getFriendshipManager().setFriendInfo(v2TIMFriendInfo, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "modifyRemark err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
            }

            @Override
            public void onSuccess() {
                ContactUtils.callbackOnSuccess(callback, remark);
                LogUtils.i(TAG, "modifyRemark success");
            }
        });
    }

    public static void deleteFriend(List<String> identifiers, IUIKitCallback<Void> callback) {

        //V2TIMFriendInfo.V2TIM_FRIEND_TYPE_SINGLE
        //V2TIMFriendInfo.V2TIM_FRIEND_TYPE_BOTH
        V2TIMManager.getFriendshipManager().deleteFromFriendList(
            identifiers,V2TIMFriendInfo.V2TIM_FRIEND_TYPE_SINGLE , new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {
                @Override
                public void onError(int code, String desc) {
                    LogUtils.e(TAG, "deleteFriends err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                    ContactUtils.callbackOnError(callback, TAG, code, desc);
                }

                @Override
                public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {
                    LogUtils.i(TAG, "deleteFriends success");
                    ContactUtils.callbackOnSuccess(callback, null);
                }
            });
    }

    public void refuseFriendApplication(FriendApplicationBean friendApplication, IUIKitCallback<Void> callback) {
        V2TIMFriendApplication v2TIMFriendApplication = friendApplication.getFriendApplication();
        if (v2TIMFriendApplication == null) {
            ContactUtils.callbackOnError(callback, "refuseFriendApplication", -1, "V2TIMFriendApplication is null");
            return;
        }

        V2TIMManager.getFriendshipManager().refuseFriendApplication(v2TIMFriendApplication, new V2TIMValueCallback<V2TIMFriendOperationResult>() {
            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "accept err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
                //ToastHelper.createToastToTxt("Error code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
            }

            @Override
            public void onSuccess(V2TIMFriendOperationResult v2TIMFriendOperationResult) {
                LogUtils.i(TAG, "refuse success");
                ContactUtils.callbackOnSuccess(callback, null);
            }
        });
    }


    public void deleteFriendApplication(FriendApplicationBean friendApplication, IUIKitCallback<Void> callback) {
        V2TIMFriendApplication v2TIMFriendApplication = friendApplication.getFriendApplication();
        if (v2TIMFriendApplication == null) {
            ContactUtils.callbackOnError(callback, "refuseFriendApplication", -1, "V2TIMFriendApplication is null");
            return;
        }
        V2TIMManager.getFriendshipManager().deleteFriendApplication(v2TIMFriendApplication, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                LogUtils.i(TAG, "refuse success");
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "accept err code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
                ContactUtils.callbackOnError(callback, TAG, code, desc);
                //ToastHelper.createToastToTxt("Error code = " + code + ", desc = " + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }

    public void createGroupChat(GroupInfo groupInfo, IUIKitCallback<String> callback) {
        V2TIMGroupInfo v2TIMGroupInfo = new V2TIMGroupInfo();
        v2TIMGroupInfo.setGroupType(groupInfo.getGroupType());
        v2TIMGroupInfo.setGroupName(groupInfo.getGroupName());
        v2TIMGroupInfo.setGroupAddOpt(groupInfo.getJoinType());
        v2TIMGroupInfo.setGroupID(groupInfo.getId());
        v2TIMGroupInfo.setFaceUrl(groupInfo.getFaceUrl());
        if (TextUtils.equals(v2TIMGroupInfo.getGroupType(), V2TIMManager.GROUP_TYPE_COMMUNITY)) {
            v2TIMGroupInfo.setSupportTopic(groupInfo.isCommunitySupportTopic());
        }

        List<V2TIMCreateGroupMemberInfo> v2TIMCreateGroupMemberInfoList = new ArrayList<>();
        for (int i = 0; i < groupInfo.getMemberDetails().size(); i++) {
            GroupMemberInfo groupMemberInfo = groupInfo.getMemberDetails().get(i);
            V2TIMCreateGroupMemberInfo v2TIMCreateGroupMemberInfo = new V2TIMCreateGroupMemberInfo();
            v2TIMCreateGroupMemberInfo.setUserID(groupMemberInfo.getAccount());
            v2TIMCreateGroupMemberInfoList.add(v2TIMCreateGroupMemberInfo);
        }

        V2TIMManager.getGroupManager().createGroup(v2TIMGroupInfo, v2TIMCreateGroupMemberInfoList, new V2TIMValueCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ContactUtils.callbackOnSuccess(callback, s);
            }

            @Override
            public void onError(int code, String desc) {
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void sendGroupTipsMessage(String groupId, String message, IUIKitCallback<String> callback) {
        V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createCustomMessage(message.getBytes());
        V2TIMManager.getMessageManager().sendMessage(
            v2TIMMessage, null, groupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, new V2TIMSendCallback<V2TIMMessage>() {
                @Override
                public void onProgress(int progress) {}

                @Override
                public void onError(int code, String desc) {
                    LogUtils.i(TAG, "sendTipsMessage error , code : " + code + " desc : " + ErrorMessageConverter.convertIMError(code, desc));
                    ContactUtils.callbackOnError(callback, TAG, code, desc);
                }

                @Override
                public void onSuccess(V2TIMMessage v2TIMMessage) {
                    LogUtils.i(TAG, "sendTipsMessage onSuccess");
                    ContactUtils.callbackOnSuccess(callback, groupId);
                }
            });
    }

    public void acceptJoinGroupApply(ContactGroupApplyInfo applyInfo, IUIKitCallback<Void> callback) {
        V2TIMGroupApplication application = applyInfo.getTimGroupApplication();
        String reason = applyInfo.getRequestMsg();
        V2TIMManager.getGroupManager().acceptGroupApplication(application, reason, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void refuseJoinGroupApply(ContactGroupApplyInfo info, String reason, IUIKitCallback<Void> callback) {
        V2TIMGroupApplication application = info.getTimGroupApplication();
        V2TIMManager.getGroupManager().refuseGroupApplication(application, reason, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void setGroupApplicationRead(IUIKitCallback<Void> callback) {
        V2TIMManager.getGroupManager().setGroupApplicationRead(new V2TIMCallback() {
            @Override
            public void onSuccess() {
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    public void loadContactUserStatus(List<ContactItemBean> dataSource, IUIKitCallback<Void> callback) {
        if (dataSource == null || dataSource.size() == 0) {
            LogUtils.d(TAG, "loadContactUserStatus datasource is null");
            ContactUtils.callbackOnError(callback, -1, "data list is empty");
            return;
        }

        HashMap<String, ContactItemBean> dataSourceMap = new HashMap<>();
        List<String> userList = new ArrayList<>();
        for (ContactItemBean itemBean : dataSource) {
            if (TextUtils.equals(TUIContactService.getAppContext().getResources().getString(R.string.new_friend),
                    itemBean.getRidStr())
                || TextUtils.equals(TUIContactService.getAppContext().getResources().getString(R.string.blacklist),
                    itemBean.getRidStr())
                || TextUtils.equals(TUIContactService.getAppContext().getResources().getString(R.string.group),
                    itemBean.getRidStr())) {
                continue;
            }
            userList.add(itemBean.getRidStr());
            dataSourceMap.put(itemBean.getRidStr(), itemBean);
        }
        V2TIMManager.getInstance().getUserStatus(userList, new V2TIMValueCallback<List<V2TIMUserStatus>>() {
            @Override
            public void onSuccess(List<V2TIMUserStatus> v2TIMUserStatuses) {
                LogUtils.i(TAG, "getUserStatus success");

                for (V2TIMUserStatus item : v2TIMUserStatuses) {
                    ContactItemBean bean = dataSourceMap.get(item.getUserID());
                    if (bean != null) {
                        bean.setStatusType(item.getStatusType());
                    }
                }

                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "getUserStatus error code = " + code + ",des = " + desc);
                ContactUtils.callbackOnError(callback, code, desc);
                if (code == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT
                    && BuildConfig.DEBUG) {
                }
            }
        });
    }


    /**
     * 新建好友分组
     * @param userIDList
     * @param groupName
     * @param callback
     */
    public static void newFriendGroup(List<String> userIDList, String groupName, IUIKitCallback<List<FriendOperationResult>> callback){

        V2TIMManager.getFriendshipManager().createFriendGroup(groupName, userIDList, new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {
            @Override
            public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {

                List<FriendOperationResult> data = new ArrayList();
                // 创建好友分组成功
                for (V2TIMFriendOperationResult item : v2TIMFriendOperationResults){
                   data.add(ContactUtils.transFormFriendOperationResult(item));
                }
                ContactUtils.callbackOnSuccess(callback, data);
            }


            @Override
            public void onError(int code, String desc) {
                // 创建好友分组失败
                ContactUtils.callbackOnError(callback, code, desc);
            }
        });
    }

    /*
     * 删除好友分组
     */
    public static void deleteFriendGroup(List<String> friendGroupList,IUIKitCallback<Void> callback){

        V2TIMManager.getFriendshipManager().deleteFriendGroup(friendGroupList, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                // 删除好友分组成功
                ContactUtils.callbackOnSuccess(callback, null);
            }


            @Override
            public void onError(int code, String desc) {
                // 删除好友分组失败
                ContactUtils.callbackOnError(callback, code, desc);

            }
        });

    }


    /**
     * 重命名好友分组
     * @param oldName
     * @param newName
     * @param callback
     */
    public static void renameFriendGroup(String oldName,String newName ,IUIKitCallback<Void> callback){
        V2TIMManager.getFriendshipManager().renameFriendGroup(oldName, newName, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                // 修改好友分组成功
                ContactUtils.callbackOnSuccess(callback, null);
            }

            @Override
            public void onError(int code, String desc) {
                // 修改好友分组失败
                ContactUtils.callbackOnError(callback, code,desc);
            }
        });

    }


    /**
     * 获取好友分组
     * @param groupNames
     */
    public static void getFriendGroups(List<String> groupNames,IUIKitCallback<List<FriendGroupInfo>> callback){

        V2TIMManager.getFriendshipManager().getFriendGroups(groupNames, new V2TIMValueCallback<List<V2TIMFriendGroup>>() {
            @Override
            public void onSuccess(List<V2TIMFriendGroup> v2TIMFriendGroups) {
                // 获取好友分组
                List<FriendGroupInfo> data = new ArrayList();
                // 创建好友分组成功
                for (V2TIMFriendGroup item : v2TIMFriendGroups){
                    data.add(ContactUtils.convertFriendGroup(item));
                }
                ContactUtils.callbackOnSuccess(callback, data);
            }


            @Override
            public void onError(int code, String desc) {
                // 创建好友分组失败
                ContactUtils.callbackOnError(callback, code,desc);
            }
        });
    }


    /**
     * 添加好友到分组
     * @param groupName
     * @param userIds
     * @param callback
     */
    public static void addFriendsToGroup(String groupName,List<String> userIds,IUIKitCallback<List<FriendOperationResult>> callback){

        V2TIMManager.getFriendshipManager().addFriendsToFriendGroup(groupName, userIds, new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {

            @Override
            public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {
                // 添加成功
                List<FriendOperationResult> data = new ArrayList();
                // 创建好友分组成功
                for (V2TIMFriendOperationResult item : v2TIMFriendOperationResults){
                    data.add(ContactUtils.transFormFriendOperationResult(item));
                }
                ContactUtils.callbackOnSuccess(callback, data);
            }

            @Override
            public void onError(int code, String desc) {
                // 创建好友分组失败
                ContactUtils.callbackOnError(callback, code,desc);
            }
        });
    }


    /**
     * 从分组中删除好友
     * @param userIDList
     * @param groupName
     */
    public static void deleteFriendsFromFriendGroup(List<String> userIDList,String groupName,IUIKitCallback<List<FriendOperationResult>> callback){

        V2TIMManager.getFriendshipManager().deleteFriendsFromFriendGroup(groupName, userIDList, new V2TIMValueCallback<List<V2TIMFriendOperationResult>>() {
            @Override
            public void onSuccess(List<V2TIMFriendOperationResult> v2TIMFriendOperationResults) {
                // 删除成功
                List<FriendOperationResult> data = new ArrayList();
                // 创建好友分组成功
                for (V2TIMFriendOperationResult item : v2TIMFriendOperationResults){
                    data.add(ContactUtils.transFormFriendOperationResult(item));
                }
                ContactUtils.callbackOnSuccess(callback, data);
            }


            @Override
            public void onError(int code, String desc) {
                // 删除失败
                ContactUtils.callbackOnError(callback, code,desc);
            }
        });

    }


    public static void searchFriends(List<String> keywordList,IUIKitCallback<List<ContactItemBean>> callback){
        V2TIMFriendSearchParam searchParam = new V2TIMFriendSearchParam();
        searchParam.setKeywordList(keywordList);
        searchParam.setSearchUserID(true);
        searchParam.setSearchNickName(true);
        searchParam.setSearchRemark(true);

        V2TIMManager.getFriendshipManager().searchFriends(searchParam, new V2TIMValueCallback<List<V2TIMFriendInfoResult>>() {
            @Override
            public void onSuccess(List<V2TIMFriendInfoResult> v2TIMFriendInfos) {

                List<ContactItemBean> data = new ArrayList<>();
                if(v2TIMFriendInfos != null){
                    for(V2TIMFriendInfoResult info : v2TIMFriendInfos){
                        ContactItemBean bean  = new ContactItemBean();
                        bean.setFriend(true);
                        //data.add( bean.covertTIMFriend(info.getFriendInfo()));
                    }
                }
                ContactUtils.callbackOnSuccess(callback, data);
            }

            @Override
            public void onError(int code, String desc) {
                // 搜索用户资料失败
                ContactUtils.callbackOnError(callback, code,desc);

            }
        });

    }




}
