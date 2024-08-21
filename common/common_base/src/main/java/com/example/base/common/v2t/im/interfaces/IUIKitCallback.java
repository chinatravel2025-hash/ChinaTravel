package com.example.base.common.v2t.im.interfaces;

public abstract class IUIKitCallback<T> {
     public void onSuccess(T data){};

     public void onError(int errCode, String errMsg){} ;

     public void onProgress(Object data){};

}
