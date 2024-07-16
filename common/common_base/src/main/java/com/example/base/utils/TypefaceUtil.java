package com.example.base.utils;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.base.base.App;


import java.io.File;
import java.util.HashMap;

/**
 * 加载字体
 */
public class TypefaceUtil {
    private static HashMap<String, android.graphics.Typeface> typefaceMap = new HashMap<>();

    public static void loadAssetTypeface(TextView textView, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            textView.setTypeface(Typeface.DEFAULT);
            return;
        }
        if (!typefaceMap.containsKey(fileName)) {
            typefaceMap.put(fileName, Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fileName + ".ttf"));
        }
        textView.setTypeface(typefaceMap.get(fileName));



    }

    public static boolean loadFileTypeface(TextView textView, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            textView.setTypeface(Typeface.DEFAULT);
            return false;
        }

        File file = new File(PathUtil.getExternalFilesDir(App.getContext()).getAbsolutePath() + File.separator + "fonts" + File.separator + fileName);
        if (file.exists() && file.length() > 0) {
            if (!typefaceMap.containsKey(fileName)) {
                typefaceMap.put(fileName, Typeface.createFromFile(file));
            }
            textView.setTypeface(typefaceMap.get(fileName));
            return true;
        } else {
            return false;
        }
    }
}
