package com.base.library.share.twitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.base.library.rxbus.RxBus

/**
 * Description:
 * Twitter分享结果广播接收器
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class TwitterResultReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        RxBus.get().post(intent.action)
    }
}