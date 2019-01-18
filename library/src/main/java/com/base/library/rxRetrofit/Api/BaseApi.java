package com.base.library.rxRetrofit.Api;

import android.util.Log;

import com.alibaba.fastjson.annotation.JSONField;
import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.http.converter.RetrofitStringConverterFactory;
import com.base.library.rxRetrofit.http.cookie.CookieResult;
import com.base.library.rxRetrofit.http.func.ResultFunc;
import com.base.library.rxRetrofit.http.head.HeadInterceptor;
import com.base.library.rxRetrofit.http.head.HttpLoggingInterceptor;
import com.base.library.rxRetrofit.httpList.httpList.ListException;
import com.base.library.rxRetrofit.utils.AppUtil;
import com.base.library.rxRetrofit.utils.CookieDbUtil;
import com.base.library.utils.utilcode.util.StringUtils;
import com.base.shareData.ShareSparse;
import com.base.shareData.user.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Describe:请求数据统一封装类
 * <p>
 * Created by zhigang wei
 * on 2017/8/29.
 * <p>
 * Company :Sichuan Ziyan
 */
public abstract class BaseApi {
    /*是否能取消加载框*/
    private transient boolean cancel = true;
    /*是否显示加载框*/
    private transient boolean showProgress = true;
    /*是否需要缓存处理*/
    protected transient boolean cache = false;
    /*是否是刷新模式：true标识不论是否有缓存处理都及时处理最新数据，并且根据cache设置控制数据缓存更新*/
    private transient boolean refresh = false;
    /*固定基础url*/
    public transient static String BASE_URL = "http://api.onlinemuslim.net/";
    /*基础url*/
    private transient String baseUrl;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private transient String method = "";
    /*超时时间-默认10秒*/
    private transient int connectionTime = 10;
    /*有网情况下的本地缓存时间默xxx秒*/
    private transient int cookieNetWorkTime = 60 * 10;
    /*无网络的情况下本地缓存时间默认一年*/
    private transient int cookieNoNetWorkTime = 24 * 60 * 60 * 30 * 12;
    /* retry次数*/
    private transient int retryCount = 1;
    /*retry延迟*/
    private transient long retryDelay = 100;
    /*retry叠加延迟*/
    private transient long retryIncreaseDelay = 100;
    /*缓存url*/
    private transient String cacheUrl;
    /*常用服务器校验字段*/
    private transient static String config;
    /*忽略结果判断-特殊接口不能按照规范返回时使用*/
    private transient boolean ignoreJudge;
    /*retrofit控制器*/
    private Retrofit retrofit;
    /*有缓存下时间过期是否先显示缓存数据在拉取最新的更新界面*/
    private transient boolean advanceLoadCache;
    /*是否是本地缓存数据回传前台*/
    private transient boolean cacheResulte;

    /**
     * 设置参数
     *
     * @return
     */
    @JSONField(serialize = false)
    public abstract Observable getObservable();

    @JSONField(serialize = false)
    public Observable getListObservable() {
        String cacheContent = getCacheContent(false);
        if (!StringUtils.isEmpty(cacheContent)) {
            return Observable.just(cacheContent);
        }
        return getObservable().map(new ResultFunc(this)).onErrorResumeNext(new ListException(this));
    }

    @JSONField(serialize = false)
    public String getUrl() {
        return getBaseUrl() + getMethod();
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public String getBaseUrl() {
        return StringUtils.isEmpty(baseUrl) ? BASE_URL : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    public boolean isIgnoreJudge() {
        return ignoreJudge;
    }

    public void setIgnoreJudge(boolean ignoreJudge) {
        this.ignoreJudge = ignoreJudge;
    }

    public boolean isAdvanceLoadCache() {
        return advanceLoadCache;
    }

    public void setAdvanceLoadCache(boolean advanceLoadCache) {
        this.advanceLoadCache = advanceLoadCache;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public static void setConfig(String config) {
        BaseApi.config = config;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public boolean isCacheResulte() {
        return cacheResulte;
    }

    public void setCacheResulte(boolean cacheResulte) {
        this.cacheResulte = cacheResulte;
    }

    @JSONField(serialize = false)
    public String getCacheUrl() {
        if (StringUtils.isEmpty(cacheUrl)) {
            return getUrl();
        }
        return cacheUrl;
    }

    @JSONField(serialize = false)
    public static String getConfig() {
        if (StringUtils.isEmpty(config)) {
            config = ((User) ShareSparse.INSTANCE.getValueBy(ShareSparse.USER_CLS)).getToken();
        }
        return config;
    }


    /**
     * 获取Retrofit对象
     * 目的是为了保证每一个api公用一个Retrofit,和方便多接口嵌套使用
     *
     * @return
     */
    @JSONField(serialize = false)
    public Retrofit getRetrofit() {
        if (null != retrofit) {
            return retrofit;
        }
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(getConnectionTime(), TimeUnit.SECONDS);
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }
        builder.addInterceptor(new HeadInterceptor(this));

        /*创建retrofit对象*/
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(RetrofitStringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
        return retrofit;
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    @JSONField(serialize = false)
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("RxRetrofit",
                "Retrofit====Message:" + message));
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


    /**
     * 获取缓存
     * 暂用list api处理中
     *
     * @param ignoreTime 是否忽略时间校验
     * @return
     */
    @JSONField(serialize = false)
    public String getCacheContent(boolean ignoreTime) {
        if (!cache) return null;
        if (refresh && !ignoreTime) return null;
        /*获取缓存数据*/
        CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(getCacheUrl());
        int duration = AppUtil.isNetworkAvailable(RxRetrofitApp.getApplication()) ? getCookieNetWorkTime()
                : getCookieNoNetWorkTime();
        if (null == cookieResult) return null;
        if (ignoreTime) {
            return cookieResult.getResult();
        }
        if (!ignoreTime && !refresh && (System.currentTimeMillis() - cookieResult.getTime()) / 1000 < duration) {
            return cookieResult.getResult();
        }
        return null;
    }
}
