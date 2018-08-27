package com.example.common.http;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2714:29
 * desc   :
 * version: 1.0
 */
public class ApiManager {

    private static final String TAG = "Zeyo";
    private ApiHttp mApiHttp;

    private static String mBaseUrl;
    private static int mTimeout = ApiHttp.DEFAULT_TIME_OUT;
    private static ApiManager mInstance;

    public static ApiManager getInstance() {
        if (mInstance == null) {
            synchronized (ApiManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiManager();
                }
            }
        }
        return mInstance;
    }

    public static void init(String baseUrl) {
        init(baseUrl, ApiHttp.DEFAULT_TIME_OUT);
    }

    public static void init(String baseUrl, int timeout) {
        mBaseUrl = baseUrl;
        mTimeout = timeout;

    }

    private ApiManager() {
        mApiHttp = new ApiHttp(mBaseUrl, mTimeout);
    }

    public ApiHttp getApiHttp() {
        return mApiHttp;
    }

    public void setApiHttp(ApiHttp apiHttp) {
        this.mApiHttp = apiHttp;
    }

    public <T> T getApiService(Class<T> service) {
        return mApiHttp.getRetrofit().create(service);
    }

}
