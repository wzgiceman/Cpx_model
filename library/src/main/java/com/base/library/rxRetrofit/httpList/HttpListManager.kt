package com.base.library.rxRetrofit.httpList

import android.app.ProgressDialog
import com.base.library.R
import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.exception.HttpTimeException
import com.base.library.rxRetrofit.httpList.httpList.HttpListOnNextListener
import com.base.library.rxRetrofit.httpList.httpList.ListApiResult
import com.base.library.rxlifecycle.LifecycleTransformer
import com.base.library.rxlifecycle.android.ActivityEvent
import com.base.library.rxlifecycle.android.FragmentEvent
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity
import com.base.library.rxlifecycle.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Description:
 * 链式调用api的Manager
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/17
 */
class HttpListManager() {
    /**链式api和回调结果类*/
    private lateinit var listResult: ListApiResult
    /**加载框可自己定义*/
    private var pd: ProgressDialog? = null
    /**保存订阅，用于取消*/
    private var disposable: Disposable? = null
    private var activity: RxAppCompatActivity? = null
    private var fragment: RxFragment? = null
    private lateinit var listener: HttpListOnNextListener

    constructor(listener: HttpListOnNextListener,
                activity: RxAppCompatActivity?) : this() {
        this.activity = activity
        this.listener = listener
    }

    constructor(listener: HttpListOnNextListener, fragment: RxFragment?) : this() {
        this.fragment = fragment
        this.listener = listener
    }

    /**
     * 链式调用api
     */
    fun doHttpListDeal(listResult: ListApiResult) {
        val apiList = listResult.getApiList()
        if (apiList == null || apiList.isEmpty() || isFinishing()) {
            listener.onListError(ApiException(NullPointerException("apiList == null:${apiList == null || apiList.isEmpty()}\n" +
                    "isFinishing():${isFinishing()}")), listResult)
            return
        }
        this.listResult = listResult
        val observable: Observable<Unit>
        if (listResult.order) {
            observable = Observable.fromArray(*apiList)
                    .concatMap {
                        (it.listObservable as Observable<String>).map { result ->
                            listResult.resultMap[it.method] = result
                        }
                    }
        } else {
            observable = Observable.fromArray(*apiList)
                    .flatMap {
                        (it.listObservable as Observable<String>).map { result ->
                            listResult.resultMap[it.method] = result
                        }
                    }
        }
        observable.buffer(apiList.size)
                .map {
                    listResult.convert()
                    listResult
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycle())
                .subscribe(object : Observer<ListApiResult> {
                    override fun onComplete() {
                        dismissProgressDialog()
                        listResult.complete()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        initProgressDialog(listResult.cancel)
                    }

                    override fun onNext(t: ListApiResult) {
                        onResultNext(t)
                    }

                    override fun onError(e: Throwable) {
                        onResultError(e as? ApiException
                                ?: ApiException(Throwable(), HttpTimeException.HTTP_CANCEL, RxRetrofitApp.getApplication().getString(R.string.http_cancel)), listResult)
                        dismissProgressDialog()
                        listResult.complete()
                    }
                })
    }

    /**
     * 获取需要绑定的RxLifeCycle生命周期
     */
    private fun lifecycle(): LifecycleTransformer<ListApiResult> {
        val fragmentLife = fragment?.bindUntilEvent<ListApiResult>(FragmentEvent.DESTROY_VIEW)
        val activityLife = activity?.bindUntilEvent<ListApiResult>(ActivityEvent.DESTROY)
        return fragmentLife ?: activityLife
        ?: throw ApiException(NullPointerException("activity or fragment is null"))
    }

    /**
     * 初始化加载框
     */
    private fun initProgressDialog(cancel: Boolean) {
        if (!listResult.showProgress) return
        val context = activity ?: fragment?.activity ?: return
        if (pd == null) {
            pd = ProgressDialog.show(context, null, context?.getString(R.string.Loading))
                    .apply {
                        setCancelable(cancel)
                        if (cancel) {
                            setOnCancelListener { onCancelProgress() }
                        }
                    }
        } else {
            pd?.show()
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    private fun onCancelProgress() {
        if (disposable == null || disposable!!.isDisposed) return
        disposable?.dispose()
        onResultError(ApiException(Throwable(), HttpTimeException.HTTP_CANCEL, RxRetrofitApp.getApplication()
                .getString(R.string.http_cancel)), listResult)
    }


    /**
     * 关闭弹窗
     */
    private fun dismissProgressDialog() {
        pd?.dismiss()
    }

    fun onResultNext(t: ListApiResult) {
        if (isFinishing()) return
        listener.onListNext(t)
    }

    fun onResultError(e: ApiException, listResult: ListApiResult) {
        if (isFinishing()) return
        listener.onListError(e, listResult)
    }

    /**
     * 判断fragment是否被销毁/activity是否正在关闭
     */
    private fun isFinishing() = (fragment == null && activity == null)
            || (fragment != null && fragment?.isAdded != true)
            || (activity != null && activity?.isFinishing == true)

}