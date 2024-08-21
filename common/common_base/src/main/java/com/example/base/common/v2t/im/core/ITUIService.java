package com.example.base.common.v2t.im.core;

import com.example.base.common.v2t.im.core.interfaces.TUIServiceCallback;

import java.util.Map;

public interface ITUIService {
    default Object onCall(String method, Map<String, Object> param) {
        return null;
    }

    default Object onCall(String method, Map<String, Object> param, TUIServiceCallback callback) {
        return null;
    }
}
