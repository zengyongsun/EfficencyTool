package com.example.zengy.efficencytool.http;


import com.example.zengy.efficencytool.api.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2018/05/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RetrofitServiceManager {

    //超时时间 5s
    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private final Retrofit mRetrofit;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        okHttpClient.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        okHttpClient.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        // 添加公共参数拦截器
//        BasicParamsInterceptor commonInterceptor = new BasicParamsInterceptor.Builder()
//            .addParam("paltform", "android")
//            .addParam("userToken", "1234343434dfdfd3434")
//            .addParam("userId", "123445")
//            .build();
//        okHttpClient.addInterceptor(commonInterceptor);

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(Api.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
