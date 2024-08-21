package com.example.base.common.v2t.im.core;

import android.text.TextUtils;


import com.example.base.common.v2t.im.core.interfaces.TUIServiceCallback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service register and call
 */
class ServiceManager {
    private static final String TAG = ServiceManager.class.getSimpleName();

    private static class ServiceManagerHolder {
        private static final ServiceManager serviceManager = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return ServiceManagerHolder.serviceManager;
    }

    private final Map<String, ITUIService> serviceMap = new ConcurrentHashMap<>();

    private ServiceManager() {}

    public void registerService(String serviceName, ITUIService service) {
        if (TextUtils.isEmpty(serviceName) || service == null) {
            return;
        }
        serviceMap.put(serviceName, service);
    }

    public void unregisterService(String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return;
        }
        serviceMap.remove(serviceName);
    }

    public ITUIService getService(String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }
        return serviceMap.get(serviceName);
    }

    public Object callService(String serviceName, String method, Map<String, Object> param) {
        ITUIService service = serviceMap.get(serviceName);
        if (service != null) {
            return service.onCall(method, param);
        } else {
            return null;
        }
    }

    public Object callService(String serviceName, String method, Map<String, Object> param, TUIServiceCallback callback) {
        ITUIService service = serviceMap.get(serviceName);
        if (service != null) {
            return service.onCall(method, param, callback);
        } else {
            return null;
        }
    }
}
