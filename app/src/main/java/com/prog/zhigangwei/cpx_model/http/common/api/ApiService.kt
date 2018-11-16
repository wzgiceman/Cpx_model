package com.prog.zhigangwei.cpx_model.http.common.api

import io.reactivex.Observable
import retrofit2.http.*


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
    @FormUrlEncoded
    @POST("message/message/userTerminal")
    fun postNotice(@Field("token")token:String?, @Field("terminal_type")terminalType:String, @Field("device_id")deviceId:String): Observable<String>
}