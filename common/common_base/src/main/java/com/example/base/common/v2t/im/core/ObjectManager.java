package com.example.base.common.v2t.im.core;

import android.text.TextUtils;


import com.example.base.common.v2t.im.core.interfaces.ITUIObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object register and create
 */
class ObjectManager {
    private static final String TAG = ObjectManager.class.getSimpleName();

    private static class ObjectManagerHolder {
        private static final ObjectManager serviceManager = new ObjectManager();
    }

    public static ObjectManager getInstance() {
        return ObjectManagerHolder.serviceManager;
    }

    private final Map<String, ITUIObjectFactory> objectFactoryMap = new ConcurrentHashMap<>();

    private ObjectManager() {}

    public void registerObjectFactory(String factoryName, ITUIObjectFactory objectFactory) {
        if (TextUtils.isEmpty(factoryName) || objectFactory == null) {
            return;
        }
        objectFactoryMap.put(factoryName, objectFactory);
    }

    public void unregisterObjectFactory(String factoryName) {
        if (TextUtils.isEmpty(factoryName)) {
            return;
        }
        objectFactoryMap.remove(factoryName);
    }

    public Object createObject(String factoryName, String objectName, Map<String, Object> param) {
        if (TextUtils.isEmpty(factoryName)) {
            return null;
        }
        ITUIObjectFactory objectFactory = objectFactoryMap.get(factoryName);
        if (objectFactory != null) {
            return objectFactory.onCreateObject(objectName, param);
        } else {
            return null;
        }
    }
}
