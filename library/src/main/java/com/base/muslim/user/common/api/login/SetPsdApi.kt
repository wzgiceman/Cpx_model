package com.base.muslim.user.common.api.login

import com.base.library.retrofit_rx.Api.BaseApi
import com.base.muslim.user.common.api.UserService
import retrofit2.Retrofit
import io.reactivex.Observable


/**
 *
 *Describe:设置密码
 *
 *Created by zhigang wei
 *on 2018/5/8
 *
 *Company :cpx
 */
class SetPsdApi constructor(var email: String, val password: String, val check_code: String) : BaseApi() {

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(UserService::class.java)
        return httpService.setPsd(this)
    }

}
