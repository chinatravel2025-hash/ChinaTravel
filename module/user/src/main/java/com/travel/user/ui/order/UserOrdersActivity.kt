package com.travel.user.ui.order

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseStatusBarActivity
import com.example.base.ext.dp2px

import com.example.router.ARouterPathList
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.travel.user.R
import com.travel.user.databinding.UserActivityOrdersBinding
import com.travel.user.ui.order.adapter.OrderListItemOrchestrator
import com.travel.user.ui.order.adapter.UserOrderAdapter
import com.travel.user.vm.UserOrdersVM
import com.travel.user.vm.UserRegisterVM
import com.zyyoona7.itemdecoration.RecyclerViewDivider

@Route(path = ARouterPathList.USER_ORDERS)
class UserOrdersActivity : BaseStatusBarActivity(), OnLoadMoreListener, OnRefreshListener, OrderListItemOrchestrator {

    private lateinit var binding: UserActivityOrdersBinding
    private lateinit var mVM: UserOrdersVM
    private var mAdapter: UserOrderAdapter? = null
    override val ivBack: Int
        get() = R.id.iv_back
    override fun getLayoutId(): Int {
        return R.layout.user_activity_orders
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserOrdersVM::class.java]
        binding = mBaseBinding as UserActivityOrdersBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm=mVM
        initRv()
        initObserve()
        mVM.getOrderList()
    }
    private fun initObserve(){
        mVM.mDataSource.observe(this){
            mAdapter?.setList(it)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initRv(){
      val itemDecoration=  RecyclerViewDivider.linear()
            .dividerSize(22.dp2px())
            .hideLastDivider()
            .build()
        binding.apply {
            smartRefreshLayout.setEnableRefresh(true)
            smartRefreshLayout.setEnableLoadMore(true)
            smartRefreshLayout.setOnLoadMoreListener(this@UserOrdersActivity)
            smartRefreshLayout.setOnRefreshListener(this@UserOrdersActivity)
            mAdapter = UserOrderAdapter(this@UserOrdersActivity)
            val manager = LinearLayoutManager(this@UserOrdersActivity)
            manager.orientation = LinearLayoutManager.VERTICAL
            listRv.layoutManager = manager
            listRv.addItemDecoration(itemDecoration)
            listRv.adapter=mAdapter
        }

    }





    override fun onLoadMore(p0: RefreshLayout) {
        p0.finishLoadMoreWithNoMoreData()
    }

    override fun onRefresh(p0: RefreshLayout) {
        p0.finishRefresh()
    }

    override fun navigateToDetail(item: String) {

    }

}