package com.example.common.packaging;

import android.support.annotation.Nullable;

import com.example.common.config.CommonConfig;
import com.orhanobut.logger.Logger;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2018/05/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoggerUtil {

    public static void d(Object object) {
        if (CommonConfig.isDebug) {
            Logger.d(object);
        }
    }

    public static void i(String message, Object... args) {
        if (CommonConfig.isDebug) {
            Logger.i(message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (CommonConfig.isDebug) {
            Logger.e(message, args);
        }
    }

}
