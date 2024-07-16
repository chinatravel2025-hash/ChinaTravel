package com.example.base.utils;

import android.view.View
import com.example.base.base.App

val Int.localized: String
    get() = App.getApp().getString(this) ?: ""


val doNothing = Unit
val ignore = {}





