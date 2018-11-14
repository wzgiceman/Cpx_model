package com.base.library.rxRetrofit.api;

import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.http.converter.RetrofitStringConverterFactory;
import com.base.library.rxRetrofit.http.head.HeadInterceptor;
import com.base.library.rxRetrofit.http.head.HttpLoggingInterceptor;
import com.base.library.utils.AbLogUtil;
import com.base.library.utils.AbSharedUtil;
import com.base.library.utils.AbStrUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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
    /*固定基础url*/
    public transient static String BASE_URL = "http://api.onlinemuslim.net/";
    /*基础url*/
    private transient String baseUrl;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private transient String method = "";
    /*超时时间-默认10秒*/
    private transient int connectionTime = 15;
    /*有网情况下的本地缓存时间默xxx秒*/
    private transient int cookieNetWorkTime = 60 * 10;
    /*无网络的情况下本地缓存时间默认30天*/
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
    /*忽略结果判断*/
    private transient boolean ignoreJudge;
    /*retrofit控制器*/
    private Retrofit retrofit;

    private Disposable mDisposable;


    /**
     * 设置参数
     *
     * @return
     */
    public abstract Observable getObservable();


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
        return AbStrUtil.isEmpty(baseUrl) ? BASE_URL : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return getBaseUrl() + getMethod();
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


    public String getCacheUrl() {
        if (AbStrUtil.isEmpty(cacheUrl)) {
            return getUrl();
        }
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }


    public static String getConfig() {
        if (AbStrUtil.isEmpty(config)) {
            config = AbSharedUtil.getString(RxRetrofitApp.getApplication(), "token");
        }
        return config;
    }

    public static void setConfig(String config) {
        BaseApi.config = config;
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    public void setDisposable(Disposable mDisposable) {
        this.mDisposable = mDisposable;
    }

    /**
     * 获取Retrofit对象
     * 目的是为了保证每一个api公用一个Retrofit,和方便多接口嵌套使用
     *
     * @return
     */
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

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> AbLogUtil.d("RxRetrofit",
                "Retrofit====Message:" + message));

        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
