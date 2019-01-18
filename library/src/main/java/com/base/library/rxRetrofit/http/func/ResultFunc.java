package com.base.library.rxRetrofit.http.func;


import com.alibaba.fastjson.JSONObject;
import com.base.library.R;
import com.base.library.rxRetrofit.Api.BaseApi;
import com.base.library.rxRetrofit.Api.resulte.BaseResult;
import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.exception.HttpTimeException;
import com.base.library.rxRetrofit.http.cookie.CookieResult;
import com.base.library.rxRetrofit.utils.CookieDbUtil;
import com.base.library.utils.utilcode.util.StringUtils;

import io.reactivex.functions.Function;

/**
 * 服务器返回数据判断
 * Created by WZG on 2017/3/23.
 */
public class ResultFunc implements Function<Object, String> {
    private BaseApi basePar;

    public ResultFunc(BaseApi basePar) {
        this.basePar = basePar;
    }

    @Override
    public String apply(Object o) {
        if (o == null || StringUtils.isEmpty(o.toString())) {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_ERROR, RxRetrofitApp.getApplication().getString(R.string
                    .service_error));
        }

        if (basePar.isIgnoreJudge()) {
            return o.toString();
        }

        /*数据回调格式统一判断*/
        BaseResult result = JSONObject.parseObject(o.toString(), BaseResult.class);
        if (result.getCode() == 0) {
            if (!basePar.isCache()) return StringUtils.isEmpty(result.getData()) ? "" : result.getData();
            if (StringUtils.isEmpty(result.getData())) return "";
            /*缓存处理*/
            CookieResult cookieRes = CookieDbUtil.getInstance().queryCookieBy(basePar.getCacheUrl());
            long time = System.currentTimeMillis();
            if (cookieRes == null) {
                cookieRes = new CookieResult(basePar.getCacheUrl(), result.getData(), time);
                CookieDbUtil.getInstance().saveCookie(cookieRes);
            } else {
                cookieRes.setResult(result.getData());
                cookieRes.setTime(time);
                CookieDbUtil.getInstance().updateCookie(cookieRes);
            }
            return result.getData();
        } else {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_POST_ERROR, StringUtils.isEmpty(result.getMsg())
                    ? RxRetrofitApp.getApplication().getString(R.string.service_error) : result.getMsg());
        }
    }
}