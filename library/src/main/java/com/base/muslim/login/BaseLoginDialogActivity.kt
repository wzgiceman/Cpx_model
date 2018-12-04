package com.base.muslim.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.base.library.R
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
 * 通过[setScreenDisplay]设置弹窗宽高，默认占满屏幕
 * 通过[window.setGravity(Gravity.CENTER)]设置位置，默认居中
 *
 * Phone/Email
 * 将账号和验证码传入
 *
 * Facebook登录，需要用浏览器打开 Facebook开发者平台：https://developers.facebook.com/apps/
 * 1.创建应用，将应用编号赋值给 gradle.properties 的 facebook_app_id 变量
 * 2.在Facebook开发者平台控制台的 设置->基本 中添加Android平台，填入Google Play包名、类名、密钥散列、隐私权政策
 * 3.在Facebook开发者平台控制台 添加产品->Facebook登录， 按照步骤操作，在第五步中启用单点登录并保存（其他步骤此sdk中已做，无需更改）
 * 4.在Facebook开发者平台控制台公开发布应用
 * 5.Facebook登录成功回调：[onSuccess]，Facebook登录取消回调：[onCancel]，Facebook登录错误回调[onError]
 * 6.在[LoginPresenter.loginAndFinish]方法中使用第三方的token进行登录，在[LoginPresenter.onNext]回调中获取登录api返回的数据，并保存用户信息、关闭窗口
 *
 * Google登录，需要用浏览器打开 Google云端平台：https://console.developers.google.com/apis/credentials?project=_
 * 1.创建网页凭据
 *
 * Twitter登录，需要提供：twitter_consumer_key和twitter_consumer_secret，填入 gradle.properties
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
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestServerAuthCode(getString(R.string.google_web_client_id))
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
        window.setGravity(Gravity.CENTER)
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


    override fun getFacebookLoginButton(): LoginButton? {
        if (loginButton == null) {
            loginButton = LoginButton(this)
        }
        return loginButton
    }

    override fun getFacebookCallbackManager(): CallbackManager? {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create()
        }
        return callbackManager
    }

    override fun getCurrentNoExpiredToken(): String {
        var accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        var token = ""
        if (accessToken == null) return token
        if (accessToken.isExpired) {
            //如果已经过期，退出登录
            var loginManager: LoginManager? = LoginManager.getInstance()
            loginManager?.logOut()
            loginManager = null
            return token
        }
        token = accessToken.token
        accessToken = null
        return token
    }

    /**
     * Facebook登录结果回调
     */
    override fun onSuccess(loginResult: LoginResult) {
        //使用Facebook的token请求我们服务器上的token
        val token = loginResult.accessToken.token
        loginPresenter?.loginAndFinish("facebook", token, null, null)
    }

    override fun onCancel() {
        LogUtils.d("facebook onCancel")
    }

    override fun onError(error: FacebookException) {
        LogUtils.e("facebook error:$error")
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

    override fun setBtnSendCodeText(stringResId: Int) {
        if (null != resources) {
            setBtnSendCodeText(resources.getString(stringResId))
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        LogUtils.e("Google Login onConnectionFailed:$connectionResult")
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
        loginPresenter?.loginByTwitter()
    }

    protected fun loginByPhoneOrEmail() {
        loginPresenter?.loginByPhoneOrEmail()
    }

    protected fun sendCode() {
        loginPresenter?.sendCode()
    }

    /**
     * 设置弹窗宽高百分比
     */
    private fun setScreenDisplay(widthPercent: Double, heightPercent: Double) {
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
