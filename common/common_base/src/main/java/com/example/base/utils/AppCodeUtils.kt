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
             TOKEN_ERROR->{toastStr ="User not logged in, unauthorized, token expired"}
             UNKNOWN_ERROR->{toastStr ="Unknown error"}
             UNKNOWN_ARGUMENT_ERROR->{toastStr ="Unknown parameter"}
             UNKNOWN_ARGUMENT_FORMANT_ERROR->{toastStr ="Parameter format error"}
             FREQUENT_EMAIL_ERROR->{toastStr ="Email sending too frequent"}
             CODE_ERROR->{toastStr ="Verification code error"}
             PASSWORD_IS_INCONSISTENT_ERROR->{toastStr ="Password input inconsistent"}
             EMAIL_OUT_TIME_ERROR->{toastStr ="Email verification timeout"}
             TOKEN_CREATE_ERROR->{toastStr ="Token generation failed"}
             EMAIL_HAS_CREATE_ERROR->{toastStr ="This email is already registered, please log in"}
             EMAIL_UN_CREATE_ERROR->{toastStr ="This email is not registered, please register"}
             ACCOUNT_OR_PASSWORD_ERROR->{toastStr ="Account or password error"}
         }
        return toastStr
    }
}