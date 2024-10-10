package com.example.base.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import java.lang.Math.abs

object TextEllipsizeSpanUtil {

    /**
     * 在文本尾部显示一个icon。如果文本过长超过TextView的最大行数，后面部分ellipsis，也会在尾部显示icon
     * 并且icon可以点击
     */
/*    fun setTextEndImageSpan(textView: TextView, text: String, drawable: Drawable?) {
        //拼接一个空格和两个点，空格为文本和icon分割，两个点为icon占位，后面会把这两个点替换为icon
        val showText = "$text .."
        textView.text = showText
        val layout = textView.layout
        if (layout == null) {//还没有布局完成，等到布局完成再执行
            textView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    setImageSpan(textView, text, showText, drawable)
                }
            })
        } else {
            setImageSpan(textView, text, showText, drawable)
        }
    }*/

    /**
     * 执行设置imageSpan
     */
    private fun setImageSpan(
        textView: TextView,
        text: String,
        showText: String,
        drawable: Drawable?
    ) {
        var layout = textView.layout ?: return
        if (drawable != null && layout.lineCount > 0) {
            setTouchListener(textView)
            val line = layout.lineCount - 1 //最后一行
            var ellipsisStart = layout.getEllipsisStart(line) //从哪里开始省略的索引值
            var count = layout.getEllipsisCount(line) //省略的文字数
            val span = SpannableStringBuilder(showText)
            //省略行之前的总字数
            val lineEnd = if (line - 1 >= 0) {
                layout.getLineEnd(line - 1)
            } else {
                0
            }
            var offset = 2//imageSpan从倒数第几个开始放置
            if (count > 0) { //文字过长，有省略的文字
                offset = 1
                val subEndIndex = lineEnd + ellipsisStart //未省略的字的最后一个的索引值
                if (subEndIndex < showText.length) {
                    span.clear()
                    if (count <= 3) { //省略的刚好是拼接的空格和两个点,说明text刚刚好显示完，再加一个字符就会导致省略
                        span.append(showText.substring(0, subEndIndex - 2))
                    } else { //源文本就很长，被省略了
                        //截取显示的文本。-2少截取两个，以便有位置拼接省略号和icon
                        span.append(showText.substring(0, subEndIndex - 1))
                    }
                    span.append("....") //拼接四个省略号，最后一个点会被icon替换
                }
            }
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val imageSpan = ImageSpan(drawable)
            span.setSpan(
                imageSpan,
                span.length - offset,
                span.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //设置icon可点击
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {//点击事件
                    //     Toast.makeText(textView.context, "copy success", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {//不设置颜色和下划线
                }
            }
            span.setSpan(
                clickableSpan,
                span.length - 2,
                span.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = span
            //再次判断是否还有省略，有省略的话，说明imageSpan没显示全
            layout = textView.layout ?: return
            count = layout.getEllipsisCount(line) //省略的文字数
            if (count > 0) {//如果还有省略的文字，说明上面showText.substring截出来的文字太长了，要少截一点
                ellipsisStart = layout.getEllipsisStart(line) //从哪里开始省略的索引值
                val subEndIndex = lineEnd + ellipsisStart //未省略的字的最后一个的索引值
                if (subEndIndex < showText.length) {
                    span.clear()
                    span.append(showText.substring(0, subEndIndex - 3))//减去3，少截取一些，保证不再出现省略
                    span.append("....") //拼接省略号和两个点，两个点会被icon替换
                    span.setSpan(
                        imageSpan,
                        span.length - offset,
                        span.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    span.setSpan(
                        clickableSpan,
                        span.length - offset,
                        span.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    textView.text = span
                }
            }
        } else {
            textView.text = text
        }
    }


    /**
     * 显示文本。如果文本过长超过TextView的最大行数，后面部分ellipsis，在尾部追加 “展开全文”，并可以点击全文展开
     */
    fun setTextEndTextSpan(textView: TextView, text: String, expendText: String,closeText:String, colorResId: Int) {
        textView.text = text
        val layout = textView.layout
        if (layout == null) {
            textView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    setTextSpan(textView, text, expendText,closeText,colorResId)
                }
            })
        } else {
            setTextSpan(textView, text, expendText,closeText,colorResId)
        }
    }

    /**
     * 执行设置span
     */
    private fun setTextSpan(textView: TextView, text: String, expendText: String,closeText:String,colorResId: Int) {
        var layout = textView.layout ?: return
        if (layout.lineCount > 0) {
            setTouchListener(textView)
            val line = layout.lineCount - 1 //最后一行
            var ellipsisStart = layout.getEllipsisStart(line) //从哪里开始省略的索引值
            var count = layout.getEllipsisCount(line) //省略的文字数
            //省略行之前的总字数
            val lineEnd = if (line - 1 >= 0) {
                layout.getLineEnd(line - 1)
            } else {
                0
            }
            if (count > 0) {
                //文字过长，有省略的文字
                var subEndIndex = lineEnd + ellipsisStart //未省略的字的最后一个的索引值
                if (subEndIndex < text.length) {
                    val span = SpannableStringBuilder()
                    subEndIndex = subEndIndex - 1 - expendText.length
                    if (subEndIndex > 0) {
                        span.append(text.substring(0, subEndIndex))
                    }
                    span.append("...$expendText")
                    //拼接省略号和endText
                    //设置endText可点击
                    val clickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            //展示全文
                            val maxLine = textView.maxLines
                            expendText(textView,text,expendText,closeText,maxLine,colorResId)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            ds.color =
                                ResourceUtils.getColor(colorResId)
                            ds.isFakeBoldText = true
                        }

                    }
                    span.setSpan(
                        clickableSpan,
                        span.length - expendText.length,
                        span.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    textView.text = span
                    //再次判断是否还有省略，有省略的话，说明imageSpan没显示全
                    layout = textView.layout ?: return
                    count = layout.getEllipsisCount(line) //省略的文字数
                    if (count > 0) {//如果还有省略的文字，说明上面text.substring截的文字太长了，要少截一点,不然endText显示不全
                        ellipsisStart = layout.getEllipsisStart(line) //从哪里开始省略的索引值
                        subEndIndex = lineEnd + ellipsisStart //未省略的字的最后一个的索引值
                        if (subEndIndex < text.length) {
                            span.clear()
                            subEndIndex = subEndIndex - 5 - expendText.length
                            if (subEndIndex > 0) {
                                span.append(text.substring(0, subEndIndex))
                            }
                            span.append("...$expendText") //拼接省略号和两个点，两个点会被icon替换
                            span.setSpan(
                                clickableSpan, span.length - expendText.length,
                                span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            textView.text = span
                        }
                    }
                }
            }
        }
    }


    private fun expendText(textView: TextView, text: String,  expendText: String,closeText:String, maxLine: Int,colorResId: Int) {
        textView.maxLines = Int.MAX_VALUE
        val span = SpannableStringBuilder()
        var subEndIndex = text.length
        span.append(text.substring(0, subEndIndex))
        span.append(" $closeText")

        //设置expandText可点击
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //收起
                textView.maxLines = maxLine//恢复初始
                setTextEndTextSpan(textView, text, expendText,closeText,colorResId)

            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ResourceUtils.getColor(colorResId)
                ds.isFakeBoldText = true
            }

        }

        span.setSpan(
            clickableSpan,
            span.length - expendText.length,
            span.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = span
    }


    /**
     * 设置触摸事件，让TextView响应ClickableSpan。此处代码参考LinkMovementMethod.onTouchEvent方法
     * 直接给TextView设置LinkMovementMethod后，文本过长时可以滑动，与ellipsize冲突
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(textView: TextView) {
        textView.setOnTouchListener(object : View.OnTouchListener {
            var downX = 0f
            var downY = 0f
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val spanned = textView.text as? Spanned ?: return false
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.x
                        downY = event.y
                        return true//返回true,接受后续的UP事件
                    }
                    MotionEvent.ACTION_UP -> {
                        var x = event.x.toInt()
                        var y = event.y.toInt()
                        //判断一下按下和抬起的位置，相差太大就不处理
                        if (abs(downX - x) > 8 || abs(downY - y) > 8) {
                            return false
                        }
                        //下面代码都是照搬LinkMovementMethod.onTouchEvent
                        x -= textView.totalPaddingLeft
                        y -= textView.totalPaddingTop

                        x += textView.scrollX
                        y += textView.scrollY

                        val layout: Layout = textView.layout
                        val line = layout.getLineForVertical(y)
                        val off = layout.getOffsetForHorizontal(line, x.toFloat())

                        val links: Array<ClickableSpan> =
                            spanned.getSpans(off, off, ClickableSpan::class.java)

                        if (links.isNotEmpty()) {
                            links[0].onClick(textView)
                            return true
                        }
                    }
                }
                return false
            }
        })
    }
}
