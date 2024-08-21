package com.example.base.toast;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.base.base.App;
import com.example.base.toast.ToastDialog;

public class ToastHelper {
    public static final int TOAST_TXT = 0;
    public static final int TOAST_HTTP_ERROR = -1;
    public static final int TOAST_FAIL = -2;
    public static final int TOAST_HTTP_SUCCESS = 1;
    public static final int TOAST_HTTP_DOWNLOAD = 2;
    public static final int TOAST_HTTP_LOADING = 3;
    private static View toastView;
    private static Toast mToast;

    public static void createToastToTxt(String content) {
        toastView = ToastDialog.INSTANCE.getView(App.getContext(), content, TOAST_TXT);
        startToast(App.getContext());
    }

    public static void createLongToastToTxt(String content) {
        toastView = ToastDialog.INSTANCE.getView(App.getContext(), content, TOAST_TXT);
        startToast(App.getContext(),Toast.LENGTH_LONG);
    }
    public static void createToastToTxt(int content) {
        toastView = ToastDialog.INSTANCE.getView(App.getContext(), App.getContext().getString(content), TOAST_TXT);
        startToast(App.getContext());
    }

    public static void createToastToTxt(Context context, String content) {
        if (!checkParams(context)) {
            return;
        }

        toastView = ToastDialog.INSTANCE.getView(context, content, TOAST_TXT);
        startToast(context);
    }

    public static void createToastToHttpError(Context context, String content) {
        if (!checkParams(context)) {
            return;
        }
        toastView = ToastDialog.INSTANCE.getView(context, content, TOAST_HTTP_ERROR);
        startToast(context);
    }

    public static void createToastToFail(Context context, String content) {
        if (!checkParams(context)) {
            return;
        }


        toastView = ToastDialog.INSTANCE.getView(context, content, TOAST_FAIL);
        startToast(context);
    }

    public static void createToastToSuccess(Context context, String content) {
        if (!checkParams(context)) {
            return;
        }
        toastView = ToastDialog.INSTANCE.getView(context, content, TOAST_HTTP_SUCCESS);
        startToast(context);
    }

    public static void createToastToDownload(Context context, String content) {
        if (!checkParams(context)) {
            return;
        }
        toastView = ToastDialog.INSTANCE.getView(context, content, TOAST_HTTP_DOWNLOAD);
        startToast(context);
    }

    private static void startToast(Context context) {
        startToast(context,Toast.LENGTH_SHORT);
    }

    private static void startToast(Context context,int time) {
        if (!checkParams(context)) {
            return;
        }
        if (mToast==null){
            mToast = new Toast(context);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(time);
        mToast.setView(toastView);
        mToast.show();
    }


    private static boolean checkParams(Context context){

        if(context != null && Looper.getMainLooper() == Looper.myLooper()){
            return true;
        }

        return false;
    }


}
