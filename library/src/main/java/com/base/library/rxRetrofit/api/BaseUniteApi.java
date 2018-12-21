package com.base.library.rxRetrofit.api;


import com.base.library.rxRetrofit.http.HttpManager;
import com.base.library.rxRetrofit.listener.HttpOnNextListener;
import com.base.project.base.activity.BaseActivity;

import io.reactivex.Observable;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseUniteApi extends BaseApi {
    private HttpManager manager;

    public BaseUniteApi(HttpOnNextListener onNextListener, BaseActivity appCompatActivity) {
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
