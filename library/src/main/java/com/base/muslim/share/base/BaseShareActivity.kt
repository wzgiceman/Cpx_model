package com.base.muslim.share.base

import android.content.Intent
import android.net.Uri
import com.base.muslim.base.activity.BaseToolsActivity
import com.base.muslim.share.ShareManager
import com.base.muslim.share.common.listener.OnShareListener

/**
 * Description:
 * 分享基类，可以继承此类实现分享，或者仿照此类中的方法调用ShareManager
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
abstract class BaseShareActivity : BaseToolsActivity(), OnShareListener {

    private val shareManager by lazy { ShareManager(this, this) }

    fun shareText(type: String, text: String) {
        shareManager.shareText(type, text)
    }

    @JvmOverloads
    fun shareLink(type: String, link: String, tag: String = "", quote: String = "") {
        shareManager.shareLink(type, link, tag, quote)
    }

    @JvmOverloads
    fun shareImage(type: String, image: Any, tag: String = "") {
        shareManager.shareImage(type, image, tag)
    }

    @JvmOverloads
    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        shareManager.shareVideo(type, videoUri, tag)
    }

    @JvmOverloads
    fun shareMedia(type: String, imageList: List<Any>, videoUriList: List<Uri>, tag: String = "") {
        shareManager.shareMedia(type, imageList, videoUriList, tag)
    }

    @JvmOverloads
    fun sendEmail(emailBody: String = "", emailSubject: String = "") {
        shareManager.sendEmail(emailBody, emailSubject)
    }

    @JvmOverloads
    fun sendImageEmail(image: Any, emailBody: String = "", emailSubject: String = "") {
        shareManager.sendImageEmail(image, emailBody, emailSubject)
    }

    @JvmOverloads
    fun sendVideoEmail(video: Uri, emailBody: String = "", emailSubject: String = "") {
        shareManager.sendVideoEmail(video, emailBody, emailSubject)
    }

    @JvmOverloads
    fun sendMediaEmail(imageList: List<Any> = ArrayList(), videoList: List<Uri> = ArrayList(), emailBody: String = "", emailSubject: String = "") {
        shareManager.sendMediaEmail(imageList, videoList, emailBody, emailSubject)
    }

    @JvmOverloads
    fun sendSMS(smsBody: String = "", phoneNumber: String = "") {
        shareManager.sendSMS(smsBody, phoneNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        shareManager.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onRelease() {
        super.onRelease()
        shareManager.release()
    }
}