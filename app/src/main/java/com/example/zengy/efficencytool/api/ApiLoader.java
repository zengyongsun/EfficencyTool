package com.example.zengy.efficencytool.api;

import com.example.common.http.ApiCallback;
import com.example.common.http.ApiManager;
import com.example.common.http.ApiObserver;
import com.example.zengy.efficencytool.bean.LoginBean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2715:14
 * desc   :
 * version: 1.0
 */
public class ApiLoader {

    private static ApiService apiService;

    private ApiLoader() {
        throw new AssertionError();
    }

    private static ApiService getApiService() {
        if (apiService == null) {
            apiService = ApiManager.getInstance().getApiService(ApiService.class);
        }
        return apiService;
    }

    public static void reqLogin(String username, String password, ApiCallback<LoginBean> callback) {
        ApiObserver.subscribe(getApiService().login(username, password), callback);
    }

}
