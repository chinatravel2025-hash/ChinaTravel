package com.example.base.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * BirthdayUtil
 */
public class BirthdayUtil {

    private static String birthday;
    private static String ageStr;
    private static int age;
    //出生年、月、日
    private static int year;
    private static int month;
    private static int day;

    public static String birthdayToAge(String b) {
        birthday = b;
        stringToInt(birthday, "yyyy-MM-dd");
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);
        // 用当前年月日减去出生年月日
        int yearMinus = yearNow - year;
        int monthMinus = monthNow - month;
        int dayMinus = dayNow - day;
        age = yearMinus;// 先大致赋值
        if (yearMinus <= 0) {
            age = 1;
            ageStr = String.valueOf(age) + "岁";
            return ageStr;
        }
        if (monthMinus < 0) {
            age = age - 1;
        } else if (monthMinus == 0) {
            if (dayMinus < 0) {
                age = age - 1;
            }
        }
        ageStr = String.valueOf(age) + "岁";
        return ageStr;
    }

    /**
     * String类型转换成date类型
     * strTime: 要转换的string类型的时间，
     * formatType: 要转换的格式yyyy-MM-dd HH:mm:ss
     * //yyyy年MM月dd日 HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     */
    private static Date stringToDate(String strTime, String formatType) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date;
            date = formatter.parse(strTime);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String类型转换为long类型
     * .............................
     * strTime为要转换的String类型时间
     * formatType时间格式
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * strTime的时间格式和formatType的时间格式必须相同
     */
    private static void stringToInt(String strTime, String formatType) {
        try {
            //String类型转换为date类型
            Calendar calendar = Calendar.getInstance();
            Date date = stringToDate(strTime, formatType);
            calendar.setTime(date);
            if (date == null) {
            } else {
                //date类型转成long类型
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
        } catch (Exception e) {
        }
    }



    public static  String calculateStar(String strTime) {
        int m=0;
        int d=0;
        try {
            //String类型转换为date类型
            Calendar calendar = Calendar.getInstance();
            Date date = stringToDate(strTime, "yyyy-MM-dd");
            calendar.setTime(date);
            if (date == null) {
            } else {
                //date类型转成long类型
                m = calendar.get(Calendar.MONTH) + 1;
                d = calendar.get(Calendar.DAY_OF_MONTH);
            }
        } catch (Exception e) {
        }
        String name1="";
        if(m>0 &&m<13 && d>0 && d<32) {
            if((m ==3 && d>20) || (m ==4 && d<20)) {
                name1 = "白羊座";
            }else if((m ==4 && d>19) || (m ==5 && d<21)){
                name1 = "金牛座";
            }else if((m ==5 && d>20) || (m ==6 && d<22)){
                name1="双子座";
            }else if((m ==6 && d>21) || (m ==7 && d<23)){
                name1="巨蟹座";
            }else if((m ==7 && d>22) || (m ==8 && d<23)){
                name1="狮子座";
            }else if((m ==8 && d>22) || (m ==9 && d<23)){
                name1="处女座";
            }else if((m ==9 && d>22) || (m ==10 && d<23)){
                name1="天枰座";
            }else if((m ==10 && d>22) || (m ==11 && d<22)){
                name1="天蝎座";
            }else if((m ==11 && d>21) || (m ==12 && d<22)){
                name1="射手座";
            }else if((m ==12 && d>21) || (m ==1 && d<20)){
                name1="摩羯座";
            }else if((m ==1 && d>19) || (m ==2 && d<19)){
                name1="水瓶座";
            }else if((m ==2 && d>18) || (m ==3 && d<21)) {
                name1 = "双鱼座";
            }

        }else{
            name1="您输入的生日格式不正确或者不是真实生日！";
        }
        return name1;
    }

}


