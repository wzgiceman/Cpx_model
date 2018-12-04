package com.base.library.rxRetrofit.downlaod.subscriber;


import android.os.Handler;

import com.base.library.rxRetrofit.downlaod.DownInfo;
import com.base.library.rxRetrofit.downlaod.loadListener.DownloadProgressListener;
import com.base.library.rxRetrofit.downlaod.DownState;
import com.base.library.rxRetrofit.downlaod.HttpDownManager;
import com.base.library.rxRetrofit.listener.HttpDownOnNextListener;
import com.base.library.rxRetrofit.utils.DownDbUtil;

import java.io.File;
import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 断点下载处理类Subscriber
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressDownSubscriber<T> implements Observer<T>, DownloadProgressListener {
    //弱引用结果回调
    private SoftReference<HttpDownOnNextListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownInfo downInfo;
    /*切换线程*/
    private Handler handler;
    /*sub对象*/
    private Disposable disposable;


    public ProgressDownSubscriber(DownInfo downInfo, Handler handler) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
        this.handler = handler;
    }


    public void setDownInfo(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        downInfo.setState(DownState.START);
        if (null == mSubscriberOnNextListener || null == mSubscriberOnNextListener.get()) return;
        mSubscriberOnNextListener.get().onStart();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.FINISH);
        DownDbUtil.getInstance().update(downInfo);
        if (null == mSubscriberOnNextListener || null == mSubscriberOnNextListener.get()) return;
        mSubscriberOnNextListener.get().onComplete();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.ERROR);
        DownDbUtil.getInstance().update(downInfo);

        File file = new File(downInfo.getSavePath());
        if (file.exists()) {
            file.delete();
        }

        if (null == mSubscriberOnNextListener || null == mSubscriberOnNextListener.get()) return;
        mSubscriberOnNextListener.get().onError(e);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (null == mSubscriberOnNextListener || null == mSubscriberOnNextListener.get()) return;
        if (downInfo.getReadLength() < downInfo.getCountLength()) {
            mSubscriberOnNextListener.get().onError(new Throwable());
            HttpDownManager.getInstance().remove(downInfo);
            unsubscribe();
            return;
        }
        mSubscriberOnNextListener.get().onNext(t);
    }


    @Override
    public void update(long read, long count, boolean done) {
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);

        /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
        if (null == mSubscriberOnNextListener || null == mSubscriberOnNextListener.get() || !downInfo.isUpdateProgress() ||
                downInfo.getState() == DownState.PAUSE || downInfo.getState() == DownState.STOP) return;
        handler.post(() -> {
            downInfo.setState(DownState.DOWN);
            mSubscriberOnNextListener.get().updateProgress(downInfo.getReadLength(), downInfo.getCountLength());
        });
    }

    /**
     * 取消订阅
     */
    public void unsubscribe() {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}