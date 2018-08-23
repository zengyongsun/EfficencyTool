package com.example.common.utils;

import android.os.Build;

/**
 * @author Kim.H
 * @version v1.0
 * @Package com.lifeix.androidbasecore.utils
 * @Description:
 * @date 2015-08-05 9:51
 */
public class BuildVersionUtil {

    /**
     * 判断系统版本是否大于version
     *
     * @param version      指定版本
     * @param includeEqual 是否包含等于
     * @return
     */
    public static final boolean isAboveTargetVersion(int version, boolean includeEqual) {
        if (includeEqual) {
            return Build.VERSION.SDK_INT >= version;
        } else {
            return Build.VERSION.SDK_INT > version;
        }
    }
}
