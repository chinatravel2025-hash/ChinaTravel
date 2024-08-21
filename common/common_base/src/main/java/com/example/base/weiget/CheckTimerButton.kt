package com.example.base.weiget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class CheckTimerButton : FrameLayout {
    var mLongListener: LongListener? = null
    private var mWatch: ButtonWatch? = null
    private val mUiHandler = Handler(Looper.getMainLooper())
    private var mMax = 0L
    private var mCount = 0L
    private var mStatus = false
    private var mGranted = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        mWatch = ButtonWatch(context, 10) {
            mUiHandler.post {
                if (mCount >= mMax) {
                    stop()
                } else {
                    mCount++
                }
                var p = mCount * 100 / mMax
                if (p < 0) {
                    p = 0
                }
                if (p > 100) {
                    p = 100
                }
                mLongListener?.onProgress(p.toInt(), mStatus)
            }
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (mGranted) {
                    if (mStatus) {
                        suspend()
                    } else {
                        start()
                    }
                }
                mLongListener?.onClick(mStatus)
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    fun start() {
        if (!mGranted) {
            return
        }
        mWatch?.start()
        mStatus = true
    }

    fun stop() {
        mWatch?.stop()
        mCount = 0
        mStatus = false
    }

    fun suspend() {
        mWatch?.stop()
        mStatus = false
    }

    fun initMax(count: Int) {
        mMax = count * 100L
        mCount = 0
    }

    fun setGranted(g: Boolean) {
        mGranted = g
    }

    interface LongListener {
        fun onProgress(progress: Int, status: Boolean)
        fun onClick(status: Boolean)
    }
}