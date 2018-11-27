package com.base.muslim.login.common.constants

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
class LoginConstants {
    companion object {
        /**Google登录RequestCode*/
        const val REQUEST_CODE_GOOGLE_SIGN_IN = 9001
        /**获取验证码倒计时时间*/
        const val VERIFICATION_CODE_TIME = 60L
        /**手机号登录方式*/
        const val PHONE = "phone"
        /**邮箱登录方式*/
        const val EMAIL = "email"
        /**Facebook登录方式*/
        const val FACEBOOK = "facebook"
        /**Google登录方式*/
        const val GOOGLE = "google"
        /**Twitter登录方式*/
        const val TWITTER = "twitter"
    }
}