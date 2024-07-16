package com.example.base.utils

import android.view.View
import androidx.core.view.isVisible


fun View.setHidden(isHidden: Boolean) {
    if(isVisible == isHidden){
        isVisible = !isHidden
    }
}
fun View.isHidden():Boolean {
    return !isVisible
}