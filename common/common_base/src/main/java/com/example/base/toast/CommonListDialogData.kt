package com.example.base.toast

import com.example.peanutmusic.base.R

object CommonListDialogData {
    /**
     * 聊天页面的电话按钮
     */
    fun phoneData(): List<CommonListBean> {
        val temp = mutableListOf<CommonListBean>()
        temp.add(CommonListBean("语音通话", textColor = R.color.black , textSize =  17, isBold = true))
        temp.add(CommonListBean("OURS通话", textColor = R.color.black ,textSize = 17, isBold = true))
        temp.add(CommonListBean("取消", textColor = R.color.red_f01164, 15, isBold = true))
        return temp
    }

}