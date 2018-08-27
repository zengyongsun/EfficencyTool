package com.example.common.http;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2715:28
 * desc   :
 * version: 1.0
 */
public abstract class SimpleCallback<T> implements ApiCallback<T>{

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCompleted() {

    }
}
