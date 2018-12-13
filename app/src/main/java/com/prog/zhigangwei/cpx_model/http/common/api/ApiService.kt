package com.prog.zhigangwei.cpx_model.http.common.api

import com.prog.zhigangwei.cpx_model.http.common.api.notice.NoticePostApi
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


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
     *获取经文语言数据
     */
    @GET("languages")
    fun loadLg(): Observable<String>

    /**
     *获取首页壁纸
     */
    @GET("index_wallpapers")
    fun loadHomeWall(): Observable<String>


    /**
     * 发送通知需要的tokean信息到服务器
     */
    @POST("message/message/userTerminal")
    fun postNotice(@Body api:NoticePostApi): Observable<String>
}