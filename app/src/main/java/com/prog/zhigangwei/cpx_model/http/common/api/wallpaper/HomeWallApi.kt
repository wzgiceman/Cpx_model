package com.prog.zhigangwei.cpx_model.http.common.api.wallpaper

import com.base.library.rxRetrofit.Api.BaseApi
import com.prog.zhigangwei.cpx_model.http.common.api.ApiService
import io.reactivex.Observable


/**
 *
 *Describe:主界面壁纸
 *
 *Created by zhigang wei
 *on 2018/5/8
 *
 *Company :cpx
 */
class HomeWallApi : BaseApi() {

    init {
        method = "index_wallpapers"
//        isCache = true
        cookieNetWorkTime=20
    }

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(ApiService::class.java)
        return httpService.loadHomeWall()
    }

}
