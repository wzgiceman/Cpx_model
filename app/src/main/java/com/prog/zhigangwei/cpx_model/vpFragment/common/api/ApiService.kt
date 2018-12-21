package com.prog.zhigangwei.cpx_model.vpFragment.common.api

import io.reactivex.Observable
import retrofit2.http.GET


/**
 *
 *Describe:接口
 *
 *Created by zhigang wei
 *on 2018/5/11
 *
 *Company :cpx
 */
interface ApiService {
    /**
     *获取首页壁纸分类
     */
    @GET("5pLsHup")
    fun loadHomeType(): Observable<String>

    /**
     *获取壁纸
     */
    @GET("wallpapers?q%5Btags_name_eq%5D=Beauty&q[s]=hot%20desc&page=1&package_name=com.acg.master.wallpapers&version_code=21&version_name=1.0.2.1&channel=google&sign=6756494ab7a09796f7fdc9c114df5c0a&platform=android&lang=zh&country=CN&sim")
    fun loadWallDetail(): Observable<String>

}