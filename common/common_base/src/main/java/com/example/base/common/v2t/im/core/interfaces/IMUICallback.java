package com.example.base.common.v2t.im.core.interfaces;

public abstract class IMUICallback {
    public abstract void onSuccess();

    public static void onSuccess(IMUICallback callback) {
        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void sdkInitFinish(boolean isSuccess){

    }

    public abstract void onError(int errorCode, String errorMessage);

    public static void onError(IMUICallback callback, int errorCode, String errorMessage) {
        if (callback != null) {
            callback.onError(errorCode, errorMessage);
        }
    }
}
