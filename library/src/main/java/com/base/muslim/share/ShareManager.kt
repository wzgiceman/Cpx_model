package com.base.muslim.share

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.muslim.share.common.listener.OnShareListener
import com.base.muslim.share.facebook.FacebookShareManager
import com.base.muslim.share.twitter.TwitterShareManager


/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareManager(activity: Activity, val onShareListener: OnShareListener) {
    private val facebookShareManager by lazy { FacebookShareManager(activity, onShareListener) }
    private val twitterShareManager by lazy { TwitterShareManager(activity, onShareListener) }
    fun shareLink(type: String, link: String, tag: String, quote: String) {
        when (type) {
            FACEBOOK -> facebookShareManager.shareLink(link, tag, quote)
            TWITTER -> {
                if (!TextUtils.isEmpty(quote)) {
                    ToastUtils.showShort("Twitter share link does not support quote yet")
                }
                twitterShareManager.shareLink(link, tag)
            }

        }
    }

    fun shareImage(type: String, image: Bitmap, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareImage(image, tag)
            TWITTER -> twitterShareManager.shareImage(image, tag)
        }
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareVideo(videoUri, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support video yet")
        }
    }

    fun shareMedia(type: String, imageList: List<Bitmap>, videoUriList: List<Uri>, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareMedia(imageList, videoUriList, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support media yet")
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager.handleActivityResult(requestCode, resultCode, data)
    }

    fun release() {
        twitterShareManager.release()
    }
}