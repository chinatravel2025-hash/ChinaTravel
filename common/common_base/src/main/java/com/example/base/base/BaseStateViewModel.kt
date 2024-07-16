package com.example.base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseStateViewModel:ViewModel() {

    var pageStateModel = MutableLiveData<PageState>()

    fun setPageLoading(){
        pageStateModel.value= PageState.PAGE_LOADING
    }
    fun setPageEmpty(){
        pageStateModel.value= PageState.PAGE_EMPTY_DATA
    }
    fun setPageNormal(){
        pageStateModel.value= PageState.PAGE_NORMAL_DATA
    }

    fun setNetError(){
        pageStateModel.value= PageState.NET_ERROR_CLICK
    }

}