/*
 * Copyright (C) 2021 Image Picker
 * Author: Nguyen Hoang Lam <hoanglamvn90@gmail.com>
 */

package com.nguyenhoanglam.imagepicker.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.nguyenhoanglam.imagepicker.R
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig

class ImagePickerToolbar : RelativeLayout {

    private lateinit var cancelTextView: TextView
    private lateinit var titleText: TextView
    private lateinit var doneText: TextView
    private var doneClickListener: OnClickListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.imagepicker_toolbar, this)
        if (isInEditMode) {
            return
        }
        cancelTextView = findViewById(R.id.text_toolbar_cancel)
        titleText = findViewById(R.id.text_toolbar_title)
        doneText = findViewById(R.id.text_toolbar_done)
    }

    fun config(config: ImagePickerConfig) {
        setBackgroundColor(Color.parseColor(config.toolbarColor))

        titleText.text = if (config.isFolderMode) config.folderTitle else config.imageTitle
        titleText.setTextColor(Color.parseColor(config.toolbarTextColor))

        doneText.text = config.doneTitle
        doneText.setTextColor(Color.parseColor(config.toolbarDoneTextColor))

    }

    fun setTitle(title: String?) {
        titleText.text = title
    }

    fun setOnBackClickListener(clickListener: OnClickListener) {
        cancelTextView.setOnClickListener(clickListener)
    }

    fun setOnTitleClickListener(clickListener: OnClickListener) {
        titleText.setOnClickListener(clickListener)
    }

    fun highlightDoneButton(highlight: Boolean) {
        if(highlight) {
            doneText.setTextColor(Color.parseColor("#008BE0"))
            doneClickListener?.let {
                doneText.setOnClickListener(it)
            }
        } else {
            doneText.setTextColor(Color.parseColor("#DFE2E5"))
            doneText.setOnClickListener(null)
        }
    }

    fun setOnDoneClickListener(clickListener: OnClickListener) {
        //doneText.setOnClickListener(clickListener)
        doneClickListener = clickListener
    }

    fun showSelectAlbum(show: Boolean) {
        val drawableRight = if(show) {
            R.mipmap.imagepicker_arrow_up
        } else {
            R.mipmap.imagepicker_arrow_down
        }
        titleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRight, 0)
    }
}