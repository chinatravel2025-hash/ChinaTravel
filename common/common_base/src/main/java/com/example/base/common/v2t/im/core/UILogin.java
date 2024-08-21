package com.example.base.common.v2t.im.core;

import static com.tencent.imsdk.v2.V2TIMManager.V2TIM_STATUS_LOGINED;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.base.common.v2t.im.conversation.util.ErrorMessageConverter;
import com.example.base.common.v2t.im.core.interfaces.IMUICallback;
import com.example.base.common.v2t.im.core.interfaces.TUILogListener;
import com.example.base.common.v2t.im.core.interfaces.TUILoginConfig;
import com.example.base.common.v2t.im.core.interfaces.UILoginListener;
import com.example.base.utils.LogUtils;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMLogListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Login logic for IM and TRTC
 */
public class UILogin {
    private static final String TAG = UILogin.class.getSimpleName();

    private static class TUILoginHolder {
        private static final UILogin loginInstance = new UILogin();
    }

    public static UILogin getInstance() {
        return TUILoginHolder.loginInstance;
    }

    private static Context appContext;

    private int sdkAppId = 0;
    private String userId;
    private String userSig;
    private boolean hasLoginSuccess = false;
    private final List<UILoginListener> listenerList = new CopyOnWriteArrayList<>();

    private UILogin() {}

    private final V2TIMSDKListener imSdkListener = new V2TIMSDKListener() {
        @Override
        public void onConnecting() {
            LogUtils.i(TAG,"onConnecting....");
            for (UILoginListener listener : listenerList) {
                listener.onConnecting();
            }
            TUICore.notifyEvent(TUIConstants.NetworkConnection.EVENT_CONNECTION_STATE_CHANGED, TUIConstants.NetworkConnection.EVENT_SUB_KEY_CONNECTING, null);
        }

        @Override
        public void onConnectSuccess() {
            LogUtils.i(TAG,"onConnectSuccess....");

            for (UILoginListener listener : listenerList) {
                listener.onConnectSuccess();
            }
            TUICore.notifyEvent(
                TUIConstants.NetworkConnection.EVENT_CONNECTION_STATE_CHANGED, TUIConstants.NetworkConnection.EVENT_SUB_KEY_CONNECT_SUCCESS, null);
        }

        @Override
        public void onConnectFailed(int code, String error) {
            LogUtils.i(TAG,"onConnectFailed..code = " + code + "; error = " + error);

            for (UILoginListener listener : listenerList) {
                listener.onConnectFailed(code, error);
            }
            TUICore.notifyEvent(
                TUIConstants.NetworkConnection.EVENT_CONNECTION_STATE_CHANGED, TUIConstants.NetworkConnection.EVENT_SUB_KEY_CONNECT_FAILED, null);
        }

        @Override
        public void onKickedOffline() {
            LogUtils.i(TAG,"onKickedOffline..");

            for (UILoginListener listener : listenerList) {
                listener.onKickedOffline();
            }
            TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_KICKED_OFFLINE, null);
        }

        @Override
        public void onUserSigExpired() {

            LogUtils.i(TAG,"onUserSigExpired..");

            for (UILoginListener listener : listenerList) {
                listener.onUserSigExpired();
            }
            TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_SIG_EXPIRED, null);
        }

        @Override
        public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
            LogUtils.i(TAG,"onSelfInfoUpdated.." + info.toString());
            UIConfig.setSelfInfo(info);
            notifyUserInfoChanged(info);
        }
    };

    /**
     * IMSDK login
     *
     * @param context   The context of the application, generally is ApplicationContext
     * @param sdkAppId  Assigned when you register your app with Tencent Cloud
     * @param userId    User ID
     * @param userSig   Obtained from the business server
     * @param callback  login callback
     */
    public static void login(@NonNull Context context, int sdkAppId, String userId, String userSig, IMUICallback callback) {
        getInstance().internalLogin(context, sdkAppId, userId, userSig, null, callback);
    }

    /**
     * IMSDK login
     *
     * @param context   The context of the application, generally is ApplicationContext
     * @param sdkAppId  Assigned when you register your app with Tencent Cloud
     * @param userId    User ID
     * @param userSig   Obtained from the business server
     * @param config    log related configs
     * @param callback  login callback
     */
    public static void login(@NonNull Context context, int sdkAppId, String userId, String userSig, TUILoginConfig config, IMUICallback callback) {
        getInstance().internalLogin(context, sdkAppId, userId, userSig, config, callback);
    }


    /**
     * User Login
     *
     * @param userId   User ID
     * @param userSig  Obtained from the business server
     * @param callback  login callback
     */
    @Deprecated
    public static void login(@NonNull String userId, @NonNull String userSig, @Nullable V2TIMCallback callback) {
        getInstance().userId = userId;
        getInstance().userSig = userSig;
        if (TextUtils.equals(userId, V2TIMManager.getInstance().getLoginUser()) && !TextUtils.isEmpty(userId)) {
            if (callback != null) {
                callback.onSuccess();
            }
            getUserInfo(userId);
            return;
        }

        V2TIMManager.getInstance().login(userId, userSig, new V2TIMCallback() {
            @Override
            public void onSuccess() {
                getInstance().hasLoginSuccess = true;
                if (callback != null) {
                    callback.onSuccess();
                }
                getUserInfo(userId);
                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGIN_SUCCESS, null);
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, ErrorMessageConverter.convertIMError(code, desc));
                }
            }
        });
    }


    /**
     * IMSDK logout
     * @param callback  logout callback
     */
    public static void logout(IMUICallback callback) {
        getInstance().internalLogout(callback);
    }

    /**
     * User Logout
     *
     * @param callback  Logout callback
     */
    @Deprecated
    public static void logout(@Nullable V2TIMCallback callback) {
        V2TIMManager.getInstance().logout(new V2TIMCallback() {
            @Override
            public void onSuccess() {
                getInstance().userId = null;
                getInstance().userSig = null;
                if (callback != null) {
                    callback.onSuccess();
                }
                UIConfig.clearSelfInfo();

                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGOUT_SUCCESS, null);
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, ErrorMessageConverter.convertIMError(code, desc));
                }
            }
        });
    }

    public static void addLoginListener(UILoginListener listener) {
        getInstance().internalAddLoginListener(listener);
    }

    public static void removeLoginListener(UILoginListener listener) {
        getInstance().internalRemoveLoginListener(listener);
    }

    private void internalLogin(Context context, final int sdkAppId, final String userId, final String userSig, TUILoginConfig config, IMUICallback callback) {
        //账号不同发起退登操作.. 否则发起
        if (this.sdkAppId != 0 && sdkAppId != this.sdkAppId && !TextUtils.equals(userId, V2TIMManager.getInstance().getLoginUser()) && !TextUtils.isEmpty(userId)) {
            LogUtils.i(TAG,"IMLogin,internalLogout 在原始阶段发起退登了 success userId = " + userId );
            logout((IMUICallback) null);
        }
        this.appContext = context.getApplicationContext();
        this.sdkAppId = sdkAppId;
        V2TIMManager.getInstance().addIMSDKListener(imSdkListener);
        // Notify init event
        TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_IMSDK_INIT_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_START_INIT, null);
        // User operation initialization, the privacy agreement has been read by default

        V2TIMSDKConfig v2TIMSDKConfig = null;
        if (config != null) {
            v2TIMSDKConfig = new V2TIMSDKConfig();
            v2TIMSDKConfig.setLogLevel(config.getLogLevel());
            TUILogListener logListener = config.getLogListener();
            if (logListener != null) {
                v2TIMSDKConfig.setLogListener(new V2TIMLogListener() {
                    @Override
                    public void onLog(int logLevel, String logContent) {
                        logListener.onLog(logLevel, logContent);
                    }
                });
            }
        }

        boolean initSuccess = V2TIMManager.getInstance().initSDK(context, sdkAppId, v2TIMSDKConfig);
        if (initSuccess) {
            callback.sdkInitFinish(true);
            this.userId = userId;
            this.userSig = userSig;
            //相同账号已登录直接登录
            if (TextUtils.equals(userId, V2TIMManager.getInstance().getLoginUser()) && !TextUtils.isEmpty(userId)) {
                hasLoginSuccess = true;
                getUserInfo(userId);
                IMUICallback.onSuccess(callback);
                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGIN_SUCCESS, null);
                return;
            }

            V2TIMManager.getInstance().login(userId, userSig, new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    hasLoginSuccess = true;
                    getUserInfo(userId);
                    IMUICallback.onSuccess(callback);
                    TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGIN_SUCCESS, null);
                }

                @Override
                public void onError(int code, String desc) {
                    IMUICallback.onError(callback, code, ErrorMessageConverter.convertIMError(code, desc));
                }
            });
        } else {
            callback.sdkInitFinish(false);
            IMUICallback.onError(callback, -1, "init failed");
        }
    }

    private void internalLogout(IMUICallback callback) {

        if(sdkAppId == 0 || userId == null){
            IMUICallback.onSuccess(callback);
            return;
        }

        TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_IMSDK_INIT_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_START_UNINIT, null);
        V2TIMManager.getInstance().logout(new V2TIMCallback() {
            @Override
            public void onSuccess() {
                LogUtils.i(TAG,"IMLogin,internalLogout success userId = " + userId );
                sdkAppId = 0;
                userId = null;
                userSig = null;
                V2TIMManager.getInstance().unInitSDK();
                UIConfig.clearSelfInfo();
                IMUICallback.onSuccess(callback);
                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_LOGOUT_SUCCESS, null);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.i(TAG,"IMLogin,internalLogout error userId = " + userId );

                IMUICallback.onError(callback, code, desc);
            }
        });
    }

    private void internalAddLoginListener(UILoginListener listener) {
        LogUtils.i(TAG, "addLoginListener listener : " + listener);
        if (listener == null) {
            return;
        }
        if (!listenerList.contains(listener)) {
            listenerList.add(listener);
        }
    }

    private void internalRemoveLoginListener(UILoginListener listener) {
        LogUtils.i(TAG, "removeLoginListener listener : " + listener);
        if (listener == null) {
            return;
        }
        listenerList.remove(listener);
    }

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    /**
     * IMSDK init
     *
     * @param context      The context of the application, generally is ApplicationContext
     * @param sdkAppId     Assigned when you register your app with Tencent Cloud
     * @param config       The related configuration items of IMSDK, generally use the default,
     *                     and need special configuration, please refer to the API documentation
     * @param listener     Listener of IMSDK init
     * @return true：init success；false：init failed
     */
    @Deprecated
    public static boolean init(@NonNull Context context, int sdkAppId, @Nullable V2TIMSDKConfig config, @Nullable V2TIMSDKListener listener) {
        if (getInstance().sdkAppId != 0 && sdkAppId != getInstance().sdkAppId) {
            logout((V2TIMCallback) null);
            unInit();
        }
        getInstance().appContext = context.getApplicationContext();
        getInstance().sdkAppId = sdkAppId;
        V2TIMManager.getInstance().addIMSDKListener(new V2TIMSDKListener() {
            @Override
            public void onConnecting() {
                if (listener != null) {
                    listener.onConnecting();
                }
            }

            @Override
            public void onConnectSuccess() {
                if (listener != null) {
                    listener.onConnectSuccess();
                }
            }

            @Override
            public void onConnectFailed(int code, String error) {
                if (listener != null) {
                    listener.onConnectFailed(code, ErrorMessageConverter.convertIMError(code, error));
                }
            }

            @Override
            public void onKickedOffline() {
                if (listener != null) {
                    listener.onKickedOffline();
                }
                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_KICKED_OFFLINE, null);
            }

            @Override
            public void onUserSigExpired() {
                if (listener != null) {
                    listener.onUserSigExpired();
                }
                TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_SIG_EXPIRED, null);
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                if (listener != null) {
                    listener.onSelfInfoUpdated(info);
                }

                UIConfig.setSelfInfo(info);
                notifyUserInfoChanged(info);
            }
        });
        TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_IMSDK_INIT_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_START_INIT, null);
        return V2TIMManager.getInstance().initSDK(context, sdkAppId, config);
    }

    @Deprecated
    public static void unInit() {
        getInstance().sdkAppId = 0;
        TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_IMSDK_INIT_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_START_UNINIT, null);

        V2TIMManager.getInstance().unInitSDK();
        UIConfig.clearSelfInfo();
    }

    private static void getUserInfo(String userId) {
        List<String> userIdList = new ArrayList<>();
        userIdList.add(userId);
        V2TIMManager.getInstance().getUsersInfo(userIdList, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {
            @Override
            public void onSuccess(List<V2TIMUserFullInfo> v2TIMUserFullInfos) {
                if (v2TIMUserFullInfos.isEmpty()) {
                    LogUtils.e(TAG, "get logined userInfo failed. list is empty");
                    return;
                }
                V2TIMUserFullInfo userFullInfo = v2TIMUserFullInfos.get(0);
                UIConfig.setSelfInfo(userFullInfo);
                notifyUserInfoChanged(userFullInfo);
            }

            @Override
            public void onError(int code, String desc) {
                LogUtils.e(TAG, "get logined userInfo failed. code : " + code + " desc : " + ErrorMessageConverter.convertIMError(code, desc));
            }
        });
    }

    private static void notifyUserInfoChanged(V2TIMUserFullInfo userFullInfo) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUILogin.SELF_ID, userFullInfo.getUserID());
        param.put(TUIConstants.TUILogin.SELF_SIGNATURE, userFullInfo.getSelfSignature());
        param.put(TUIConstants.TUILogin.SELF_NICK_NAME, userFullInfo.getNickName());
        param.put(TUIConstants.TUILogin.SELF_FACE_URL, userFullInfo.getFaceUrl());
        param.put(TUIConstants.TUILogin.SELF_BIRTHDAY, userFullInfo.getBirthday());
        param.put(TUIConstants.TUILogin.SELF_ROLE, userFullInfo.getRole());
        param.put(TUIConstants.TUILogin.SELF_GENDER, userFullInfo.getGender());
        param.put(TUIConstants.TUILogin.SELF_LEVEL, userFullInfo.getLevel());
        param.put(TUIConstants.TUILogin.SELF_ALLOW_TYPE, userFullInfo.getAllowType());
        TUICore.notifyEvent(TUIConstants.TUILogin.EVENT_LOGIN_STATE_CHANGED, TUIConstants.TUILogin.EVENT_SUB_KEY_USER_INFO_UPDATED, param);
    }

    public static int getSdkAppId() {
        return getInstance().sdkAppId;
    }

    public static String getUserId() {
        return getInstance().userId;
    }

    public static String getUserSig() {
        return getInstance().userSig;
    }

    public static String getNickName() {
        return UIConfig.getSelfNickName();
    }

    public static String getFaceUrl() {
        return UIConfig.getSelfFaceUrl();
    }

    public static Context getAppContext() {
        return getInstance().appContext;
    }

    public static boolean isUserLogined() {
        return getInstance().hasLoginSuccess && V2TIMManager.getInstance().getLoginStatus() == V2TIM_STATUS_LOGINED;
    }

    public static String getLoginUser() {
        return V2TIMManager.getInstance().getLoginUser();
    }

}