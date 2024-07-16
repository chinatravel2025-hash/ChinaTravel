package com.example.base.utils

import android.media.MediaMetadataRetriever
import com.aws.bean.msg.V2TMsgExtLocal
import com.aws.bean.util.GsonUtil
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import java.util.UUID
import kotlin.math.roundToInt

object VideoMsgUtil {
    fun buildVideoMessage(path: String): V2TIMMessage? {
        val mmr = MediaMetadataRetriever()
        try {
            mmr.setDataSource(path)
            val sDuration =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val bitmap =
                mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC)
            if (bitmap == null) {

                return null
            }
            val bitmapPath: String = FileUtil.generateImageFilePath()
            val result: Boolean = FileUtil.saveBitmap(bitmapPath, bitmap)
            if (!result) {
                return null
            }
            val imgWidth = bitmap.width
            val imgHeight = bitmap.height
            val duration = java.lang.Long.valueOf(sDuration)
            val message = V2TIMManager.getMessageManager()
                .createVideoMessage(path, "mp4", (duration * 1.0f / 1000).roundToInt(), bitmapPath)
            val uuid = UUID.randomUUID().toString()
            message.localCustomData = GsonUtil.toJson(V2TMsgExtLocal(uuid, 0, -1).apply {
                ours_width = imgWidth
                ours_height = imgHeight
            })
            return message
        } catch (ex: Exception) {

        } finally {
            mmr.release()
        }
        return null
    }
}