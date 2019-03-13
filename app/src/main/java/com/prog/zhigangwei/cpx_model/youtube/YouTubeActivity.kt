package com.prog.zhigangwei.cpx_model.youtube

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.listener.HttpOnNextListener
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.project.base.activity.BaseActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.youtube.comment.YouTubeCommentActivity
import com.prog.zhigangwei.cpx_model.youtube.common.api.YouTubeVideoApi
import com.prog.zhigangwei.cpx_model.youtube.video.YouTubeVideoActivity
import com.prog.zhigangwei.cpx_model.youtube.youtube.YouTubeAdapter
import kotlinx.android.synthetic.main.activity_you_tube.*

/**
 * Description:
 * Youtube视频列表页面
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/11
 */
class YouTubeActivity : BaseActivity(), HttpOnNextListener, RecyclerArrayAdapter.OnMoreListener {

    private val httpManager by lazy { HttpManager(this, this) }
    private val youTubeVideoApi by lazy { YouTubeVideoApi() }
    private val youTubeAdapter by lazy { YouTubeAdapter(this) }

    override fun layoutId() = R.layout.activity_you_tube

    override fun initData() {
        rv.setRefreshing(true)
        youTubeVideoApi.category = "funny"
        httpManager.doHttpDeal(youTubeVideoApi)
    }

    override fun initView() {
        rv.setLayoutManager(LinearLayoutManager(this))
        rv.adapter = youTubeAdapter
        youTubeAdapter.setOnItemClickListener {
            YouTubeVideoActivity.start(this@YouTubeActivity, youTubeAdapter.allData[it].video_id)
        }
        youTubeAdapter.setOnItemChildClickListener { position, id ->
            YouTubeCommentActivity.start(this@YouTubeActivity, youTubeAdapter.allData[position].video_id)
        }
        rv.setRefreshAndRetryListener {
            youTubeVideoApi.pageToken = ""
            httpManager.doHttpDeal(youTubeVideoApi)
        }
        youTubeAdapter.setMore(R.layout.view_load_more, this)
    }

    override fun onMoreShow() {
        if (youTubeVideoApi.pageToken.isEmpty()) {
            youTubeAdapter.pauseMore()
            return
        }
        httpManager.doHttpDeal(youTubeVideoApi)
    }

    override fun onMoreClick() {
    }

    override fun onNext(result: String, method: String) {
        LogUtils.d("method:$method\nresult:$result")
        rv.setRefreshing(false)
        val youtubeResult = youTubeVideoApi.convert(result)
        val videos = youtubeResult?.content?.items
        if (TextUtils.isEmpty(youTubeVideoApi.pageToken)) {
            youTubeAdapter.clear()
        }
        if (TextUtils.isEmpty(youTubeVideoApi.pageToken) && (videos == null || videos.isEmpty())) {
            rv.showEmpty()
            return
        }
        youTubeAdapter.addAll(videos)
        // 更新下一页的pageToken
        youTubeVideoApi.pageToken = if (youtubeResult?.content?.next_page_token == null) "" else youtubeResult.content.next_page_token
    }

    override fun onError(e: ApiException, method: String) {
        LogUtils.d("error:${e.displayMessage}\nmethod:$method")
        rv.setRefreshing(false)
        rv.showError()
        ToastUtils.showShort(e.displayMessage)
    }
}
