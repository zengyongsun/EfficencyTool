package com.example.zengy.efficencytool;


import com.example.common.CommonApplication;
import com.example.common.http.ApiManager;
import com.example.zengy.efficencytool.api.Api;


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
        init();
    }
    private void init(){
        ApiManager.init(Api.base_url);
    }
}
