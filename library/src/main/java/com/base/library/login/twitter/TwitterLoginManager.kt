package com.base.muslim.login.twitter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.base.muslim.login.common.bean.LoginAuth
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.muslim.login.common.listener.OnLoginListener
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton

/**
 * Description:
 * Twitter登录管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterLoginManager(context: Context, private val onLoginListener: OnLoginListener) : Callback<TwitterSession>() {

    private val twitterLoginButton = TwitterLoginButton(context)

    fun login() {
        twitterLoginButton.callback = this
        twitterLoginButton.callOnClick()
    }

    override fun success(result: Result<TwitterSession>?) {
        val token = result?.data?.authToken?.token
        val secret = result?.data?.authToken?.secret
        if (token == null || TextUtils.isEmpty(token)) {
            onLoginListener.onLoginFail(TWITTER, "Twitter Login fail, token is null")
            return
        }
        if (secret == null || TextUtils.isEmpty(secret)) {
            onLoginListener.onLoginFail(TWITTER, "Twitter Login fail, secert is null")
            return
        }
        onLoginListener.onLoginSuccess(TWITTER, LoginAuth(token, secret))
    }

    override fun failure(exception: TwitterException?) {
        onLoginListener.onLoginFail(TWITTER, "Twitter login fail:$exception")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }
}