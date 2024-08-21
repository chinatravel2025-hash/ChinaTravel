package com.aws.module_home.ui.im.weiget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

class PhoneWatch( val task: (time: Long)-> Unit) {
    private val bucketName = "PhoneWatch"
    private var timer: Timer? = null
    private var startTime = 0L
    private var endTime = 0L
    fun start() {
        startTime = System.currentTimeMillis()
        timer?.cancel()
        timer = fixedRateTimer(bucketName, true, 0, 1000) {
            CoroutineScope(Dispatchers.Main).launch {
                task.invoke(System.currentTimeMillis() - startTime)
            }
        }
    }

    fun stop():Long {
        timer?.cancel()
        timer = null
        endTime = System.currentTimeMillis()
        return endTime - startTime
    }

}