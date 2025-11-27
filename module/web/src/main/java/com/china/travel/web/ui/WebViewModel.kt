package com.china.travel.web.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {
    val isProgressShowHide = MutableLiveData(false)
    val progress = MutableLiveData(0)
    val title = MutableLiveData<String>()

}