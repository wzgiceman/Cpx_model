package com.base.library.retrofit_rx.Api;


import com.base.library.retrofit_rx.http.HttpManager;
import com.base.library.retrofit_rx.listener.HttpOnNextListener;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import io.reactivex.Observable;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseUniteApi extends BaseApi {
    private HttpManager manager;

    public BaseUniteApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        manager = new HttpManager(onNextListener, appCompatActivity);
    }


    protected void doHttpDeal() {
        manager.httpDeal(this);
    }

    @Override
    public Observable getObservable() {
        return null;
    }
}
