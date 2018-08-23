package com.example.common;

import android.app.Application;

import com.example.common.packaging.GlideLoader;
import com.orhanobut.logger.AndroidLogAdapter;
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
public class CommonApplication extends Application {

    public GlideLoader mGlideLoader;

    /**
     * 初始化Glide
     */
    public void initGlideLoader() {
        mGlideLoader = GlideLoader.getInstance();
    }

    public void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}
