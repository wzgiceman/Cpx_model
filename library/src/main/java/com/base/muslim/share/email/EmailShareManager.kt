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

//    /*** 设置收件人地址信息*/
//    private void setRecipientT0() throws MessagingException, UnsupportedEncodingException {
//        if (recipientT0List.size() > 0) {
//            InternetAddress[] sendTo = new InternetAddress[recipientT0List.size()];
//            for (int i = 0; i < recipientT0List.size(); i++) {
//                System.out.println("发送到:" + recipientT0List.get(i));
//                sendTo[i] = new InternetAddress(recipientT0List.get(i), "", "UTF-8");
//            }
//            message.addRecipients(MimeMessage.RecipientType.TO, sendTo);
//        }
//    }
//
//    /***设置密送地址**/
//    private void setRecipientCC() throws MessagingException, UnsupportedEncodingException {
//        if (recipientCCList.size() > 0) {
//            InternetAddress[] sendTo = new InternetAddress[recipientCCList.size()];
//            for (int i = 0; i < recipientCCList.size(); i++) {
//                System.out.println("发送到:" + recipientCCList.get(i));
//                sendTo[i] = new InternetAddress(recipientCCList.get(i), "", "UTF-8");
//            }
//            message.addRecipients(MimeMessage.RecipientType.CC, sendTo);
//        }
//    }
//
//    /***设置抄送邮件地址**/
//    private void setRecipientBCC() throws MessagingException, UnsupportedEncodingException {
//        if (recipientBCCList.size() > 0) {
//            InternetAddress[] sendTo = new InternetAddress[recipientBCCList.size()];
//            for (int i = 0; i < recipientBCCList.size(); i++) {
//                System.out.println("发送到:" + recipientBCCList.get(i));
//                sendTo[i] = new InternetAddress(recipientBCCList.get(i), "", "UTF-8");
//            }
//            message.addRecipients(MimeMessage.RecipientType.BCC, sendTo);
//        }
//    }

}