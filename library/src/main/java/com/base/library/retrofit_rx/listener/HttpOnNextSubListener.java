package com.base.library.retrofit_rx.listener;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * 回调ober对象
 * Created by WZG on 2016/12/12.
 */

public interface HttpOnNextSubListener {

    /**
     * ober成功回调
     *
     * @param observable
     * @param method
     */
    void onNext(@NonNull Observable<String> observable, @NonNull String method);
}
