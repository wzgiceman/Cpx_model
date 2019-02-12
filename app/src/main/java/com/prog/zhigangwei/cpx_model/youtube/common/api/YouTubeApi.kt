package com.prog.zhigangwei.cpx_model.youtube.common.api

import com.alibaba.fastjson.JSONObject
import com.base.library.rxRetrofit.Api.BaseApi
import com.prog.zhigangwei.cpx_model.youtube.common.bean.YouTubeResult
import io.reactivex.Observable

/**
 * Description:
 * YoutubeApi
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeApi : BaseApi() {

    init {
        baseUrl = "http://54.153.41.63:8080/"
        isIgnoreJudge = true
        isShowProgress = false
    }

    // 分类，理论上可以传任何参数，因为本质上后台是走的YouTube搜索Api
    var category: String? = null
    // 页数token，可不传，不传时默认为第一页，retrofit不允许path传null，所以设定为""
    var pageToken: String = ""
    // 列表视频个数，默认即可
    var limit = 5

    override fun getObservable(): Observable<*> {
        val apiService = retrofit.create(YouTubeApiService::class.java)
        return apiService.getVideo(category, pageToken, limit)
    }

    /**
     * 处理接口返回的数据
     */
    fun convert(result: String): List<YouTubeResult.ContentBean.ItemsBean>? {
        val youtubeResult = JSONObject.parseObject(result, YouTubeResult::class.java)
        // 更新下一页的pageToken
        pageToken = youtubeResult.content.next_page_token
        return youtubeResult.content.items
    }
}