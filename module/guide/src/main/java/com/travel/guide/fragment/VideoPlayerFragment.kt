/*
package com.travel.guide.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aws.module_home.databinding.FragmentVideoBinding
import com.travel.guide.viewmodel.VideoPlayerViewModel
import com.aws.module_home.utils.SaveUtils
import com.example.base.base.App
import com.example.base.common.v2t.ICallback
import com.example.base.common.v2t.IMCallback
import com.example.base.event.VideoPageEvent
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.DateUtils
import com.example.base.utils.localCustomDataToBean
import com.example.commponent.ui.comm.LocalScreenVideoManager
import com.example.commponent.ui.fragment.ImageItemFragment
import com.example.commponent.ui.toast.ToastHelper
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class VideoPlayerFragment constructor(val userId: String, val url: String,val index: Int,val isPlayer: Boolean) : Fragment(),
    LocalScreenVideoManager.CTVideoEventListener, ICallback {
    private lateinit var mBinding: FragmentVideoBinding
    private lateinit var mViewModel: VideoPlayerViewModel
    private lateinit var videoManager: LocalScreenVideoManager
    private var isSeekTouch = false
    var mImageItemListener: ImageItemFragment.ImageItemListener? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this)[VideoPlayerViewModel::class.java]
        return FragmentVideoBinding.inflate(inflater, container, false).apply {
            mBinding = this
            mBinding.lifecycleOwner = this@VideoPlayerFragment
            mBinding.vm = mViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        EventBus.getDefault().register(this)
        IMCallback.addICallback(this)
    }

    fun initView() {
        videoManager = LocalScreenVideoManager.newSimpleInstance(mBinding.mpv)
        if (File(url).exists()) {
            setProgress(100)
            initPlayer()
        } else {
            setProgress(-1)
        }
        mBinding.ivClose.setOnClickListener {
            mImageItemListener?.onClickClose()
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        videoManager.stop()
        videoManager.release()
        super.onDestroyView()
    }

    private fun initPlayer() {
        videoManager.listener = this
        videoManager.loadCover(url ?: "", 1)
        videoManager.playbackVideo(url ?: "", isPlayer, false, false, false)
        videoManager.showCover(true)
        mBinding.ivPlayerStoptallk.setOnClickListener {
            if (videoManager.isPlaying()) {
                videoManager.pause()
            } else {
                videoManager.play()
            }
        }
        mBinding.ivPlayerStart.setOnClickListener {
            mBinding.ivPlayerStoptallk.performClick()
        }
        mBinding.flBack.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            if(mBinding.clBack.visibility == View.VISIBLE){
                mBinding.clBack.visibility = View.GONE
                return@setOnClickListener
            }
            mBinding.clBack.visibility = View.VISIBLE
            handler.postDelayed({
                mBinding.clBack.visibility = View.GONE
            }, 3000)
        }
        mBinding.ivSave.setOnClickListener {
            SaveUtils.saveVideoToAlbum(requireActivity(), url ?: "")
            ToastHelper.createToastToSuccess(App.getContext(), "Saved to album")
        }
        mBinding.seekBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            @SuppressLint("SetTextI18n")
            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                if (isFromUser && videoManager.videoDuration > 0) {
                    if(leftValue==100F || leftValue == 99F){
                        videoManager.seekTo(0)
                        return
                    }
                    videoManager.seekTo(((videoManager.videoDuration/100F) * leftValue).toLong())

                }
            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                isSeekTouch = true
            }
            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                isSeekTouch = false
            }
        })
    }

    override fun onVideoStart() {
        videoManager.showCover(false)
        mBinding.ivPlayerStoptallk.visibility = View.GONE
        mBinding.ivPlayerStart.setImageResource(com.ours.res.R.mipmap.img_32_to_stoptallk)
        mBinding.flBack.visibility = View.VISIBLE
        mBinding.clBack.visibility = View.VISIBLE
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            mBinding.clBack.visibility = View.GONE
            mBinding.flBack.visibility = View.VISIBLE
        }, 3000)
    }

    override fun onVideoEnd() {
        handler.removeCallbacksAndMessages(null)
        videoManager.showCover(true)
        mBinding.ivPlayerStoptallk.visibility = View.VISIBLE
        mBinding.ivPlayerStart.setImageResource(com.ours.res.R.mipmap.img_32_play)
        mBinding.flBack.visibility = View.GONE
        mBinding.clBack.visibility = View.VISIBLE
        mBinding.seekBar.setProgress(0F)
    }

    override fun onVideoPosition(currentPosition: Int, duration: Int, progress: Int) {
        if(!isSeekTouch){
            mBinding.seekBar.setProgress(progress.toFloat())
        }
        mBinding.tvCurrent.text = DateUtils.second2Time(currentPosition/1000.toLong())
        mBinding.tvTotal.text = DateUtils.second2Time(duration/1000.toLong())
    }

    override fun onVideoPause() {
        super.onVideoPause()
        handler.removeCallbacksAndMessages(null)
        mBinding.ivPlayerStart.setImageResource(com.ours.res.R.mipmap.img_32_play)
        mBinding.ivPlayerStoptallk.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoPageEvent(event: VideoPageEvent) {
        if (index != event.index) {
            videoManager.pause()
        }
    }

    fun setProgress(progress: Int) {
        mBinding.ivPlayerStoptallk.visibility = if (progress == 100) View.VISIBLE else View.GONE
        mBinding.ivSave.visibility = mBinding.ivPlayerStoptallk.visibility
        mBinding.pbDownload.visibility =
            if (progress == -1 || progress == 100) View.GONE else View.VISIBLE
        mBinding.pbDownload.progress = if (progress != -1) progress else 0
        mBinding.ivError.visibility = if (progress == -1) View.VISIBLE else View.GONE
        if (progress == 100 && File(url).exists()) {
            initPlayer()
        }
    }

    private fun getProgress(vo: TUIMessageBean): Int {
        if (vo.message == null) {
            return -1
        }
        var isSend = vo.message?.isSelf == true
        var progress = if (isSend) -1 else 100
        vo.message?.localCustomDataToBean()?.let { localCustomData ->
            progress = when {
                !isSend && !vo.isDownload -> (localCustomData.ours_download_progress ?: -1)
                isSend && !vo.isUpload -> (localCustomData.ours_upload_progress ?: -1)
                isSend && vo.uploadStatus == TUIMessageBean.MSG_STATUS_SEND_FAIL -> -1
                else -> 100
            }
        }
        return progress
    }

    override fun onMessageProgress(bean: TUIMessageBean) {
        bean.message?.let { message->
            if (userId == bean.userId && bean.message!=null) {
                val progress = getProgress(bean)
                setProgress(progress)
            }
        }
    }

    override fun onMessageReceived(messages: TUIMessageBean, isSender: Boolean) {
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String, url: String, index: Int,isPlayer:Boolean) = VideoPlayerFragment(userId, url,index,isPlayer)

    }

}*/
