package com.base.muslim.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.listener.HttpOnNextListener
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.ScreenUtils
import com.base.muslim.base.activity.BaseToolsActivity
import com.base.muslim.login.common.constants.LoginConstants.Companion.REQUEST_CODE_GOOGLE_SIGN_IN
import com.base.muslim.login.mvp.LoginContract
import com.base.muslim.login.mvp.LoginPresenter
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.twitter.sdk.android.core.identity.TwitterLoginButton


/**
 * Description:
 * 登录弹窗基类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
@SuppressLint("Registered")
abstract class BaseLoginDialogActivity : BaseToolsActivity(), LoginContract.View,
        GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult>, HttpOnNextListener {

    private val httpManager by lazy { HttpManager(this, this) }
    private var loginPresenter: LoginPresenter? = null

    /**
     * google登录
     */
    private val signInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.web_client_id_google_login))
//                .requestServerAuthCode(getString(R.string.web_client_id_google_login))
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestServerAuthCode(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }
    private val apiClient by lazy {
        GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build()
    }

    /**
     * facebook登录
     */
    private var loginButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    /**
     * Twitter登录
     */
    private var twitterLoginButton: TwitterLoginButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenDisplay(1.0, 1.0)
        window.setGravity(Gravity.BOTTOM)
        setFinishOnTouchOutside(false)
        loginPresenter = LoginPresenter(httpManager)
        loginPresenter?.attachView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            loginPresenter?.handleGoogleLoginResult(data)
        }
        getTwitterLoginButton()?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRelease() {
        super.onRelease()
        loginPresenter?.detachView()
        loginButton?.unregisterCallback(callbackManager)
        loginPresenter = null
        loginButton = null
        callbackManager = null
        twitterLoginButton = null
    }


    override fun createFacebookLoginButton(): LoginButton? {
        if (loginButton == null) {
            loginButton = LoginButton(this)
        }
        return loginButton
    }

    override fun createFacebookCallbackManager(): CallbackManager? {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create()
        }
        return callbackManager
    }

    override fun getCurrentNoExpiredToken(): String {
        var accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        var token = ""
        if (accessToken != null && accessToken.isExpired) {
            //如果已经过期，退出登录
            var loginManager: LoginManager? = LoginManager.getInstance()
            loginManager?.logOut()
            loginManager = null
        } else if (accessToken != null) {
            token = accessToken.token
        }
        accessToken = null
        return token
    }

    override fun onSuccess(loginResult: LoginResult) {
        //使用Facebook的token请求我们服务器上的token
        val token = loginResult.accessToken.token
        loginPresenter?.loginAndFinish("facebook", token, null, null)
    }

    override fun onCancel() {
        LogUtils.d("facebook onCancel")
    }

    override fun onError(error: FacebookException) {
        LogUtils.d("facebook error:$error")
    }

    override fun getGoogleApiClient(): GoogleApiClient {
        return apiClient
    }

    override fun getTwitterLoginButton(): TwitterLoginButton? {
        if (twitterLoginButton == null) {
            twitterLoginButton = TwitterLoginButton(this)
        }
        return twitterLoginButton
    }

//    override fun getAccount(): String? {
//        return null
//    }
//
//    override fun getVerificationCode(): String? {
//        return null
//    }
//
//    override fun setBtnSendCodeEnabled(enabled: Boolean) {
//
//    }
//
//    override fun setBtnSendCodeText(text: String) {
//
//    }

    override fun setBtnSendCodeText(stringResId: Int) {
        if (null != resources) {
            setBtnSendCodeText(resources.getString(stringResId))
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        LogUtils.d("Google Login onConnectionFailed:$connectionResult")
    }

    override fun startGoogleLoginActivityForResult(signInIntent: Intent) {
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun setResultAndFinish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    override fun onNext(resulte: String, method: String) {
        loginPresenter?.onNext(resulte, method)
    }

    override fun onError(e: ApiException, method: String) {
        LogUtils.d("error:${e.message}\nmethod:$method")
    }

    protected fun loginByFacebook() {
        loginPresenter?.loginByFacebook()
    }

    protected fun loginByGoogle() {
        loginPresenter?.loginByGoogle()
    }

    protected fun loginByTwitter() {
        LogUtils.d("protected fun loginByTwitter() {")
        loginPresenter?.loginByTwitter()
    }

    protected fun loginByPhoneOrEmail() {
        loginPresenter?.loginByPhoneOrEmail()
    }

    protected fun sendCode(type: String) {
        loginPresenter?.sendCode(type)
    }

    /**
     * 设置弹窗宽高百分比
     */
    protected fun setScreenDisplay(widthPercent: Double, heightPercent: Double) {
        val params = window.attributes
        // 屏幕的宽度百分比
        if (widthPercent == WindowManager.LayoutParams.WRAP_CONTENT.toDouble() || widthPercent == WindowManager.LayoutParams.MATCH_PARENT.toDouble()) {
            params.width = widthPercent.toInt()
        } else {
            params.width = (ScreenUtils.getScreenWidth() * widthPercent).toInt()
        }
        // 屏幕的高度百分比
        if (heightPercent == WindowManager.LayoutParams.WRAP_CONTENT.toDouble() || heightPercent == WindowManager.LayoutParams.MATCH_PARENT.toDouble())
            params.height = heightPercent.toInt()
        else {
            params.height = (ScreenUtils.getScreenHeight() * heightPercent).toInt()
        }
        window.attributes = params
    }

}
