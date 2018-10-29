package com.base.library.retrofit_rx.http.func;

import com.base.library.retrofit_rx.exception.FactoryException;


import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * 异常处理
 * Created by WZG on 2017/3/23.
 */

public class ExceptionFunc implements Function<Throwable, Observable> {

    @Override
    public Observable apply(Throwable throwable) {
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}
