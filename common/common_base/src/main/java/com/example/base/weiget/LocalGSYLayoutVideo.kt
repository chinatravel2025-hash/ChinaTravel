package com.example.base.weiget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.base.base.App
import com.example.base.glide.GlideApp
import com.example.base.utils.LogUtils
import com.example.peanutmusic.base.R
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer


class LocalGSYLayoutVideo : StandardGSYVideoPlayer {

    private val TAG = this.javaClass.simpleName

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var ivCover: ImageView? = null

    var isResetPageAfterPlayEnd = true

    override fun getLayoutId(): Int {
        return R.layout.view_local_video
    }

    override fun resolveNormalVideoShow(oldF: View, vp: ViewGroup, gsyVideoPlayer: GSYVideoPlayer) {
        val landLayoutVideo = gsyVideoPlayer as LocalGSYLayoutVideo
        landLayoutVideo.dismissProgressDialog()
        landLayoutVideo.dismissVolumeDialog()
        landLayoutVideo.dismissBrightnessDialog()
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer)
    }

    override fun init(context: Context) {
        super.init(context)
        ivCover = findViewById(R.id.iv_cover)
        mTextureViewContainer.setOnClickListener(null)
        post {
            gestureDetector = GestureDetector(
                getContext().applicationContext,
                object : SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        return super.onDoubleTap(e)
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        return super.onSingleTapConfirmed(e)
                    }

                    override fun onLongPress(e: MotionEvent) {
                        super.onLongPress(e)
                    }
                })
        }
    }

    override fun changeUiToCompleteClear() {
        super.changeUiToCompleteClear()
        setTextAndProgress(0, true)
    }

    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        setTextAndProgress(0, true)
    }

    fun loadCover(url: String,frameTime:Long) {
        if (url.isEmpty()) {
            return
        }
        if(url.startsWith("file:///")){
            loadVideoScreenshot(url,frameTime)
            return
        }
        ivCover?.apply {
            Glide.with(context.applicationContext)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .frame(1000000)
                        .centerCrop()
                )
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
                .into(this)
        }

    }

    fun showCover(isShow: Boolean) {
        ivCover?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun setFull(){
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL)
    }


    fun getBitmapFromVideo():Bitmap?{
        if(mTextureView == null){
            return null
        }
        val b = mTextureView.initCover()
        return b;

    }


    override fun onAutoCompletion() {

        LogUtils.i(TAG,"onAutoCompletion 被调用了")

        if(isResetPageAfterPlayEnd){
            super.onAutoCompletion()
            return
        }

        setStateAndUi(CURRENT_STATE_AUTO_COMPLETE)

        mSaveChangeViewTIme = 0
        mCurrentPosition = 0


        if (!mIfCurrentIsFullscreen) gsyVideoManager.setLastListener(null)
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener)
        if (mContext is Activity) {
            try {
                (mContext as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } catch (e: java.lang.Exception) {
            }
        }
        releaseNetWorkState()

        if (mVideoAllCallBack != null && isCurrentMediaListener) {
            Debuger.printfLog("onAutoComplete")
            mVideoAllCallBack.onAutoComplete(mOriginUrl, mTitle, this)
        }
        mHadPlay = false

        if (mLockCurScreen) {
            lockTouchLogic()
            mLockScreen.visibility = GONE
        }
    }

    override fun onCompletion() {
        super.onCompletion()
        LogUtils.i(TAG,"onCompletion 被调用了")
    }

    @SuppressLint("CheckResult")
    fun loadVideoScreenshot(
        uri: String,
        frameTimeMicros: Long
    ) {

        ivCover?.apply {
            try {
                val media = MediaMetadataRetriever()
                val s = uri.replace("file:///android_asset/", "")
                val afd: AssetFileDescriptor = App.getContext().getAssets().openFd(s)
                media.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                val bitmap = media.getFrameAtTime(frameTimeMicros, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                GlideApp.with(context)
                    .load(bitmap)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
                    .into(this)
            } catch (e: Exception) {
            }
        }
    }

}