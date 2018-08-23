package com.example.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class SDCardUtils {

    /**
     * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
     *
     * @param context    上下文信息
     * @param uniqueName 目录名字
     * @return 返回目录名字
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // 检查是否安装或存储媒体是内置的,如果是这样,试着使用
        // 外部缓存 目录
        // 否则使用内部缓存 目录
        if (context == null || StringUtils.isEmpty(uniqueName)) {
            return null;
        }
        String cachePath = context.getCacheDir().getPath();
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable()) {
            if (getExternalCacheDir(context) != null) {
                cachePath = getExternalCacheDir(context).getPath();
            }
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 判断是否有sdcard
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || isExternalStorageRemovable();
    }

    /**
     * 检查如果外部存储器是内置的或是可移动的。
     *
     * @return 如果外部存储是可移动的(就像一个SD卡)返回为 true,否则false。
     */
    public static boolean isExternalStorageRemovable() {
        if (AndroidVersionCheckUtils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * 获得外部应用程序缓存目录
     *
     * @param context 上下文信息
     * @return 外部缓存目录
     */
    public static File getExternalCacheDir(Context context) {
        if (AndroidVersionCheckUtils.hasFroyo()) {
            return context.getExternalCacheDir();
        }
        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }
}
