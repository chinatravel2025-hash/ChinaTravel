package com.example.base.databing

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.base.utils.BitmapUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.ViewStatusUtil
import java.util.Objects

object ImageViewBindAdapter {


    enum class ScaleType{
        FIT_CENTER,

        //慎用
        CENTER_CROP,


        CENTER_INSIDE,
    }



    @SuppressLint("SuspiciousIndentation", "CheckResult")
    fun setImageDrawable(image:ImageView, url: Any,
                         placeholder: Any? = null,
                         error: Any? = null,
                         isCircle:Boolean = false,
                         scaleType: ScaleType = ScaleType.FIT_CENTER,
                         width:Int = 0, height:Int = 0){


       val requestBuilder =  Glide.with(image)
           .load(url)
            .error(error)
           .fitCenter()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略

       if (scaleType == ScaleType.CENTER_CROP){
            requestBuilder.centerCrop()
        }else if(scaleType == ScaleType.CENTER_INSIDE){
            requestBuilder.centerInside()
        }else {
            requestBuilder.fitCenter()
        }

        if(placeholder == null){
            if (error is Int){
                requestBuilder.placeholder(error)
            }else if(error is Drawable){
                requestBuilder.placeholder(error)
            }
        }else {
            if (placeholder is Int){
                requestBuilder.placeholder(placeholder)
            }else if(placeholder is Drawable){
                requestBuilder.placeholder(placeholder)
            }
            if(error == null){
                requestBuilder.error(placeholder)
            }
        }

        if(width  > 0 && height > 0){
            requestBuilder.override(width,height)
        }

            if (isCircle){
                requestBuilder.circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
                    .apply(RequestOptions.circleCropTransform())
            }

            requestBuilder.into(object : CustomViewTarget<ImageView, Drawable>(image) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if (error != null)
                        view.setImageDrawable(errorDrawable)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    if(ViewStatusUtil.isViewCanUsed(view)){

                        (view as ImageView) .drawable?.apply {
                            view.setImageDrawable(BitmapUtils.copyDrawable(this))
                        }
                    }
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    if(image.drawable is BitmapDrawable && resource is BitmapDrawable){

                        if((image.drawable as BitmapDrawable).bitmap == resource.bitmap){
                            //同一个对象那么，不更新了
                            return
                        }
                    }
                    image.setImageDrawable(resource)
                }
            })

    }



}






