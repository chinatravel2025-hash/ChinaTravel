
package com.example.base.utils
import android.content.Context
import java.util.*
import kotlin.concurrent.fixedRateTimer

class TimeWatcherUtils(
    val context: Context,
    private val checkSeconds: Int,
    val accomplish: (stopWatch: TimeWatcherUtils)-> Unit) {
    private val timerName = "SmsTimeWatcher"
    private var timer: Timer? = null

    fun start() {
        timer?.cancel()
        timer = fixedRateTimer(timerName, true, 100, checkSeconds  * 100L) {
            accomplish( this@TimeWatcherUtils)
        }
    }

    fun stop() {
        timer?.cancel()
        timer = null
    }

}