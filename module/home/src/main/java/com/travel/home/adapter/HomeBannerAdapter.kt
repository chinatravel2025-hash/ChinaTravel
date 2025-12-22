package com.travel.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.BannerItem
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.base.base.App
import com.example.base.glide.GlideApp
import com.example.base.utils.AppConfig
import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.dialog.ImagePreviewDialogFragment
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(mDatas: List<BannerItem?>?) : BannerAdapter<BannerItem?, HomeBannerAdapter.BannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder?, data: BannerItem?, position: Int, size: Int) {
        holder?.imageView?.let { imageView ->
            GlideApp.with(App.getContext())
                .load(AppConfig.appBaseImg(data?.picUrl))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
                .into(imageView)
            
            imageView.setOnClickListener {
                // 点击查看大图
                val imageUrl = data?.picUrl
                if (!imageUrl.isNullOrEmpty()) {
                    val activity = imageView.context as? FragmentActivity
                    activity?.let {
                        val dialog = ImagePreviewDialogFragment.newInstance(imageUrl)
                        dialog.show(it.supportFragmentManager, "ImagePreviewDialog")
                    }
                }
            }
        }
    }

     inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_img)
     }
}