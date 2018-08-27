package com.example.common.http.old;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    @author : https://www.jianshu.com/p/811ba49d0748
 *     time   : 2018/05/07
 *     desc   : 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 *     version: 1.0
 * </pre>
 */
public class ObjectLoader {

    /**
     * @param observable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

}
