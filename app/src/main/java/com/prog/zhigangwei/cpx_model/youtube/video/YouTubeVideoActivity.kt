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
                putString("videoId", videoId)
            })
        }
    }

    override fun layoutId() = R.layout.activity_you_tube_video

    override fun initData() {
    }

    override fun initView() {
        player_view.initialize("AIzaSyARmhayYZqhBUNlxpNhAqswyZjY6oMglyk", this)
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