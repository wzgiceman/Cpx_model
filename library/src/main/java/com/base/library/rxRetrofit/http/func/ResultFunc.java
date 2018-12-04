package com.base.library.rxRetrofit.http.func;


import com.alibaba.fastjson.JSONObject;
import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.rxRetrofit.api.result.BaseResult;
import com.base.library.rxRetrofit.exception.HttpTimeException;
import com.base.library.rxRetrofit.http.cookie.CookieResult;
import com.base.library.rxRetrofit.downlaod.utils.CookieDbUtil;
import com.base.library.utils.AbStrUtil;

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
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_ERROR, "error service");
        }

        if (basePar.isIgnoreJudge()) {
            return o.toString();
        }

        /*数据回调格式统一判断*/
        BaseResult result = JSONObject.parseObject(o.toString(), BaseResult.class);
        if (result.getCode() == 0) {
            if (AbStrUtil.isEmpty(result.getData())) return "";
            /*缓存处理*/
            CookieResult cookieRes = CookieDbUtil.getInstance().queryCookieBy(basePar.getCacheUrl());
            long time = System.currentTimeMillis();
            if (cookieRes == null && basePar.isCache()) {
                cookieRes = new CookieResult(basePar.getCacheUrl(), result.getData(), time);
                CookieDbUtil.getInstance().saveCookie(cookieRes);
            }
            if (cookieRes != null) {
                cookieRes.setResult(result.getData());
                cookieRes.setTime(time);
                CookieDbUtil.getInstance().updateCookie(cookieRes);
            }

            return result.getData();
        } else {
            throw new HttpTimeException(HttpTimeException.CACHE_HTTP_POST_ERROR, AbStrUtil.isEmpty(result.getMsg()) ? "error " +
                    "data" : result.getMsg());
        }

    }
}
