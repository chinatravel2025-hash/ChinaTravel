package com.example.base.common.v2t.im.chat.model;


import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

public class ChatProvider {



    public void sendTextMessage(String content, String userId, IUIKitCallback<V2TIMMessage> callback){


        //V2TIMManager.getMessageManager().sendMessage(v2TIMMessage,userId,null, )

//        V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage("/sdcard/xxx");
//        // 发送消息
//        V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, userId, null, V2TIMMessage.V2TIM_PRIORITY_NORMAL, false, null, new V2TIMSendCallback<V2TIMMessage>() {
//            @Override
//            public void onProgress(int progress) {
//                // 文本消息不会回调进度
//            }
//
//
//            @Override
//            public void onSuccess(V2TIMMessage message) {
//                // 文本消息发送成功
//                //最后转一层
//                callback.onSuccess(message);
//            }
//
//
//            @Override
//            public void onError(int code, String desc) {
//                // 文本消息发送失败
//            }
//        });

        // API 返回 msgID，按需使用
        String msgID = V2TIMManager.getInstance().sendC2CTextMessage(content, userId, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onSuccess(V2TIMMessage message) {
                // 发送单聊文本消息成功
                callback.onSuccess(message);
            }


            @Override
            public void onError(int code, String desc) {
                // 发送单聊文本消息失败
                callback.onError(code,desc);
            }
        });


    }

    public void loadMessage(){

    }


}