package com.example.base.databing

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("visibleWhen")
fun visibleWhen(view:View,visible:Boolean?) = when(visible){
    true-> view.visibility=View.VISIBLE
    else->view.visibility=View.GONE
}
@BindingAdapter("goneWhen")
fun goneWhen(view:View,gone:Boolean?) = when(gone){
    true-> view.visibility=View.GONE
    else->view.visibility=View.VISIBLE
}
@BindingAdapter("goneWhenStr")
fun goneWhenStr(view:View,data:String?) = when(data.isNullOrEmpty()){
    true-> view.visibility=View.GONE
    else->view.visibility=View.VISIBLE
}

@BindingAdapter("invisibleWhenStr")
fun invisibleWhenStr(view:View,data:String?) = when(data.isNullOrEmpty()){
    true-> view.visibility=View.INVISIBLE
    else->view.visibility=View.VISIBLE
}

@BindingAdapter("goneInvisible")
fun goneInvisible(view:View,gone:Boolean?) = when(gone){
    true-> view.visibility=View.INVISIBLE
    else->view.visibility=View.VISIBLE
}


@BindingAdapter("visibleOrInvisible")
fun visibleOrInvisible(view:View,visible:Boolean?) = when(visible){
    true-> view.visibility=View.VISIBLE
    else->view.visibility=View.INVISIBLE
}


@BindingAdapter("goneEmpty")
fun goneEmpty(view:View,data:List<String>?) = when(data.isNullOrEmpty()){
    true-> view.visibility=View.GONE
    else->view.visibility=View.VISIBLE
}
@BindingAdapter("visibleEmpty")
fun visibleEmpty(view:View,data:List<String>?) = when(data.isNullOrEmpty()){
    true-> view.visibility=View.VISIBLE
    else->view.visibility=View.GONE
}