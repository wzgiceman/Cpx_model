package com.base.library.rxRetrofit.http.func;

import com.alibaba.fastjson.JSONObject;
import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.rxRetrofit.api.result.BaseResult;
import com.base.library.rxRetrofit.exception.HttpTimeException;
import com.base.library.utils.AbStrUtil;


import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.POST;

/**
 * Describe:toekan失效统一处理
 * <p>
 * Created by zhigang wei
 * on 2018/1/12.
 * <p>
 */
public class TokenFunc implements Function<Object, Observable> {
    private BaseApi baseApi;
    private Retrofit retrofit;

    public TokenFunc(BaseApi baseApi, Retrofit retrofit) {
        this.baseApi = baseApi;
        this.retrofit = retrofit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Observable apply(Object o) {
        /*这里简单的以tokean为空来判断，实际运用中需要通过服务器错误标示来判断启用tokean机制*/
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_ERROR, "The network server runs down");
        }
        /*数据回调格式统一判断*/
        BaseResult result = JSONObject.parseObject(o.toString(), BaseResult.class);

        if (result.getCode() == 1) {
            return retrofit.create(TokenServiceApi.class).token()
                    .flatMap(new Function() {
                        @Override
                        public Observable apply(Object o) {
                            if (!AbStrUtil.isEmpty(o.toString())) {
                                BaseResult resulte = JSONObject.parseObject(o.toString(), BaseResult.class);
                                if (0 == resulte.getCode()) {
                                    /*解析出tokean传入到当前请求的api接口类中*/
                                    BaseApi.setConfig("");
                                } else {
                                    throw new HttpTimeException(HttpTimeException.HTTP_TOKEN_ERROR, "The network server runs down");
                                }
                                /*继续当前接口请求*/
                                return baseApi.getObservable();
                            }
                            throw new HttpTimeException(HttpTimeException.HTTP_TOKEN_ERROR, "The network server runs down");
                        }
                    });
        } else {
            return Observable.just(o);
        }
    }


    public interface TokenServiceApi {
        /**
         * 获取最新tokean
         *
         * @return
         */
        @POST("yf/medical/app/user/getRePatientToken")
        Observable<String> token();
    }
}
