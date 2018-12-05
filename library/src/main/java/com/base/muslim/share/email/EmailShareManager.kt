package com.base.muslim.share.email

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import com.base.muslim.share.common.constants.ShareConstants.Companion.EMAIL
import com.base.muslim.share.common.constants.ShareConstants.Companion.REQUEST_CODE_SEND_EMAIL
import com.base.muslim.share.common.listener.OnShareListener

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
    fun sendEmail(emailBody: String, emailSubject: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject)
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody)
        activity.startActivityForResult(Intent.createChooser(email, "Choose App"), REQUEST_CODE_SEND_EMAIL)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if(requestCode == REQUEST_CODE_SEND_EMAIL){
            when (resultCode) {
                RESULT_OK -> onShareListener.onShareSuccess(EMAIL)
                RESULT_CANCELED -> onShareListener.onShareFail(EMAIL,"Email share cancel")
                else -> onShareListener.onShareFail(EMAIL,"Email share fail")
            }
        }
    }
}