package com.example.base.weiget

import android.content.Context
import java.util.*
import kotlin.concurrent.fixedRateTimer

class ButtonWatch(
                val context: Context,
                val checkSeconds: Long,
                val accomplish: (stopWatch: ButtonWatch)-> Unit) {
    private val bucketName = "ButtonWatch"
    private var timer: Timer? = null

    fun start() {
        timer?.cancel()
        timer = fixedRateTimer(bucketName, true, 0, checkSeconds) {
            accomplish( this@ButtonWatch)
        }
    }

    fun stop() {
        timer?.cancel()
        timer = null
    }

}