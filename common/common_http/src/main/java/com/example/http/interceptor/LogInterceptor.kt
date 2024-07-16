package com.example.http.interceptor

import com.example.base.utils.TLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.util.concurrent.TimeUnit

class LogInterceptor : Interceptor {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().build()
        val url = request.url.toString()
        scope.launch {
            TLog.request(
                url,
                Protocol.HTTP_1_1.toString(),
                request.method,
                request.headers.iterator().let {
                    val headerMap = mutableMapOf<String, String>()
                    it.forEach { pair ->
                        headerMap[pair.first] = pair.second
                    }
                    headerMap
                },
                requestBodyString(request)
            )
        }
        // 记录开始请求的时间
        val startNs = System.nanoTime()
        val response = chain.proceed(newRequest)
        val bodyResponse = response.body
        var newBody: ResponseBody? = null
        val mediaType = bodyResponse?.contentType()
        val networkString = if (isText(mediaType?.subtype)) {
            val bodyString = bodyResponse?.string() ?: ""
            newBody = bodyString.toResponseBody(mediaType)
            bodyString
        } else {
            "body is file"
        }
        scope.launch {
            TLog.response(
                url,
                Protocol.HTTP_1_1.toString(),
                response.code,
                response.message,
                response.headers.iterator().let {
                    val headerMap = mutableMapOf<String, String>()
                    it.forEach { pair ->
                        headerMap[pair.first] = pair.second
                    }
                    headerMap
                },
                networkString,
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
            )
        }
        return if (newBody == null) response else response.newBuilder().body(newBody).build()
    }


    private fun requestBodyString(request: Request): String {
        val body = request.body ?: return ""
        val mediaType = request.body?.contentType()
        val subType = mediaType?.subtype
        val isTextRequest = isText(subType)
        if (isTextRequest) {
            return body.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                buffer
            }.readUtf8()
        }
        return "body is file"
    }

    private fun isText(subType: String?): Boolean {
        return subType?.contains("json") == true
                || subType?.contains("xml") == true
                || subType?.contains("plain") == true
                || subType?.contains("html") == true
    }
}