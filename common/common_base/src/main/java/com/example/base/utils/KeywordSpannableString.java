package com.example.base.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class KeywordSpannableString extends SpannableString {
    String originalString = "";
    public KeywordSpannableString(String originalString) {
        super(originalString);
        this.originalString = originalString;
    }

    public void setKeyword(String keyword, int size, boolean ignoreCase) {
        String org = ignoreCase ? originalString.toLowerCase(): originalString;
        String search = ignoreCase? keyword.toLowerCase(): keyword;
        for (int i = 0; i < org.length(); i += search.length()) {
            i = org.indexOf(search,  i);
            if (i < 0 ) {
                break;
            }
            int min =i + keyword.length();
            int max =i +org.length();
            int endIndex =Math.min(min, max);
            AbsoluteSizeSpan foregroundColorSpan = new AbsoluteSizeSpan(size);
            this.setSpan(foregroundColorSpan, i,endIndex,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void setKeywords(String[] keywords, int size, boolean ignoreCase) {
        for (int i = 0; i < keywords.length; i++) {
            setKeyword(keywords[i], size, ignoreCase);
        }
    }
}
