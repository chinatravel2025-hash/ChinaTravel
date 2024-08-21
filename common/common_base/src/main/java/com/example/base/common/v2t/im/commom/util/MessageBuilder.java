package com.example.base.common.v2t.im.commom.util;

import android.text.TextUtils;

import com.example.base.common.v2t.im.commom.bean.TUIMessageBean;
import com.example.base.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;

public class MessageBuilder {
    private static final String TAG = MessageBuilder.class.getSimpleName();

    public static void mergeCloudCustomData(TUIMessageBean messageBean, String key, Object data) {
        if (messageBean == null || messageBean.getV2TIMMessage() == null) {
            return;
        }
        String cloudCustomData = messageBean.getV2TIMMessage().getCloudCustomData();
        Gson gson = new Gson();
        HashMap hashMap = null;
        if (TextUtils.isEmpty(cloudCustomData)) {
            hashMap = new HashMap();
        } else {
            try {
                hashMap = gson.fromJson(cloudCustomData, HashMap.class);
            } catch (JsonSyntaxException e) {
                LogUtils.e(TAG, " mergeCloudCustomData error " + e.getMessage());
            }
        }
        if (hashMap != null) {
            hashMap.put(key, data);
            cloudCustomData = gson.toJson(hashMap);
        }
        messageBean.getV2TIMMessage().setCloudCustomData(cloudCustomData);
    }
}
