package com.example.base.utils

import com.blankj.utilcode.util.StringUtils
import java.math.BigDecimal
import java.util.regex.Pattern

object CommonNumber {
    const val num_10 = 10

    const val month_1 = 1
    const val month_2 = 2
    const val month_3 = 3
    const val month_4 = 4
    const val month_5 = 5
    const val month_6 = 6
    const val month_7 = 7
    const val month_8 = 8
    const val month_9 = 9
    const val month_10 = 10
    const val month_11 = 11
    const val month_12 = 12
    const val maxDay_28 = 28
    const val maxDay_29 = 29
    const val maxDay_30 = 30
    const val maxDay_31 = 31
    const val start_year_1900 = 1900
    const val end_year_2022 = 2022


    const val num_0 =0
    const val num_1 =1
    const val num_2 =2
    const val num_3 =3
    const val num_4 =4



    fun formatBigNum(num: String): String {
        return if (StringUtils.isEmpty(num)) {
            // 数据为空直接返回0
            "0"
        } else try {
            val sb = StringBuffer()
            if (!isNumeric(num)) {
                // 如果数据不是数字则直接返回0
                return "0"
            }
            val b0 = BigDecimal("1000")
            val b1 = BigDecimal("10000")
            val b2 = BigDecimal("100000000")
            val b3 = BigDecimal(num)
            var formatedNum = "" //输出结果
            var unit = "" //单位
            if (b3.compareTo(b0) ==-1) {
                sb.append(b3.toString())
            } else if (b3.compareTo(b0) == 0 || b3.compareTo(b0) ==1
                || b3.compareTo(b1) == -1
            ) {
                formatedNum = b3.divide(b0).toString()
                unit = "k"
            } else if (b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1
                || b3.compareTo(b2) == -1
            ) {
                formatedNum = b3.divide(b1).toString()
                unit = "w"
            } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
                formatedNum = b3.divide(b2).toString()
                unit = "亿"
            }
            if ("" != formatedNum) {
                var i = formatedNum.indexOf(".")
                if (i == -1) {
                    sb.append(formatedNum).append(unit)
                } else {
                    i = i + 1
                    val v = formatedNum.substring(i, i + 1)
                    if (v != "0") {
                        sb.append(formatedNum.substring(0, i + 1)).append(unit)
                    } else {
                        sb.append(formatedNum.substring(0, i - 1)).append(unit)
                    }
                }
            }
            if (sb.isEmpty()) "0" else sb.toString()
        } catch (e: Exception) {
            num
        }
    }


    fun formatBigNum(content: Long): String {
        var num =content.toString()
        return if (StringUtils.isEmpty(num)) {
            // 数据为空直接返回0
            "0"
        } else try {
            val sb = StringBuffer()
            if (!isNumeric(num)) {
                // 如果数据不是数字则直接返回0
                return "0"
            }
            val b0 = BigDecimal("1000")
            val b1 = BigDecimal("10000")
            val b2 = BigDecimal("100000000")
            val b3 = BigDecimal(num)
            var formatedNum = "" //输出结果
            var unit = "" //单位
            if (b3.compareTo(b0) ==-1) {
                sb.append(b3.toString())
            } else if (b3.compareTo(b0) == 0 || b3.compareTo(b0) ==1
                || b3.compareTo(b1) == -1
            ) {
                formatedNum = b3.divide(b0).toString()
                unit = "k"
            } else if (b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1
                || b3.compareTo(b2) == -1
            ) {
                formatedNum = b3.divide(b1).toString()
                unit = "w"
            } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
                formatedNum = b3.divide(b2).toString()
                unit = "亿"
            }
            if ("" != formatedNum) {
                var i = formatedNum.indexOf(".")
                if (i == -1) {
                    sb.append(formatedNum).append(unit)
                } else {
                    i = i + 1
                    val v = formatedNum.substring(i, i + 1)
                    if (v != "0") {
                        sb.append(formatedNum.substring(0, i + 1)).append(unit)
                    } else {
                        sb.append(formatedNum.substring(0, i - 1)).append(unit)
                    }
                }
            }
            if (sb.isEmpty()) "0" else sb.toString()
        } catch (e: Exception) {
            num
        }
    }


    private fun isNumeric(str: String): Boolean {
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }

    fun formatNumber(num: String): String {
        val tmp = StringBuffer().append(num).reverse()
        val retNum: String =
            Pattern.compile("(\\d{3})(?=\\d)").matcher(tmp.toString()).replaceAll("$1,")
        return StringBuffer().append(retNum).reverse().toString()
    }


}