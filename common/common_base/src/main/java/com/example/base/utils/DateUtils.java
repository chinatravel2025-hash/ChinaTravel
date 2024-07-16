package com.example.base.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 时间管理
 */
public class DateUtils {
    /** 日志对象 */

    /**
     * 年-月-日 时:分:秒 显示格式
     */
    // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    /**
     * Date类型转为指定格式的String类型
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }


    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }


    /**
     * 返回移动多少天
     * millseconds
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDaysWithTimeStamp(long startTime,long endTime){

        try {
            long  days = (endTime - startTime) /  (24 * 3600 * 1000);
            return Integer.parseInt(String.valueOf(days));
        }catch (Exception e){
            return 0;
        }
    }


    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringDateShort(long time) {
        Date currentTime = new Date();
        currentTime.setTime(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    public static int getCurrentYear() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.YEAR);
    }

    public static int getYear(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }


    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }


    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }


    /**
     * 两个时间之间的天数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getDays(Long time1, Long time2) {

        long day = (time1 - time2) / (24 * 60 * 60 * 1000);
        return day;
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiff(Long endTime) {
        String strTime = "";
        if (endTime==null||endTime<=0){
            return "已到达";
        }
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        Date end =  new  Date(endTime*1000);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String nowtime = simpleDateFormat.format(curDate);
        String endtime = simpleDateFormat.format(end);
        try {
            // 获得两个时间的毫秒时间差异
            diff =simpleDateFormat.parse(endtime).getTime()- simpleDateFormat.parse(nowtime).getTime();
            if (diff<=0){
                return "已到达";
            }

            day = diff / nd;// 计算差多少天
            if (day>0){
                strTime =day+"天";
            }
            long hour = diff % nd / nh;// 计算差多少小时
            if (hour>0){
                strTime =strTime+ hour+"小时";
            }
            long min = diff % nd % nh / nm;// 计算差多少分钟
            if (min>0){
                strTime =strTime+ min+"分钟";
            }
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            return strTime;
        } catch (ParseException e) {
        }
        return "已到达";
    }


    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiffShort(Long endTime) {
        String strTime = "";
        if (endTime==null||endTime<=0){
            return "已送达";
        }
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        Date end =  new  Date(endTime*1000);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String nowtime = simpleDateFormat.format(curDate);
        String endtime = simpleDateFormat.format(end);
        try {
            // 获得两个时间的毫秒时间差异
            diff =simpleDateFormat.parse(endtime).getTime()- simpleDateFormat.parse(nowtime).getTime();
            if (diff<=0){
                return "已送达";
            }
            day = diff / nd;// 计算差多少天
            if (day>0){
                strTime =day+"天后送达";
                return strTime;
            }
            long hour = diff % nd / nh;// 计算差多少小时
            if (hour>0){
                strTime =strTime+ hour+"小时后送达";
                return strTime;
            }
            long min = diff % nd % nh / nm;// 计算差多少分钟
            if (min>0){
                strTime =strTime+ min+"分钟后送达";
                return strTime;
            }
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            return strTime;
        } catch (ParseException e) {
        }
        return strTime;
    }



    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    public static StringBuilder mFormatBuilder = new StringBuilder();
    public static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    /**
     * 将长度转换为时间
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 格式化获取到的时间
     */
    public static String formatTime(long time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }

    /*
     * 将时间戳转换为时间
     *
     * s就是时间戳
     */
    public static String stampToDate(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        //如果它本来就是long类型的,则不用写这一步
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    // 将字符串转为时间戳  "2023-03-30T14:07:41+08:00";
    public static long StrDateToTime(String strTime) {

        if (strTime==null||strTime.isEmpty()){
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return 0;
        }
        long timestamp = date.getTime();
     return  timestamp;
    }


    /**
     * @param second 秒
     * @description: 秒转换为时分秒 HH:mm:ss 格式 仅当小时数大于0时 展示HH
     * @return: {@link String}
     * @author: pzzhao
     * @date: 2022-05-08 13:55:17
     */
    public static String second2Time(Long second) {
        if (second == null || second < 0) {
            return "00:00";
        }

        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = second % 60;
        String str = "";
        if (h > 0) {
            str = (h < 10 ? ("0" + h) : h) + ":";
        }
        str += (m < 10 ? ("0" + m) : m) + ":";
        str += (s < 10 ? ("0" + s) : s);
        return str;

    }


    /**
     * 距离当前时间88天之内的日期
     *
     * @param time type:1--88天之内的
     * @return
     */
    public static boolean getDayDiffFromToday(String time) throws ParseException {
        //将字符串转为日期
//time=20171210144833  -->要对应"yyyyMMddHHmmss"不然会报unparase
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date param = sdf.parse(time);//参数时间
        long s1 = param.getTime();//将时间转为毫秒
        long s2 = System.currentTimeMillis();//得到当前的毫秒
        int day = Math.toIntExact((s2 - s1) / 1000 / 60 / 60 / 24);
        if (day > 0 && day <= 88) {
            return true;
        }
        return false;
    }


    public static String realTimeFormat(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    public static String convertNormalFormat(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    // 将字符串转为短时间  "2023-03-30T14:07:41+08:00";
    public static String StrDateToShortStr(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }


    public static String NormalDateToYearStr(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    public static String StrDateToYearStr(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    //判断选择的日期是否是本年
    public static boolean isThisYear(Date time) {
        return isThisTime(time, "yyyy");
    }

    //判断选择的日期是否是本季度
    public static boolean isThisQuarter(Date time) {
        Date QuarterStart = getCurrentQuarterStartTime();
        Date QuarterEnd = getCurrentQuarterEndTime();
        return time.after(QuarterStart) && time.before(QuarterEnd);
    }

    /**
     *判断是否是当天 本周 本月 本年 都是和现在时间对比
     */
    private static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 获得季度开始时间
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }


    public static Date convertStrToDate(String strTime){
        if (strTime==null||strTime.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return null;
        }
        return date;
    }



    public static String findSameMonth(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    public static String strYearMonthDay(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);
        return  simpleDateFormat.format(date1);
    }
    public static String strYearMonth(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);
        return  simpleDateFormat.format(date1);
    }


    public static String StrMonth(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);
        return  simpleDateFormat.format(date1);
    }
    public static String StrNormalMonth(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);
        return  simpleDateFormat.format(date1);
    }


    public static String StrYear(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    /**
     * 将rfc3339时间格式换成 -> 时分
     * @param strTime
     * @return
     */
    public static String rfc33DateTimeStrToHHMM(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    /**
     * 将rfc3339时间格式换成 -> 时分
     * @param strTime
     * @return
     */
    public static String rfc33DateTimeStrToYYHHDD(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }


    /**
     * 自定义日期时间格式
     * @param strTime
     * @param myformatDate
     * @return
     */
    public static String rfc33DateTimeStrToCustomeDate(String strTime,String myformatDate) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myformatDate);
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }
    public static String StrNormalDay(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }
    public static String StrDay(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }

    public static long rf33DateTimeToMillTimestamp(String strTime) {

        if (strTime==null||strTime.isEmpty()){
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return 0;
        }
        return date.getTime();

    }


    // 将字符串转为短时间  "2023-03-30T14:07:41+08:00";
    public static String StrDateToMonthDay(String strTime) {
        if (strTime==null||strTime.isEmpty()){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.CHINA); // 定义日期格式
        Date date = null; // 将字符串解析为 Date 对象
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
        }
        if (date==null){
            return "";
        }
        long timestamp = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date1 = new Date(timestamp);

        return  simpleDateFormat.format(date1);
    }



    /**
     * 传入两个时间范围，返回这两个时间范围内的所有日期，并保存在一个集合中
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static List<Calendar> findEveryDay(String beginTime, String endTime)
            throws Exception {
        //创建一个放所有日期的集合
        List<String> dates = new ArrayList();
        List<Calendar> datesss = new ArrayList();
        //创建时间解析对象规定解析格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //将传入的时间解析成Date类型,相当于格式化
        Date dBegin = sdf.parse(beginTime);
        Date dEnd = sdf.parse(endTime);
        //将格式化后的第一天添加进集合
        dates.add(sdf.format(dBegin));
        //使用本地的时区和区域获取日历
        Calendar calBegin = Calendar.getInstance();
        //传入起始时间将此日历设置为起始日历
        calBegin.setTime(dBegin);
        //判断结束日期前一天是否在起始日历的日期之后
        while (dEnd.after(calBegin.getTime())) {
            //根据日历的规则:月份中的每一天，为起始日历加一天
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            //得到的每一天就添加进集合
            dates.add(sdf.format(calBegin.getTime()));

            Calendar sss = Calendar.getInstance();
            //传入起始时间将此日历设置为起始日历
            sss.setTime(calBegin.getTime());
            //如果当前的起始日历超过结束日期后,就结束循
            datesss.add(sss);
        }
        return datesss;
    }
    public static String dayOfWeek(Calendar calBegin) {
        switch (calBegin.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return  "Sun";
            case Calendar.MONDAY:
                return  "Mon";
            case Calendar.TUESDAY:
                return  "Tue";
            case Calendar.WEDNESDAY:
                return  "Wed";
            case Calendar.THURSDAY:
                return  "Thu";
            case Calendar.FRIDAY:
                return  "Fri";
            case Calendar.SATURDAY:
                return  "Sat";

        }
        return "";
    }



    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {

        }
        return date.getTime();
    }


}
