package com.base.library.retrofit_rx.http.head;

import com.alibaba.fastjson.JSONObject;
import com.base.library.R;
import com.base.library.retrofit_rx.Api.BaseApi;
import com.base.library.retrofit_rx.Api.resulte.BaseResulte;
import com.base.library.retrofit_rx.RxRetrofitApp;
import com.base.library.retrofit_rx.exception.HttpTimeException;
import com.base.library.utils.AbStrUtil;
import com.base.router.ActivityRouter;
import com.base.router.RouterList;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Describe:统一的head拦截器处理
 * <p>
 * Created by zhigang wei
 * on 2018/1/10
 * <p>
 * Company :Sichuan Ziyan
 */
public class HeadInterceptor implements Interceptor {
    private BaseApi baseApi;

    public HeadInterceptor(BaseApi baseApi) {
        this.baseApi = baseApi;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        /*设置方法*/
        String method = original.url().url().toString().replace(baseApi.getBaseUrl(), "");
        baseApi.setMethod(method);

        /*设置head信息-tokean*/
        String[] config = baseApi.getConfig().split("&");
        Request request = original.newBuilder()
                .header("language", config[0])
                .header("Authorization", "Token " + (config.length > 1 && !AbStrUtil.isEmpty(config[1]) ? config[1] : ""))
                .header("version", RxRetrofitApp.getApplication().getString(R.string.versionWeb))
                .method(original.method(), original.body())
                .build();

        /*错误信息*/
        Response response = chain.proceed(request);
        if (400 == response.code() || 401 == response.code() || 403 == response.code()) {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            BaseResulte resulte = JSONObject.parseObject(buffer.clone().readString(Charset.forName("UTF-8")), BaseResulte.class);
            /*tokean过期*/
            if (-2 == resulte.getCode()) {
                ActivityRouter.startActivityForName(RxRetrofitApp.getApplication(), RouterList.LOGINT_ACTIVITY);
            }
            throw new HttpTimeException(HttpTimeException.HTTP_IGNORE_ERROR, resulte.getMsg());
        }
        return response;
    }
}
