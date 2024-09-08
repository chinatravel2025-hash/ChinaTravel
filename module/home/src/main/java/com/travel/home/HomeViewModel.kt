package com.travel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.ARouterPathList

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun navigationSearch(){
        ARouter.getInstance().build(ARouterPathList.HOME_SEARCH)
            .navigation()
    }
}