package com.travel.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.BannerItem
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.base.base.App
import com.example.base.glide.GlideApp
import com.example.router.ARouterPathList
import com.travel.home.R
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(mDatas: List<BannerItem?>?) : BannerAdapter<BannerItem?, HomeBannerAdapter.BannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder?, data: BannerItem?, position: Int, size: Int) {

        holder?.imageView?.let {
            GlideApp.with(App.getContext())
                .load(data?.picUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置缓存的策略
                .into(it)
            it.setOnClickListener {
                ARouter.getInstance().build(ARouterPathList.WEB_HOME)
                    .navigation()
            }
        }
    }

     inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_img)
     }
}