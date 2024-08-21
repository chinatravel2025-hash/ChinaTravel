package com.example.base.weiget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;

import androidx.core.view.ViewCompat;

/**
 * @author: LI YI
 * @desc: 多点触控的弹性ScrollView
 * @create_time: 2023/1/30 17:02
 */

public class OursElasticScrollView extends ScrollView {
    /**
     * 是否可滑动
     */
    private boolean canScroll;
    /**
     * 手势
     */
    private GestureDetector mGestureDetector;
    /**
     * 滑动起点
     */
    private Point mOriginPos;

    /**
     * 滑动超出边界的处理
     */
    private OverScroller mOverScroller;

    private float startY;
    /**
     * 当前手指Id
     */
    private int activePointerId;
    private float mLastY;

    private View mTargetView;
    private Rect normal = new Rect();
    private Context mContext;
    private int topY = 0;
    private int heightPixels = 0;
    /**
     * A null/invalid pointer ID.
     */
    private final int INVALID_POINTER = -1;


    public OursElasticScrollView(Context context) {
        this(context, null);
    }

    public OursElasticScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OursElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mOriginPos = new Point();
        mGestureDetector = new GestureDetector(context, new ScrollGestureDetector());
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mOverScroller = new OverScroller(context, interpolator);
        canScroll = true;
        setVerticalScrollBarEnabled(false);
        setFillViewport(true);
        if (mContext instanceof Activity) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
            heightPixels = dm.heightPixels;
        }
    }

    /**
     * onFinishInflate 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mTargetView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTargetView != null) {
            mOriginPos.x = mTargetView.getLeft();
            mOriginPos.y = mTargetView.getTop();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                startY = mLastY = ev.getY(0);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            canScroll = true;
        }
        return mGestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTargetView == null) {
            return false;
        }
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mOnPullListener != null) {
                    mOnPullListener.onDownPush();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                activePointerId = event.getPointerId(actionIndex);
                mLastY = event.getY(actionIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 如果松开的是活动手指
                if (activePointerId == event.getPointerId(actionIndex)) {
                    final int newPointerIndex = actionIndex == 0 ? 1 : 0;
                    activePointerId = event.getPointerId(newPointerIndex);
                    mLastY = event.getY(newPointerIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER;
                if (mOnPullListener != null && mTargetView != null) {
                    // 往下拉的距离超过了100dp
                    if (mTargetView.getTop() - mOriginPos.y > dp2px(100)) {
                        mOverScroller.startScroll(mTargetView.getLeft(), mTargetView.getTop(), 0, heightPixels, 300);
                        invalidate();
                        postDelayed(() -> {
                            topY = mOriginPos.y - mTargetView.getTop();//记录位置
                            mOnPullListener.onDownPull();
                            setVisibility(GONE);
                        }, 300);
                        break;
                        // 往上拉的距离超过了100dp
                    } else if (mOriginPos.y - mTargetView.getTop() > dp2px(100)) {
                        mOnPullListener.onUpPull();
                    }
                }
                if (!normal.isEmpty()) {//回退原位置
                    mOverScroller.startScroll(mTargetView.getLeft(), mTargetView.getTop(), 0, mOriginPos.y - mTargetView.getTop(), 300);
                    invalidate();
                    if (mOnPullListener != null) {
                        mOnPullListener.onReset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    activePointerId = event.getPointerId(actionIndex);
                }
                final int pointerIndex = event.findPointerIndex(activePointerId);
                float currentY = event.getY(pointerIndex);
                // 假如是下拉, currentY > perY, offset > 0
                int offset = (int) (currentY - mLastY);
                if (offset <= 0) {
                    //如果是下拉，则不移动
                    break;
                }
                mLastY = currentY;
                int deltaY = Math.abs(mTargetView.getTop() - mOriginPos.y);
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        normal.set(mTargetView.getLeft(), mTargetView.getTop(), mTargetView.getRight(), mTargetView.getBottom());
                    }
                    int newOffset = calculateNewOffset(deltaY, offset);
                    ViewCompat.offsetTopAndBottom(mTargetView, newOffset);
                    mOnPullListener.onMoveFraction(newOffset);
                }
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            ViewCompat.offsetTopAndBottom(mTargetView, mOverScroller.getCurrY() - mTargetView.getTop());
            invalidate();
        }
    }

    class ScrollGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                canScroll = true;
            } else {
                canScroll = false;
            }
            if (canScroll) {
                if (e1.getAction() == MotionEvent.ACTION_DOWN) {
                    startY = e1.getY();
                }
            }
            return false;
            //return canScroll;
        }
    }

    /**
     * 每隔多少距离就开始增大阻力系数, 数值越小阻力就增大的越快
     */
    private final int LENGTH = 150;
    /**
     * 阻力系数, 越大越难拉
     */
    private int mFraction = 2;

    private int calculateNewOffset(int deltaY, int offset) {
        int newOffset = offset / (mFraction + deltaY / LENGTH);
        if (newOffset == 0) {
            newOffset = offset >= 0 ? 1 : -1;
        }
        return newOffset;
    }

    /**
     * 是否需要移动
     *
     * @return
     */
    private boolean isNeedMove() {
        int offset = mTargetView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    public void reset() {
        setVisibility(VISIBLE);
        mOverScroller.startScroll(mTargetView.getLeft(), mTargetView.getTop(), 0, topY, 300);
        invalidate();
        if (mOnPullListener != null) {
            mOnPullListener.onReset();
        }
    }

    private float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    /**
     * 下拉或上拉监听
     */
    public interface OnPullListener {
        void onDownPush();

        void onDownPull();

        void onUpPull();

        void onMoveFraction(int newOffset);

        void onReset();
    }

    private OnPullListener mOnPullListener;

    public void setOnPullListener(OnPullListener listener) {
        mOnPullListener = listener;
    }
}
