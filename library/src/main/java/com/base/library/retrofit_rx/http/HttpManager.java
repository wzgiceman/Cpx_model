package com.base.library.retrofit_rx.http;

import android.support.annotation.NonNull;

import com.base.library.retrofit_rx.Api.BaseApi;
import com.base.library.retrofit_rx.exception.RetryWhenNetworkException;
import com.base.library.retrofit_rx.http.func.ExceptionFunc;
import com.base.library.retrofit_rx.http.func.ResulteFunc;
import com.base.library.retrofit_rx.listener.HttpOnNextListener;
import com.base.library.retrofit_rx.subscribers.ProgressSubscriber;
import com.base.library.rxlifecycle.android.ActivityEvent;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {
    /*软引用對象*/
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<RxAppCompatActivity> appCompatActivity;

    public HttpManager(@NonNull HttpOnNextListener onNextListener, @NonNull RxAppCompatActivity appCompatActivity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager() {

    }


    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public Observable<String> doHttpDeal(final BaseApi basePar) {
        return httpDeal(basePar);
    }

    /**
     * RxRetrofit处理
     *
     * @param basePar
     */
    public Observable httpDeal(BaseApi basePar) {
        /*失败后的retry配置*/
        Observable observable = basePar.getObservable()
                /*失败后retry处理控制*/
                .retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                        basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*tokean过期统一处理*/
//                .flatMap(new TokeanFunc(basePar, retrofit))
                /*返回数据统一判断*/
                .onErrorResumeNext(new ExceptionFunc())
                .map(new ResulteFunc(basePar))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());


        /*数据String回调*/
        if (onNextListener != null && null != onNextListener.get() && null != appCompatActivity && null != appCompatActivity
                .get()) {
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
            observable.compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY)).observeOn(AndroidSchedulers
                    .mainThread()).subscribe
                    (subscriber);
        }

        return observable;
    }

}
