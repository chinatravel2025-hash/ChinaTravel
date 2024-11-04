package com.travel.user.vm

import LoginRepository
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.dialog.StatusDialog
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.ext.showToast
import com.example.base.utils.LogUtils
import com.example.base.utils.PasswordUtil
import com.example.base.utils.SmartActivityUtils
import com.example.router.ARouterPathList
import com.tencent.qcloud.tuicore.util.ToastUtil

class UserPasswordSettingVM : ViewModel() {
    var mail :String?=null
    var actionType :Int?=0
    var originPw = MutableLiveData("")
    var confirmPw = MutableLiveData("")
    var showPw = MutableLiveData(false)
    var canNext = MutableLiveData(false)

    fun navigationVerificationCode() {
        if (canNext.value!=true){
            SmartToast.classic().showInCenter("密码不符合规则")

        }else{
            //设置名称 actionType：0 注册流程 1 修改密码流程
            if (actionType==0){
                LoginRepository.loginRepository.registerByEmail(mail?:"",
                    originPw.value?:"",confirmPw.value?:""){
                    ARouter.getInstance().build(ARouterPathList.USER_SET_NICKNAME)
                        .navigation()
                }
            }else{
                LoginRepository.loginRepository.resetPwdByEmail(mail?:"",
                    originPw.value?:"",confirmPw.value?:""){
                showDialog((SmartActivityUtils.getTopActivity() as FragmentActivity).supportFragmentManager)
                }
            }


        }

    }

    private fun showDialog(fragmentManager: FragmentManager) {
        val show = StatusDialog() {
            ARouter.getInstance().build(ARouterPathList.USER_EMAIL_LOGIN)
                .withString("mail",mail?:"")
                .navigation(SmartActivityUtils.getTopActivity(), object : NavCallback() {
                    override fun onArrival(p0: Postcard?) {
                        SmartActivityUtils.getTopActivity().finish()
                    }

                })
            null
        }
        show.show(fragmentManager, "StatusDialog")
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