package com.base.muslim.login.common.api

import com.base.library.rxRetrofit.api.BaseApi
import io.reactivex.Observable

/**
 * Description:
 * 短信/邮箱登录时，发送登录验证码Api
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
class PostVerificationCodeApi constructor(private val t: String,
                                          private val account: String) : BaseApi() {
    override fun getObservable(): Observable<*> {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.postVerificationCode(t, account)
    }
}