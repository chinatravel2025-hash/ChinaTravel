package com.example.base.common.v2t.im.conversation.util;


import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.tencent.imsdk.v2.V2TIMMessage;


public class TUIConversationUtils {
//    public static <T> void callbackOnError(IUIKitCallback<T> callBack, String module, int errCode, String desc) {
//        if (callBack != null) {
//            callBack.onError(module, errCode, ErrorMessageConverter.convertIMError(errCode, desc));
//        }
//    }

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError( errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnSuccess(IUIKitCallback<T> callBack, T data) {
        if (callBack != null) {
            callBack.onSuccess(data);
        }
    }

    public static boolean hasRiskContent(V2TIMMessage v2TIMMessage) {
        if (v2TIMMessage != null) {
            return v2TIMMessage.hasRiskContent() && v2TIMMessage.getStatus() != V2TIMMessage.V2TIM_MSG_STATUS_LOCAL_REVOKED;
        }
        return false;
    }
}