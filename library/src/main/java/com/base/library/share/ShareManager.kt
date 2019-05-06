package com.base.library.share

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.base.library.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.library.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.email.EmailShareManager
import com.base.library.share.facebook.FacebookShareManager
import com.base.library.share.sms.SMSShareManager
import com.base.library.share.twitter.TwitterShareManager


/**
 * Description:
 * 分享管理类
 *
 * 1.Facebook分享：支持facebook分享文字、链接、图片、视频、多媒体
 *  facebook分享链接时，通过tag添加文字，通过quote添加引文
 *  facebook只支持分享本地视频
 *  facebook分享多媒体时，图片+视频总数最多6个
 *  分享图片、视频、多媒体时，可以通过 tag 添加文字
 *
 * 2.Twitter分享：支持Twitter分享文字、图片
 *  分享图片时，可以通过 tag 添加文字
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareManager {
    private var facebookShareManager: FacebookShareManager? = null
    private var twitterShareManager: TwitterShareManager? = null
    private var emailShareManager: EmailShareManager? = null
    private var smsShareManager: SMSShareManager? = null
    private var contextHolder: Any
    private var onShareListener: OnShareListener

    constructor(activity: Activity, onShareListener: OnShareListener) {
        contextHolder = activity
        this.onShareListener = onShareListener
    }

    constructor(fragment: Fragment, onShareListener: OnShareListener) {
        contextHolder = fragment
        this.onShareListener = onShareListener
    }

    private fun getFacebookShareManager(): FacebookShareManager? {
        if (facebookShareManager == null)
            facebookShareManager = FacebookShareManager(contextHolder, onShareListener)
        return facebookShareManager
    }

    private fun getTwitterShareManager(): TwitterShareManager? {
        if (twitterShareManager == null)
            twitterShareManager = when (contextHolder) {
                is Activity -> TwitterShareManager(contextHolder as Activity, onShareListener)
                is Fragment -> TwitterShareManager((contextHolder as Fragment).context, onShareListener)
                else -> null
            }
        return twitterShareManager
    }

    private fun getEmailShareManager(): EmailShareManager? {
        if (emailShareManager == null)
            emailShareManager = EmailShareManager(contextHolder, onShareListener)
        return emailShareManager
    }

    private fun getSMSShareManager(): SMSShareManager? {
        if (smsShareManager == null)
            smsShareManager = SMSShareManager(contextHolder, onShareListener)
        return smsShareManager
    }

    fun shareText(type: String, text: String) {

        when (type) {
            FACEBOOK -> getFacebookShareManager()?.shareText(text)
            TWITTER -> getTwitterShareManager()?.shareText(text)
        }
    }

    fun shareLink(type: String, link: String, tag: String, quote: String) {
        when (type) {
            FACEBOOK -> getFacebookShareManager()?.shareLink(link, tag, quote)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support link yet")
        }
    }

    fun shareImage(type: String, image: Any?, tag: String = "") {
        when (type) {
            FACEBOOK -> getFacebookShareManager()?.shareImage(image, tag)
            TWITTER -> getTwitterShareManager()?.shareImage(image, tag)
        }
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        when (type) {
            FACEBOOK -> getFacebookShareManager()?.shareVideo(videoUri, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support video yet")
        }
    }

    fun shareMedia(type: String, imageList: List<Any>, videoUriList: List<Uri>, tag: String) {
        when (type) {
            FACEBOOK -> getFacebookShareManager()?.shareMedia(imageList, videoUriList, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support media yet")
        }
    }

    fun sendEmail(emailBody: String = "", emailSubject: String = "") {
        getEmailShareManager()?.sendTextEmail(emailBody, emailSubject)
    }

    fun sendImageEmail(image: Any = Uri.EMPTY, emailBody: String = "", emailSubject: String = "") {
        getEmailShareManager()?.sendImageEmail(image, emailBody, emailSubject)
    }

    fun sendVideoEmail(video: Uri = Uri.EMPTY, emailBody: String = "", emailSubject: String = "") {
        getEmailShareManager()?.sendVideoEmail(video, emailBody, emailSubject)
    }

    fun sendMediaEmail(imageList: List<Any> = ArrayList(), videoList: List<Uri> = ArrayList(), emailBody: String = "", emailSubject: String = "") {
        getEmailShareManager()?.sendMediaEmail(imageList, videoList, emailBody, emailSubject)
    }

    fun sendSMS(smsBody: String = "", phoneNumber: String = "") {
        getSMSShareManager()?.sendSMS(smsBody, phoneNumber)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager?.handleActivityResult(requestCode, resultCode, data)
        emailShareManager?.handleActivityResult(requestCode, resultCode)
        smsShareManager?.handleActivityResult(requestCode, resultCode)
    }

    fun release() {
        twitterShareManager?.release()
    }

}