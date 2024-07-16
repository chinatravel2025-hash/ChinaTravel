package com.example.base.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


object OursDistanceUtil {

    /**
     *
     *
    距离显示最小单位：0.1km（100m）
    特殊化处理：当距离大于0且小于等于99m时，也统一显示0.1km
    其余的显示规则：
    大于等于N100m且小于N199m时，显示为N.1km
    大于等于N200m且小于N299m时，显示为N.2km
    大于等于N300m且小于N399m时，显示为N.3km
    大于等于N400m且小于N499m时，显示为N.4km
    ......
    大于等于N900m且小于N999是的，显示为N.9km
    大于等于N1000m且小于N1099m的，显示为N1.0km
    大于等于N1100m且小于N1199m的，显示为N1.1km
    以此类推
     */

    fun convertDistance(distance:Int?):String{
        if (distance==null||distance==0){
            return ""
        }
        if (distance in 1..99){
            return "0.1km"
        }
        val normal =1000
        val result =distance.toDouble() / normal.toDouble()
        val df = DecimalFormat("#0.0")
        df.roundingMode = RoundingMode.DOWN
     val formattedNumber: String = df.format(result)
        return formattedNumber + "km"


    }



}