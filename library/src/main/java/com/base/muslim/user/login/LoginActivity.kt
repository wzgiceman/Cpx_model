package com.base.muslim.user.login

import android.content.Intent
import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.base.library.R
import com.base.library.retrofit_rx.exception.ApiException
import com.base.library.retrofit_rx.http.HttpManager
import com.base.library.retrofit_rx.listener.HttpOnNextListener
import com.base.library.utils.AbStrUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.muslim.user.common.api.source.NoticePostApi
import com.base.share_data.ShareSparse
import com.base.share_data.share_msg.ShareDataDb
import com.base.share_data.user.User
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.iid.FirebaseInstanceId
import com.base.muslim.user.common.api.login.LoginApi
import com.base.muslim.user.common.api.login.LoginFaceBookApi
import kotlinx.android.synthetic.main.activity_login.*
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference


/**
 *
 *Describe:登录
 *
 *Created by zhigang wei
 *on 2018/6/14
 *
 *Company :cpx
 */
class LoginActivity : BaseFragmentActivity(), HttpOnNextListener {
    private val user by lazy { ShareSparse.getValueBy(ShareSparse.USER_CLS) as User }
    private val httpManager by lazy { HttpManager(this, this) }
    private var loginApi: LoginApi? = null

    override fun initActivity() {
        super.initActivity()
        initComplexWidget()
    }

    override fun setContentViews() {
        setContentView(R.layout.activity_login)
        super.setContentViews()
    }

    override fun initResource() {
        user.token = ""
    }

    override fun initWidget() {
        tv_back.setOnClickListener { finish() }
        tv_login.setOnClickListener {
            collectionFireabse(R.string.click_login)
            val email = etv_email.text.toString()
            val psd = etv_psd.text.toString()
            if (AbStrUtil.isEmpty(psd) || AbStrUtil.isEmpty(email) || !AbStrUtil.isEmail(email)) {
                showToast(R.string.input_error)
                return@setOnClickListener
            }
            loginApi = LoginApi(email, psd)
            httpManager.doHttpDeal(loginApi)
        }
        tv_sign_up.setOnClickListener { jumpActivityFinish(SignUpActivity::class.java) }
        tv_forgeot.setOnClickListener { jumpActivity(ResetPsdActivity::class.java) }

    }

    override fun onNext(resulte: String, method: String) {
        if (loginApi != null && loginApi!!.method.equals(method)) {
            user.setLoginUser(resulte)
            ShareDataDb.getInstance().savrOrUpdate(user)
            showToast(R.string.login_suc)
            collectionFireabse(R.string.login_sucs)
            finish()
        } else {
            val faceUser = JSONObject.parseObject(resulte)
            if (getString(R.string.facebook_log).equals(faceUser.getString("status"))) {
                jumpActivity(BindUserActivity::class.java, bundle)
            } else {
                user.setLoginUser(faceUser.getString("user"))
                ShareDataDb.getInstance().savrOrUpdate(user)
                showToast(R.string.login_suc)
                collectionFireabse(R.string.login_sucs)
                finish()
            }
        }
    }

    override fun onError(e: ApiException, method: String) {
        showToast(e.displayMessage)
        facebookLogin = false
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent?.hasExtra(getString(R.string.loginSuc))) {
            finish()
        }
    }

    /*start----------------facebook-----------------------*/
    private var mCallbackManager: CallbackManager? = null
    private var facebookLogin = false
    private var fcLogin: WeakReference<LoginButton>? = null

    private val mCallback by lazy {
        GraphRequest.Callback {
            val userObj = it.jsonObject
            if (facebookLogin || userObj == null) return@Callback
            bundle = Bundle()
            bundle.putString("email", if (userObj.has("email")) userObj.getString("email") else "")
            bundle.putString("tokean", AccessToken.getCurrentAccessToken().token)
            facebookLogin = true
            httpManager.doHttpDeal(LoginFaceBookApi(AccessToken.getCurrentAccessToken().token))
        }
    }

    override fun initComplexWidget() {
        super.initComplexWidget()

        /*第三方facebook登录*/
        tv_facbook.setOnClickListener {
            val accessToken = AccessToken.getCurrentAccessToken()
            val isLoggedIn = accessToken != null && !accessToken.isExpired
            if (isLoggedIn) {
                facebookGetUser()
            } else {
                initFacebook()
            }
        }
    }


    private fun initFacebook() {
        fcLogin = WeakReference(LoginButton(this))
        fcLogin?.get()?.setReadPermissions("email", "public_profile")
        mCallbackManager = CallbackManager.Factory.create()
        fcLogin?.get()?.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                facebookGetUser()
            }

            override fun onCancel() {
            }

            override fun onError(e: FacebookException) {
                showToast(e.message)
            }
        })
        fcLogin?.get()?.callOnClick()
    }


    /**
     * 获取facebook信息
     */
    private fun facebookGetUser() {
        val params = Bundle()
        params.putString("fields", "picture,name,id,email,permissions")
        val request = GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                params,
                HttpMethod.GET,
                mCallback
        )
        request.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            var tokean = FirebaseInstanceId.getInstance().token
            if (user.isLogin && !AbStrUtil.isEmpty(tokean)) {
                HttpManager().doHttpDeal(NoticePostApi(tokean)).observeOn(Schedulers.newThread()).subscribe({
                    user.firebaseTokean = tokean
                    ShareDataDb.getInstance().savrOrUpdate(user)
                }, {})
            }
            fcLogin?.get()?.unregisterCallback(mCallbackManager)
            fcLogin = null
            mCallbackManager = null
        }
    }

}