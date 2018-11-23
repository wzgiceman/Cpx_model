package com.prog.zhigangwei.cpx_model.rxbus

import com.base.library.rxbus.RxBus
import com.base.library.rxbus.annotation.Subscribe
import com.base.library.rxbus.thread.EventThread
import com.base.muslim.base.activity.BaseActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.rxbus.common.event.SendEvent
import kotlinx.android.synthetic.main.activity_rxbus.*

/**
 *
 *Describe:RxBus使用
 *
 * 不推荐使用rxbus(跳跃性和逻辑性跨度太大,不易理解),能用回掉或者系统回掉处理推荐使用系统的
 *
 *Created by zhigang wei
 *on 2018/8/24
 *
 *Company :cpx
 */
class RxBusActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_rxbus

    override fun initData() {
        RxBus.get().register(this)
    }

    override fun initView() {
        btn_send.setOnClickListener {
            RxBus.get().post(SendEvent("发送消息"))
        }
    }

    /**
     * 刷新当前位置数据
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun updateScRecord(event: SendEvent) {
        tv_name.text = event.msg
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            RxBus.get().unregister(this)
        }
    }

}
