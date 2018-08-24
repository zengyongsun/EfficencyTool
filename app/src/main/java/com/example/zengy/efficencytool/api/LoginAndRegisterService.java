package com.example.zengy.efficencytool.api;

import com.example.zengy.efficencytool.bean.BaseBean;
import com.example.zengy.efficencytool.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2317:39
 * desc   :
 * version: 1.0
 */
public interface LoginAndRegisterService {

    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username,
                                @Field("password") String password);

    @POST("user/register")
    Observable<BaseBean> register(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("repassword") String repassword);

}
