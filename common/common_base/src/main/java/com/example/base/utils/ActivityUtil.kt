package com.example.base.utils

import android.app.Activity
import android.content.Context
import android.os.Vibrator


fun Activity.vibrator(milliseconds: Long) {
    if (!PhoneAudioHelper.isMute(this)) {
        getSystemService(Context.VIBRATOR_SERVICE)?.apply {
            (this as Vibrator).apply {
                vibrate(milliseconds)
            }
        }
    }
}