package com.example.base.common.v2t.im.commom.util;

import android.text.TextUtils;

import com.example.base.common.v2t.im.commom.bean.MessageFeature;
import com.example.base.common.v2t.im.commom.bean.MessageReactBean;
import com.example.base.common.v2t.im.commom.bean.MessageRepliesBean;
import com.example.base.common.v2t.im.commom.bean.TUIMessageBean;
import com.example.base.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageParser {
    private static final String TAG = MessageParser.class.getSimpleName();

    public static MessageReactBean parseMessageReact(TUIMessageBean messageBean) {
        V2TIMMessage message = messageBean.getV2TIMMessage();
        String cloudCustomData = message.getCloudCustomData();

        try {
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(cloudCustomData, HashMap.class);
            if (hashMap != null) {
                Object reactContentObj = hashMap.get(TIMCommonConstants.MESSAGE_REACT_KEY);
                MessageReactBean reactBean = null;
                if (reactContentObj instanceof Map) {
                    reactBean = gson.fromJson(gson.toJson(reactContentObj), MessageReactBean.class);
                }
                if (reactBean != null) {
                    if (reactBean.getVersion() > MessageReactBean.VERSION) {
                        return null;
                    }
                    return reactBean;
                }
            }
        } catch (JsonSyntaxException e) {
            LogUtils.e(TAG, " getCustomJsonMap error ");
        }
        return null;
    }

    public static MessageRepliesBean parseMessageReplies(TUIMessageBean messageBean) {
        V2TIMMessage message = messageBean.getV2TIMMessage();
        String cloudCustomData = message.getCloudCustomData();

        try {
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(cloudCustomData, HashMap.class);
            if (hashMap != null) {
                Object repliesContentObj = hashMap.get(TIMCommonConstants.MESSAGE_REPLIES_KEY);
                MessageRepliesBean repliesBean = null;
                if (repliesContentObj instanceof Map) {
                    repliesBean = gson.fromJson(gson.toJson(repliesContentObj), MessageRepliesBean.class);
                }
                if (repliesBean != null) {
                    if (repliesBean.getVersion() > MessageRepliesBean.VERSION) {
                        return null;
                    }
                    return repliesBean;
                }
            }
        } catch (JsonSyntaxException e) {
            LogUtils.e(TAG, " getCustomJsonMap error ");
        }
        return null;
    }

    public static MessageFeature isSupportTyping(TUIMessageBean messageBean) {
        String cloudCustomData = messageBean.getV2TIMMessage().getCloudCustomData();
        if (TextUtils.isEmpty(cloudCustomData)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            HashMap featureHashMap = gson.fromJson(cloudCustomData, HashMap.class);
            if (featureHashMap != null) {
                Object featureContentObj = featureHashMap.get(TIMCommonConstants.MESSAGE_FEATURE_KEY);
                MessageFeature messageFeature = null;
                if (featureContentObj instanceof Map) {
                    messageFeature = gson.fromJson(gson.toJson(featureContentObj), MessageFeature.class);
                }
                if (messageFeature != null) {
                    if (messageFeature.getVersion() > MessageFeature.VERSION) {
                        return null;
                    }

                    return messageFeature;
                }
            }
        } catch (JsonSyntaxException e) {
            LogUtils.e(TAG, " isSupportTyping exception e = " + e);
        }
        return null;
    }
}
