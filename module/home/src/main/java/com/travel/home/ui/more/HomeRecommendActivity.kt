package com.travel.home.ui.more

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.ObjectType
import com.china.travel.widget.dialog.FilterPartShadowPopupView
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.BaseStatusBarActivity
import com.example.router.ARouterPathList
import com.google.android.material.tabs.TabLayoutMediator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.SimpleCallback
import com.travel.home.R
import com.travel.home.databinding.HomeActivityRecommendBinding
import com.travel.home.vm.HomeCityDetailViewModel
import com.travel.home.vm.HomeRecommendViewModel


@Route(path = ARouterPathList.HOME_RECOMMEND)
class HomeRecommendActivity : BaseStatusBarActivity(), (CityItem) -> Unit {

    private lateinit var binding: HomeActivityRecommendBinding
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.home_activity_recommend
    }
    private lateinit var mVM: HomeRecommendViewModel
    private var popupView: FilterPartShadowPopupView? = null
    private val titles: Array<String> = arrayOf("Recommend", "Day Trip", "Sightseeing", "Local Shop", "Restaurant")
    @JvmField
    @Autowired
    var city: CityItem? = null

    @JvmField
    @Autowired
    var type: String? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding = mBaseBinding as HomeActivityRecommendBinding
        ARouter.getInstance().inject(this)
        mVM = ViewModelProvider(this)[HomeRecommendViewModel::class.java]
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm=mVM
        mVM.mSelectCity.value=city
        initTabs()
        showFilter()
        mVM.getCityList()
        currentTab(type?:"")
    }
    private fun currentTab(type:String){
        when(type){
            "sight"->{binding.viewPager.setCurrentItem(2,false)}
            "shop"->{binding.viewPager.setCurrentItem(3,false)}
            "restaurant"->{binding.viewPager.setCurrentItem(4,false)}
            "travel-products"->{binding.viewPager.setCurrentItem(1,false)}
            else->{
                binding.viewPager.setCurrentItem(0,false)
            }
        }
    }

    private fun showFilter() {
        binding.llFilter.setOnClickListener {
            if (mVM.mDataCity.value.isNullOrEmpty()){
                SmartToast.classic().showInCenter("获取城市失败")
            }else{
                popupView = XPopup.Builder(this)
                    .atView(it)
                    .setPopupCallback(object : SimpleCallback() {

                    })
                    .asCustom(FilterPartShadowPopupView(this, mVM.mDataCity.value?: emptyList(),this)) as FilterPartShadowPopupView?

                popupView?.show()
            }
        }
    }

    override fun invoke(filter: CityItem) {
        mVM.mSelectCity.value=filter
        popupView?.dismiss()
    }


    private fun initTabs() {
        binding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.viewPager.isSaveEnabled = false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
            }
        })
        val adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return titles.size
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    1 -> {
                        HomeRecommendTripFragment.newInstance("Shanghai")
                    }
                    2 -> {
                        HomeRecommendPlaceFragment.newInstance("Shanghai", "sight")
                    }
                    3 -> {
                        HomeRecommendPlaceFragment.newInstance("Shanghai", "shop")
                    }
                    4 -> {
                        HomeRecommendPlaceFragment.newInstance("Shanghai", "restaurant")
                    }
                    else -> {
                        HomeRecommendPlaceFragment.newInstance("Shanghai", "")
                    }
                }


            }
        }
        binding.viewPager.adapter = adapter
        val mediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = titles[position]
            }
        mediator.attach()
    }


}