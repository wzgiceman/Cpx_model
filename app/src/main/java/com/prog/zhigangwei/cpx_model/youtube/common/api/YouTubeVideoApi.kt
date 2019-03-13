package com.prog.zhigangwei.cpx_model.youtube.common.api

import com.alibaba.fastjson.JSONObject
import com.base.library.rxRetrofit.api.BaseApi
import com.prog.zhigangwei.cpx_model.youtube.common.bean.YouTubeVideoResult
import io.reactivex.Observable

/**
 * Description:
 * Youtube视频Api
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeVideoApi : BaseApi() {

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
        return apiService.getVideos(category, pageToken, limit)
    }

    /**
     * 处理接口返回的数据
     */
    fun convert(result: String): YouTubeVideoResult? {
        return JSONObject.parseObject(result, YouTubeVideoResult::class.java)
    }
}