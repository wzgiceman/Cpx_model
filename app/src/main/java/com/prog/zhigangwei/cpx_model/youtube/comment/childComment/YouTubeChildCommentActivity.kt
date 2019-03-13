package com.prog.zhigangwei.cpx_model.youtube.comment.childComment

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
import com.prog.zhigangwei.cpx_model.youtube.comment.childComment.childComment.YouTubeChildCommentAdapter
import com.prog.zhigangwei.cpx_model.youtube.common.api.YouTubeChildCommentApi
import kotlinx.android.synthetic.main.activity_you_tube_child_comment.*

/**
 * Description:
 * YouTube评论页
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/13
 */
class YouTubeChildCommentActivity : BaseActivity(), HttpOnNextListener, RecyclerArrayAdapter.OnMoreListener {

    private val parentId by lazy { bundle.getString("parentId") }
    private val httpManager by lazy { HttpManager(this, this) }
    private val youTubeChildCommentApi by lazy { YouTubeChildCommentApi() }
    private val youTubeChildCommentAdapter by lazy { YouTubeChildCommentAdapter(this) }

    companion object {
        @JvmStatic
        fun start(context: Context, videoId: String) {
            context.jumpActivity(YouTubeChildCommentActivity::class.java, Bundle().apply {
                // YouTubePlayerView播放视频需要的videoId
                putString("parentId", videoId)
            })
        }
    }

    override fun layoutId() = R.layout.activity_you_tube_child_comment

    override fun initData() {
        rv.setRefreshing(true)
        youTubeChildCommentApi.parentId = parentId
        httpManager.doHttpDeal(youTubeChildCommentApi)
    }

    override fun initView() {
        rv.setLayoutManager(LinearLayoutManager(this))
        rv.adapter = youTubeChildCommentAdapter
        rv.setRefreshAndRetryListener {
            youTubeChildCommentApi.pageToken = ""
            httpManager.doHttpDeal(youTubeChildCommentApi)
        }
        youTubeChildCommentAdapter.setMore(R.layout.view_load_more, this)
    }

    override fun onMoreShow() {
        if (youTubeChildCommentApi.pageToken.isEmpty()) {
            youTubeChildCommentAdapter.pauseMore()
            return
        }
        httpManager.doHttpDeal(youTubeChildCommentApi)
    }

    override fun onMoreClick() {
    }

    override fun onNext(result: String, method: String) {
        LogUtils.d("method:$method\nresult:$result")
        rv.setRefreshing(false)
        val youtubeResult = youTubeChildCommentApi.convert(result)
        val comments = youtubeResult?.content?.comments
        if (TextUtils.isEmpty(youTubeChildCommentApi.pageToken)) {
            youTubeChildCommentAdapter.clear()
        }
        if (TextUtils.isEmpty(youTubeChildCommentApi.pageToken) && (comments == null || comments.isEmpty())) {
            rv.showEmpty()
            return
        }
        youTubeChildCommentAdapter.addAll(comments)
        // 更新下一页的pageToken
        youTubeChildCommentApi.pageToken = if (youtubeResult?.content?.next_page_token == null) "" else youtubeResult.content.next_page_token
    }

    override fun onError(e: ApiException, method: String) {
        LogUtils.d("error:${e.displayMessage}\nmethod:$method")
        rv.setRefreshing(false)
        rv.showError()
        ToastUtils.showShort(e.displayMessage)
    }
}
