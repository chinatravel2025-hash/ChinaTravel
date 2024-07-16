package com.example.base.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 仿微信时间戳格式转换
 */
object OursTimeUtil {
    var dayNames = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    fun getNewChatTime(timesamp: Long): String {
        var result = ""
        if (timesamp==0L){
            return result
        }
        val todayCalendar = Calendar.getInstance()
        val otherCalendar = Calendar.getInstance()
        otherCalendar.timeInMillis = timesamp
        var timeFormat = "M月d日 HH:mm"
        var yearTimeFormat = "yyyy年M月d日 HH:mm"
        var am_pm = ""
        val hour = otherCalendar[Calendar.HOUR_OF_DAY]
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨"
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上"
        } else if (hour == 12) {
            am_pm = "中午"
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午"
        } else if (hour >= 18) {
            am_pm = "晚上"
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm"
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm"
        val yearTemp = todayCalendar[Calendar.YEAR] == otherCalendar[Calendar.YEAR]
        result = if (yearTemp) {
            val todayMonth = todayCalendar[Calendar.MONTH]
            val otherMonth = otherCalendar[Calendar.MONTH]
            if (todayMonth == otherMonth) { //表示是同一个月
                val temp =
                    todayCalendar[Calendar.DATE] - otherCalendar[Calendar.DATE]
                when (temp) {
                    0 -> getHourAndMin(timesamp)
                    1 -> "昨天 " + getHourAndMin(timesamp)
                    2, 3, 4, 5, 6 -> {
                        val dayOfMonth = otherCalendar[Calendar.WEEK_OF_MONTH]
                        val todayOfMonth = todayCalendar[Calendar.WEEK_OF_MONTH]
                        if (dayOfMonth == todayOfMonth) { //表示是同一周
                            val dayOfWeek = otherCalendar[Calendar.DAY_OF_WEEK]
                            if (dayOfWeek != 1) { //判断当前是不是星期日 如想显示为：周日 12:09 可去掉此判断
                                dayNames[otherCalendar[Calendar.DAY_OF_WEEK] - 1] + getHourAndMin(
                                    timesamp
                                )
                            } else {
                                getTime(timesamp, timeFormat)
                            }
                        } else {
                            getTime(timesamp, timeFormat)
                        }
                    }
                    else -> getTime(timesamp, timeFormat)
                }
            } else {
                getTime(timesamp, timeFormat)
            }
        } else {
            getYearTime(timesamp, yearTimeFormat)
        }
        return result
    }

    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    fun getHourAndMin(time: Long): String {
        val format = SimpleDateFormat("HH:mm")
        return format.format(Date(time))
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    fun getTime(time: Long, timeFormat: String?): String {
        val format = SimpleDateFormat(timeFormat)
        return format.format(Date(time))
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    fun getYearTime(time: Long, yearTimeFormat: String?): String {
        val format = SimpleDateFormat(yearTimeFormat)
        return format.format(Date(time))
    }
}