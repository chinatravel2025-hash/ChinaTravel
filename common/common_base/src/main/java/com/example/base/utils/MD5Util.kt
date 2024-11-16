package com.example.base.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Util {

    fun md5(input: String): String? {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(input.toByteArray())
            val digest = md5.digest()
            val hexString = StringBuffer()
            for (b in digest) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }
    }
}
