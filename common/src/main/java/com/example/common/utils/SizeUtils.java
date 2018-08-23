package com.example.common.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * SizeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-15
 */
public class SizeUtils {

    /**
     * gb to byte *
     */
    public static final long GB_2_BYTE = 1073741824;
    /**
     * mb to byte *
     */
    public static final long MB_2_BYTE = 1048576;
    /**
     * kb to byte *
     */
    public static final long KB_2_BYTE = 1024;

    private SizeUtils() {
        throw new AssertionError();
    }

    public static int getPixByDip(int dip, DisplayMetrics metrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }
}
