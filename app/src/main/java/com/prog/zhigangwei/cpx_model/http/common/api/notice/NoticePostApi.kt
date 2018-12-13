package com.prog.zhigangwei.cpx_model.http.common.api.notice

import com.base.library.rxRetrofit.api.BaseApi
import com.base.library.utils.utilcode.util.DeviceUtils
import com.prog.zhigangwei.cpx_model.http.common.api.ApiService
import io.reactivex.Observable

/**
 * 通知中通过post请求发送token到服务器的api
 */

class NoticePostApi constructor(var token: String?) : BaseApi() {
    val terminalType: String
    val deviceId: String

    init {
        isShowProgress = false
        terminalType = "android"
        isCache=false
        method="message/message/userTerminal"
        deviceId = DeviceUtils.getMacAddress()
    }

    override fun getObservable(): Observable<*> {
        val service = retrofit!!.create(ApiService::class.java)
        return service.postNotice(this)
    }
}