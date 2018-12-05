package com.base.muslim.login.facebook

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.listener.OnLoginListener
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class FacebookLoginManager(val context: Context, val onLoginListener: OnLoginListener) : FacebookCallback<LoginResult> {
    val callbackManager by lazy {  CallbackManager.Factory.create()}
    fun loginByFacebook() {
        val currentToken = getCurrentNotExpiredFacebookToken()
        if (TextUtils.isEmpty(currentToken)) {
            val loginButton = LoginButton(context)
            loginButton.registerCallback(callbackManager, this)
            loginButton.callOnClick()
        } else {
            onLoginListener.onLoginSuccess(FACEBOOK, currentToken)
        }
    }

    /**
     * 获取当前未过期的Facebook Token
     */
    fun getCurrentNotExpiredFacebookToken(): String {
        val accessToken: AccessToken = AccessToken.getCurrentAccessToken() ?: return ""
        if (accessToken.isExpired) {
            //如果已经过期，退出登录
            val loginManager: LoginManager? = LoginManager.getInstance()
            loginManager?.logOut()
            return ""
        }
        return accessToken.token
    }

    override fun onSuccess(result: LoginResult?) {
        if (result == null || result.accessToken == null || TextUtils.isEmpty(result.accessToken.token)) {
            onLoginListener.onLoginFail(FACEBOOK, "Facebook Login fail, token is null")
            return
        }
        onLoginListener.onLoginSuccess(FACEBOOK, result.accessToken.token)
    }

    override fun onCancel() {
        onLoginListener.onLoginFail(FACEBOOK, "Facebook login cancel")
    }

    override fun onError(error: FacebookException?) {
        onLoginListener.onLoginFail(FACEBOOK, "Facebook login fail:$error")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}