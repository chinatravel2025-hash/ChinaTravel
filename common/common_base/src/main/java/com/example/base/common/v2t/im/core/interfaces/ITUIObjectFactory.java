package com.example.base.common.v2t.im.core.interfaces;

import java.util.Map;

public interface ITUIObjectFactory {
    Object onCreateObject(String objectName, Map<String, Object> param);
}
