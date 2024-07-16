package com.example.base.utils

import java.text.DecimalFormat

object OUrsMoneyUtil {

    fun convertMoney(fen:Int):String{
        if (fen<=0){
            return "0"
        }
        val df = DecimalFormat()
        val ss = df.format(fen.toDouble() / 100)

        return ss
    }



}