package com.travel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.makeramen.roundedimageview.RoundedImageView
import com.travel.map.R

class TripPagerAdapter : PagerAdapter() {
    private var mDatas: List<String>? = null

    fun setData(mDatas: List<String>?) {
        this.mDatas = mDatas
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mDatas?.size?:0
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val layout = LayoutInflater.from(collection.context)
            .inflate(R.layout.item_paper_trip, collection, false) as ViewGroup
        val imageView = layout.findViewById<RoundedImageView>(R.id.iv_img)

        /*    GlideApp.with(imageView.getContext())
                .load("")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);*/
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}
