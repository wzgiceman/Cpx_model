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
import com.prog.zhigangwei.cpx_model.youtube.common.api.YouTubeApi
import com.prog.zhigangwei.cpx_model.youtube.video.YouTubeVideoActivity
import com.prog.zhigangwei.cpx_model.youtube.youTube.YouTubeAdapter
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
    private val youTubeApi by lazy { YouTubeApi() }
    private val youTubeAdapter by lazy { YouTubeAdapter(this) }

    override fun layoutId() = R.layout.activity_you_tube

    override fun initData() {
        rv.setRefreshing(true)
        youTubeApi.category = "funny"
        httpManager.doHttpDeal(youTubeApi)
    }

    override fun initView() {
        rv.setLayoutManager(LinearLayoutManager(this))
        rv.adapter = youTubeAdapter
        youTubeAdapter.setOnItemClickListener {
            YouTubeVideoActivity.start(this@YouTubeActivity, youTubeAdapter.allData[it].video_id)
        }
        youTubeAdapter.setMore(R.layout.view_load_more, this)
        rv.setRefreshAndRetryListener {
            youTubeApi.pageToken = ""
            httpManager.doHttpDeal(youTubeApi)
        }
    }

    override fun onMoreShow() {
        httpManager.doHttpDeal(youTubeApi)
    }

    override fun onMoreClick() {
    }

    override fun onNext(result: String, method: String) {
        LogUtils.d("method:$method\nresult:$result")
        rv.setRefreshing(false)
        val videos = youTubeApi.convert(result)
        if (TextUtils.isEmpty(youTubeApi.pageToken)) {
            youTubeAdapter.clear()
        }
        if (TextUtils.isEmpty(youTubeApi.pageToken) && (videos == null || videos.isEmpty())) {
            rv.showEmpty()
            return
        }
        youTubeAdapter.addAll(videos)
    }

    override fun onError(e: ApiException, method: String) {
        LogUtils.d("error:${e.displayMessage}\nmethod:$method")
        rv.setRefreshing(false)
        rv.showError()
        ToastUtils.showShort(e.displayMessage)
    }
}
