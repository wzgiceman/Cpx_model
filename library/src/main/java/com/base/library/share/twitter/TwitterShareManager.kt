package com.base.library.share.twitter

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import com.base.library.rxbus.RxBus
import com.base.library.rxbus.annotation.Subscribe
import com.base.library.rxbus.thread.EventThread
import com.base.library.share.common.constants.ShareConstants.Companion.TWITTER
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.common.util.ShareUtils
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import com.twitter.sdk.android.tweetcomposer.TweetUploadService

/**
 * Description:
 * Twitter分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {

    init {
        RxBus.get().register(this)
    }

    /**
     * 分享文字
     * @param text 文字内容
     */
    fun shareText(text: String) {
        //分享卡片参考：https://developer.twitter.com/en/docs/tweets/optimize-with-cards/overview/player-card
        shareImage(text = text)
    }

    /**
     * 分享本地图片
     * @param image 图片Bitmap或者Uri
     * @param text 文字内容
     */
    fun shareImage(image: Any? = Uri.EMPTY, text: String = "") {
        if (!checkSession()) return
        val imageUri = when (image) {
            is Bitmap -> ShareUtils.bitmap2Uri(image)
            is Uri -> image
            else -> Uri.EMPTY
        }
        activity.startActivity(ComposerActivity.Builder(activity)
                .image(imageUri)
                .text(text)
                .session(TwitterCore.getInstance().sessionManager.activeSession)
                .createIntent())
    }

    private fun checkSession(): Boolean {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        return if (session == null) {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, need Login by Twitter first")
            false
        } else true
    }


    @Suppress("unused")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun twitterShareReceiver(action: String) {
        ShareUtils.clearShareTempPictures()
        when (action) {
            TweetUploadService.UPLOAD_SUCCESS -> onShareListener.onShareSuccess(TWITTER)
            TweetUploadService.TWEET_COMPOSE_CANCEL -> onShareListener.onShareFail(TWITTER, "Twitter share cancel")
            else -> onShareListener.onShareFail(TWITTER, "Twitter share fail, please check if the content is duplicate")
        }
    }

    fun release() {
        RxBus.get().unregister(this)
    }
}