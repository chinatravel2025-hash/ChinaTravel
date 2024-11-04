package com.example.base.databing
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.base.glide.GlideApp
import com.example.base.utils.AppConfig
import com.example.base.utils.TLog
import com.example.base.utils.UrlUtil
import com.example.peanutmusic.base.R
import jp.wasabeef.glide.transformations.BlurTransformation

@BindingAdapter(value = ["url", "placeholder"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeholder: Int = 0) {
    GlideApp.with(imageView.context)
        .load(url)
        .placeholder(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
        .error(placeholder).centerInside()
        .into(imageView)
}

@BindingAdapter(value = ["urlList", "placeholder"], requireAll = false)
fun loadImage(imageView: ImageView, url: List<String>?, placeholder: Int = 0) {
    GlideApp.with(imageView.context)
        .load(AppConfig.appBaseImg(url?.get(0)))
        .placeholder(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
        .error(placeholder).centerInside()
        .into(imageView)
}

@BindingAdapter(value = ["circleUrl", "circlePlaceholder"], requireAll = false)
fun loadCircleImage(imageView: ImageView, url: String? , placeholder: Int = 0) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(bitmapTransform(CircleCrop()))
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
        .placeholder(placeholder)
        .error(placeholder)
        .into(imageView)
}






