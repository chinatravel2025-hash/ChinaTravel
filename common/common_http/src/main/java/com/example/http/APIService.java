package com.example.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;


import com.example.http.app.TokenInvalidEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.UUID;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class APIService {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void initRetrofit(Context context,String buildType) {
        API.Companion.setEnv(buildType);
        RequestManager.INSTANCE.addInterceptor(chain -> {
            String ts = String.valueOf(System.currentTimeMillis());
            String token="";

            Request request = chain.request()
                    .newBuilder()
                    .addHeader("request-id", UUID.randomUUID().toString())
                    .addHeader("request-agent", "1")

                    .addHeader("os-version", "1")
                    .addHeader("sdk-version", Build.VERSION.RELEASE)
                    .addHeader("phone-model", Build.BRAND + "_" + Build.MODEL)
                   // .addHeader("market", App.getChannel())
                   .addHeader("token", token)
                    .addHeader("app-name", "1")
                  //  .addHeader("app-id", AppConfig.APP_ID)
                    .addHeader("timestamp", ts)
                  //  .addHeader("customer-id", User.INSTANCE.getUserId())
                  //  .addHeader("access-token", User.INSTANCE.getToken())
                //    .addHeader("sign",
                   //         SHA256Util.hashByHMacSHA256(AppConfig.APP_ID + ts,
                 //                   AppConfig.APP_SECRET))
                    .build();
            Response response = chain.proceed(request);
            String responseString = "";
            try {
                responseString = response.body().string();
                JSONObject json = new JSONObject(responseString);
                if (json.has("code")) {
                    String errCode = json.getString("code");
                    if (!TextUtils.isEmpty(errCode) &&( (errCode.startsWith("40210")|| (errCode.startsWith("40209"))))) {
                        EventBus.getDefault().post(new TokenInvalidEvent());
                    }
                }
            } catch (Exception e) {

            }
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString)).build();
        });
        retrofit = RequestManager.INSTANCE.build(new API().host(), null);
    }
}
