package com.base.library.rxRetrofit.http;

import android.support.annotation.NonNull;

import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.rxRetrofit.exception.RetryWhenNetworkException;
import com.base.library.rxRetrofit.http.func.ExceptionFunc;
import com.base.library.rxRetrofit.http.func.ResultFunc;
import com.base.library.rxRetrofit.listener.HttpOnNextListener;
import com.base.library.rxRetrofit.subscribers.ProgressSubscriber;
import com.base.library.rxlifecycle.android.ActivityEvent;
import com.base.library.rxlifecycle.android.FragmentEvent;
import com.base.project.base.activity.BaseActivity;
import com.base.project.base.fragment.BaseFragment;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
@SuppressWarnings("unchecked")
public class HttpManager {
    /*软引用對象*/
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<BaseActivity> mActivity;
    private SoftReference<BaseFragment> mFragment;

    public HttpManager(@NonNull HttpOnNextListener onNextListener, @NonNull BaseActivity activity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.mActivity = new SoftReference(activity);
    }

    public HttpManager(@NonNull HttpOnNextListener onNextListener, @NonNull BaseFragment fragment) {
        this.onNextListener = new SoftReference(onNextListener);
        this.mFragment = new SoftReference<>(fragment);
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
     * @param baseApi api
     */
    public Observable httpDeal(BaseApi baseApi) {
        /*失败后的retry配置*/
        Observable observable = baseApi.getObservable()
                /*失败后retry处理控制*/
                .retryWhen(new RetryWhenNetworkException(baseApi.getRetryCount(),
                        baseApi.getRetryDelay(), baseApi.getRetryIncreaseDelay()))
                /*tokean过期统一处理*/
//                .flatMap(new TokenFunc(baseApi, retrofit))
                /*返回数据统一判断*/
                .onErrorResumeNext(new ExceptionFunc())
                .map(new ResultFunc(baseApi))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());


        /*数据String回调*/
        if (onNextListener != null && null != onNextListener.get() && null != mActivity && null != mActivity
                .get()) {
            ProgressSubscriber subscriber = new ProgressSubscriber();
            subscriber.setAtProgSub(baseApi, onNextListener, mActivity);
            observable.compose(mActivity.get().bindUntilEvent(ActivityEvent.DESTROY)).observeOn(AndroidSchedulers
                    .mainThread()).subscribe(subscriber);
        }
        if (onNextListener != null && null != onNextListener.get() && null != mFragment && null != mFragment.get()) {
            ProgressSubscriber subscriber = new ProgressSubscriber();
            subscriber.setFgProgSub(baseApi, onNextListener,mFragment);
            observable.compose(mFragment.get().bindUntilEvent(FragmentEvent.DESTROY_VIEW)).observeOn(AndroidSchedulers
                    .mainThread()).subscribe(subscriber);
        }
        return observable;
    }

}
