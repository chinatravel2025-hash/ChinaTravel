package com.example.base.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.NonNull;


public class ColorSpannableString extends SpannableString {
    String originalString;
    GenericCompact.Functor<String, Void> mOnClick;
    public ColorSpannableString(String originalString, GenericCompact.Functor<String, Void> onClick) {
        super(originalString);
        this.originalString = originalString;
        this.mOnClick = onClick;
    }

    public ColorSpannableString(String originalString) {
        super(originalString);
        this.originalString = originalString;
        this.mOnClick = null;
    }

    public void setKeyword(String keyword, int color, boolean ignoreCase) {
        String org = ignoreCase ? originalString.toLowerCase(): originalString;
        String search = ignoreCase? keyword.toLowerCase(): keyword;
        for (int i = 0; i < org.length(); i += search.length()) {
            i = org.indexOf(search,  i);
            if (i < 0 ) {
                break;
            }
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
            this.setSpan(foregroundColorSpan, i, Math.min(i + keyword.length(), org.length()),  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (this.mOnClick  != null) {
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        mOnClick.apply(keyword);
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(color);
                    }
                };
                this.setSpan(clickableSpan, i, Math.min(i + keyword.length(), org.length()),  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public void setKeywords(String[] keywords, int[] color, boolean ignoreCase) {
        if (keywords.length != color.length) {
            return;
        }
        for (int i = 0; i < keywords.length; i++) {
            setKeyword(keywords[i], color[i], ignoreCase);
        }
    }
}
