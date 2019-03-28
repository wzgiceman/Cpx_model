package com.base.library.rxRetrofit.httpList.httpList;


import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.utils.utilcode.util.StringUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 异常的处理方式
 * 如果有缓存返回处理
 * Created by WZG on 2016/10/17.
 */
public class ListException implements Function<Throwable, Observable> {
    private BaseApi api;

    public ListException(com.base.library.rxRetrofit.api.BaseApi api) {
        this.api = api;
    }


    @Override
    public Observable apply(Throwable throwable) {
        String cacheContent = api.getCacheContent(true);
        if (!StringUtils.isEmpty(cacheContent)) {
            return Observable.just(cacheContent);
        }
        return Observable.error(throwable);
    }

}


