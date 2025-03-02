package com.example.common_http

import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val byteArray = byteArrayOf( 118, 98, 98, 110, 98)

        // 将字节数组转换为字符串（使用 UTF-8 字符集）

        // 将字节数组转换为字符串（使用 UTF-8 字符集）
        val resultString = String(byteArray, StandardCharsets.UTF_8)

        // 打印结果

        // 打印结果
        println(resultString)
    }
}