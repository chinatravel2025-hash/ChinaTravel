package com.example.base.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Description: 用于显示信息的气泡布局
 * Author: cyf 2020/12/8 13:38
 * Version: 1.0
 */
public class BubbleRelativeLayout extends RelativeLayout {
    /**
     * 气泡尖角方向
     */
    public enum BubbleLegOrientation {
        TOP, LEFT, RIGHT, BOTTOM
    }

    private final int PADDING = 15;     //默认padding，px，为尖角提供显示空间（同时尖角也使用此值计算顶点位置，决定尖角大小）
    private final int TRIANGLE_HALF_BASE = (int) (PADDING / 1.5);    //px，默认尖角中点对应轴上的距离
    private final float STROKE_WIDTH = 2.0f;        //默认绘制边界的笔画宽度
    private final float CORNER_RADIUS = 8.0f;       //默认边界圆角大小
    private final int BG_COLOR = Color.argb(255, 255, 255, 255);    //默认气泡背景颜色
    private final int SHADOW_COLOR = Color.argb(0, 255, 255, 255);  //默认阴影颜色

    private int mPadding = PADDING;  //padding
    private int mTriangleHalfBase = TRIANGLE_HALF_BASE;//px，尖角中点对应轴上的距离
    private float mStrokeWidth = STROKE_WIDTH; //绘制边界的笔画宽度
    private float mCornerRadius = CORNER_RADIUS;//边界圆角大小
    private int mBgColor = BG_COLOR;//气泡背景颜色
    private int mShadowColor = SHADOW_COLOR;//阴影颜色
    private final Path mPath = new Path();                  //边界路径
    private final Path mBubbleTrianglePath = new Path();    //尖角路径
    private final Paint mPaint = new Paint(Paint.DITHER_FLAG);  //阴影画笔
    private Paint mFillPaint = null;                        //填充画笔

    private float mBubbleTriangleOffset = 0;//尖角偏移量（中点相对于气泡显示零点）
    private BubbleLegOrientation mBubbleTriangleOrientation = BubbleLegOrientation.LEFT;//尖角方向

    public BubbleRelativeLayout(Context context) {
        this(context, null);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * 初始化
     * @param context context
     * @param attrs 属性
     */
    @SuppressLint("CustomViewStyleable")
    private void init(final Context context, final AttributeSet attrs) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        //获取layout中设置的属性
/*
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BubbleRelativeLayoutPop);
            try {
                mPadding = a.getDimensionPixelSize(R.styleable.BubbleRelativeLayoutPop_padding, PADDING);
                mBgColor = a.getInt(R.styleable.BubbleRelativeLayoutPop_bgColor, BG_COLOR);
                mShadowColor = a.getInt(R.styleable.BubbleRelativeLayoutPop_shadowColor, SHADOW_COLOR);
                mTriangleHalfBase = a.getDimensionPixelSize(R.styleable.BubbleRelativeLayoutPop_halfBaseOfLeg, TRIANGLE_HALF_BASE);
                mStrokeWidth = a.getFloat(R.styleable.BubbleRelativeLayoutPop_strokeWidth, STROKE_WIDTH);
                mCornerRadius = a.getFloat(R.styleable.BubbleRelativeLayoutPop_cornerRadius, CORNER_RADIUS);
            } finally {
                if (a != null) {
                    a.recycle();
                }
            }
        }
*/

        mPaint.setColor(mShadowColor);
        mPaint.setStyle(Style.FILL);
        mPaint.setStrokeCap(Cap.BUTT);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        mFillPaint = new Paint(mPaint);
        mFillPaint.setColor(mBgColor);
        setLayerType(LAYER_TYPE_SOFTWARE, mFillPaint);

        mPaint.setShadowLayer(2f, 2F, 5F, mShadowColor);

        //生成尖角path
        renderBubbleTrianglePath();

        //设置padding
        setPaddingByOrientation();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 设置padding
     *
     * @param mPadding padding
     */
    public void setmPadding(int mPadding) {
        this.mPadding = mPadding;
        setPaddingByOrientation();
        renderBubbleTrianglePath();
    }

    /**
     * 获取padding
     *
     * @return padding
     */
    public int getmPadding() {
        return mPadding;
    }

    /**
     * 设置尖角中点对应轴上的距离
     *
     * @param mTriangleHalfBase 尖角中点对应轴上的距离
     */
    public void setmTriangleHalfBase(int mTriangleHalfBase) {
        this.mTriangleHalfBase = mTriangleHalfBase;
        renderBubbleTrianglePath();
    }

    /**
     * 获取尖角中点对应轴上的距离
     *
     * @return 尖角中点对应轴上的距离
     */
    public int getmTriangleHalfBase() {
        return mTriangleHalfBase;
    }

    /**
     * 设置画笔宽度
     *
     * @param mStrokeWidth 画笔宽度
     */
    public void setmStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        if (mPaint != null) {
            mPaint.setStrokeWidth(mStrokeWidth);
        }

        if (mFillPaint != null) {
            mFillPaint.setStrokeWidth(mStrokeWidth);
        }
    }

    /**
     * 获取画笔宽度
     *
     * @return 画笔宽度
     */
    public float getmStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * 设置气泡主体矩形的圆角大小
     *
     * @param mCornerRadius 圆角半径
     */
    public void setmCornerRadius(float mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
    }

    /**
     * 获取气泡主体矩形的圆角大小
     *
     * @return 气泡主体矩形的圆角大小
     */
    public float getmCornerRadius() {
        return mCornerRadius;
    }

    /**
     * 设置气泡背景颜色
     *
     * @param mBgColor 气泡背景颜色
     */
    public void setmBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        if (mFillPaint != null) {
            mFillPaint.setColor(mBgColor);
        }
    }

    /**
     * 获取气泡背景颜色
     *
     * @return 气泡背景颜色
     */
    public int getmBgColor() {
        return mBgColor;
    }

    /**
     * 设置阴影颜色
     *
     * @param mShadowColor 阴影颜色
     */
    public void setmShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        if (mPaint != null) {
            mPaint.setColor(mShadowColor);
        }
    }

    /**
     * 获取阴影颜色
     *
     * @return 阴影颜色
     */
    public int getmShadowColor() {
        return mShadowColor;
    }

    /**
     * 设置方向及偏移量
     *
     * @param bubbleTriangleOrientation 尖角方向
     * @param bubbleTriangleOffset      尖角偏移量
     */
    public void setBubbleParams(final BubbleLegOrientation bubbleTriangleOrientation, final float bubbleTriangleOffset) {
        mBubbleTriangleOffset = bubbleTriangleOffset;
        mBubbleTriangleOrientation = bubbleTriangleOrientation;
        setPaddingByOrientation();
    }

    /**
     * 根据尖角位置设置padding
     */
    private void setPaddingByOrientation() {
        switch (mBubbleTriangleOrientation) {
            case LEFT:
                setPadding(mPadding, 0, 0, 0);
                break;
            case TOP:
                setPadding(0, mPadding, 0, 0);
                break;
            case RIGHT:
                setPadding(0, 0, mPadding, 0);
                break;
            case BOTTOM:
                setPadding(0, 0, 0, mPadding);
                break;
        }
    }

    /**
     * 获取主体的圆角矩形
     *
     * @param width  整体布局宽度
     * @param height 整体布局高度
     * @return 主体的圆角矩形
     */
    private RectF renderRectPath(float width, float height) {
        //根据气泡位置为尖角预留显示空间并生成气泡主体的圆角矩形
        RectF roundRect = new RectF(0, 0, width - mPadding, height);
        switch (mBubbleTriangleOrientation) {
            case LEFT:
                roundRect = new RectF(mPadding, 0, width, height);
                break;
            case TOP:
                roundRect = new RectF(0, mPadding, width, height);
                break;
            case RIGHT:
                roundRect = new RectF(0, 0, width - mPadding, height);
                break;
            case BOTTOM:
                roundRect = new RectF(0, 0, width, height - mPadding);
                break;
        }

        return roundRect;
    }

    /**
     * 获取尖角路径
     */
    private void renderBubbleTrianglePath() {
        mBubbleTrianglePath.rewind();
        mBubbleTrianglePath.moveTo(0, 0);
        mBubbleTrianglePath.lineTo(mPadding * 1.5f, -mTriangleHalfBase);
        mBubbleTrianglePath.lineTo(mPadding * 1.5f, mTriangleHalfBase);
        mBubbleTrianglePath.close();
    }

    /**
     * 根据显示方向，获取尖角位置矩阵
     *
     * @param width  整体布局宽度
     * @param height 整体控件高度
     * @return 调整后的尖角矩阵
     */
    private Matrix renderBubbleTriangleMatrix(final float width, final float height) {
        final float offset = Math.max(mBubbleTriangleOffset, mTriangleHalfBase);

        float dstX = 0;
        float dstY = Math.min(offset, height - mTriangleHalfBase);
        final Matrix matrix = new Matrix();

        switch (mBubbleTriangleOrientation) {
            case LEFT:
                dstX = 0;
                dstY = Math.min(offset, height - mTriangleHalfBase);
                break;
            case TOP:
                dstX = Math.min(offset, width - mTriangleHalfBase);
                dstY = 0;
                matrix.postRotate(90);
                break;
            case RIGHT:
                dstX = width;
                dstY = Math.min(offset, height - mTriangleHalfBase);
                matrix.postRotate(180);
                break;
            case BOTTOM:
                dstX = Math.min(offset, width - mTriangleHalfBase);
                dstY = height;
                matrix.postRotate(270);
                break;
        }

        matrix.postTranslate(dstX, dstY);
        return matrix;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();

        mPath.rewind();
        mPath.addRoundRect(renderRectPath(width, height), mCornerRadius, mCornerRadius, Direction.CW);
        mPath.addPath(mBubbleTrianglePath, renderBubbleTriangleMatrix(width, height));

        canvas.drawPath(mPath, mPaint);
        canvas.scale((width - mStrokeWidth) / width, (height - mStrokeWidth) / height, width / 2f, height / 2f);
        canvas.drawPath(mPath, mFillPaint);
    }
}
