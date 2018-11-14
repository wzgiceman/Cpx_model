package com.prog.zhigangwei.cpx_model.http.api.wallpaper

import com.base.library.rxRetrofit.api.BaseApi
import com.prog.zhigangwei.cpx_model.http.api.ApiService
import retrofit2.Retrofit
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
        cache = true
    }

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(ApiService::class.java)
        return httpService.loadHomeWall()
    }

}
