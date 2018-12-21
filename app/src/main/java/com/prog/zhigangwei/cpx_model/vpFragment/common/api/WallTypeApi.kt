package com.prog.zhigangwei.cpx_model.vpFragment.common.api

import com.base.library.rxRetrofit.api.BaseApi
import io.reactivex.Observable


/**
 *
 *Describe:主界面壁纸分类
 *
 *Created by zhigang wei
 *on 2018/5/8
 *
 *Company :cpx
 */
class WallTypeApi : BaseApi() {

    init {
        baseUrl="https://url.cn/"
        isCache = true
        isIgnoreJudge=true
        method="5pLsHup"
    }

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(ApiService::class.java)
        return httpService.loadHomeType()
    }

}
