package com.base.muslim.login.mvp

import android.content.Intent
import com.base.clawin.base.mvp.IBasePresenter
import com.base.clawin.base.mvp.IBaseView
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.api.GoogleApiClient
import com.twitter.sdk.android.core.identity.TwitterLoginButton

/**
 * Description:
 * 登录契约类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/10/9
 */
class LoginContract {
    interface View : IBaseView {
        /**Google登录*/
        /**获取GoogleApiClient*/
        fun getGoogleApiClient(): GoogleApiClient

        /**发起Google登录请求，在onActivityResult中收到登录结果*/
        fun startGoogleLoginActivityForResult(signInIntent: Intent)

        /**Facebook登录*/
        /**获取Facebook登录按钮*/
        fun getFacebookLoginButton(): LoginButton?

        /**获取Facebook CallbackManager*/
        fun getFacebookCallbackManager(): CallbackManager?

        /**获取当前未过期的facebook token*/
        fun getCurrentNoExpiredToken(): String

        /**Twitter登录*/
        /**获取Twitter登录按钮*/
        fun getTwitterLoginButton(): TwitterLoginButton?
    }

    interface Presenter : IBasePresenter<View> {

        /**Facebook登录*/
        fun loginByFacebook()

        /**Twitter登录*/
        fun loginByTwitter()

        /**Google登录*/
        fun loginByGoogle()

        /**在Presenter中处理Google登录回调*/
        fun handleGoogleLoginResult(data: Intent?)
    }
}