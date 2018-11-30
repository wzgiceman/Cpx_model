package com.prog.zhigangwei.cpx_model.http.down.down

/**
 *
 *Describe:网络变动回掉处理
 *
 *Created by zhigang wei
 *on 2018/11/30
 *
 *Company :cpx
 */
interface NetWorkConnectChangeListener {

    /**
     * 网络变动回掉处理
     */
    fun networkChangeListener(connect: Boolean)
}