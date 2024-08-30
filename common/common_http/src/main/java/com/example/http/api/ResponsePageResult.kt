package com.example.http.api


data class ResponsePageResult<T>(
    var rows: List<T>,
    var total: Long
)
