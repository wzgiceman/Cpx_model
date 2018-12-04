package com.base.muslim.login.twitter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.base.muslim.login.common.constants.LoginConstants
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.muslim.login.common.listener.OnLoginListener
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterLoginManager(val context: Context, val onLoginListener: OnLoginListener) : Callback<TwitterSession>() {
    val twitterLoginButton = TwitterLoginButton(context)
    fun loginByTwitter() {
        twitterLoginButton.callback = this
        twitterLoginButton.callOnClick()
    }

    override fun success(result: Result<TwitterSession>?) {
        val token = result?.data?.authToken?.token
        if (token == null || TextUtils.isEmpty(token)) {
            onLoginListener.onLoginFail(TWITTER, "Twitter Login fail, token is null")
            return
        }
        onLoginListener.onLoginSuccess(TWITTER, token)
    }

    override fun failure(exception: TwitterException?) {
        onLoginListener.onLoginFail(LoginConstants.GOOGLE, "Twitter login fail:$exception")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }
}