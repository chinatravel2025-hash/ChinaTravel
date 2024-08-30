package com.nguyenhoanglam.imagepicker.helper

import android.net.Uri
import android.widget.ImageView
import coil.load
import coil.size.Dimension
import com.nguyenhoanglam.imagepicker.R

class ImageLoader {

    companion object {

        fun loadImage(imageView: ImageView, uri: Uri) {
            imageView.load(uri) {
                crossfade(true)
                placeholder(R.drawable.imagepicker_image_placeholder)
                error(R.drawable.imagepicker_image_error)
                size(width = Dimension.Pixels(120), height = Dimension.Pixels(120))
            }
        }

        fun loadImageUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(true)
                placeholder(R.drawable.imagepicker_image_placeholder)
                error(R.drawable.imagepicker_image_error)
                size(width = Dimension.Pixels(120), height = Dimension.Pixels(120))
            }
        }

    }

}