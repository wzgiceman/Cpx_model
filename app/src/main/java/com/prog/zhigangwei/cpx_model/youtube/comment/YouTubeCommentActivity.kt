package com.prog.zhigangwei.cpx_model.youtube.comment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.listener.HttpOnNextListener
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.project.base.activity.BaseActivity
import com.base.project.base.extension.jumpActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.youtube.comment.childComment.YouTubeChildCommentActivity
import com.prog.zhigangwei.cpx_model.youtube.comment.comment.YouTubeCommentAdapter
import com.prog.zhigangwei.cpx_model.youtube.common.api.YouTubeCommentApi
import kotlinx.android.synthetic.main.activity_you_tube_comment.*

/**
 * Description:
 * YouTube评论页
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/13
 */
class YouTubeCommentActivity : BaseActivity(), HttpOnNextListener, RecyclerArrayAdapter.OnMoreListener {

    private val videoId by lazy { bundle.getString("videoId") }
    private val httpManager by lazy { HttpManager(this, this) }
    private val youTubeCommentApi by lazy { YouTubeCommentApi() }
    private val youTubeCommentAdapter by lazy { YouTubeCommentAdapter(this) }

    companion object {
        @JvmStatic
        fun start(context: Context, videoId: String) {
            context.jumpActivity(YouTubeCommentActivity::class.java, Bundle().apply {
                // YouTubePlayerView播放视频需要的videoId
                putString("videoId", videoId)
            })
        }
    }

    override fun layoutId() = R.layout.activity_you_tube_comment

    override fun initData() {
        rv.setRefreshing(true)
        youTubeCommentApi.videoId = videoId
        httpManager.doHttpDeal(youTubeCommentApi)
    }

    override fun initView() {
        rv.setLayoutManager(LinearLayoutManager(this))
        rv.adapter = youTubeCommentAdapter
        youTubeCommentAdapter.setOnItemClickListener {
            YouTubeChildCommentActivity.start(this@YouTubeCommentActivity, youTubeCommentAdapter.allData[it].id)
        }
        rv.setRefreshAndRetryListener {
            youTubeCommentApi.pageToken = ""
            httpManager.doHttpDeal(youTubeCommentApi)
        }
        youTubeCommentAdapter.setMore(R.layout.view_load_more, this)
    }

    override fun onMoreShow() {
        if (youTubeCommentApi.pageToken.isEmpty()) {
            youTubeCommentAdapter.pauseMore()
            return
        }
        httpManager.doHttpDeal(youTubeCommentApi)
    }

    override fun onMoreClick() {
    }

    override fun onNext(result: String, method: String) {
        LogUtils.d("method:$method\nresult:$result")
        rv.setRefreshing(false)
        val youtubeResult = youTubeCommentApi.convert(result)
        val comments = youtubeResult?.content?.comments
        if (TextUtils.isEmpty(youTubeCommentApi.pageToken)) {
            youTubeCommentAdapter.clear()
        }
        if (TextUtils.isEmpty(youTubeCommentApi.pageToken) && (comments == null || comments.isEmpty())) {
            rv.showEmpty()
            return
        }
        youTubeCommentAdapter.addAll(comments)
        // 更新下一页的pageToken
        youTubeCommentApi.pageToken = if (youtubeResult?.content?.next_page_token == null) "" else youtubeResult.content.next_page_token
    }

    override fun onError(e: ApiException, method: String) {
        LogUtils.d("error:${e.message}\nmethod:$method")
        rv.setRefreshing(false)
        rv.showError()
        ToastUtils.showShort(e.displayMessage)
    }
}
