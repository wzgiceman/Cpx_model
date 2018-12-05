package com.base.muslim.share.twitter

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.base.library.rxPermissions.RxPermissions
import com.base.library.rxbus.RxBus
import com.base.library.rxbus.annotation.Subscribe
import com.base.library.rxbus.thread.EventThread
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.muslim.share.common.constants.ShareConstants.Companion.TWITTER
import com.base.muslim.share.common.listener.OnShareListener
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
        if (!checkSession()) return
        activity.startActivity(ComposerActivity.Builder(activity)
                .text(text)
                .session(TwitterCore.getInstance().sessionManager.activeSession)
                .createIntent())

    }

    /**
     * 分享图片
     * @param image 图片Bitmap
     * @param tag 文字内容
     */
    @Suppress("checkResult")
    fun shareImage(image: Bitmap, tag: String) {
        if (!checkSession()) return
        RxPermissions.getInstance(activity)
                .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        activity.startActivity(ComposerActivity.Builder(activity)
                                .image(bitmap2Uri(image))
                                .hashtags(tag)
                                .session(TwitterCore.getInstance().sessionManager.activeSession)
                                .createIntent())
                    } else {
                        ToastUtils.showShort("No Permission")
                    }
                }
    }

    private fun checkSession(): Boolean {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        return if (session == null) {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, need Login by Twitter first")
            false
        } else true
    }

    private fun bitmap2Uri(image: Bitmap) =
            Uri.parse(MediaStore.Images.Media.insertImage(activity.contentResolver, image, null, null))

    @Suppress("unused")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun twitterShareReceiver(action: String) {
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