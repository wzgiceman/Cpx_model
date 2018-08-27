package com.base.library.retrofit_rx.Api;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.base.library.retrofit_rx.http.HttpManager;
import com.base.library.retrofit_rx.listener.HttpOnNextListener;
import com.base.library.retrofit_rx.listener.HttpOnNextSubListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseUniteApi extends BaseApi {
    private HttpManager manager;

    public BaseUniteApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        manager = new HttpManager(onNextListener, appCompatActivity);
    }

    public BaseUniteApi(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        manager = new HttpManager(onNextSubListener, appCompatActivity);
    }

    protected Retrofit getRetrofit() {
        return manager.getReTrofit(this);
    }


    protected void doHttpDeal(Retrofit retrofit) {
        manager.httpDeal(retrofit, this);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return null;
    }
}
