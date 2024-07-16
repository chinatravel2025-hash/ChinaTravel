package com.example.http;

import android.os.Build;
import android.text.TextUtils;

import com.example.base.base.App;
import com.example.base.base.User;
import com.example.http.app.TokenInvalidEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class APIService {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void initRetrofit(String buildType) {
        API.Companion.setEnv(buildType);
        RequestManager.INSTANCE.addInterceptor(chain -> {
            String ts = String.valueOf(System.currentTimeMillis());
            String cudid = App.getDeviceId();
            String version = App.getVersion();
            String token = User.INSTANCE.getToken();
            String market = App.getChannel();
            String phoneModel = Build.BRAND + "_" + Build.MODEL;
            String phoneVersion = Build.VERSION.RELEASE;
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "bearer " + token)
                    .addHeader("client-user",
                            "cudid=" + cudid + "&open_udid=" + cudid
                                    + "&app_version=" + version + "&model=" + phoneModel
                                    + "&app_channel=" + market + "&os=" + phoneVersion+"&timestamp=" + ts)
                   // .addHeader("client-dynamic", "lat="+App.getLat()+"&lng="+App.getLng())
                    .addHeader("client-dynamic", "lat="+"39.907259"+"&lng="+"116.413023"+"&city_code="+"110100")
                    .build();
            Response response = chain.proceed(request);
            String responseString = "";
            try {
                responseString = response.body().string();
                JSONObject json = new JSONObject(responseString);
                if (json.has("code")) {//token过期
                    String errCode = json.getString("code");
                    if (!TextUtils.isEmpty(errCode) && (errCode.startsWith("401") && errCode.length() == 3)) {
                        EventBus.getDefault().post(new TokenInvalidEvent());
                    }
                }
            } catch (Exception e) {

            }
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString)).build();
        });
        retrofit = RequestManager.INSTANCE.build(new API().hostH5(), null);
    }
}
