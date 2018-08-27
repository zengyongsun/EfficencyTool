package com.example.zengy.efficencytool.api;

import com.example.zengy.efficencytool.bean.BaseBean;
import com.example.zengy.efficencytool.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2715:14
 * desc   :
 * version: 1.0
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseBean> register(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("repassword") String repassword);
}
