package com.example.base.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * Description: 用于显示信息的气泡弹窗
 * Author: li yi 2020/12/8 13:37
 * Version: 1.0
 */
public class BubblePopupWindow extends PopupWindow {

    private final BubbleRelativeLayout bubbleView; //气泡布局

    public BubblePopupWindow(Context context) {
        bubbleView = new BubbleRelativeLayout(context);
        //设置窗口大小
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);

        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);
    }

    /**
     * 设置气泡内容
     *
     * @param view 显示气泡内容的view
     */
    public void setBubbleView(View view,int radius) {
        bubbleView.setBackgroundColor(Color.TRANSPARENT);
        bubbleView.setmCornerRadius(radius);
        view.setPadding(10, 10, 10, 10);
        bubbleView.addView(view);
        setContentView(bubbleView);
    }

    /**
     * 设置气泡尖角大小
     *
     * @param high   垂直于气泡上尖角所在边的高度
     * @param bottom 位于气泡上尖角所在边的尖角边长度
     */
    public void setTriangleSize(int high, int bottom) {
        bubbleView.setmPadding(high);
        bubbleView.setmTriangleHalfBase(bottom / 2);
    }

    /**
     * 设置气泡背景颜色
     *
     * @param color 颜色
     */
    public void setBubbleBgColor(int color) {
        bubbleView.setmBgColor(color);
    }

    /**
     * 设置气泡大小
     *
     * @param width  宽
     * @param height 高
     */
    public void setParam(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * 显示弹窗（默认为左侧气泡）
     *
     * @param parent               对照控件
     * @param gravity              相对位置
     * @param bubbleOffsetX        气泡在x轴的偏移量（默认显示位置不满足需要时调整此处，正数向右）
     * @param bubbleOffsetY        气泡在y轴的偏移量（默认显示位置不满足需要时调整此处，正数向下）
     * @param bubbleTriangleOffset 气泡尖角位置偏移量（左右时为y轴偏移正数向下，上下时为x轴偏移正数向右）
     */
    public void show(View parent, int gravity, int bubbleOffsetX, int bubbleOffsetY, float bubbleTriangleOffset) {
        if (!this.isShowing()) {
            //设置气泡尖角所在位置及偏移量
            BubbleRelativeLayout.BubbleLegOrientation orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
            switch (gravity) {
                case Gravity.BOTTOM:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
                    break;
                case Gravity.TOP:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
                    break;
                case Gravity.END:
                case Gravity.RIGHT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
                    break;
                case Gravity.START:
                case Gravity.LEFT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.RIGHT;
                    break;
                default:
                    break;
            }
            bubbleView.setBubbleParams(orientation, bubbleTriangleOffset); // 设置气泡布局方向及尖角偏移

            int[] location = new int[2];
            parent.getLocationOnScreen(location);//获取对照控件的位置

            //根据相对位置设置位置
            int x = location[0] - getMeasuredWidth() + bubbleOffsetX;
            int y = location[1] + bubbleOffsetY;
            switch (gravity) {
                case Gravity.BOTTOM:
                    x = location[0] + bubbleOffsetX;
                    y = location[1] + parent.getHeight() + bubbleOffsetY;
                    break;
                case Gravity.TOP:
                    x = location[0] + bubbleOffsetX;
                    y = location[1] - getMeasureHeight() + bubbleOffsetY;
                    break;
                case Gravity.END:
                case Gravity.RIGHT:
                    x = location[0] + parent.getWidth() + bubbleOffsetX;
                    y = location[1] + bubbleOffsetY;
                    break;
                case Gravity.START:
                case Gravity.LEFT:
                    x = location[0] - getMeasuredWidth() + bubbleOffsetX;
                    y = location[1] + bubbleOffsetY;
                    break;
                default:
                    break;
            }
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        } else {
            this.dismiss();
        }
    }

    /**
     * 测量高度
     *
     */
    public int getMeasureHeight() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return getContentView().getMeasuredHeight();
    }

    /**
     * 测量宽度
     *
     */
    public int getMeasuredWidth() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return getContentView().getMeasuredWidth();
    }
}
