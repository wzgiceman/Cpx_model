package com.prog.zhigangwei.cpx_model.http.down.down

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.base.library.utils.utilcode.util.NetworkUtils

/**
 *
 *Describe:
 * 网络改变监控广播
 * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
 * 然后对相应的界面进行相应的操作，并将 状态 保存在我们的APP里面
 *
 *Created by zhigang wei
 *on 2018/11/30
 *
 *Company :cpx
 */

class NetworkConnectChangedReceiver(private val listener: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        listener(NetworkUtils.isConnected())
    }

}