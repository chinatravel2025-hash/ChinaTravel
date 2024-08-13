package com.travel.user.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.utils.PasswordUtil
import com.example.router.ARouterPathList

class UserEmailLoginVM:ViewModel() {
    var originPw = MutableLiveData("")
    var confirmPw = MutableLiveData("")
    var showPw = MutableLiveData(false)
    var canNext = MutableLiveData(false)

    fun navigationVerificationCode() {
        if (canNext.value!=true){
            SmartToast.classic().showInCenter("密码不符合规则")
        }else{
            ARouter.getInstance().build(ARouterPathList.USER_SET_NICKNAME)
                .navigation()
        }

    }

    fun setOriginPw(content: CharSequence?) {
        originPw.value = content.toString()
        checkNext()
    }

    fun setConfirmPw(content: CharSequence?) {
        confirmPw.value = content.toString()
        checkNext()
    }


    /**
     * 最少8位，最多20位。大写，小写，数字，特殊符号中包含其中2种。
     * At least 8 characters, no longer than 20 characters.
     * Contains at least 2 kinds of characters:
     * uppercase/lowercase/number/special symbol.
     */
    private fun checkNext() {
        val origin = PasswordUtil.isPswComplex(originPw.value)
        val confirm = PasswordUtil.isPswComplex(confirmPw.value)
        canNext.value = origin && confirm
    }


}