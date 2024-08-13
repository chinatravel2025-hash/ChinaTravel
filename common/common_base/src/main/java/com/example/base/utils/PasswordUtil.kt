package com.example.base.utils


object PasswordUtil {
    //数字
    const val REG_NUMBER: String = ".*\\d+.*"

    //小写字母
    const val REG_UPPERCASE: String = ".*[A-Z]+.*"

    //大写字母
    const val REG_LOWERCASE: String = ".*[a-z]+.*"

    //特殊符号
    const val REG_SYMBOL: String = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*"

    fun validateEmailPasswordFormat(password: String): Boolean {
        // 正则表达式，要求：
        // 1. 最少8位，最多20位
        // 2. 至少包含（大写|小写|数字|特殊符号）中的两种
        //  val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$"
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}\$"
        //val regex = "^(?=(?:.*[a-z]){0,1})(?=(?:.*[A-Z]){0,1})(?=(?:.*\\d){0,1})(?=(?:.*[\\W_]){0,1})(?!.*\\s)(?=.{8,20}\$)(?:(?=.*[a-z].*[A-Z])|(?=.*[A-Z].*\\d)|(?=.*\\d.*[\\W_])|(?=.*[\\W_].*[a-z])|(?=.*[a-z].*[\\W_])|(?=.*[A-Z].*[\\W_])).*"
        return password.matches(regex.toRegex())
    }

    fun isPswComplex(password: String?): Boolean {
        //密码为空或者长度小于8位则返回false
        if (password == null || password.length < 8) return false
        var i = 0
        if (password.matches(REG_NUMBER.toRegex())) i++
        if (password.matches(REG_LOWERCASE.toRegex())) i++
        if (password.matches(REG_UPPERCASE.toRegex())) i++
        if (password.matches(REG_SYMBOL.toRegex())) i++

        if (i < 2) return false
        return true
    }


}


