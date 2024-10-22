package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.TravelProductItem
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.ResourceUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.databinding.HomeActivityShopDetailBinding
import com.travel.home.databinding.HomeActivityTripDetailBinding
import com.travel.home.vm.HomeShopDetailViewModel
import com.travel.home.vm.HomeTripDetailViewModel


@Route(path = ARouterPathList.HOME_TRIP_DETAIL)
class HomeTripDetailActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityTripDetailBinding
    private lateinit var mVM: HomeTripDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_trip_detail
    }

    @JvmField
    @Autowired
    var tripId: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeTripDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityTripDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initAboutContent()
        binding.banner.setAdapter(NormalBannerAdapter(listOf("", "")))
        mVM.getHomeTravelProducts(tripId?:0)


    }




    private fun initAboutContent(){

        val sss= "Shanghai is the economic, financial, commercial, and cultural " +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            " boasting the world’s busiest container port. The city i"
        val readMoreOption = ReadMoreOption.Builder(this)
            .textLength(3,ReadMoreOption.TYPE_LINE)
            .moreLabel("Read more")
            .lessLabel("  Read less")
            .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
            .expandAnimation(true)
            .build()
        readMoreOption.addReadMoreTo(binding.tvAboutContent,sss)

    }









}