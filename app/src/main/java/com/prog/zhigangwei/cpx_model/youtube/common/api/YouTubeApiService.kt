package com.prog.zhigangwei.cpx_model.youtube.common.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description:
 * YouTubeApiService
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
interface YouTubeApiService{
    /**
     * 获取YoutubeVideo列表
     */
    @GET("youtube/find/{category}/{page_token}/{limit}")
    fun getVideo(@Path("category") category: String?,
                 @Path("page_token") pageToken: String,
                 @Path("limit") limit: Int): Observable<String>
}