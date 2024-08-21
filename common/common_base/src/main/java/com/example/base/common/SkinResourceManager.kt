package com.example.base.common


import android.app.Application
import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieImageAsset
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.base.base.App
import com.example.base.base.User
import com.example.base.utils.LogUtils
import com.example.base.utils.ReflectionUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.libpag.PAGImageView
import org.libpag.PAGScaleMode
import java.io.File
import java.io.FileInputStream


object SkinResourceManager {

    var isNeedChangeSkin = false

    val DEFAULT_SKIN_PKG_OUT_PATH by lazy {
        app?.let {
            var path = it.getExternalFilesDir(null)?.absolutePath ?: it.filesDir.absolutePath
            var _path = path + File.separator + "skin"
            _path
        } ?: throw NullPointerException("context未初始化")
    }


    var TAG = "SkinResource"

    var app: Application? = null

    //文件夹路径
    /**
     *
     * repeatCount =-1 循环播放.
     */
    fun setPGAAnimation(
        pagImageView: PAGImageView,
        pagFileName: String,
        autoPlay: Boolean = true,
        repeatCount:Int = -1,
        scaleMode:Int = PAGScaleMode.LetterBox
    ) {

        if (TextUtils.isEmpty(pagFileName)) {
            return
        }
        pagImageView.clearAnimation()
        if(pagImageView.composition != null)
            pagImageView.composition.removeAllLayers()
        //如果存储卡上有这个地址，那么使用存储卡的动画，否则什么都不做.  取最后的动画名字
        var anName = if (pagFileName.contains(File.separator)) {

            var parts = pagFileName.split(File.separator)
            //倒手第二个
            parts[parts.size - 1]

        } else {
            pagFileName
        }

        var success = false
        /*getAnimationPath(anName)?.let {//todo
            try {
                val animationFile = File("${it}${File.separator}${anName}.pag")
                if (animationFile.exists()) {
                    //pagImageView.setCurrentFrame(1)
                    pagImageView.setPath(animationFile.absolutePath)
                    success = true
                } else {
                    // 文件不存在的处理逻辑
                }
                "shi yong"
            } catch (e: Exception) {
                LogUtils.e(TAG, e?.message ?: "lottie 动画解析出错")
            }
        }*/

        //表示
        if (!success) {
            LogUtils.i(TAG, "rid = ${User.ridString}")
            //pagImageView.setCurrentFrame(1)
            pagImageView.setPath("assets://${pagFileName}${File.separator}${anName}.pag")
            LogUtils.i(TAG, "${anName}加载了本地")
        }
        pagImageView.setRepeatCount(repeatCount)
        pagImageView.setScaleMode(scaleMode)
        if (autoPlay) {
            pagImageView.play()
        }
    }

}