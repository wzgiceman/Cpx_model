package com.base.muslim.user.login

import com.base.library.R
import com.base.library.retrofit_rx.exception.ApiException
import com.base.library.retrofit_rx.http.HttpManager
import com.base.library.retrofit_rx.listener.HttpOnNextListener
import com.base.library.utils.AbStrUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.share_data.ShareSparse
import com.base.share_data.share_msg.ShareDataDb
import com.base.share_data.user.User
import com.base.muslim.user.common.api.login.BindFaceBookApi
import kotlinx.android.synthetic.main.activity_bind_user.*


/**
 *
 *Describe:facebook绑定用户登录
 *
 *Created by zhigang wei
 *on 2018/6/14
 *
 *Company :cpx
 */
class BindUserActivity : BaseFragmentActivity(), HttpOnNextListener {
    private val httpManager by lazy { HttpManager(this, this) }

    override fun initActivity() {
        super.initActivity()
        initComplexWidget()
    }

    override fun setContentViews() {
        setContentView(R.layout.activity_bind_user)
        super.setContentViews()
    }

    override fun initResource() {

    }

    override fun initWidget() {
        etv_email.text = bundle.getString("email")
        tv_back.setOnClickListener { finish() }
        tv_deal.setOnClickListener {
            val psd = etv_psd.text.toString()
            if (AbStrUtil.isEmpty(psd)) {
                showToast(R.string.input_error)
                return@setOnClickListener
            }
            httpManager.doHttpDeal(BindFaceBookApi(bundle.getString("tokean"), psd))
        }
    }

    override fun onNext(resulte: String, method: String) {
        val user = ShareSparse.getValueBy(ShareSparse.USER_CLS) as User
        user.setLoginUser(resulte)
        ShareDataDb.getInstance().savrOrUpdate(user)
        bundle.putBoolean(getString(R.string.loginSuc), true)
        showToast(R.string.bind_suc)
        jumpActivity(LoginActivity::class.java, bundle)
    }

    override fun onError(e: ApiException, method: String) {
        showToast(e.displayMessage)
    }

}
