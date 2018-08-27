package com.example.common.http;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2714:26
 * desc   :
 * version: 1.0
 */
public interface ApiCallback<T> {

    void onNext(T t);

    void onError(Throwable e);

    void onCompleted();
}
