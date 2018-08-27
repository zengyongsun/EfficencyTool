package com.example.zengy.efficencytool.api;

import com.example.common.http.old.ObjectLoader;
import com.example.zengy.efficencytool.bean.BaseBean;
import com.example.zengy.efficencytool.bean.LoginBean;
import com.example.zengy.efficencytool.http.RetrofitServiceManager;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2317:38
 * desc   :
 * version: 1.0
 */
public class LoginAndRegisterLoader extends ObjectLoader {

    private LoginAndRegisterService mService;

    public LoginAndRegisterLoader() {
       mService =  RetrofitServiceManager.getInstance().create(LoginAndRegisterService.class);
    }

    public Observable<LoginBean> login(String username, String password){
        return observe(mService.login(username,password)).map(new Function<LoginBean, LoginBean>() {
            @Override
            public LoginBean apply(LoginBean loginBean) throws Exception {
                return loginBean;
            }
        });
    }

    public Observable<BaseBean> register(String username,String password,String repassword){
        return observe(mService.register(username,password,repassword)).map(new Function<BaseBean, BaseBean>() {
            @Override
            public BaseBean apply(BaseBean baseBean) throws Exception {
                return baseBean;
            }
        });
    }
}
