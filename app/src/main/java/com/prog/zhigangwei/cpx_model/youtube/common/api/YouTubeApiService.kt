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
interface YouTubeApiService {
    /**
     * 获取YouTubeVideo列表
     */
    @GET("youtube/video/find/{category}/{page_token}/{limit}")
    fun getVideos(@Path("category") category: String?,
                  @Path("page_token") pageToken: String,
                  @Path("limit") limit: Int): Observable<String>

    /**
     * 获取YouTube视频评论列表
     */
    @GET("youtube/comment/find/{video_id}/{page_token}/{limit}")
    fun getVideoComments(@Path("video_id") videoId: String?,
                         @Path("page_token") pageToken: String,
                         @Path("limit") limit: Int): Observable<String>

    /**
     * 获取YouTube二级评论列表
     */
    @GET("youtube/comment/findChild/{parent_id}/{page_token}/{limit}")
    fun getVideoChildComments(@Path("parent_id") parentId: String?,
                              @Path("page_token") pageToken: String,
                              @Path("limit") limit: Int): Observable<String>
}