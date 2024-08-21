package com.example.base.common.v2t.im.chat.presenter;


import com.example.base.common.v2t.im.chat.model.ChatProvider;
import com.example.base.common.v2t.im.interfaces.IUIKitCallback;
import com.example.base.utils.LogUtils;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.Random;

public  class ChatPresenter {

    private final String TAG = this.getClass().getSimpleName();

    private ChatProvider chatProvider;


    public ChatPresenter(){
        chatProvider = new ChatProvider();
    }




    public void sendMessage(String content,String userId,IUIKitCallback<Object> iuiKitCallback){

        chatProvider.sendTextMessage(content, userId, new IUIKitCallback<V2TIMMessage>() {
            @Override
            public void onSuccess(V2TIMMessage data) {
                //消息最好上层转一道，外面不要使用SDK中的引用
                LogUtils.i(TAG,"发送成功 = " + userId);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                LogUtils.i(TAG,"发送失败 errCode = " + errCode + "; errMsg = " + errMsg);
            }
        });

    }

    //主要是为了创建对应的会话做一些处理

    public void mockSendMessage(){

        /**
         * liuzhiming
         * //@USR#5M5JjG_UXSs 吴关俊
         * //@USR#Xh405EhPodM 另一个 吧...
         */
        int random = new Random().nextInt(1000000);
        //liuzhiming
        sendMessage("我是测试消息" + random, "liuzhiming", new IUIKitCallback<Object>() {
        });

        //@USR#Xh405EhPodM
        sendMessage("我是测试消息" + random, "@USR#Xh405EhPodM", new IUIKitCallback<Object>() {
        });
    }


}
