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
class WallDetailApi(var bless_blog_id:String) : BaseApi() {

    init {
        baseUrl="http://api.acgmonster.com/shine/"
        cacheUrl="$url/$bless_blog_id"
        isCache=false
        isShowProgress=false
        isIgnoreJudge=true
    }

    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(ApiService::class.java)
        return httpService.loadWallDetail()
    }

}
