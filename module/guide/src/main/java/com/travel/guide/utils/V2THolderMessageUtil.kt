package com.travel.guide.utils

import com.aws.bean.msg.V2TMsgExtLocal
import com.aws.bean.util.GsonUtil
import com.example.base.base.App
import com.example.base.common.v2t.IMCallback
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.LogUtils
import com.example.base.utils.getReadPath
import com.example.base.utils.getReadSnapshotPath
import com.example.base.utils.getReadVideoPath
import com.tencent.imsdk.v2.V2TIMDownloadCallback
import com.tencent.imsdk.v2.V2TIMElem
import com.tencent.imsdk.v2.V2TIMMessage


/**
 * 转换类
 */
object V2THolderMessageUtil {
    val downloadMap = HashSet<String>()
    fun downloadFile(vo: TUIMessageBean) {
        if (downloadMap.contains(vo.message?.msgID?:"")) {
            return
        }
        LogUtils.i("V2THolderMessageUtil","downloadFile--${vo.message?.msgID}")
        downloadMap.add(vo.message?.msgID?:"")
        val msg = vo.message ?: return

        val callback = object : V2TIMDownloadCallback {
            override fun onSuccess() {
                LogUtils.e("V2THolderMessageUtil","onSuccess--${vo.message?.msgID}")
                msg.localCustomData = GsonUtil.toJson(V2TMsgExtLocal("",-1, 100))
            }

            override fun onError(code: Int, desc: String?) {
                LogUtils.e("V2THolderMessageUtil","onError--$code---$desc--${vo.message?.msgID}")
                msg.localCustomData = GsonUtil.toJson(V2TMsgExtLocal("",-1, -1))
                downloadMap.remove(vo.message?.msgID?:"")
            }

            override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo?) {
                progressInfo?.apply {
                    val p = ((currentSize.toFloat()/totalSize.toFloat())*100).toInt()
                    LogUtils.i("V2THolderMessageUtil","onProgress--$p--${vo.message?.msgID}")
                    msg.localCustomData = GsonUtil.toJson(V2TMsgExtLocal("",-1, p))
                    IMCallback.sendMessageProgress(vo)
                }
            }
        }
        when(msg.elemType){
            V2TIMMessage.V2TIM_ELEM_TYPE_SOUND->{
                if(msg.soundElem.path?.isNotEmpty() == true){
                    return
                }
                msg.soundElem.downloadSound(msg.soundElem.getReadPath(),callback)
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO->{
                if(msg.videoElem.videoPath?.isNotEmpty() == true){
                    return
                }
                msg.videoElem.downloadSnapshot(msg.videoElem.getReadSnapshotPath(),
                    object : V2TIMDownloadCallback {
                        override fun onSuccess() {
                            msg.videoElem.downloadVideo(msg.videoElem.getReadVideoPath(),callback)
                        }

                        override fun onError(code: Int, desc: String?) {
                            msg.videoElem.downloadVideo(msg.videoElem.getReadVideoPath(),callback)
                        }

                        override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo?) {
                        }
                    })
            }
        }
    }

    fun liteOpenMessage(conversationId:String,msg:V2TIMMessage) {
        IMCallback.openMessage(conversationId,msg)
    }
}