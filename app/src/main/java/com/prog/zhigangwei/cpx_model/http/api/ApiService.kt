package com.prog.zhigangwei.cpx_model.http.api

import com.prog.zhigangwei.cpx_model.http.api.notice.NoticePostApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable


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
     *获取首页壁纸
     */
    @GET("index_wallpapers")
    fun loadHomeWall(): Observable<String>


    /**
     * 发送通知需要的tokean信息到服务器
     */
    @POST("message/message/userTerminal")
    fun postNotice(@Body notic: NoticePostApi): Observable<String>
}