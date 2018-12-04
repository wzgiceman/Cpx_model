package com.base.muslim.login.mvp

import android.content.Intent
import android.text.TextUtils
import com.base.clawin.base.mvp.BasePresenter
import com.base.library.utils.utilcode.util.LogUtils
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

/**
 * Description:
 * 登录Presenter
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    /**
     * facebook登录
     */
    override fun loginByFacebook() {
        //如果facebookToken存在且没有过期
        if (!TextUtils.isEmpty(rootView?.getCurrentNoExpiredToken())) {
//            loginAndFinish(FACEBOOK, rootView?.getCurrentNoExpiredToken(), null, null)
        } else {
            //使用模拟点击Facebook LoginButton实现登录，否则会出现很多弹窗，导致界面不整洁
            rootView?.getFacebookLoginButton()?.registerCallback(rootView?.getFacebookCallbackManager(), rootView as FacebookCallback<LoginResult>)
            rootView?.getFacebookLoginButton()?.callOnClick()
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
//        loginAndFinish(GOOGLE, account.idToken, null, null)
    }

    /**
     * twitter登录
     */
    override fun loginByTwitter() {
        rootView?.getTwitterLoginButton()?.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
//                loginAndFinish(TWITTER, result?.data?.authToken?.token, null, result?.data?.authToken?.secret)
            }

            override fun failure(exception: TwitterException?) {
                LogUtils.e(exception)
            }

        }
        rootView?.getTwitterLoginButton()?.callOnClick()
    }
}
