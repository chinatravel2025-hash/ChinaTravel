package com.travel.home.widget

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import kotlin.math.max
import kotlin.math.min

/**
 * 支持缩放和拖拽的ImageView
 * 参考微信的图片查看体验
 */
class ZoomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private val matrix = Matrix()
    private var minScale = 1.0f
    private var maxScale = 3.0f
    private var currentScale = 1.0f
    
    private val scaleGestureDetector: ScaleGestureDetector
    private val gestureDetector: GestureDetector
    
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false
    
    private var onSingleTapListener: (() -> Unit)? = null
    
    init {
        scaleType = ScaleType.MATRIX
        
        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor
                val newScale = currentScale * scaleFactor
                
                if (newScale in minScale..maxScale) {
                    val focusX = detector.focusX
                    val focusY = detector.focusY
                    
                    matrix.postScale(scaleFactor, scaleFactor, focusX, focusY)
                    currentScale = newScale
                    imageMatrix = matrix
                    return true
                }
                return false
            }
        })
        
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                onSingleTapListener?.invoke()
                return true
            }
            
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val x = e.x
                val y = e.y
                
                val targetScale = if (currentScale > minScale) {
                    // 缩小到最小
                    minScale
                } else {
                    // 放大到中等大小
                    (minScale + maxScale) / 2
                }
                
                val scale = targetScale / currentScale
                
                // 计算缩放中心点相对于图片的坐标
                val values = FloatArray(9)
                matrix.getValues(values)
                val transX = values[Matrix.MTRANS_X]
                val transY = values[Matrix.MTRANS_Y]
                
                val focusX = (x - transX) / currentScale
                val focusY = (y - transY) / currentScale
                
                matrix.postScale(scale, scale, x, y)
                currentScale = targetScale
                
                // 限制边界
                limitBounds()
                
                imageMatrix = matrix
                return true
            }
        })
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                isDragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1 && currentScale > minScale) {
                    val deltaX = event.x - lastTouchX
                    val deltaY = event.y - lastTouchY
                    
                    if (!isDragging && (kotlin.math.abs(deltaX) > 10 || kotlin.math.abs(deltaY) > 10)) {
                        isDragging = true
                    }
                    
                    if (isDragging) {
                        matrix.postTranslate(deltaX, deltaY)
                        imageMatrix = matrix
                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                // 限制图片边界
                limitBounds()
            }
        }
        
        return true
    }
    
    private fun limitBounds() {
        val drawable = drawable ?: return
        
        val drawableWidth = drawable.intrinsicWidth.toFloat()
        val drawableHeight = drawable.intrinsicHeight.toFloat()
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        
        val values = FloatArray(9)
        matrix.getValues(values)
        var transX = values[Matrix.MTRANS_X]
        var transY = values[Matrix.MTRANS_Y]
        
        val scaledWidth = drawableWidth * currentScale
        val scaledHeight = drawableHeight * currentScale
        
        // 限制X方向
        if (scaledWidth > viewWidth) {
            transX = max(min(transX, 0f), viewWidth - scaledWidth)
        } else {
            transX = (viewWidth - scaledWidth) / 2
        }
        
        // 限制Y方向
        if (scaledHeight > viewHeight) {
            transY = max(min(transY, 0f), viewHeight - scaledHeight)
        } else {
            transY = (viewHeight - scaledHeight) / 2
        }
        
        values[Matrix.MTRANS_X] = transX
        values[Matrix.MTRANS_Y] = transY
        matrix.setValues(values)
        imageMatrix = matrix
    }
    
    override fun setImageMatrix(matrix: Matrix) {
        super.setImageMatrix(matrix)
        this.matrix.set(matrix)
    }
    
    override fun setImageDrawable(drawable: android.graphics.drawable.Drawable?) {
        super.setImageDrawable(drawable)
        post {
            initImageMatrix()
        }
    }
    
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && drawable != null) {
            initImageMatrix()
        }
    }
    
    private fun initImageMatrix() {
        val drawable = drawable ?: return
        val drawableWidth = drawable.intrinsicWidth.toFloat()
        val drawableHeight = drawable.intrinsicHeight.toFloat()
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        
        if (drawableWidth <= 0 || drawableHeight <= 0 || viewWidth <= 0 || viewHeight <= 0) {
            return
        }
        
        matrix.reset()
        
        // 计算缩放比例，使图片适应屏幕
        val scaleX = viewWidth / drawableWidth
        val scaleY = viewHeight / drawableHeight
        minScale = min(scaleX, scaleY)
        currentScale = minScale
        
        // 居中显示
        val scaledWidth = drawableWidth * minScale
        val scaledHeight = drawableHeight * minScale
        val dx = (viewWidth - scaledWidth) / 2
        val dy = (viewHeight - scaledHeight) / 2
        
        matrix.postScale(minScale, minScale)
        matrix.postTranslate(dx, dy)
        imageMatrix = matrix
    }
    
    fun setOnSingleTapListener(listener: () -> Unit) {
        this.onSingleTapListener = listener
    }
    
    fun resetScale() {
        initImageMatrix()
    }
    
    fun getCurrentScale(): Float {
        return currentScale
    }
    
    fun getMinScale(): Float {
        return minScale
    }
}




