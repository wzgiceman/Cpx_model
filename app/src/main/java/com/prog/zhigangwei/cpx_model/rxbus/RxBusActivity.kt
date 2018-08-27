package com.prog.zhigangwei.cpx_model.rxbus

import com.base.library.rxbus.RxBus
import com.base.library.rxbus.annotation.Subscribe
import com.base.library.rxbus.thread.EventThread
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.rxbus.event.SendEvent
import kotlinx.android.synthetic.main.activity_rxbus.*

/**
 *
 *Describe:RxBus使用
 *
 *Created by zhigang wei
 *on 2018/8/24
 *
 *Company :cpx
 */
class RxBusActivity : BaseFragmentActivity() {


    override fun setContentViews() {
        setContentView(R.layout.activity_rxbus)
        super.setContentViews()
    }

    override fun initResource() {
        RxBus.get().register(this)
    }

    override fun initWidget() {
        btn_send.setOnClickListener {
            RxBus.get().post(SendEvent("发送消息"))
        }
    }

    /**
     * 刷新当前位置数据
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun updateScRecord(event: SendEvent) {
        tv_name.setText(event.msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

}
