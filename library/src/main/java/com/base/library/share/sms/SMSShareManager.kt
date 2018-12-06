package com.base.library.share.sms

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.base.library.share.common.constants.ShareConstants.Companion.REQUEST_CODE_SEND_SMS
import com.base.library.share.common.constants.ShareConstants.Companion.SMS
import com.base.library.share.common.listener.OnShareListener

/**
 * Description:
 * 短信分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class SMSShareManager(private val context: Context, private val onShareListener: OnShareListener) {

    /**
     * 发送短信
     * @param smsBody 短信内容
     * @param phoneNumber 手机号码
     */
    fun sendSMS(smsBody: String, phoneNumber: String) {
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"))
        sendIntent.putExtra("address", phoneNumber)
        sendIntent.putExtra("sms_body", smsBody)
        sendIntent.type = "vnd.android-dir/mms-sms"
        when (context) {
            is Activity -> context.startActivityForResult(Intent.createChooser(sendIntent,"Choose App"), REQUEST_CODE_SEND_SMS)
            else -> onShareListener.onShareFail(SMS,"SMS share just support Activity")
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == REQUEST_CODE_SEND_SMS) {
            when (resultCode) {
                Activity.RESULT_OK -> onShareListener.onShareSuccess(SMS)
                Activity.RESULT_CANCELED -> onShareListener.onShareFail(SMS, "SMS share cancel")
                else -> onShareListener.onShareFail(SMS, "SMS share fail")
            }
        }
    }
}