package com.base.muslim.login

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.base.library.R
import com.base.muslim.login.common.constants.LoginConstants
import com.base.muslim.login.common.constants.LoginConstants.Companion.GOOGLE
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class GoogleLoginManager(val fragmentActivity: FragmentActivity, val onLoginListener: OnLoginListener) :
        GoogleApiClient.OnConnectionFailedListener {


    /**
     * google登录
     */
    private val signInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(fragmentActivity.getString(R.string.google_web_client_id))
                .requestServerAuthCode(fragmentActivity.getString(R.string.google_web_client_id))
                .requestEmail()
                .build()
    }
    private val apiClient by lazy {
        GoogleApiClient.Builder(fragmentActivity)
                .enableAutoManage(fragmentActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build()
    }

    fun loginByGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
        fragmentActivity.startActivityForResult(signInIntent, LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN) {
            val token = data?.let {
                Auth.GoogleSignInApi.getSignInResultFromIntent(it).signInAccount?.idToken
            }
            if (token == null || TextUtils.isEmpty(token)) {
                onLoginListener.onLoginFail(GOOGLE, "Google Login fail, token is null")
                return
            }
            onLoginListener.onLoginSuccess(GOOGLE, token)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        onLoginListener.onLoginFail(GOOGLE, "Google login fail:$connectionResult")
    }

}