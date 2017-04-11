package com.tinnotech.basic;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by LZM on 2017/2/9.
 */

public class TntUtil {
    public static String encodeHex(byte[] bytes) {
        char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        final int nBytes = bytes.length;
        StringBuffer result = new StringBuffer(2 * nBytes);
        for (int i = 0; i < nBytes; i++) {
            result.append(HEX[(0xF0 & bytes[i]) >>> 4]);
            result.append(HEX[(0x0F & bytes[i])]);
        }

        return result.toString();
    }

    public static byte[] toMD5(String datas, String charset) throws UnsupportedEncodingException {
        return toMD5(datas.getBytes(charset));
    }

    public static String toMD5Hex(String datas) {
        String result = null;
        try {
            result = toMD5Hex(datas, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toMD5Hex(String datas, String charset) throws UnsupportedEncodingException {
        return toMD5Hex(datas.getBytes(charset));
    }

    public static byte[] toMD5(byte[] datas) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
            return md.digest(datas);
        } catch (NoSuchAlgorithmException e) {
            // throw new BCMSException();
            return null;
        }
    }

    public static String toMD5Hex(byte[] datas) {
        return toHexString(toMD5(datas));
    }

    public static String toHexString(byte[] datas) {
        StringBuilder sb = new StringBuilder();
        if (datas == null)
            return sb.toString();
        for (int i = 0; i < datas.length; i++) {
            String hex = Integer.toHexString(datas[i] & 0xFF);
            if (hex.length() <= 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、78中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
}
