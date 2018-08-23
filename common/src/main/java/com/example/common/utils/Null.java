package com.example.common.utils;

/**
 * @author Kim.H
 * @version v1.0
 * @Package com.lifeix.androidbasecore.utils
 * @Description:
 * @date 2015-08-04 13:37
 */
public class Null {
    public static final <V> boolean isNull(V v) {
        return null == v;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
