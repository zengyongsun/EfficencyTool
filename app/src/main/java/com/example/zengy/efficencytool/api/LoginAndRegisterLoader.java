package com.example.zengy.efficencytool.api;

import com.example.common.http.ObjectLoader;
import com.example.zengy.efficencytool.bean.BaseBean;
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
        RetrofitServiceManager.getInstance().create(LoginAndRegisterService.class);
    }

    public Observable<BaseBean> login(String username,String password){
        return mService.login(username,password).map(new Function<BaseBean, BaseBean>() {
            @Override
            public BaseBean apply(BaseBean baseBean) throws Exception {
                return baseBean;
            }
        });
    }

    public Observable<BaseBean> register(String username,String password,String repassword){
        return mService.register(username,password,repassword).map(new Function<BaseBean, BaseBean>() {
            @Override
            public BaseBean apply(BaseBean baseBean) throws Exception {
                return baseBean;
            }
        });
    }
}
