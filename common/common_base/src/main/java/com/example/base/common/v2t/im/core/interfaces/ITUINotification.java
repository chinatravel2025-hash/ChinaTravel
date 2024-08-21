package com.example.base.common.v2t.im.core.interfaces;

import java.util.Map;

public interface ITUINotification {
    void onNotifyEvent(String key, String subKey, Map<String, Object> param);
}
