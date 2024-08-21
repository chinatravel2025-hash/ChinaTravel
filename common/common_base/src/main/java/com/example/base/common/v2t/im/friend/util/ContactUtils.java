package com.example.base.common.v2t.im.friend.util;

import com.example.base.common.v2t.im.conversation.util.ErrorMessageConverter;
import com.example.base.common.v2t.im.friend.bean.FriendGroupInfo;
import com.example.base.common.v2t.im.friend.bean.FriendOperationResult;
import com.example.base.common.v2t.im.friend.bean.OursFriendCheckResult;
import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMFriendCheckResult;
import com.tencent.imsdk.v2.V2TIMFriendGroup;
import com.tencent.imsdk.v2.V2TIMFriendOperationResult;
import com.tencent.imsdk.v2.V2TIMManager;

public class ContactUtils {
    public static <T> void callbackOnError(IUIKitCallback<T> callBack, String module, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError( errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError(errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnSuccess(IUIKitCallback<T> callBack, T data) {
        if (callBack != null) {
            callBack.onSuccess(data);
        }
    }

    public static boolean isC2CChat(int chatType) {
        return chatType == V2TIMConversation.V2TIM_C2C;
    }

    public static boolean isGroupChat(int chatType) {
        return chatType == V2TIMConversation.V2TIM_GROUP;
    }

    public static String getLoginUser() {
        return V2TIMManager.getInstance().getLoginUser();
    }



    public static FriendOperationResult transFormFriendOperationResult( V2TIMFriendOperationResult result){

        FriendOperationResult r = new FriendOperationResult();
        r.setResultCode(result.getResultCode());
        r.setUserId(result.getUserID());
        r.setResultInfo(result.getResultInfo());

        return r;
    }

    public static FriendGroupInfo convertFriendGroup( V2TIMFriendGroup result){

        FriendGroupInfo r = new FriendGroupInfo();
        r.setFriendCount(result.getFriendCount());
        r.setGroupName(result.getName());
        r.setFriendIds(result.getFriendIDList());

        return r;
    }

    /**
     * 转换好友关系检测.
     * @param result
     * @return
     */
    public static OursFriendCheckResult convertFriendCheckResult(V2TIMFriendCheckResult result){

        OursFriendCheckResult r = new OursFriendCheckResult();
        r.setUserId(result.getUserID());
        r.setRelationType(result.getResultType());
        r.setResultCode(result.getResultCode());
        r.setResultStr(result.getResultInfo());

        return r;
    }
}
