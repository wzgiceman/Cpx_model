package com.base.library.share.common.constants

/**
 * Description:
 * 分享常量类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
class ShareConstants {
    companion object {
        /**Facebook分享方式*/
        const val FACEBOOK = "facebook"
        /**Twitter分享方式*/
        const val TWITTER = "twitter"
        /**Email分享方式*/
        const val EMAIL = "email"
        /**短信分享方式*/
        const val SMS = "sms"
        /**邮件分享RequestCode*/
        const val REQUEST_CODE_SEND_EMAIL = 1001
        /**短信分享RequestCode*/
        const val REQUEST_CODE_SEND_SMS = 1002
    }
}