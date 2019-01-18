package com.base.library.rxRetrofit.listener;


import android.support.annotation.NonNull;

import com.base.library.rxRetrofit.exception.ApiException;


/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface HttpOnNextListener {
    /**
     * 成功后回调方法
     *
     * @param result
     * @param method
     */
    void onNext(@NonNull String result
            , @NonNull String method);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     *
     * @param e
     */
    void onError(@NonNull ApiException e, @NonNull String method);
}
