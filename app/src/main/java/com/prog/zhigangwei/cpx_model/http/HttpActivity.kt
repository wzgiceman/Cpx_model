package com.prog.zhigangwei.cpx_model.http

import com.base.library.retrofit_rx.exception.ApiException
import com.base.library.retrofit_rx.http.HttpManager
import com.base.library.retrofit_rx.listener.HttpOnNextListener
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.http.api.notice.NoticePostApi
import com.prog.zhigangwei.cpx_model.http.api.wallpaper.HomeWallApi
import com.prog.zhigangwei.cpx_model.http.down.DownActivity
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
 * 更复杂用例参考地址:https://github.com/wzgiceman/RxjavaRetrofitDemo-string-master/blob/master/app/src/main/java/com/example/retrofit/activity/DownLaodActivity.java
 */
class HttpActivity : BaseFragmentActivity(), HttpOnNextListener {
    private val httpManager by lazy { HttpManager(this, this) }

    private val wallApi by lazy { HomeWallApi() }
    private val noticeApi by lazy { NoticePostApi("test") }


    override fun setContentViews() {
        setContentView(R.layout.activity_http)
        super.setContentViews()
    }

    override fun initResource() {
    }

    override fun initWidget() {
        btn_get.setOnClickListener { httpManager.doHttpDeal(wallApi) }
        btn_post.setOnClickListener { httpManager.doHttpDeal(noticeApi) }
        btn_down.setOnClickListener { jumpActivity(DownActivity::class.java)}
    }


    override fun onNext(resulte: String, method: String) {
        when (method) {
            wallApi.method -> {
                tv_msg.text = "suc:wallApi->${resulte}"
            }
            noticeApi.method -> {
                tv_msg.text = "suc:noticeApi->${resulte}"
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
        }
    }
}
