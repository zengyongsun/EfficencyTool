package com.example.common.http;


import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2714:31
 * desc   :
 * version: 1.0
 */
public class ApiObserver<T> implements Observer<T> {

    private static final String TAG = "Zeyo";

    private ApiCallback<T> mCallback;

    private String mTag = TAG;

    private boolean isLog = true;

    public ApiObserver(ApiCallback<T> callback) {
        this.mCallback = callback;
    }

    public ApiObserver(ApiCallback<T> callback, String tag) {
        this(callback, tag, true);
    }

    public ApiObserver(ApiCallback<T> callback, String tag, boolean isLog) {
        this.mCallback = callback;
        this.mTag = tag;
        this.isLog = isLog;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        if (isLog) {
            Logger.e(mTag, "onError");
        }
        if (mCallback != null) {
            mCallback.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if (isLog) {
            Logger.e(mTag, "Response:" + t);
        }
        if (mCallback != null) {
            mCallback.onNext(t);
        }
    }

    @Override
    public void onComplete() {
        if (mCallback != null) {
            mCallback.onCompleted();
        }
    }

    public static <T> ApiObserver<T> getApiObserver(ApiCallback<T> callback) {
        return new ApiObserver<>(callback);
    }

    public static <T> void subscribe(Observable<T> observable, ApiCallback<T> callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getApiObserver(callback));
    }

}
