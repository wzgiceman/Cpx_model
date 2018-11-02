package com.base.muslim.user.login

import android.view.View
import com.base.library.retrofit_rx.exception.ApiException
import com.base.library.retrofit_rx.http.HttpManager
import com.base.library.retrofit_rx.listener.HttpOnNextListener
import com.base.library.utils.AbStrUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.library.R
import com.base.muslim.user.common.api.login.SendCodeApi
import com.base.muslim.user.common.api.login.SetPsdApi
import com.base.library.rxlifecycle.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_find_psd.*
import java.util.concurrent.TimeUnit


/**
 *
 *Describe:找回密码
 *
 *Created by zhigang wei
 *on 2018/6/14
 *
 *Company :cpx
 */
class ResetPsdActivity : BaseFragmentActivity(), HttpOnNextListener {
    private val httpManager by lazy { HttpManager(this, this) }
    private lateinit var sendCodeApi: SendCodeApi

    override fun setContentViews() {
        setContentView(R.layout.activity_find_psd)
        super.setContentViews()
    }

    override fun initResource() {

    }

    override fun initWidget() {
        tv_back.setOnClickListener { finish() }
        tv_get_code.setOnClickListener {
            val email = etv_email.text.toString()
            if (AbStrUtil.isEmpty(email) || !AbStrUtil.isEmail(email)) {
                showToast(R.string.input_error)
                return@setOnClickListener
            }
            sendCodeApi = SendCodeApi(email)
            httpManager.doHttpDeal(sendCodeApi)
            tv_get_code.isEnabled = false
        }
        tv_sign_up.setOnClickListener {
            val email = etv_email.text.toString()
            val code = etv_code.text.toString()
            val psd = etv_psd.text.toString()

            if (AbStrUtil.isEmpty(psd) || AbStrUtil.isEmpty(email) || AbStrUtil.isEmpty(code) || !AbStrUtil.isEmail(email)) {
                showToast(R.string.input_error)
                return@setOnClickListener
            }
            httpManager.doHttpDeal(SetPsdApi(email, psd, code))
        }
    }


    override fun onNext(resulte: String, method: String) {
        if (method.equals(sendCodeApi.method)) {
            Observable.interval(1, TimeUnit.SECONDS).take(91).compose(bindUntilEvent(ActivityEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        if (it == 90L && AbStrUtil.isEmpty(etv_code.text.toString())) {
                            tv_get_code.visibility = View.VISIBLE
                            tv_get_code.isEnabled = true
                        } else {
                            tv_time.text = (90 - it).toString() + "'"
                            tv_get_code.visibility = View.INVISIBLE
                        }
                        if (it == 90L) tv_time.text = ""
                    }
        } else {
            showToast(R.string.set_psd_suc)
            jumpActivity(LoginActivity::class.java)
        }
    }

    override fun onError(e: ApiException, method: String) {
        showToast(e.displayMessage)
    }

}