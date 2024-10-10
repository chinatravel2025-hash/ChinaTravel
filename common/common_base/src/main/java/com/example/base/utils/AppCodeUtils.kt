package com.example.base.utils

object AppCodeUtils {

    const val TOKEN_ERROR = 401 //用户未登录，未授权，token失效
    const val UNKNOWN_ERROR = 10000 //未知错误
    const val UNKNOWN_ARGUMENT_ERROR = 10001 //未知参数
    const val UNKNOWN_ARGUMENT_FORMANT_ERROR = 10002 //参数格式错误
    const val FREQUENT_EMAIL_ERROR = 10003 //邮件发送频繁
    const val CODE_ERROR = 10004 //验证码错误
    const val PASSWORD_IS_INCONSISTENT_ERROR = 10005 //密码输入前后不一致
    const val EMAIL_OUT_TIME_ERROR = 10006 //邮箱验证超时过期
    const val TOKEN_CREATE_ERROR = 20000 //token生成失败
    const val EMAIL_HAS_CREATE_ERROR = 20001 //该邮箱已注册，请前往登录
    const val EMAIL_UN_CREATE_ERROR = 20002 //该邮箱未注册，请前往注册
    const val ACCOUNT_OR_PASSWORD_ERROR = 20003 //账号或密码错误
    const val USER_HAS_ACTION = 10008 //用户已操作


    fun errorToast(code:Int):String{
        var toastStr=""
         when(code){
             TOKEN_ERROR->{toastStr ="用户未登录，未授权，token失效"}
             UNKNOWN_ERROR->{toastStr ="未知错误"}
             UNKNOWN_ARGUMENT_ERROR->{toastStr ="未知参数"}
             UNKNOWN_ARGUMENT_FORMANT_ERROR->{toastStr ="参数格式错误"}
             FREQUENT_EMAIL_ERROR->{toastStr ="邮件发送频繁"}
             CODE_ERROR->{toastStr ="验证码错误"}
             PASSWORD_IS_INCONSISTENT_ERROR->{toastStr ="密码输入前后不一致"}
             EMAIL_OUT_TIME_ERROR->{toastStr ="邮箱验证超时过期"}
             TOKEN_CREATE_ERROR->{toastStr ="token生成失败"}
             EMAIL_HAS_CREATE_ERROR->{toastStr ="该邮箱已注册，请前往登录"}
             EMAIL_UN_CREATE_ERROR->{toastStr ="该邮箱未注册，请前往登录"}
             ACCOUNT_OR_PASSWORD_ERROR->{toastStr ="账号或密码错误"}
         }
        return toastStr
    }
}