package com.prog.zhigangwei.cpx_model.youtube.video

import android.content.Context
import android.os.Bundle
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.project.base.activity.BaseYouTubeActivity
import com.base.project.base.extension.jumpActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_you_tube_video.*

/**
 * Description:
 * 播放YouTube视频的页面
 * 需要在Manifest里面添加android:configChanges="orientation|screenSize|keyboardHidden"，否则旋转屏幕时会重新加载视频
 * 准备步骤：
 * 1.在google api控制台搜索YouTube Data API，并申请ApiKey：https://console.cloud.google.com/apis
 * 2.将申请到的Api key填入gradle.properties的you_tube_api_key
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeVideoActivity : BaseYouTubeActivity() {

    private val videoId by lazy { bundle.getString("videoId") }

    companion object {
        @JvmStatic
        fun start(context: Context, videoId: String) {
            context.jumpActivity(YouTubeVideoActivity::class.java, Bundle().apply {
                // YouTubePlayerView播放视频需要的videoId
                putString("videoId", videoId)
            })
        }
    }

    override fun layoutId() = R.layout.activity_you_tube_video

    override fun initData() {
    }

    override fun initView() {
        player_view.initialize(getString(R.string.you_tube_api_key), this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        LogUtils.d("init success, wasRestored:$wasRestored")
        if (wasRestored) return
        player?.cueVideo(videoId)
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
        LogUtils.d("error:$errorReason")
        ToastUtils.showShort(errorReason.toString())
    }
}