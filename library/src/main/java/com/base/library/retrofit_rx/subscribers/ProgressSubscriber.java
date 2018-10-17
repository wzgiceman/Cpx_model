package com.base.library.retrofit_rx.subscribers;


import android.app.ProgressDialog;
import android.content.Context;

import com.base.library.R;
import com.base.library.retrofit_rx.Api.BaseApi;
import com.base.library.retrofit_rx.RxRetrofitApp;
import com.base.library.retrofit_rx.exception.ApiException;
import com.base.library.retrofit_rx.exception.HttpTimeException;
import com.base.library.retrofit_rx.http.cookie.CookieResulte;
import com.base.library.retrofit_rx.listener.HttpOnNextListener;
import com.base.library.retrofit_rx.utils.AppUtil;
import com.base.library.retrofit_rx.utils.CookieDbUtil;
import com.base.library.utils.AbLogUtil;
import com.base.library.utils.AbStrUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    //    回调接口
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    //    软引用反正内存泄露
    private SoftReference<RxAppCompatActivity> mActivity;
    //    加载框可自己定义
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;


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
    public void onStart() {
        if (api.isCache()) {
            /*获取缓存数据*/
            int duration = AppUtil.isNetworkAvailable(RxRetrofitApp.getApplication()) ? api.getCookieNetWorkTime() : api
                    .getCookieNoNetWorkTime();
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
            if (cookieResulte != null && (System.currentTimeMillis() - cookieResulte.getTime()) / 1000 < duration) {
                resulteOnNext(cookieResulte.getResulte());
                onCompleted();
                unsubscribe();
                return;
            }
        }

        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        if (!api.isShowProgress()) return;
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = ProgressDialog.show(context, null, context.getString(R.string.Loading));
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(dialogInterface -> onCancelProgress());
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
    public void onCompleted() {
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
        if (null == mActivity || null == mActivity.get() || mActivity.get().isFinishing()) return;
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
        CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
        if (cookieResulte == null) {
            /*获取gson文件缓存数据*/
            String resulte = AbStrUtil.getAssetsJsonBy(RxRetrofitApp.getApplication(), "gson/" + api.getMethod() + ".json");
            if (!AbStrUtil.isEmpty(resulte)) {
                resulteOnNext(resulte);
            } else {
                errorDo(te);
            }
        } else {
            resulteOnNext(cookieResulte.getResulte());
        }
    }


    /**
     * 错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e) {
        if (e instanceof ApiException) {
            resulteonError((ApiException) e);
        } else {
            resulteonError(new ApiException(e, HttpTimeException.UNKNOWN_ERROR, RxRetrofitApp.getApplication().getString(R
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
        resulteOnNext(t.toString());
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
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
    private void resulteonError(ApiException apiException) {
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
     * @param resulte
     */
    private void resulteOnNext(String resulte) {
        if (null != mSubscriberOnNextListener && null != mSubscriberOnNextListener.get() && null != mActivity && null !=
                mActivity.get() && !mActivity.get().isFinishing()) {
            mSubscriberOnNextListener.get().onNext(resulte, api.getMethod());
        }
    }
}