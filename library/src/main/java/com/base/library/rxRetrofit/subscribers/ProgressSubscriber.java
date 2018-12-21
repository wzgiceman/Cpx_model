package com.base.library.rxRetrofit.subscribers;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.base.library.R;
import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.api.BaseApi;
import com.base.library.rxRetrofit.downlaod.utils.CookieDbUtil;
import com.base.library.rxRetrofit.exception.ApiException;
import com.base.library.rxRetrofit.exception.HttpTimeException;
import com.base.library.rxRetrofit.http.cookie.CookieResult;
import com.base.library.rxRetrofit.listener.HttpOnNextListener;
import com.base.library.utils.utilcode.util.LogUtils;
import com.base.library.utils.utilcode.util.ResourceUtils;
import com.base.library.utils.utilcode.util.StringUtils;
import com.base.project.base.activity.BaseActivity;
import com.base.project.base.fragment.BaseFragment;

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
    private SoftReference<BaseActivity> mActivity;
    private SoftReference<BaseFragment> mFragment;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;
    /*缓存数据的的文件夹名称*/
    private final String CACHE_DATA_DIR_NAME = "gson/";
    /*json 后缀*/
    private final String JSON_SUFFIX = ".json";


    public void setFgProgSub(@NonNull BaseApi api, @NonNull SoftReference<HttpOnNextListener> listenerSoftReference,
                             @NonNull SoftReference<BaseFragment> mFragment) {
        this.api = api;
        this.mSubscriberOnNextListener = listenerSoftReference;
        this.mFragment = mFragment;
        this.mActivity = new SoftReference(mFragment.get().getContext());
    }

    public void setAtProgSub(@NonNull BaseApi api, @NonNull SoftReference<HttpOnNextListener> listenerSoftReference,
                             @NonNull SoftReference<BaseActivity> mActivity) {
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
        if (api.isCache() && !api.isRefresh()) {
            /*获取缓存数据*/
            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
            int duration = isNetworkAvailable(RxRetrofitApp.getApplication()) ? api.getCookieNetWorkTime()
                    : api.getCookieNoNetWorkTime();
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

        initProgressDialog(api.isCancel(), d);
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel, Disposable d) {
        if (!api.isShowProgress()) return;
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
        CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(api.getCacheUrl());
        if (cookieResult == null) {
            /*获取gson文件缓存数据*/
            String result = ResourceUtils.readAssets2String(getPreCacheFileName());
            if (StringUtils.isEmpty(result)) {
                errorDo(te);
            } else {
                resultOnNext(result);
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
        String method = api.getMethod();
        if (method.endsWith("/")) {
            method = method.substring(0, method.length() - 1);
        }
        String fileName = CACHE_DATA_DIR_NAME + method;
        if (!method.endsWith(JSON_SUFFIX)) {
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
     * 回调接口成功回调处理
     *
     * @param result
     */
    private void resultOnNext(String result) {
        if (isValid()) {
            mSubscriberOnNextListener.get().onNext(result, api.getMethod());
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
            if (isValid()) {
                httpOnNextListener.onError(apiException, api.getMethod());
            }
        } catch (Exception e) {
            LogUtils.d("listener onError error--->" + e.getMessage());
        }

    }

    /**
     * 判断Fragment/Activity是否已销毁
     *
     * @return 是否已销毁
     */
    private boolean isValid() {
        boolean atValid= null != mSubscriberOnNextListener && null != mSubscriberOnNextListener.get() && null != mActivity && null !=
                mActivity.get() && !mActivity.get().isFinishing();
        if(null==mFragment){
            return atValid;
        }else{
            return atValid&&null != mFragment.get() && mFragment.get().isAdded();
        }
    }


    /**
     * 是否有网络
     *
     * @param context
     * @return
     */
    private boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}