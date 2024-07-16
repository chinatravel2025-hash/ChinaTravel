package com.example.http.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitCallback<T> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(call, response)
        } else {
            onFailure(call, Throwable(response.message()))
        }
    }

    abstract fun onSuccess(call: Call<T>?, response: Response<T>?)

    //用于进度的回调
    abstract fun onLoading(total: Long, progress: Long)
}