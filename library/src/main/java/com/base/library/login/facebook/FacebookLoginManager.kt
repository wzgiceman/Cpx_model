package com.base.library.login.facebook

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.library.login.common.listener.OnLoginListener
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

/**
 * Description:
 * Facebook登录管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class FacebookLoginManager(private val context: Context, private val onLoginListener: OnLoginListener) : FacebookCallback<LoginResult> {
    private val callbackManager by lazy { CallbackManager.Factory.create() }
    private val loginButton by lazy { LoginButton(context) }

    /**
     * Facebook登录
     */
    fun login() {
        val currentToken = getCurrentNotExpiredFacebookToken()
        if (TextUtils.isEmpty(currentToken)) {
            loginButton.registerCallback(callbackManager, this)
            loginButton.callOnClick()
        } else {
            onLoginListener.onLoginSuccess(FACEBOOK, LoginAuth(currentToken))
        }
    }

    /**
     * 获取当前未过期的Facebook Token
     */
    private fun getCurrentNotExpiredFacebookToken(): String {
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

        onLoginListener.onLoginSuccess(FACEBOOK, LoginAuth(result.accessToken.token))
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

    fun release() {
        loginButton.unregisterCallback(callbackManager)
    }
}