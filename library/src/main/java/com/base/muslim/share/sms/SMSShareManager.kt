package com.base.muslim.share.sms

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.base.muslim.share.common.constants.ShareConstants.Companion.REQUEST_CODE_SEND_SMS
import com.base.muslim.share.common.constants.ShareConstants.Companion.SMS
import com.base.muslim.share.common.listener.OnShareListener

/**
 * Description:
 * 短信分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class SMSShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {

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
        activity.startActivityForResult(sendIntent, REQUEST_CODE_SEND_SMS)
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