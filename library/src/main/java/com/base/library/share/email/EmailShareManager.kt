package com.base.muslim.share.email

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.muslim.share.common.constants.ShareConstants.Companion.EMAIL
import com.base.muslim.share.common.constants.ShareConstants.Companion.REQUEST_CODE_SEND_EMAIL
import com.base.muslim.share.common.listener.OnShareListener
import com.base.muslim.share.common.util.ShareUtils

/**
 * Description:
 * 邮件分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class EmailShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {
    /**
     * 发送邮件
     * @param emailBody 邮件内容
     * @param emailSubject 邮件主题
     */
    fun sendTextEmail(emailBody: String = "", emailSubject: String = "") {
        sendMediaEmail(emailBody = emailBody, emailSubject = emailSubject)
    }

    fun sendImageEmail(image: Any, emailBody: String = "", emailSubject: String = "") {
        sendMediaEmail(imageList = listOf(image), emailBody = emailBody, emailSubject = emailSubject)
    }

    fun sendVideoEmail(video: Uri, emailBody: String = "", emailSubject: String = "") {
        sendMediaEmail(videoList = listOf(video), emailBody = emailBody, emailSubject = emailSubject)
    }

    @Suppress("DEPRECATION")
    fun sendMediaEmail(imageList: List<Any> = ArrayList(), videoList: List<Uri> = ArrayList(), emailBody: String = "", emailSubject: String = "") {
        val email = Intent(Intent.ACTION_SEND_MULTIPLE)
        email.type = "application/octet-stream"
        val uriList = ArrayList<Uri>()
        for (image in imageList) {
            val imageUri = when (image) {
                is Bitmap -> ShareUtils.bitmap2Uri(image)
                is Uri -> image
                else -> Uri.EMPTY
            }
            uriList.add(imageUri)
        }
        uriList.addAll(videoList)
        email.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
        email.putExtra(Intent.EXTRA_TEXT, emailBody)
        email.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        activity.startActivityForResult(Intent.createChooser(email, "Choose App"), REQUEST_CODE_SEND_EMAIL)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == REQUEST_CODE_SEND_EMAIL) {
            ShareUtils.clearShareTempPictures()
            when (resultCode) {
                RESULT_OK -> onShareListener.onShareSuccess(EMAIL)
                RESULT_CANCELED -> onShareListener.onShareFail(EMAIL, "Email share cancel")
                else -> onShareListener.onShareFail(EMAIL, "Email share fail")
            }
        }
    }
}