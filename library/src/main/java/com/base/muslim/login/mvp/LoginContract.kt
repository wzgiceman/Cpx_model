package com.base.muslim.login.mvp

import android.content.Intent
import android.support.annotation.StringRes
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
        /**获取完整手机号或者邮箱*/
        fun getAccount(): String?

        /**获取验证码*/
        fun getVerificationCode(): String?

        /**Google登录*/
        fun getGoogleApiClient(): GoogleApiClient

        fun startGoogleLoginActivityForResult(signInIntent: Intent)

        /**Facebook登录*/
        fun createFacebookLoginButton(): LoginButton?

        fun createFacebookCallbackManager(): CallbackManager?

        fun getCurrentNoExpiredToken(): String

        /**Twitter登录*/
        fun getTwitterLoginButton(): TwitterLoginButton?

        /**设置sendCode按钮是否可用*/
        fun setBtnSendCodeEnabled(enabled: Boolean)

        /**设置sendCode按钮文字*/
        fun setBtnSendCodeText(text: String)

        fun setBtnSendCodeText(@StringRes stringResId: Int)

        /**登录成功，关闭登录弹窗*/
        fun setResultAndFinish(resultCode: Int)

    }

    interface Presenter : IBasePresenter<View> {
        /**手机/邮箱登录*/
        fun loginByPhoneOrEmail()

        /**发送验证码*/
        fun sendCode(type: String)

        /**Facebook登录*/
        fun loginByFacebook()

        /**Twitter登录*/
        fun loginByTwitter()

        /**Google登录*/
        fun loginByGoogle()

        fun handleGoogleLoginResult(data: Intent?)

        /**Http onNext回调，到达Presenter层处理*/
        fun onNext(resulte: String, method: String)

        /**登录成功，关闭登录弹窗*/
        fun loginAndFinish(type: String, loginMethodToken: String?, account: String?, secret: String?)
    }
}