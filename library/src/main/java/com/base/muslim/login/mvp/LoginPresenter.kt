package com.base.muslim.login.mvp

import android.content.Intent
import android.text.TextUtils
import com.base.clawin.base.mvp.BasePresenter
import com.base.library.R
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.muslim.login.common.api.LoginApi
import com.base.muslim.login.common.api.PostVerificationCodeApi
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Description:
 * 登录Presenter
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
class LoginPresenter(private val httpManager: HttpManager) : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private var loginApi: LoginApi? = null
    private var postVerificationCodeApi: PostVerificationCodeApi? = null
    private var disposable: Disposable? = null

    /**
     * facebook登录
     */
    override fun loginByFacebook() {
        //如果facebookToken存在且没有过期
        if (!TextUtils.isEmpty(rootView?.getCurrentNoExpiredToken())) {
            loginAndFinish("facebook", rootView?.getCurrentNoExpiredToken(), null, null)
        } else {
            //使用模拟点击Facebook LoginButton实现登录，否则会出现很多弹窗，导致界面不整洁
            rootView?.createFacebookLoginButton()?.registerCallback(rootView?.createFacebookCallbackManager(), rootView as FacebookCallback<LoginResult>)
            rootView?.createFacebookLoginButton()?.callOnClick()
        }
    }

    /**
     * Google登录
     */
    override fun loginByGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(rootView?.getGoogleApiClient())
        rootView?.startGoogleLoginActivityForResult(signInIntent)
    }

    /**
     * 获取到google登录的token后，进行登录
     */
    override fun handleGoogleLoginResult(data: Intent?) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        val account = result.signInAccount ?: return//得到登录账户
        loginAndFinish("google", account.idToken, null, null)
    }

    /**
     * twitter登录
     */
    override fun loginByTwitter() {
        LogUtils.d("override fun loginByTwitter() {")
        rootView?.getTwitterLoginButton()?.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                LogUtils.d("twitter success,token:${result?.data?.authToken?.token},secret:${result?.data?.authToken?.secret}")
                loginAndFinish("twitter", result?.data?.authToken?.token, null, result?.data?.authToken?.secret)
            }

            override fun failure(exception: TwitterException?) {
                LogUtils.e(exception)
            }

        }
        rootView?.getTwitterLoginButton()?.callOnClick()
    }

    /**
     * 手机或邮箱登录,若account包含'@'则为邮箱登录，否则为手机登录
     */
    override fun loginByPhoneOrEmail() {
        val account = rootView?.getAccount()
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort(R.string.account_cannot_be_empty)
            return
        }
        val verificationCode = rootView?.getVerificationCode()
        if (TextUtils.isEmpty(verificationCode)) {
            ToastUtils.showShort(R.string.please_input_verification_code)
            return
        }
        var type = "phone"
        if (account!!.contains("@")) {
            type = "email"
        }
        loginAndFinish(type, verificationCode, account, null)
    }

    /**
     * 发送验证码
     */
    override fun sendCode(type: String) {
        val account = rootView?.getAccount()
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort(R.string.account_cannot_be_empty)
            return
        }
        rootView?.setBtnSendCodeEnabled(false)
        //发送验证码
        postVerificationCodeApi = PostVerificationCodeApi(type, account!!)
        httpManager.doHttpDeal(postVerificationCodeApi)
        disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it >= 60L) {
                        rootView?.setBtnSendCodeText(R.string.send_code)
                        rootView?.setBtnSendCodeEnabled(true)
                        disposable?.dispose()
                    } else {
                        rootView?.setBtnSendCodeText(MessageFormat.format("{0}s", 60 - it))
                    }
                }
        if (disposable != null) {
            addSubscription(disposable!!)
        }
    }

    /**
     * 登录，使用第三方的token请求我们服务器上的token
     */
    override fun loginAndFinish(type: String, loginMethodToken: String?, account: String?, secret: String?) {
        val fields = HashMap<String, String>()
        if (!TextUtils.isEmpty(loginMethodToken)) {
            fields["token"] = loginMethodToken!!
        }
        if (!TextUtils.isEmpty(type)) {
            fields["t"] = type
        }
        if (!TextUtils.isEmpty(account)) {
            fields["account"] = account!!
        }
        if (!TextUtils.isEmpty(secret)) {
            fields["tokenSecret"] = secret!!
        }
        //获取到token和用户信息后关闭页面
        loginApi = LoginApi(fields)
        httpManager.doHttpDeal(loginApi)
    }

    /**
     * httpManager onNext回调，发送到Presenter层处理
     */
    override fun onNext(resulte: String, method: String) {
        when (method) {
            loginApi?.method -> {
//                val userInfo = JSONObject.parseObject(resulte, UserInfo::class.java) ?: return
//                if (!TextUtils.isEmpty(userInfo.token)) {
//                    SPUtils.getInstance().put(Constants.TOKEN, userInfo.token)
//                    BaseApi.setConfig(userInfo.token)
//                }
            }
        }
    }
}
