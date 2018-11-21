package com.prog.zhigangwei.cpx_model.http.common.api.LanguageApi

import com.base.library.rxRetrofit.api.BaseApi
import com.prog.zhigangwei.cpx_model.http.common.api.ApiService
import io.reactivex.Observable


/**
 *
 *Describe:语言对象
 *
 *Created by zhigang wei
 *on 2018/5/8
 *
 *Company :cpx
 */
class LanguagesApi : BaseApi() {

    init {
        /*必须提前将数据放入到base、assets/gson 文件命名根据mehtod定义的方法命名*/
        method = "languages"
        isCache = true
        cookieNetWorkTime = 3600
        retryCount=0
        baseUrl = "http://ss.afr99.com/"
    }

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(ApiService::class.java)
        return httpService.loadLg()
    }


}
