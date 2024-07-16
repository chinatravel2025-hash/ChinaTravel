package com.example.base.utils

import android.util.Log
import com.example.peanutmusic.base.BuildConfig
import org.json.JSONObject
import java.util.*

/**
 * 传tag，则以tag作为Tag，不传tag，则以文件名做为Tag
 *
 * ```java
 * TTLog.v("日志内容");
 * TTLog.v(() -> "日志内容");
 * TTLog.v("tag","日志内容");Lo
 * TTLog.v("tag", () -> "日志内容");
 * TTLog.v( () -> "日志内容");
 * ```
 *
 * ```kotlin
 * TTLog.v("日志内容");
 * TTLog.v{ "日志内容" }
 * TTLog.v("tag","日志内容");
 * TTLog.v("tag") { "日志内容" }
 * TTLog.v{ "日志内容" }
 * ```
 */
object TLog {

    internal val LINE_SEPARATOR: String = System.getProperty("line.separator") ?: "\n"
    const val JSON_INDENT = 4
    const val TOP =
        "┌──────────────────────────────────────────────────────────────────────────────────────"
    const val TOP_REQUEST =
        "┌────── request ───────────────────────────────────────────────────────────────────────"
    const val TOP_RESPONSE =
        "┌────── response ──────────────────────────────────────────────────────────────────────"
    const val BOTTOM =
        "└──────────────────────────────────────────────────────────────────────────────────────"
    const val CORNER_UP = "┌ ";
    const val CORNER_BOTTOM = "└ ";
    const val CENTER_LINE = "├ ";
    const val DEF_LINE = "│ ";
    const val TAG_REQUEST = "Request"

    @JvmInline
    value class Priority(val value: Int) {
        companion object {
            /**详细日志**/
            val V = Priority(Log.VERBOSE)

            /**调试日志：功能调试辅助日志**/
            val D = Priority(Log.DEBUG)

            /**重要信息日志: 网络请求、前后台切换、功能流程、生命周期...**/
            val I = Priority(Log.INFO)

            /**警告日志**/
            val W = Priority(Log.WARN)

            /**错误日志：异常、流程错误、框架流程错误回调**/
            val E = Priority(Log.ERROR)
        }

        override fun toString(): String {
            return when (this) {
                V -> "V"; D -> "D";I -> "I";W -> "W";E -> "E";else -> ""
            }
        }
    }

    var enable: Boolean = false
        private set

    private val listeners = mutableListOf<LogListener>()

   public fun setEnable(enable: Boolean): TLog {
        this.enable = enable
        return this
    }

    /**
     * 不允许在[LogListener]中调用[TLog]打印日志
     */
    fun addLogListener(listener: LogListener): TLog {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
        return this
    }

    fun removeLogListener(listener: LogListener): TLog {
        listeners.remove(listener)
        return this
    }

    @JvmStatic
    fun v(log: Any?) {
        printLog(Priority.V, null) {
            log
        }
    }

    @JvmStatic
    fun v(tag: String?, log: Any?) {
        printLog(Priority.V, tag) {
            log
        }
    }

    @JvmStatic
    fun v(tag: String?, log: () -> Any?) {
        printLog(Priority.V, tag, log)
    }

    @JvmStatic
    fun v(log: () -> Any?) {
        printLog(Priority.V, null, log)
    }

    @JvmStatic
    fun d(log: Any?) {
        printLog(Priority.D, null) {
            log
        }
    }

    @JvmStatic
    fun d(tag: String?, log: Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.D, tag) {
            log
        }
    }

    @JvmStatic
    fun d(tag: String?, log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.D, tag, log)
    }

    @JvmStatic
    fun d(log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.D, null, log)
    }

    @JvmStatic
    fun i(log: Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.I, null) {
            log
        }
    }

    @JvmStatic
    fun i(tag: String?, log: Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.I, tag) {
            log
        }
    }

    @JvmStatic
    fun i(tag: String?, log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.I, tag, log)
    }

    @JvmStatic
    fun i(log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.I, null, log)
    }

    @JvmStatic
    fun w(log: Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.W, null) {
            log
        }
    }

    @JvmStatic
    fun w(tag: String?, log: Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.W, tag) {
            log
        }
    }

    @JvmStatic
    fun w(tag: String?, log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        printLog(Priority.W, tag, log)
    }

    @JvmStatic
    fun w(log: () -> Any?) {
        printLog(Priority.W, null, log)
    }

    @JvmStatic
    fun e(log: Any?) {
        printLog(Priority.E, null) {
            log
        }
    }

    @JvmStatic
    fun e(tag: String?, log: Any?) {
        printLog(Priority.E, tag) {
            log
        }
    }

    @JvmStatic
    fun e(tag: String?, log: () -> Any?) {
        printLog(Priority.E, tag, log)
    }

    @JvmStatic
    fun e(log: () -> Any?) {
        printLog(Priority.E, null, log)
    }

    fun request(
        url: String, protocol: String, method: String,
        header: Map<String, String>, body: String
    ) {
        mutableListOf<String>().apply {
            add(TOP_REQUEST)
            add("$DEF_LINE${method.uppercase(Locale.getDefault())} $url ${protocol.uppercase(Locale.getDefault())}")
            header.forEach {
                add("$DEF_LINE${it.key}: ${it.value}")
            }
            add(DEF_LINE)
//            body.takeIf { !TextUtils.isEmpty(it) }
//                ?.asJsonList(JSON_INDENT)
//                ?.forEach {
//                    add("$DEF_LINE$it")
//                }
            add("$DEF_LINE$body")
            add(BOTTOM)
        }.forEach {
            printLog(Priority.I, TAG_REQUEST) {
                it
            }
        }
    }

    fun response(
        url: String, protocol: String, code: Int, msg: String,
        header: Map<String, String>, body: String, receivedTime: Long
    ) {
        mutableListOf<String>().apply {
            add(TOP_RESPONSE)
            add("$DEF_LINE${receivedTime}ms $url")
            add(DEF_LINE)
            add("$DEF_LINE${protocol.uppercase(Locale.getDefault())} $code $msg")
            header.forEach {
                add("$DEF_LINE${it.key}: ${it.value}")
            }
            add(DEF_LINE)
//            body.asJsonList(JSON_INDENT).forEach {
//                add("$DEF_LINE$it")
//            }
            add("$DEF_LINE$body")
            add(BOTTOM)
        }.forEach {
            printLog(Priority.I, TAG_REQUEST) {
                it
            }
        }
    }

    private fun printLog(priority: Priority, tag: String?, log: () -> Any?) {
        if(!BuildConfig.DEBUG){
            return
        }
        if (listeners.isEmpty() && !enable) {
            return
        }
        val stackTrace = Thread.currentThread().stackTrace
        val stackTraceElement = stackTrace[4].let {
            if (it.className == this::class.java.name) {
                // kotlin 代码中调用会多一层 default 函数
                stackTrace[5]
            } else {
                it
            }
        }
        val logMsg = log().toString()
        val fileName = stackTraceElement.fileName
        LogEvent(
            priority, tag ?: fileName, logMsg, stackTraceElement
        ).also { logEvent ->
            listeners.forEach { listener ->
                listener(logEvent)
            }
            if (!enable) return@also
            logEvent.content.asJsonList(JSON_INDENT).let { jsonList ->
                if (jsonList.size > 1) {
                    jsonList.map {
                        "${DEF_LINE}$it"
                    }.toMutableList().apply {
                        add(0, TOP)
                        add(BOTTOM)
                    }
                } else {
                    jsonList
                }
            }.apply {
                printLogEvent(logEvent, this)
            }
        }
    }

    private fun printLogEvent(logEvent: LogEvent, logs: List<String>) {
        val fileName = logEvent.stackTraceElement.fileName
        val logHeader = StringBuilder("(${fileName}:${logEvent.stackTraceElement.lineNumber})")
            .let {
                if (logEvent.tag != TAG_REQUEST) {
                    it.append("#${logEvent.stackTraceElement.methodName}> ")
                }
                it.toString()
            }
        logs.forEach { log ->
            val logContent = "$logHeader$log"
            print(logEvent.priority, logEvent.tag, logContent)
        }
    }

    private fun print(priority: Priority, tag: String, log: String) {
        val tagNew = "TTLog#$tag"
        when (priority) {
            Priority.V -> {
                Log.v(tagNew, log)
            }
            Priority.D -> {
                Log.d(tagNew, log)
            }
            Priority.I -> {
                Log.i(tagNew, log)
            }
            Priority.W -> {
                Log.w(tagNew, log)
            }
            Priority.E -> {
                Log.e(tagNew, log)
            }
        }
    }
}

typealias LogListener = (LogEvent) -> Unit

data class LogEvent(
    val priority: TLog.Priority, val tag: String,
    val content: String, val stackTraceElement: StackTraceElement
)

fun String.asJsonList(indentSpaces: Int = 4): MutableList<String> {
    try {
        when {
            startsWith("{") -> JSONObject(this).toString(indentSpaces)
            startsWith("[") -> JSONObject(this).toString(indentSpaces)
            else -> null
        }?.also { json ->
            val jsonList = mutableListOf<String>()
            json.split(TLog.LINE_SEPARATOR).forEach {
                jsonList.add(it)
            }
            return jsonList
        }
    } catch (e: Exception) {
    }
    return mutableListOf(this)
}

//fun TTLog.test() {
//    val json =
//        "{\"code\":0,\"data\":{\"liked\":false,\"likes\":[],\"_id\":\"6150235a2d26a6001eb9ae51\",\"updated_at\":\"2021-09-26T07:38:24.970Z\",\"created_at\":\"2021-09-26T07:38:02.572Z\",\"content\":\"123445667\",\"__v\":0,\"top_gift\":[],\"gift_count\":0,\"like_count\":0,\"status\":2,\"images\":[{\"url\":\"http://bucketyh.yuhunapp.com/60f8dc1e7240674f347da197/moments/1632641878976.jpg\",\"_id\":\"6150235a2d26a6001eb9ae52\",\"status\":0}]}}"
//    v { "详细信息" }
//    d { "调试信息" }
//    i { json }
//    w { "警告信息" }
//    e { NullPointerException("空指针异常").fillInStackTrace() }
//    val header = mapOf(
//        "Authorization" to "Bearer I6MTYzMjkxMzIxNH0.reUqMK9wlodmr4CmRIMpLNWgsVgYAOMcFNMi9WkELL0",
//        "Accept-Language" to "zh-CN,zh;q=0.8",
//        "Cache-Control" to "no-cache",
//        "sn-common" to "version=1.3.8&android_version=11&device_type=OnePlusGM1910&app=20200900&channel=website&niu_plus_uuid=10e41798e6eb37452ff2eaba8197c7eb21",
//    )
//    request(
//        "http://yuhunapp.mloveli.com/api/v1/users/in_jail/my",
//        "http/1.1", "GET", header, json
//    )
//    response(
//        "http://yuhunapp.mloveli.com/api/v1/users/in_jail/my",
//        "http/1.1", 200, "", header, json, 55
//    )
//}