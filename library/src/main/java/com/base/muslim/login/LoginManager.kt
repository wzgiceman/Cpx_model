package com.base.muslim.login

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.constants.LoginConstants.Companion.GOOGLE
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class LoginManager(val activity: FragmentActivity, val onLoginListener: OnLoginListener) {
    private val facebookLoginManager by lazy { FacebookLoginManager(activity, onLoginListener) }
    private val googleLoginManager by lazy { GoogleLoginManager(activity, onLoginListener) }
    private val twitterLoginManager by lazy { TwitterLoginManager(activity, onLoginListener) }

    fun loginBy(type: String) {
        when (type) {
            FACEBOOK -> facebookLoginManager.loginByFacebook()
            GOOGLE -> googleLoginManager.loginByGoogle()
            TWITTER -> twitterLoginManager.loginByTwitter()
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookLoginManager.handleActivityResult(requestCode, resultCode, data)
        googleLoginManager.handleActivityResult(requestCode, resultCode, data)
        twitterLoginManager.handleActivityResult(requestCode, resultCode, data)
    }
}