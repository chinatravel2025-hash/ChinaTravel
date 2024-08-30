package com.example.http.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * SHA256 证书工具
 *
 */
public class SHA256Util {

    /**
     * 通过HmacSHA256进行哈希
     *
     * @param stringToSign
     * @param appSecret
     * @return
     */

    public static String hashByHMacSHA256(String stringToSign, String appSecret) {

        String signature;

        try {

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");

            byte[] keyBytes = appSecret.getBytes("UTF-8");

            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            byte[] data = hmacSha256.doFinal(stringToSign.getBytes("UTF-8"));

            StringBuffer buffer = new StringBuffer();

            for (byte item : data) {

                buffer.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

            }

            signature = buffer.toString().toUpperCase();

        } catch (Exception e) {

            throw new RuntimeException("通过HmacSHA256进行哈希出现异常：" + e.getMessage());

        }

        return signature;

    }

}

