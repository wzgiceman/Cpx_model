package com.prog.zhigangwei.cpx_model.http

import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.http.func.ResultFunc
import com.base.library.rxRetrofit.listener.HttpOnNextListener
import com.base.library.utils.utilcode.util.LogUtils
import com.base.project.base.activity.BaseToolsActivity
import com.base.project.base.extension.jumpActivity
import com.base.project.base.extension.showToast
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.http.common.api.LanguageApi.LanguagesApi
import com.prog.zhigangwei.cpx_model.http.common.api.notice.NoticePostApi
import com.prog.zhigangwei.cpx_model.http.common.api.wallpaper.HomeWallApi
import com.prog.zhigangwei.cpx_model.http.common.bean.AllResulte
import com.prog.zhigangwei.cpx_model.http.down.DownActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_http.*

/**
 *
 *Describe:网络请求
 *
 *Created by zhigang wei
 *on 2018/8/24
 *
 *Company :cpx
 *
 */
class HttpActivity : BaseToolsActivity(), HttpOnNextListener {

    private val httpManager by lazy { HttpManager(this, this) }

    private val wallApi by lazy { HomeWallApi() }
    private val languagesApi by lazy { LanguagesApi() }
    private val noticeApi by lazy { NoticePostApi("test") }

    override fun layoutId() = R.layout.activity_http

    override fun initData() {
    }

    override fun initView() {
        btn_get.setOnClickListener {
            wallApi.isRefresh = false
            httpManager.doHttpDeal(wallApi)
        }

        btn_get_refresh.setOnClickListener {
            wallApi.isRefresh = true
            httpManager.doHttpDeal(wallApi)
        }

        btn_post.setOnClickListener { httpManager.doHttpDeal(noticeApi) }

        /*可以提前将网络关闭测试，永远不会失败*/
        btn_pre_gson.setOnClickListener { httpManager.doHttpDeal(languagesApi) }

        btn_down.setOnClickListener { jumpActivity(DownActivity::class.java) }

        btn_all.setOnClickListener {
            val allResulte = AllResulte()
            languagesApi.observable.map(ResultFunc(languagesApi))
                    .flatMap {
                        allResulte.lgResulte = it
                        wallApi.observable.map(ResultFunc(wallApi))
                    }.flatMap {
                        allResulte.wallResulte = it
                        noticeApi.observable.map(ResultFunc(noticeApi))
                    }
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<String> {
                        override fun onComplete() {
                            closeLoading()
                        }

                        override fun onSubscribe(d: Disposable) {
                            showLoading()
                        }

                        override fun onNext(it: String) {
                            allResulte.noticeResulte = it
                            tv_msg.append("lg-->${allResulte.lgResulte}")
                            tv_msg.append("\n\n wall-->${allResulte.wallResulte}")
                            tv_msg.append("\n\n notice-->${allResulte.noticeResulte}")
                        }

                        override fun onError(it: Throwable) {
                            showToast("${it.message}")
                            closeLoading()
                        }

                    })
        }
    }


    override fun onNext(resulte: String, method: String) {
        LogUtils.d("method:$method\nresult:$resulte")
        when (method) {
            wallApi.method -> {
                tv_msg.text = "suc:wallApi->$resulte"
            }
            noticeApi.method -> {
                tv_msg.text = "suc:noticeApi->$resulte"
            }
            languagesApi.method -> {
                tv_msg.text = "suc:languagesApi->$resulte"
            }
        }
    }

    override fun onError(e: ApiException, method: String) {
        showToast(e.displayMessage)
        when (method) {
            wallApi.method -> {
                tv_msg.text = "error:wallApi->${e.displayMessage}"
            }
            noticeApi.method -> {
                tv_msg.text = "error:noticeApi->${e.displayMessage}"
            }
            languagesApi.method -> {
                tv_msg.text = "error:languagesApi->${e.displayMessage}"
            }
        }
    }
}
