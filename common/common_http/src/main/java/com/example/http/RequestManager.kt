package com.example.http

import android.os.Build
import androidx.collection.ArrayMap
import com.example.common_http.BuildConfig
import com.example.http.api.LiveDataCallAdapterFactory
import com.example.http.interceptor.LogInterceptor
import com.example.http.util.HttpEventListener
import com.example.http.util.SSLContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.SoftReference
import java.util.concurrent.TimeUnit


object RequestManager {
    private val okHttpClient: OkHttpClient by lazy {
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                mInterceptors.forEach {
                    addInterceptor(it)
                }
            }
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttp.addInterceptor(loggingInterceptor)
            okHttp.addInterceptor(LogInterceptor())

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             SSLContextUtil.createUntrustedSSLClient(okHttp);
        } else {
            okHttp.eventListenerFactory(HttpEventListener.FACTORY)
            okHttp.build()
        }
    }
    private val mRetrofitCache = ArrayMap<String, SoftReference<Retrofit>>()

    private val mInterceptors: MutableList<Interceptor> = ArrayList()

    fun addInterceptor(interceptor: Interceptor) {
        mInterceptors.add(interceptor)
    }

    fun removeInterceptor(interceptor: Interceptor) {
        mInterceptors.remove(interceptor)
    }

    fun build(domain: String, factory: CallAdapter.Factory?): Retrofit {
        val key = genKey(domain, factory)
        var retrofit: Retrofit? = mRetrofitCache[key]?.get()
        if (retrofit != null) {
            return retrofit
        }
        retrofit = Retrofit.Builder()
            .baseUrl(domain)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                if (factory != null) {
                    addCallAdapterFactory(factory)
                }
            }.client(okHttpClient).build()

        mRetrofitCache[key] = SoftReference<Retrofit>(retrofit)
        return retrofit
    }

    fun build(domain: String): Retrofit {
        return build(domain, LiveDataCallAdapterFactory())
    }

    private fun genKey(domain: String, factory: CallAdapter.Factory?): String {
        return domain + factory?.javaClass?.canonicalName
    }
}