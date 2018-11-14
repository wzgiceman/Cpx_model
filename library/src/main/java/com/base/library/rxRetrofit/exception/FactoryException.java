package com.base.library.rxRetrofit.exception;


import com.base.library.R;
import com.base.library.rxRetrofit.RxRetrofitApp;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * 异常处理工厂
 * 主要是解析异常，输出自定义ApiException
 * Created by WZG on 2016/12/12.
 */

public class FactoryException {
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*fastjson错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x4;
    /*无法解析该域名*/
    public static final int UNKNOWN_HOST_ERROR = 0x6;

    private static final String HTTP_EXCEPTION_MSG = RxRetrofitApp.getApplication().getString(R.string.service_error);
    private static final String CONNECT_EXCEPTION_MSG = RxRetrofitApp.getApplication().getString(R.string.service_error);
    private static final String JSON_EXCEPTION_MSG = RxRetrofitApp.getApplication().getString(R.string.json_error);
    private static final String UNKNOWN_HOST_EXCEPTION_MSG = RxRetrofitApp.getApplication().getString(R.string.url_error);

    /**
     * 解析异常
     *
     * @param e
     * @return
     */
    public static ApiException analysisException(Throwable e) {
        ApiException apiException = new ApiException(e);
        if (e instanceof HttpException) {
            /*网络异常*/
            apiException.setCode(HTTP_ERROR);
            apiException.setDisplayMessage(HTTP_EXCEPTION_MSG);
        } else if (e instanceof HttpTimeException) {
            /*自定义运行时异常*/
            HttpTimeException exception = (HttpTimeException) e;
            apiException.setCode(exception.getCode());
            apiException.setDisplayMessage(exception.getMessage());
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            /*链接异常*/
            apiException.setCode(HTTP_ERROR);
            apiException.setDisplayMessage(CONNECT_EXCEPTION_MSG);
        } else if (e instanceof JSONException || e instanceof ParseException) {
            apiException.setCode(JSON_ERROR);
            apiException.setDisplayMessage(JSON_EXCEPTION_MSG);
        } else if (e instanceof UnknownHostException) {
            /*无法解析该域名异常*/
            apiException.setCode(UNKNOWN_HOST_ERROR);
            apiException.setDisplayMessage(UNKNOWN_HOST_EXCEPTION_MSG);
        } else {
            /*未知异常*/
            apiException.setCode(UNKNOWN_ERROR);
            apiException.setDisplayMessage(HTTP_EXCEPTION_MSG);
        }
        return apiException;
    }

}
