package com.example.base.common.v2t.im.core;

import android.text.TextUtils;
import android.view.View;

import com.example.base.common.v2t.im.core.interfaces.ITUIExtension;
import com.example.base.common.v2t.im.core.interfaces.TUIExtensionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UI extension registration and acquisition
 */
class ExtensionManager {
    private static final String TAG = ExtensionManager.class.getSimpleName();

    private static class ExtensionManagerHolder {
        private static final ExtensionManager extensionManager = new ExtensionManager();
    }

    public static ExtensionManager getInstance() {
        return ExtensionManagerHolder.extensionManager;
    }

    private final Map<String, List<ITUIExtension>> extensionHashMap = new ConcurrentHashMap<>();

    private ExtensionManager() {}

    public void registerExtension(String extensionID, ITUIExtension extension) {

        if (TextUtils.isEmpty(extensionID) || extension == null) {
            return;
        }
        List<ITUIExtension> list = extensionHashMap.get(extensionID);
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
            extensionHashMap.put(extensionID, list);
        }
        if (!list.contains(extension)) {
            list.add(extension);
        }
    }

    public void unRegisterExtension(String extensionID, ITUIExtension extension) {

        if (TextUtils.isEmpty(extensionID) || extension == null) {
            return;
        }
        List<ITUIExtension> list = extensionHashMap.get(extensionID);
        if (list == null) {
            return;
        }
        list.remove(extension);
    }

    @Deprecated
    public Map<String, Object> getExtensionInfo(String key, Map<String, Object> param) {

        if (TextUtils.isEmpty(key)) {
            return null;
        }
        List<ITUIExtension> list = extensionHashMap.get(key);
        if (list == null) {
            return null;
        }
        for (ITUIExtension extension : list) {
            return extension.onGetExtensionInfo(key, param);
        }
        return null;
    }

    public List<TUIExtensionInfo> getExtensionList(String extensionID, Map<String, Object> param) {

        List<TUIExtensionInfo> extensionInfoList = new ArrayList<>();
        if (TextUtils.isEmpty(extensionID)) {
            return extensionInfoList;
        }
        List<ITUIExtension> ituiExtensionList = extensionHashMap.get(extensionID);
        if (ituiExtensionList == null || ituiExtensionList.isEmpty()) {
            return extensionInfoList;
        }
        for (ITUIExtension ituiExtension : ituiExtensionList) {
            List<TUIExtensionInfo> extensionInfo = ituiExtension.onGetExtension(extensionID, param);
            if (extensionInfo != null) {
                extensionInfoList.addAll(extensionInfo);
            }
        }
        return extensionInfoList;
    }

    public void raiseExtension(String extensionID, View parentView, Map<String, Object> param) {

        if (TextUtils.isEmpty(extensionID)) {
            return;
        }
        List<ITUIExtension> list = extensionHashMap.get(extensionID);
        if (list == null) {
            return;
        }

        boolean isResponded = false;
        for (ITUIExtension extension : list) {
            isResponded = extension.onRaiseExtension(extensionID, parentView, param);
            if (isResponded) {
                break;
            }
        }
    }
}
