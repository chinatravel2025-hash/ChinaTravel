package com.example.base.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.*
import android.view.View
import java.util.regex.Pattern

object TextUtil {

    @JvmStatic
    fun matcherText(text: String, keyword: String, color: Int): SpannableString {
        val spannableString = SpannableString(text)
        val pattern = Pattern.compile(Pattern.quote(keyword))
        val matcher = pattern.matcher(SpannableString(text))
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            spannableString.setSpan(
                ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    fun getSpannableMultiStr(
        context: Context?,
        stringBuild: String?,
        maxKeyword: ArrayList<String>,
        maxColor: Int,
    ): SpannableString {
        if (null == context || stringBuild.isNullOrEmpty() || maxKeyword.isNullOrEmpty()) return SpannableString(stringBuild)
        val spannableString = SpannableString(stringBuild)

        for (curr in maxKeyword){
            val pattern1 = Pattern.compile(Pattern.quote(curr))
            val matcher1 = pattern1.matcher(spannableString)

            while (matcher1.find()){
                val indexStart = matcher1.start()
                val indexEnd = matcher1.end()
                spannableString.setSpan(
                    ForegroundColorSpan(maxColor),
                    indexStart,
                    indexEnd,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    indexStart,
                    indexEnd,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableString
    }


    fun getSpannableMultiStr(
        context: Context?,
        stringBuild: String?,
        maxKeyword: ArrayList<String>,
        maxColor: Int,
        maxSize: Float,
        minKeyword: String,
        minColor: Int,
        minSize: Float,
    ): SpannableString {
        if (null == context || stringBuild.isNullOrEmpty() || maxKeyword.isNullOrEmpty()) return SpannableString(stringBuild)
        val spannableString = SpannableString(stringBuild)

        for (curr in maxKeyword){
            val pattern1 = Pattern.compile(Pattern.quote(curr))
            val matcher1 = pattern1.matcher(spannableString)

            while (matcher1.find()){
                val indexStart = matcher1.start()
                val indexEnd = matcher1.end()
                val fontSize =DisplayUtils.dp2px(context,maxSize)
                spannableString.setSpan(
                    AbsoluteSizeSpan(fontSize),
                    indexStart,indexEnd,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(
                    ForegroundColorSpan(maxColor),
                    indexStart,
                    indexEnd,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    indexStart,
                    indexEnd,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
        val pattern = Pattern.compile(Pattern.quote(minKeyword))
        val matcher = pattern.matcher(spannableString)
        val fontSize =DisplayUtils.dp2px(context,minSize)

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                AbsoluteSizeSpan(fontSize),
                start,end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(
                ForegroundColorSpan(minColor),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }



    /**
     * 将一段文字中指定range的文字添加style效果
     * @param range 要添加删除线的文字的范围
     */
    fun CharSequence.toStyleSpan(style: Int = Typeface.BOLD, range: IntRange): CharSequence {
        return SpannableString(this).apply {
            setSpan(
                StyleSpan(style),
                range.start,
                range.endInclusive,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }





}