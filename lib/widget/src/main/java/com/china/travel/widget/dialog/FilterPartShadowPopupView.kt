package com.china.travel.widget.dialog

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aws.bean.entities.home.CityItem
import com.china.travel.widget.R
import com.lxj.xpopup.impl.PartShadowPopupView

class FilterPartShadowPopupView(context: Context, val data :List<CityItem>,val  callback:(CityItem)->Unit) : PartShadowPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.filter_part_shadow_popup
    }

    override fun onCreate() {
        super.onCreate()
        val rvFilter = findViewById<RecyclerView>(R.id.rv_filter)
        rvFilter.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = manager
           val  mAdapter = FilterListAdapter(callback)
            adapter = mAdapter
            mAdapter.setList(data)

        }
    }

    override fun onShow() {
        super.onShow()
    }

    override fun onDismiss() {
        super.onDismiss()
    }
}
