package com.example.base.weiget;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class OursLinearLayoutManager extends LinearLayoutManager {

    private boolean isLowSmooth;

    public OursLinearLayoutManager(Context context) {
        super(context);
    }


    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position, boolean isLowSmooth) {
        this.isLowSmooth = isLowSmooth;
        smoothScrollToPosition(recyclerView, state, position);

    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller smoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        // 返回：滑过1px时经历的时间(ms)。
                        return (isLowSmooth ? 50f : 25f) / displayMetrics.densityDpi;
                    }

                    @Override
                    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                        return boxStart - viewStart;
                    }
                };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }


    public void setReverse(boolean isReverse){
        if(getReverseLayout() == isReverse){
            return;
        }
        setReverseLayout(isReverse);
    }

    public boolean isReverse(){
        return getReverseLayout();
    }



    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try{
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e){

        }
    }
}
