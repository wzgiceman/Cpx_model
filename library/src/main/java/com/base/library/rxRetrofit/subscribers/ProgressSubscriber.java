package com.base.library.rxRetrofit.subscribers;


import android.app.ProgressDialog;
import android.content.Context;

import com.base.library.R;
import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.exception.ApiException;
import com.base.library.rxRetrofit.exception.HttpTimeException;
import com.base.library.rxRetrofit.http.cookie.CookieResult;
import com.base.library.rxRetrofit.listener.HttpOnNextListener;
import com.base.library.rxRetrofit.utils.AppUtil;
import com.base.library.rxRetrofit.utils.CookieDbUtil;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;
import com.base.library.utils.AbLogUtil;
import com.base.library.utils.AbStrUtil;


import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressSubscriber<T> implements Observer<T> {
    /*回调接口*/
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /* 软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;
    /*缓存数据的的文件夹名称*/
    private final String CACHE_DATA_DIR_NAME = "gson/";
    /*json 后缀*/
    private final String JSON_SUFFIX = ".json";

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api, SoftReference<HttpOnNextListener> listenerSoftReference,
                              SoftReference<RxAppCompatActivity> mActivity) {
        this.api = api;
        this.mSubscriberOnNextListener = listenerSoftReference;
        this.mActivity = mActivity;
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        if (api.isCache()) {
            /*获取缓存数据*/
            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
            int duration = AppUtil.isNetworkAvailable(RxRetrofitApp.getApplication()) ? api.getCookieNetWorkTime() : api
                    .getCookieNoNetWorkTime();
            if (null != cookieResult && (System.currentTimeMillis() - cookieResult.getTime()) / 1000 < duration) {
                onComplete();
                d.dispose();
                resultOnNext(cookieResult.getResult());
                return;
            }

            if (null != cookieResult && api.isAdvanceLoadCache()) {
                resultOnNext(cookieResult.getResult());
            }
        }

        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel(), d);
        }
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel, Disposable d) {
        if (!api.isShowProgress()) {
            return;
        }
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = ProgressDialog.show(context, null, context.getString(R.string.Loading));
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(dialogInterface -> onCancelProgress(d));
            }
        } else if (pd != null && context != null) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
        }
    }


    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (null == mActivity || null == mActivity.get() || mActivity.get().isFinishing()) {
            return;
        }
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            getCache(e);
        } else {
            errorDo(e);
        }
        dismissProgressDialog();
    }

    /**
     * 获取cache数据
     */
    private void getCache(Throwable te) {
        /*获取db缓存数据*/
        CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
        if (cookieResult == null) {
            /*获取gson文件缓存数据*/
            String result = AbStrUtil.getAssetsJsonBy(RxRetrofitApp.getApplication(), getPreCacheFileName());
            if (!AbStrUtil.isEmpty(result)) {
                resultOnNext(result);
            } else {
                errorDo(te);
            }
        } else {
            resultOnNext(cookieResult.getResult());
        }
    }

    /**
     * 获取预制的缓存文件名
     *
     * @return
     */
    private String getPreCacheFileName() {
        String fileName = CACHE_DATA_DIR_NAME + api.getMethod();
        if (!api.getMethod().endsWith(JSON_SUFFIX)) {
            fileName += JSON_SUFFIX;
        }
        return fileName;
    }


    /**
     * 错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e) {
        if (e instanceof ApiException) {
            resultOnError((ApiException) e);
        } else {
            resultOnError(new ApiException(e, HttpTimeException.UNKNOWN_ERROR, RxRetrofitApp.getApplication().getString(R
                    .string.service_error)));
        }
    }


    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        resultOnNext(t.toString());
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            if (api.isCache()) {
                getCache(null);
            } else {
                errorDo(new ApiException(new Throwable(), HttpTimeException.HTTP_CANCEL, RxRetrofitApp.getApplication()
                        .getString(R.string.http_cancel)));
            }
        }
    }


    /**
     * 异常的统一回调处理
     *
     * @param apiException
     */
    private void resultOnError(ApiException apiException) {
        try {
            HttpOnNextListener httpOnNextListener = mSubscriberOnNextListener.get();
            if (null != mSubscriberOnNextListener && null != mSubscriberOnNextListener.get() && null != mActivity && null !=
                    mActivity.get() && !mActivity.get().isFinishing()) {
                httpOnNextListener.onError(apiException, api.getMethod());
            }
        } catch (Exception e) {
            AbLogUtil.e("listener onError error--->" + e.getMessage());
        }

    }


    /**
     * 回调接口成功回调处理
     *
     * @param result
     */
    private void resultOnNext(String result) {
        if (null != mSubscriberOnNextListener && null != mSubscriberOnNextListener.get() && null != mActivity && null !=
                mActivity.get() && !mActivity.get().isFinishing()) {
            mSubscriberOnNextListener.get().onNext(result, api.getMethod());
        }
    }
}