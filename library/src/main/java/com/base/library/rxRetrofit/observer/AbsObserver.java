package com.base.library.rxRetrofit.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 带取消取消订阅的Observer
 * @author xuechao
 * @date 2018/10/26 下午3:37
 * @copyright cpx
 */

public abstract class AbsObserver<T> implements Observer<T> {

    private Disposable disposable;

    public AbsObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onComplete() {
        unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        unsubscribe();
    }

    /**
     * 取消订阅
     */
    public void unsubscribe(){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
