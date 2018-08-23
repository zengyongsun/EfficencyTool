package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jacky.Cai
 * @version v1.0
 * @Package com.dayhr.cscec.pmc.common.utils
 * @Description:
 * @date 16/7/28 下午2:58
 */
public class MD5 {

    /**
     * Used building output as Hex
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g'};

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            // resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
            resultSb.append(byteToNumString(b[i]));// 使用本函数则返回加密结果的10进制数字字串，即全数字形式
        }
        return resultSb.toString();
    }

    private static String byteToNumString(byte b) {

        int _b = b;
        if (_b < 0) {
            _b = 256 + _b;
        }

        return String.valueOf(_b);
    }

    public static String MD5Encode(String origin) {
        String resultString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(origin.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }


    public static String MD5(String origin) {
        String resultString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(origin.getBytes());
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception ex) {

        }

        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("10000000"));
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text, String charset) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes(charset)); // 注意改接口是按照utf-8编码形式加密

        } catch (UnsupportedEncodingException e) {

            throw new IllegalStateException("System doesn't support your  EncodingException.");

        }

        byte[] bytes = msgDigest.digest();

        String md5Str = new String(encodeHex(bytes));

        return md5Str;
    }

    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }
}
