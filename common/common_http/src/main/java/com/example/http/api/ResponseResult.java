package com.example.http.api;


import android.text.TextUtils;


import com.example.common_http.BuildConfig;

import java.io.Serializable;

import retrofit2.Response;


public class ResponseResult<T> implements Serializable {
    private String code;
    private String requestId;
    private String businessDesc;
    private T data;
    private String msg;

    private long timestamp;
    private int httpCode;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return !TextUtils.isEmpty(msg) ? msg : "";
    }

    public T getData() {
        return data;
    }


    public boolean isSuccessful() {
        return TextUtils.equals(code, Code.SUCCESS);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExternalSuccessful() {
        return TextUtils.equals(code, Code.SUCCESS2);
    }

    public static <R> ResponseResult<R> onResponse(Response<R> response) {
        ResponseResult<R> responseResult = new ResponseResult<R>();
        responseResult.httpCode = response.code();
        responseResult.msg = response.message();
        try {
            if (response.isSuccessful()) {
                ResponseResult result = (ResponseResult) response.body();
                responseResult.code = result.code;
                responseResult.msg = result.msg;
                if (responseResult.isSuccessful()) {
                    responseResult.data = (R) result.data;
                }
            } else {
                responseResult.code = Code.SERVER_ERROR;
                responseResult.msg = response.message();
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.code = Code.UNKONW_ERROR;
            responseResult.msg = e.getMessage();
        }
        return responseResult;
    }

    public static <R> ResponseResult<R> onFailure(String message) {
        String msg = "网络错误,请检查网络连接";
        if (BuildConfig.DEBUG) {
            msg = message;
        }
        ResponseResult<R> responseResult = new ResponseResult<>();
        responseResult.code = Code.NETWORK_ERROR;
        responseResult.msg = msg;
        return responseResult;
    }
}