package com.base.muslim.login.common.api

import com.base.library.rxRetrofit.api.BaseApi
import io.reactivex.Observable

/**
 * Description:
 * 登录Api
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
class LoginApi constructor(private val fields: HashMap<String, String>) : BaseApi() {
    init {
        isShowProgress = true
    }

    override fun getObservable(): Observable<*> {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.getLogin(fields)
    }
}