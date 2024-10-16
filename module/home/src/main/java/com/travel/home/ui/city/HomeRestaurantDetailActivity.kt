package com.travel.home.ui.city

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.PlaceItem
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.ResourceUtils

import com.example.router.ARouterPathList
import com.travel.home.R
import com.travel.home.databinding.HomeActivityRestaurantDetailBinding
import com.travel.home.vm.HomeRestaurantDetailViewModel


@Route(path = ARouterPathList.HOME_RESTAURANT_DETAIL)
class HomeRestaurantDetailActivity : BaseStatusBarActivity() {

    private lateinit var binding: HomeActivityRestaurantDetailBinding
    private lateinit var mVM: HomeRestaurantDetailViewModel
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.home_activity_restaurant_detail
    }

    @JvmField
    @Autowired
    var cityId: Long? = 0L
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeRestaurantDetailViewModel::class.java]
        binding = mBaseBinding as HomeActivityRestaurantDetailBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initAboutContent()
      //  binding.ivCity.setImageResource(R.mipmap.banner)
     //   binding.ivCity.scaleType=ImageView.ScaleType.CENTER_CROP

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