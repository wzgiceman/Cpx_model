package com.prog.zhigangwei.cpx_model.youtube.common.api

import com.alibaba.fastjson.JSONObject
import com.base.library.rxRetrofit.api.BaseApi
import com.prog.zhigangwei.cpx_model.youtube.common.bean.YouTubeCommentResult
import io.reactivex.Observable

/**
 * Description:
 * Youtube二级评论Api
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeChildCommentApi : BaseApi() {

    init {
        baseUrl = "http://54.153.41.63:8080/"
        isIgnoreJudge = true
        isShowProgress = false
    }

    // 父评论id
    var parentId: String = ""
    // 页数token，可不传，不传时默认为第一页，retrofit不允许path传null，所以设定为""
    var pageToken: String = ""
    // 评论个数，默认即可
    var limit = 1

    override fun getObservable(): Observable<*> {
        val apiService = retrofit.create(YouTubeApiService::class.java)
        return apiService.getVideoChildComments(parentId, pageToken, limit)
    }

    /**
     * 处理接口返回的数据
     */
    fun convert(result: String): YouTubeCommentResult? {
        return JSONObject.parseObject<YouTubeCommentResult?>(result, YouTubeCommentResult::class.java)
    }
}