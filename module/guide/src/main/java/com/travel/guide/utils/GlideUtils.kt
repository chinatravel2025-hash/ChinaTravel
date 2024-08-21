package com.travel.guide.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.Placeholder
import androidx.fragment.app.Fragment
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.base.glide.GlideApp
import com.example.base.utils.TLog
import com.example.base.utils.UrlUtil
import com.travel.guide.R

object GlideUtils  {


    /**
     * 加载默认资源文件(没有缓存)
     * @param mContext
     * @param imageurl
     * @param imageView
     */
    fun loadImgNoCash(
        mContext: Context,
        imageurl: String?,
        imageView: ImageView,
        holerImage: Int,
        errorImage: Int
    ) {
        val options = RequestOptions().placeholder(holerImage).error(errorImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(true)
        Glide.with(mContext)
            .load(imageurl)
            .apply(options)
            .into(imageView)
    }

    fun loadGifNoCash(
        mContext: Context,
        imageurl: String?,
        imageView: ImageView,
        holerImage: Int,
        errorImage: Int
    ) {
        val options = RequestOptions().placeholder(holerImage).error(errorImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(true)
        Glide.with(mContext)
            .load(imageurl)
            .apply(options)
            .into(imageView)
    }

    /**
     * 普通缓存策略
     */
    fun loadNormalPic(mContext: Context, url: String?, imageView: ImageView) {
        GlideApp.with(mContext)
            .load(url)
            .placeholder(com.example.peanutmusic.base.R.mipmap.ic_normal_load_default)
            .error(com.example.peanutmusic.base.R.mipmap.ic_banner_error_default)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
            .into(imageView)
    }


}