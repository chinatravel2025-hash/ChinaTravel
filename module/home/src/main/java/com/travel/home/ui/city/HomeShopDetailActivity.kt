package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.PlaceType
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.ResourceUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.adapter.NormalBannerAdapter
import com.travel.home.databinding.HomeActivityShopDetailBinding
import com.travel.home.vm.HomeShopDetailViewModel


@Route(path = ARouterPathList.HOME_SHOP_DETAIL)
class HomeShopDetailActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityShopDetailBinding
    private lateinit var mVM: HomeShopDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_shop_detail
    }

    @JvmField
    @Autowired
    var placeId: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeShopDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityShopDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initAboutContent()
        binding.banner.setAdapter(NormalBannerAdapter(listOf("", "")))
        mVM.getPlaceList(PlaceType.SHOP,placeId?:0)



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