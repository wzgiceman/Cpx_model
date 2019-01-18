package com.base.library.rxRetrofit.http.func;

import com.alibaba.fastjson.JSONObject;
import com.base.library.rxRetrofit.Api.BaseApi;
import com.base.library.rxRetrofit.Api.resulte.BaseResult;
import com.base.library.rxRetrofit.exception.HttpTimeException;

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
public class TokeanFunc implements Function<Object, Observable> {
    private BaseApi basePar;
    private Retrofit retrofit;

    public TokeanFunc(BaseApi basePar, Retrofit retrofit) {
        this.basePar = basePar;
        this.retrofit = retrofit;
    }

    @Override
    public Observable apply(Object o) throws Exception {
        /*这里简单的以tokean为空来判断，实际运用中需要通过服务器错误标示来判断启用tokean机制*/
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_ERROR, "The network server runs down");
        }
        /*数据回调格式统一判断*/
        BaseResult result = JSONObject.parseObject(o.toString(), BaseResult.class);

//        if (result.getCode() == 1) {
//            return retrofit.create(TokeanServiceApi.class).tokean()
////                    .flatMap(new Func1() {
////                        @Override
////                        public Observable call(Object o) {
////                            if (!AbStrUtil.isEmpty(o.toString())) {
////                                BaseResulte result = JSONObject.parseObject(o.toString(), BaseResulte.class);
////                                if (0 == result.getCode()) {
////                                    /*解析出tokean传入到当前请求的api接口类中*/
////                                    basePar.setConfig("");
////                                } else {
////                                    throw new HttpTimeException(HttpTimeException.HTTP_TOKEAN_ERROR, "The network server runs down");
////                                }
////                                /*继续当前接口请求*/
////                                return basePar.getObservable(retrofit);
////                            }
////                            throw new HttpTimeException(HttpTimeException.HTTP_TOKEAN_ERROR, "The network server runs down");
////                        }
////                    });
//        } else {
        return Observable.just(o);
//        }
    }


    public interface TokeanServiceApi {
        /**
         * 获取最新tokean
         *
         * @return
         */
        @POST("yf/medical/app/user/getRePatientToken")
        Observable<String> tokean();
    }
}
