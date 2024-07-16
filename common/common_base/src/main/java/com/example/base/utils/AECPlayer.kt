package com.example.base.utils

import android.content.Context
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import com.google.common.primitives.Bytes
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by liyi on 2021/10/6.
 */
class AECPlayer {
    private val BYTES_OF_PER_SAMPLE = 2
    private val FADE_BUFFER_SIZE = 16 * 2 * 300
    private var mAudioTrack: AudioTrack? = null
    private var mPlayerThread: Thread? = null
    private val mContext: Context? = null
    private var mBufferSize = 0
    private val mPerPlaySize = 0
    private var mIsPlayThreadAlive = false
    private val mEnable // 是否通过喇叭输出降噪音频
            = true
    private var mProcessQueue: LinkedBlockingQueue<QueueBuffer>? = null
    private var mReleaseBuffers: Stack<QueueBuffer>? = null
    private var mTmpQueueBuffer: QueueBuffer? = null
    private var mFrameIndex = 0
    private var isPlayer = false
    fun start(filePath: String, listener: ((Boolean) -> Unit?)?) {
        if (!mEnable) {
            return
        }
        stop()
        mIsPlayThreadAlive = true
        createAudioTrack()
        startPlayThread(filePath, listener)
    }

    fun stop() {
        mIsPlayThreadAlive = false
        if (mPlayerThread != null) {
            mPlayerThread?.interrupt()
            try {
                mPlayerThread?.join()
            } catch (e: InterruptedException) {
            }
            mPlayerThread = null
        }
        if (mAudioTrack != null) {
            releaseAudioTrack()
        }
    }

    private fun createAudioTrack() {

        mBufferSize = AudioTrack.getMinBufferSize(
            RATE,
            AudioFormat.CHANNEL_CONFIGURATION_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        if (null != mAudioTrack) {
            releaseAudioTrack()
        }
        // 创建

        mAudioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC, RATE,
            AudioFormat.CHANNEL_CONFIGURATION_MONO,
            AudioFormat.ENCODING_PCM_16BIT, mBufferSize,
            AudioTrack.MODE_STREAM
        )
        if (mBufferSize == AudioTrack.ERROR_BAD_VALUE
            || mBufferSize == AudioTrack.ERROR
        ) {
            throw RuntimeException("create audio track fail")
        }
        mProcessQueue = LinkedBlockingQueue(MAX_QUEUE_SIZE)
        mReleaseBuffers = Stack()
        for (i in 0 until MAX_QUEUE_SIZE) {
            val buffer = QueueBuffer(FADE_BUFFER_SIZE)
            mReleaseBuffers?.push(buffer)
        }
        mTmpQueueBuffer = QueueBuffer(FADE_BUFFER_SIZE)
        mTmpQueueBuffer?.setFormat(QueueBuffer.ImageFormat.NV21)

    }

    fun isPlayer(): Boolean {
        return isPlayer
    }

    fun pushBuffer(data: ByteArray?) {
        if (!mEnable) {
            return
        }
        val buffer = nextAvailableBuffer
        if (buffer == null) {
            mFrameIndex++

            return
        }
        buffer.cacheBufer(data, 0, false)
        buffer.setIndex(mFrameIndex++.toLong())
        if (!mProcessQueue!!.offer(buffer)) {
            mReleaseBuffers?.push(buffer)
            return
        }
    }

    @get:Throws(InterruptedException::class)
    val nextFrame: QueueBuffer?
        get() {
            val nextFrame = mProcessQueue!!.take()
            if (nextFrame != null) {
                QueueBuffer.swapBuffer(nextFrame, mTmpQueueBuffer)
                mReleaseBuffers?.push(nextFrame)
                return mTmpQueueBuffer
            }
            return null
        }
    private val nextAvailableBuffer: QueueBuffer?
        private get() = if (mReleaseBuffers!!.empty()) {
            null
        } else mReleaseBuffers?.pop()

    private fun startPlayThread(filePath: String, listener: ((Boolean) -> Unit?)?) {
        isPlayer = true
        val playBuffer = getPCMStream(filePath)

        mPlayerThread = Thread {
            do {
                if (mAudioTrack!!.playState != AudioTrack.PLAYSTATE_PLAYING) {
                    mAudioTrack?.play()
                }
                mAudioTrack?.write(playBuffer, 0, playBuffer.size)
                //播放完毕
                Handler(Looper.getMainLooper()).post {
                    listener?.invoke(true)
                }
                stop()
                isPlayer = false
            } while (mIsPlayThreadAlive)
        }
        mPlayerThread?.start()
    }

    private fun releaseAudioTrack() {
        synchronized(this@AECPlayer) {
            if (null != mAudioTrack) {
                if (mAudioTrack!!.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    mAudioTrack?.stop()
                }
                mAudioTrack?.release()
                mAudioTrack = null
            }

        }
    }

    companion object {
        private const val TAG = "AECPlayer"
        private const val RATE = 16000
        private const val MAX_QUEUE_SIZE = 5

        val mainPlayer = AECPlayer()

        /**
         * 读取 PCM 音频流
         *
         * @param filePath
         * @return
         */
        fun getPCMStream(filePath: String?): ByteArray {
            val stream: MutableList<Byte> = ArrayList()
            val bufferSize = AudioTrack.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(filePath)
                val buffer = ByteArray(bufferSize)
                while (fis.read(buffer) != -1) {
                    for (bf in buffer) {
                        stream.add(bf)
                    }
                }
            } catch (e: Exception) {
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                    }
                }
            }
            return Bytes.toArray(stream)
        }

        /**
         * 读取 PCM 音频流
         *
         * @param filePath
         * @return
         */
        fun getPCMStreamSeconds(filePath: String?): Float {
            return File(filePath).length() / (16000 * 2F)
        }
    }
}