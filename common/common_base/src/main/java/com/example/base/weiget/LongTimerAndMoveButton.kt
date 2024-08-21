package com.example.base.weiget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class LongTimerAndMoveButton : FrameLayout {
    var mLongListener: LongListener? = null
    private var mWatch: ButtonWatch? = null
    private val mUiHandler = Handler(Looper.getMainLooper())
    private var mMax = 0L
    private var mCount = 0L
    private var mTime = 0
    private var isStart = false
    var isDown = true

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
        mWatch = ButtonWatch(context, 10) { sw: ButtonWatch? ->
            if(mTime>=6000){
                stop(isNotChange = false, isSand = false)
                return@ButtonWatch
            }
            mTime ++
            mUiHandler.post {
                if (mCount >= mMax) {
                    sw?.stop()
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
                mLongListener?.onProgress(p.toInt())
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
                if(!isDown){
                    mLongListener?.onTouchError()
                    return true
                }
                onTouchStart()
            }
            MotionEvent.ACTION_MOVE -> {
                mLongListener?.onTouchChange(event.y <0)
                if(event.y <0){
                    //手指松开取消发送

                }else{
                    //手指上滑取消发送
                }
            }
            MotionEvent.ACTION_UP -> {
                if(!isDown){
                    return true
                }
                if(isStart){
                    stop(event.x <0,event.y <0)//由于getY是相对控件自己的坐标，因此当<0时，手指已再也不此控件上
                }
            }
        }
        return true
    }

    fun onTouchStart() {
        mLongListener?.onTouchStart()
        mLongListener?.onTouchChange(false)
    }

    fun start() {
        mTime = 0
        mWatch?.start()
        isStart = true
    }

    fun stop(isNotChange:Boolean,isSand: Boolean) {
        mWatch?.stop()
        mLongListener?.onTouchStop(isNotChange,!isSand,mCount)
        mCount = 0
        isStart = false
    }

    fun suspend() {
        mWatch?.stop()
        mCount = 0
        isStart = false
    }

    fun initMax(count: Int) {
        mMax = count * 100L
        mCount = 0
    }

    interface LongListener {
        fun onTouchStart()
        fun onProgress(progress: Int)
        fun onTouchStop(isChange: Boolean,isSand: Boolean,progress: Long)
        fun onTouchChange(isTop: Boolean)

        fun onTouchError()
    }
}