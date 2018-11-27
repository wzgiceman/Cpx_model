package com.base.clawin.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description:
 * P基类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/29
 */
open class BasePresenter<T : IBaseView> : IBasePresenter<T> {

    /**View层*/
    var rootView: T? = null
        private set

    /**管理所有通知者，页面关闭时取消订阅*/
    private var compositeDisposable = CompositeDisposable()


    override fun attachView(mRootView: T) {
        this.rootView = mRootView
    }

    override fun detachView() {
        rootView = null
        /**保证activity结束时取消所有正在执行的订阅*/
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private val isViewAttached: Boolean
        get() = rootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    /**添加到通知者管理列表中*/
    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IBasePresenter.attachView(IBaseView) before" + " requesting data to the IBasePresenter")

}