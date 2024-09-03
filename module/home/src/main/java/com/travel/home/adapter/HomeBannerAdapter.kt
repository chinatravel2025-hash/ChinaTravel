package com.travel.home.adapter

import com.youth.banner.adapter.BannerAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.travel.home.R

class HomeBannerAdapter(mDatas: List<String?>?) : BannerAdapter<String?, HomeBannerAdapter.BannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        holder?.imageView?.let {
        }
    }

     inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_img)
     }
}