package com.example.base.common.v2t.im.core.interfaces;

import java.util.Map;

public abstract class TUIExtensionEventListener {
    public void onClicked(Map<String, Object> param) {}

    public void onLongPressed(Map<String, Object> param) {}

    public void onTouched(Map<String, Object> param) {}

    public void onSwiped(int direction, Map<String, Object> param) {}
}
