package com.example.zengy.efficencytool;

import android.app.Application;

import com.example.common.CommonApplication;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2317:13
 * desc   :
 * version: 1.0
 */
public class AppApplication extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }
}
