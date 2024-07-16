package com.example.base.utils;

import android.graphics.Bitmap;
import android.view.View;


public class ViewUtils {
    /**
     * 对View进行截图
     */
    public static Bitmap viewSnapshot(View view) {
        //使控件可以进行缓存
        view.setDrawingCacheEnabled(true);
        //获取缓存的 Bitmap
        Bitmap drawingCache = view.getDrawingCache();
        //复制获取的 Bitmap
        drawingCache = Bitmap.createBitmap(drawingCache);
        //关闭视图的缓存
        view.setDrawingCacheEnabled(false);

        if (drawingCache != null) {
            return drawingCache;
        } else {
            return null;
        }
    }


}


